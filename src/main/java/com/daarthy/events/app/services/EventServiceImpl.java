package com.daarthy.events.app.services;

import com.daarthy.events.app.modules.events.Event;
import com.daarthy.events.app.modules.events.EventToken;
import com.daarthy.events.app.modules.guilds.Guild;
import com.daarthy.events.persistence.event_dao.EventDao;
import com.daarthy.events.persistence.mission_dao.ActionType;
import com.zaxxer.hikari.HikariDataSource;

import java.util.*;

public class EventServiceImpl implements EventService {

    private List<Event> events = new ArrayList<>();

    private final EventDao eventDao;
    private final HikariDataSource dataSource;

    public EventServiceImpl(EventDao eventDao, HikariDataSource dataSource) {
        this.eventDao = eventDao;
        this.dataSource = dataSource;
    }

    @Override
    public EventToken registerAction(UUID playerId, Guild guild, ActionType actionType) {
        return null;

    }

    @Override
    public List<StringBuilder> getActiveEvents() {
        return null;
    }

    @Override
    public StringBuilder getEventInfo(String eventName) {
        return null;
    }

    @Override
    public List<Long> removeInactiveEvents() {
        return null;
    }

    @Override
    public void setUpEvents() {

    }


    @Override
    public void initPlayer(UUID playerId) {

    }

    @Override
    public void removePlayer(UUID playerId) {

    }
}
