package com.daarthy.events.api;

import com.daarthy.events.app.modules.events.EventToken;
import com.daarthy.events.persistence.SqlConnections;
import com.daarthy.events.persistence.mission_dao.ActionType;
import com.daarthy.events.persistence.mission_dao.MissionData;
import com.daarthy.events.persistence.player_dao.PlayerData;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class ApiGatewayTest {

    private HikariDataSource dataSource = SqlConnections.getInstance().getDataSource();

    private ApiGateway getApi() {return new ApiGatewayImpl(dataSource);}

    public ApiGatewayTest() throws IOException {
    }

    @Test
    public void testApiByLogin() {

        UUID playerId = UUID.randomUUID();
        ApiGateway api = getApi();
        api.getContainer().getEventService().setUpEvents();

        api.logInRequest(playerId);

        assertTrue(true);
    }

    @Test
    public void testApiByLogout() {

        UUID playerId = UUID.randomUUID();
        ApiGateway api = getApi();
        api.getContainer().getEventService().setUpEvents();

        api.logInRequest(playerId);
        api.logOutRequest(playerId);

        assertTrue(true);
    }

    @Test
    public void testApiByPlayerData() {

        UUID playerId = UUID.randomUUID();
        ApiGateway api = getApi();
        api.getContainer().getEventService().setUpEvents();

        api.logInRequest(playerId);

        assertNotNull(api.getPlayerDataRequest(playerId));
    }

    @Test
    public void testApiByGuildData() {

        UUID playerId = UUID.randomUUID();
        ApiGateway api = getApi();
        api.getContainer().getEventService().setUpEvents();

        api.logInRequest(playerId);


        assertNotNull(api.getGuildDataRequest(playerId));
    }

    @Test
    public void testApiByPlayerCreateAndDeleteGuildRequest() {

        UUID playerId = UUID.randomUUID();
        ApiGateway api = getApi();
        api.getContainer().getEventService().setUpEvents();

        api.logInRequest(playerId);

        api.createGuildRequest(playerId, 5L, "n");

        assertEquals(5L, api.getPlayerDataRequest(playerId).getGuildId(), 0.0);

        api.deleteGuildRequest(5L);

        assertEquals(1L, api.getPlayerDataRequest(playerId).getGuildId(),0.0);
    }

    @Test
    public void testApiByPlayerCreateAndSaveDataRequest() {

        UUID playerId = UUID.randomUUID();
        ApiGateway api = getApi();
        api.getContainer().getEventService().setUpEvents();

        api.logInRequest(playerId);

        PlayerData playerData = api.getPlayerDataRequest(playerId);
        playerData.setAmpBasicRewards(9.0F);
        api.savePlayerRequest(playerId);

        PlayerData expected = api.getPlayerDataRequest(playerId);
        assertEquals(9.0F, expected.getAmpBasicRewards(), 0.0);
    }


    @Test
    public void testApiByEventActivity() {

        UUID playerId = UUID.randomUUID();
        ApiGateway api = getApi();
        api.getContainer().getEventService().setUpEvents();

        api.logInRequest(playerId);

        api.createGuildRequest(playerId, 5L, "n");

        List<EventToken> eventTokens = new ArrayList<>();
        while(eventTokens.isEmpty()) {
            eventTokens.addAll(api.eventActivityRequest(playerId, ActionType.KILL));
        }

        assertTrue(true);

        api.deleteGuildRequest(5L);
    }

    @Test
    public void testApiByEventActivityEvents() {

        UUID playerId = UUID.randomUUID();
        ApiGateway api = getApi();
        api.getContainer().getEventService().setUpEvents();

        api.logInRequest(playerId);

        api.createGuildRequest(playerId, 5L, "n");

        assertFalse(api.getActiveEventsRequest().isEmpty());
        api.deleteGuildRequest(5L);
    }

    @Test
    public void testApiByEventInfoInvalid() {

        UUID playerId = UUID.randomUUID();
        ApiGateway api = getApi();
        api.getContainer().getEventService().setUpEvents();

        api.logInRequest(playerId);

        assertEquals("You cant participate in this event when you are in the Server guild.",
                api.getEventInfoRequest(playerId, "HuntingEvent").toString());
    }
    @Test
    public void testApiByEventInfo() {

        UUID playerId = UUID.randomUUID();
        ApiGateway api = getApi();
        api.getContainer().getEventService().setUpEvents();

        api.logInRequest(playerId);

        api.createGuildRequest(playerId, 5L, "n");

        assertFalse(api.getEventInfoRequest(playerId, "HuntingEvent").toString().isEmpty());

        api.deleteGuildRequest(5L);
    }

    @Test
    public void testApiByEventMedals() {

        UUID playerId = UUID.randomUUID();
        ApiGateway api = getApi();
        api.getContainer().getEventService().setUpEvents();

        api.logInRequest(playerId);

        api.createGuildRequest(playerId, 5L, "n");

        assertEquals(0, api.getGuildMedalsOnEventRequest(playerId, 1L));
        api.deleteGuildRequest(5L);
    }

    @Test
    public void testApiByMissionGuildDash() {

        UUID playerId = UUID.randomUUID();
        ApiGateway api = getApi();
        api.getContainer().getEventService().setUpEvents();

        api.logInRequest(playerId);

        api.createGuildRequest(playerId, 5L, "n");

        assertFalse(api.getGuildDashBoardRequest(playerId).isEmpty());
        api.deleteGuildRequest(5L);
    }

    @Test
    public void testApiByMissionJoin() {

        UUID playerId = UUID.randomUUID();
        ApiGateway api = getApi();
        api.getContainer().getEventService().setUpEvents();

        api.logInRequest(playerId);

        api.createGuildRequest(playerId, 5L, "n");

        MissionData m = api.getGuildDashBoardRequest(playerId).keySet().stream().findFirst().get();

        StringBuilder result = api.joinMissionRequest(playerId, m.getMissionId());

        assertEquals("Mission Accepted", result.toString());
        api.deleteGuildRequest(5L);
    }

    @Test
    public void testApiByPlayerDash() {

        UUID playerId = UUID.randomUUID();
        ApiGateway api = getApi();
        api.getContainer().getEventService().setUpEvents();

        api.logInRequest(playerId);

        api.createGuildRequest(playerId, 5L, "n");

        MissionData m = api.getGuildDashBoardRequest(playerId).keySet().stream().findFirst().get();

        StringBuilder result = api.joinMissionRequest(playerId, m.getMissionId());

        assertEquals(1, api.getPlayerDashBoardRequest(playerId).size());
        api.deleteGuildRequest(5L);
    }

    @Test
    public void testApiByCompletions() {

        UUID playerId = UUID.randomUUID();
        ApiGateway api = getApi();
        api.getContainer().getEventService().setUpEvents();

        api.logInRequest(playerId);

        api.createGuildRequest(playerId, 10L, "n");

        MissionData m = api.getGuildDashBoardRequest(playerId).keySet().stream().findFirst().get();

        api.joinMissionRequest(playerId, m.getMissionId());

        assertEquals(1, api.getPlayerRatesRequest(playerId).size());
        api.deleteGuildRequest(10L);
    }

}
