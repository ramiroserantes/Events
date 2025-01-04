package com.daarthy.events.model.facades.event.memory;

import com.daarthy.events.Events;
import com.daarthy.events.model.facades.event.structure.EventAdapter;
import com.daarthy.events.model.facades.event.structure.ExtendedEvent;
import com.daarthy.events.persistence.PersistenceContext;
import com.daarthy.events.persistence.daos.event.entities.EventData;
import com.daarthy.events.persistence.daos.event.entities.GuildMedals;
import com.daarthy.events.persistence.daos.event.entities.GuildMedalsKey;
import com.daarthy.mini.shared.classes.enums.festivals.Scope;
import com.daarthy.mini.shared.classes.processor.MiniProcessor;
import com.daarthy.mini.shared.criteria.FestivalSelector;
import com.daarthy.mini.shared.criteria.MiniCriteria;
import com.daarthy.mini.shared.criteria.PostgresCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class EventMemoryImpl extends MiniProcessor implements EventMemory {
    private static final AtomicReference<EventMemoryImpl> instance = new AtomicReference<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(EventMemoryImpl.class);
    private static final int EXPECTED_POOL = 2;

    private final Map<GuildMedalsKey, GuildMedals> guildMedals = new ConcurrentHashMap<>();
    private final PersistenceContext persistenceContext;
    private Set<ExtendedEvent> events = new CopyOnWriteArraySet<>();

    private EventMemoryImpl(long loopTime, TimeUnit timeUnit, PersistenceContext persistenceContext) {
        super(loopTime, timeUnit, EXPECTED_POOL);
        this.persistenceContext = persistenceContext;
        this.scheduleAdditionalSync(this::syncEvents, calculateInitialDelayToMidnight(), TimeUnit.MILLISECONDS);
        uploadEvents();
    }

    // Memory items must be Singleton's
    public static EventMemoryImpl getInstance(long loopTime, TimeUnit timeUnit, PersistenceContext persistenceContext) {
        return instance.updateAndGet(existingInstance ->
                existingInstance != null ? existingInstance : new EventMemoryImpl(loopTime, timeUnit,
                        persistenceContext)
        );
    }

    @Override
    protected void sync() {
        LOGGER.info(Events.MICRO_NAME + "- Starting EventMemory sync of the EventFacade");

        savePlayersProgress();
        saveGuildMedals();

        LOGGER.info(Events.MICRO_NAME + "- Done EventMemory sync of the EventFacade");
    }

    @Override
    public void removeInactiveEvents() {
        List<Long> inactiveEventIds = events.stream()
                .filter(event -> event.getEventData().getEndDate().isBefore(
                        LocalDate.now().atStartOfDay().toLocalDate()))
                .map(event -> event.getEventData().getId())
                .toList();

        events.removeIf(event -> inactiveEventIds.contains(event.getEventData().getId()));
        guildMedals.keySet().removeIf(key -> inactiveEventIds.contains(
                key.getEventId()));
    }

    @Override
    public void savePlayersProgress() {
        events.forEach(e ->
                persistenceContext.playerContributionDao().saveAll(e.getPlayers().values())
        );
    }

    @Override
    public void saveGuildMedals() {
        persistenceContext.guildMedalsDao().saveAll(guildMedals.values());
    }

    @Override
    public void uploadEvents() {
        //TODO: For now, only ALL worldscopes allowed.
        MiniCriteria<EventData> dailyEventsCriteria = PostgresCriteria.<EventData>builder()
                .selector(FestivalSelector.FIND_CURRENT_EVENTS)
                .params(List.of(Scope.ALL))
                .resultClass(EventData.class)
                .build();

        List<EventData> dailyEvents = persistenceContext.searchDao().findByCriteria(dailyEventsCriteria);
        events = dailyEvents.stream().map(EventAdapter::new)
                .collect(Collectors.toCollection(CopyOnWriteArraySet::new));
    }

    @Override
    public Set<ExtendedEvent> getEvents() {
        return events;
    }

    @Override
    public Map<GuildMedalsKey, GuildMedals> getGuildMedals() {
        return guildMedals;
    }

    // *****************************************************
    // Internal Methods
    // *****************************************************
    private void syncEvents() {
        savePlayersProgress();
        saveGuildMedals();
        removeInactiveEvents();
        uploadEvents();
    }

    private long calculateInitialDelayToMidnight() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextMidnight = now.toLocalDate().atStartOfDay().plusDays(1);
        ZonedDateTime zonedNow = ZonedDateTime.now(ZoneId.systemDefault());
        ZonedDateTime zonedNextMidnight = nextMidnight.atZone(ZoneId.systemDefault());
        return zonedNextMidnight.toInstant().toEpochMilli() - zonedNow.toInstant().toEpochMilli();
    }
}
