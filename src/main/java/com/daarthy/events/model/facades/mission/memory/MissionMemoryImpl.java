package com.daarthy.events.model.facades.mission.memory;

import com.daarthy.events.Events;
import com.daarthy.events.model.facades.mission.structure.ExtendedObjective;
import com.daarthy.events.model.facades.mission.structure.PlayerTracker;
import com.daarthy.events.model.facades.mission.structure.PlayerTrackerImpl;
import com.daarthy.events.persistence.PersistenceContext;
import com.daarthy.events.persistence.daos.mission.entities.Mission;
import com.daarthy.events.persistence.daos.objective.entities.Objective;
import com.daarthy.events.persistence.daos.objective.entities.ObjectiveProgress;
import com.daarthy.events.persistence.daos.objective.entities.ObjectiveProgressKey;
import com.daarthy.mini.shared.classes.processor.MiniProcessor;
import com.daarthy.mini.shared.criteria.FestivalSelector;
import com.daarthy.mini.shared.criteria.MiniCriteria;
import com.daarthy.mini.shared.criteria.PostgresCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class MissionMemoryImpl extends MiniProcessor implements MissionMemory {
    private static final AtomicReference<MissionMemoryImpl> instance = new AtomicReference<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(MissionMemoryImpl.class);
    private static final int EXPECTED_POOL = 1;

    // Watched + Viewers
    private final Map<ExtendedObjective, AtomicReference<Long>> observedObjectives = new ConcurrentHashMap<>();
    private final Map<UUID, PlayerTracker> playerObjectives = new ConcurrentHashMap<>();
    private final PersistenceContext persistenceContext;

    private MissionMemoryImpl(long loopTime, TimeUnit timeUnit, PersistenceContext persistenceContext) {
        super(loopTime, timeUnit, EXPECTED_POOL);
        this.persistenceContext = persistenceContext;
    }

    // Memory items must be Singleton's
    public static MissionMemoryImpl getInstance(long loopTime, TimeUnit timeUnit,
                                                PersistenceContext persistenceContext) {
        return instance.updateAndGet(existingInstance ->
                existingInstance != null ? existingInstance : new MissionMemoryImpl(loopTime, timeUnit,
                        persistenceContext)
        );
    }

    @Override
    protected void sync() {
        LOGGER.info(Events.MICRO_NAME + "- Starting MissionMemory sync of the MissionFacade");

        savePlayersProgress();
        cleanExpiredMissions();

        LOGGER.info(Events.MICRO_NAME + "- Done MissionMemory sync of the MissionFacade");
    }

    @Override
    public void savePlayersProgress() {
        persistenceContext.objectiveProgressDao().saveAll(
                playerObjectives.values().stream()
                        .flatMap(tracker -> tracker.getObjectiveTrack().values().stream())
                        .toList()
        );
    }

    @Override
    public void addObjective(Mission mission, Objective objective, UUID playerId) {
        ExtendedObjective cacheObjective = new ExtendedObjective(objective, mission.getExpiration());

        this.observedObjectives.compute(cacheObjective, (key, currentValue) -> {
            if (currentValue == null) {
                return new AtomicReference<>(1L);
            } else {
                currentValue.updateAndGet(value -> value + 1);
                return currentValue;
            }
        });

        PlayerTracker playerTracker = this.playerObjectives.computeIfAbsent(playerId, id -> new PlayerTrackerImpl());

        playerTracker.getObjectiveTrack().computeIfAbsent(cacheObjective, key ->
                persistenceContext.objectiveProgressDao().save(ObjectiveProgress.builder()
                        .key(ObjectiveProgressKey.builder()
                                .objectiveId(objective.getId())
                                .playerId(playerId)
                                .build())
                        .build()));
    }

    public void removeObjectiveFromCache(ExtendedObjective objective) {
        observedObjectives.computeIfPresent(objective, (key, count) -> {
            count.updateAndGet(value -> value > 0 ? value - 1 : 0);
            return count;
        });
    }

    @Override
    public Map<ExtendedObjective, AtomicReference<Long>> getObservedObjectives() {
        return observedObjectives;
    }

    @Override
    public Map<UUID, PlayerTracker> getPlayerObjectives() {
        return playerObjectives;
    }

    // *****************************************************
    // Internal Methods
    // *****************************************************
    private void cleanExpiredMissions() {

        // First clear the missions on cache
        playerObjectives.values().forEach(tracker ->
                tracker.getObjectiveTrack().keySet().removeIf(ExtendedObjective::isExpired)
        );

        observedObjectives.keySet().removeIf(ExtendedObjective::isExpired);

        // Secondly execute an update in BD
        MiniCriteria<Void> failureCriteria = PostgresCriteria.<Void>builder()
                .selector(FestivalSelector.FAIL_PAST_MISSIONS)
                .build();

        persistenceContext.searchDao().querySelectorExecutor(failureCriteria);
    }
}
