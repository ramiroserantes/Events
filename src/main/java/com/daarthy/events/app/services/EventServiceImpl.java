package com.daarthy.events.app.services;

import com.daarthy.events.Events;
import com.daarthy.events.app.modules.events.Event;
import com.daarthy.events.app.modules.events.EventImpl;
import com.daarthy.events.app.modules.events.EventToken;
import com.daarthy.events.app.modules.guilds.EventMedals;
import com.daarthy.events.app.util.EventMessageReader;
import com.daarthy.events.persistence.event_dao.Contribution;
import com.daarthy.events.persistence.event_dao.EventDao;
import com.daarthy.events.persistence.event_dao.EventData;
import com.daarthy.events.persistence.event_dao.ScopeEnum;
import com.daarthy.events.persistence.mission_dao.ActionType;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

public class EventServiceImpl implements EventService {

    private final List<Event> events = new ArrayList<>();
    private final Map<Long, EventMedals> guildMedals = new HashMap<>();

    private final EventDao eventDao;
    private final HikariDataSource dataSource;

    public EventServiceImpl(EventDao eventDao, HikariDataSource dataSource) {
        this.eventDao = eventDao;
        this.dataSource = dataSource;
    }

    @Override
    public List<EventToken> registerAction(UUID playerId, Long guildId, ActionType actionType) {

        List<EventToken> eventTokens = new ArrayList<>();

        for(Event event : events) {
            Contribution contribution = event.applyPlan(playerId, actionType);
            if(contribution != null) {
                eventTokens.add(EventToken.fromString(event.getData().getName()));
                if(event.getData().getMaxMedals() >= event.getPlayers().get(playerId).getMedals()) {
                    guildMedals.get(guildId).addMedals(event.getData().getEventId());
                }
            }
        }

        return eventTokens;
    }

    @Override
    public List<StringBuilder> getActiveEvents() {

        List<StringBuilder> eventsInfo = new ArrayList<>();

        StringBuilder info;
        for(Event event : events) {
           info = new StringBuilder();
           info.append(">> ").append(event.getData().getName()).append(" -. Remaining Duration:  ");
           info.append(event.getData().getRemainingTime());
           eventsInfo.add(info);
        }

        return eventsInfo;
    }

    @Override
    public void saveAllGuilds() {

        guildMedals.forEach((guildId, data) -> saveGuild(guildId));

    }

    @Override
    public StringBuilder getEventInfo(UUID playerId, String eventName) {

        DecimalFormat decimalFormat = new DecimalFormat("#.######");
        StringBuilder result = new StringBuilder();
        for(Event e : events) {
            if(e.getData().getName().equalsIgnoreCase(eventName)) {
                Contribution contribution = e.getPlayers().get(playerId);
                result.append(EventMessageReader.getMessage(e.getData().getName(),
                        decimalFormat.format(e.getPlan().getCoefficient()),
                        decimalFormat.format(contribution.getItems()),
                        decimalFormat.format(e.getPlan().getProbability(contribution.getItems())),
                        decimalFormat.format(e.getPlan().getLimit())));
            }
        }

        if(result.toString().isEmpty()) {
            result.append("This event is not Available.");
        }

        return result;
    }

    @Override
    public List<EventData> removeInactiveEvents() {

        List<EventData> eventsRemoved = new ArrayList<>();

        Iterator<Event> iterator = events.iterator();
        while (iterator.hasNext()) {
            Event event = iterator.next();
            if (event.getData().getEndDate().isBefore(LocalDate.now().atStartOfDay().toLocalDate())) {
                iterator.remove();
                eventsRemoved.add(event.getData());
            }
        }

        return eventsRemoved;
    }

    @Override
    public EventMedals getGuildMedals(Long guildId) {
        return guildMedals.getOrDefault(guildId, null);
    }

    @Override
    public void setUpEvents() {

        try (Connection connection = dataSource.getConnection()) {

            List<EventData> eventsData = eventDao.findCurrentEvents(connection, ScopeEnum.ALL);

            for (EventData eventData : eventsData) {
                if (events.stream().noneMatch(e -> e.getData().equals(eventData))) {
                    events.add(new EventImpl(eventData));
                }
            }

        } catch (SQLException e) {
            Events.logInfo("setUpEvents error");
        }
    }


    @Override
    public void initPlayer(UUID playerId, Long guildId) {

        try (Connection connection = dataSource.getConnection()) {

            for(Event e : events) {
                Contribution contribution = eventDao.findPlayerContribution(connection, e.getData().getEventId(), playerId);
                if(contribution != null) {
                    e.addPlayer(playerId, contribution);
                } else {
                    e.addPlayer(playerId, new Contribution());
                }
            }

            EventMedals eventMedals = guildMedals.getOrDefault(guildId, null);

            if(eventMedals == null) {
                EventMedals medals = new EventMedals();
                for(Event e : events) {
                    medals.getMedals().put(e.getData().getEventId(), eventDao.findGuildMedals(
                            connection, guildId, e.getData().getEventId()));
                }
                guildMedals.put(guildId, medals);
            }


        } catch (SQLException e) {
            Events.logInfo("Error on Event init player");
        }
    }

    @Override
    public void removeGuild(Long guildId) {

        saveGuild(guildId);
        guildMedals.remove(guildId);
    }

    @Override
    public void saveGuild(Long guildId) {

        try (Connection connection = dataSource.getConnection()) {
            guildMedals.get(guildId).getMedals().forEach((eventId, medals) ->
                    eventDao.saveGuildMedals(connection, guildId, eventId, medals)
            );
        } catch (SQLException e) {
            Events.logInfo("Error on saveGuild");
        }
    }

    @Override
    public void savePlayer(UUID playerId) {

        try (Connection connection = dataSource.getConnection()) {

            events.forEach(e -> {
                if(e.getPlayers().containsKey(playerId)) {
                    eventDao.savePlayerContribution(connection, playerId, e.getData().getEventId(),
                            e.getPlayers().get(playerId), e.getData().getMaxMedals());
                }
            });

        } catch (SQLException e) {
            Events.logInfo("Error on savePlayer");
        }

    }


    @Override
    public void removePlayer(UUID playerId) {
        savePlayer(playerId);
        events.forEach(e -> e.removePlayer(playerId));
    }

}
