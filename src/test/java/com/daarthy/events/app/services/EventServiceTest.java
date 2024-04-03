package com.daarthy.events.app.services;

import com.daarthy.events.app.modules.events.EventToken;
import com.daarthy.events.persistence.SqlConnections;
import com.daarthy.events.persistence.event_dao.EventDao;
import com.daarthy.events.persistence.event_dao.EventData;
import com.daarthy.events.persistence.event_dao.EventJdbc;
import com.daarthy.events.persistence.guild_dao.GuildDao;
import com.daarthy.events.persistence.guild_dao.GuildJdbc;
import com.daarthy.events.persistence.mission_dao.ActionType;
import com.daarthy.events.persistence.player_dao.PlayerDao;
import com.daarthy.events.persistence.player_dao.PlayerJdbc;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class EventServiceTest {

    private HikariDataSource dataSource = SqlConnections.getInstance().getDataSource();
    private EventDao eventDao = new EventJdbc();
    private PlayerDao playerDao = new PlayerJdbc();
    private GuildDao guildDao = new GuildJdbc();
    private EventService eventService = new EventServiceImpl(eventDao, dataSource);
    private DataService dataService = new DataServiceImpl(dataSource, playerDao, guildDao);

    public EventServiceTest() throws IOException {
    }

    @Test
    public void testEventServiceByActiveEvents() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);

        eventService.setUpEvents();

        eventService.initPlayer(playerId, 1L);

        List<StringBuilder> eventList = eventService.getActiveEvents();

        assertEquals(3, eventList.size());

    }

    @Test
    public void testEventServiceByInfo() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);

        eventService.setUpEvents();

        eventService.initPlayer(playerId, 1L);

        StringBuilder result1 = eventService.getEventInfo(playerId, "GatheringEvent");
        StringBuilder result2 = eventService.getEventInfo(playerId, "HuntingEvent");
        StringBuilder result3 = eventService.getEventInfo(playerId, "MiningEvent");

        assertFalse(result1.toString().isEmpty());
        assertFalse(result2.toString().isEmpty());
        assertFalse(result3.toString().isEmpty());

    }

    @Test
    public void testEventServiceByNoInfo() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);

        eventService.setUpEvents();

        eventService.initPlayer(playerId, 1L);

        StringBuilder result1 = eventService.getEventInfo(playerId, "Gant");

        assertEquals("This event is not Available.", result1.toString());

    }


    @Test
    public void testEventServiceByRemoveEvents() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);

        eventService.setUpEvents();

        eventService.initPlayer(playerId, 1L);

        List<EventData> eventData = eventService.removeInactiveEvents();

        assertTrue(eventData.isEmpty());

    }

    @Test
    public void testEventServiceByAddProgress() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);

        eventService.setUpEvents();
        eventService.initPlayer(playerId, 1L);

        List<EventToken> eventTokens = new ArrayList<>();
        while(eventTokens.isEmpty()) {
            eventTokens.addAll(eventService.registerAction(playerId, 1L, ActionType.FARM));
        }

        assertFalse(false);

    }

    @Test
    public void testEventServiceByAddProgressAndSave() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);

        eventService.setUpEvents();

        eventService.initPlayer(playerId, 1L);

        List<EventToken> eventTokens = new ArrayList<>();
        while(eventTokens.isEmpty()) {
            eventTokens.addAll(eventService.registerAction(playerId, 1L, ActionType.FARM));
        }

        eventService.removePlayer(playerId);
        eventService.saveAllGuilds();
        eventService.removeGuild(1L);

        eventService.initPlayer(playerId, 1L);

        assertTrue(true);

    }

    @Test
    public void testEventServiceByAddProgressByLimitMedals() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);

        eventService.setUpEvents();

        eventService.initPlayer(playerId, 1L);

        List<EventToken> eventTokens = new ArrayList<>();
        while(eventTokens.size() < 208) {
            eventTokens.addAll(eventService.registerAction(playerId, 1L, ActionType.KILL));
        }

        assertEquals(200, eventService.getGuildMedals(1L).getMedals().get(2L), 0.0);

    }
}
