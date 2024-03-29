package com.daarthy.events.app.services;

import com.daarthy.events.app.modules.guilds.GuildCache;
import com.daarthy.events.persistence.SqlConnectionsTest;
import com.daarthy.events.persistence.guildDao.GuildDao;
import com.daarthy.events.persistence.guildDao.GuildJdbc;
import com.daarthy.events.persistence.missionDao.*;
import com.daarthy.events.persistence.playerDao.PlayerDao;
import com.daarthy.events.persistence.playerDao.PlayerData;
import com.daarthy.events.persistence.playerDao.PlayerJdbc;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class MissionServiceTest {

    private HikariDataSource dataSource = SqlConnectionsTest.getInstance().getDataSource();
    private static final Long guildId = 2L;

    private GuildDao guildDao = new GuildJdbc();
    private PlayerDao playerDao = new PlayerJdbc();
    private MissionDao missionDao = new MissionJdbc();
    private DataService dataService = new DataServiceImpl(dataSource, playerDao, guildDao);
    private MissionService missionService = new MissionServiceImpl(missionDao, dataSource);

    public MissionServiceTest() throws IOException {
    }

    @Test
    public void testMissionServiceByPlayer() {

        UUID playerId = UUID.randomUUID();

        dataService.getPlayerData(playerId);

        missionService.initPlayer(playerId);

        HashMap<MissionData, List<ObjectiveData>> map = missionService.findPlayerDashBoard(playerId);

        assertTrue(map.isEmpty());
    }

    @Test
    public void testMissionServiceByFillDashBoard() {

        UUID playerId = UUID.randomUUID();

        dataService.createGuild(guildId, "kName");
        PlayerData playerData = dataService.getPlayerData(playerId);
        playerData.setGuildId(guildId);
        dataService.savePlayer(playerId);

        GuildCache guildCache = dataService.getGuildByPlayer(playerId, playerData);

        missionService.fillGuildDashBoard(guildId, guildCache);

        HashMap<MissionData, List<ObjectiveData>> obs = missionService.findGuildDashBoard(guildId);

        dataService.deleteGuild(guildId);

        assertTrue(obs.size() == 4);
    }

    @Test
    public void testMissionServiceByJoinMission() {

        UUID playerId = UUID.randomUUID();

        dataService.createGuild(guildId, "kName");
        PlayerData playerData = dataService.getPlayerData(playerId);
        playerData.setGuildId(guildId);
        dataService.savePlayer(playerId);

        GuildCache guildCache = dataService.getGuildByPlayer(playerId, playerData);

        missionService.fillGuildDashBoard(guildId, guildCache);

        HashMap<MissionData, List<ObjectiveData>> obs = missionService.findGuildDashBoard(guildId);

        missionService.initPlayer(playerId);

        StringBuilder joinResult = missionService.joinMission(playerId, playerData, guildCache, obs.keySet().iterator().next());

        HashMap<MissionData, List<ObjectiveData>> result = missionService.findPlayerDashBoard(playerId);

        assertEquals(result.size(),  1);
        assertEquals(joinResult.toString() , "Mission Accepted");

        dataService.deleteGuild(guildId);

    }

    @Test
    public void testMissionServiceByAddProgressAndDashboard() {

        UUID playerId = UUID.randomUUID();

        dataService.createGuild(guildId, "kName");
        PlayerData playerData = dataService.getPlayerData(playerId);
        playerData.setGuildId(guildId);
        dataService.savePlayer(playerId);

        GuildCache guildCache = dataService.getGuildByPlayer(playerId, playerData);

        missionService.fillGuildDashBoard(guildId, guildCache);

        HashMap<MissionData, List<ObjectiveData>> obs = missionService.findGuildDashBoard(guildId);

        missionService.initPlayer(playerId);

        StringBuilder joinResult = missionService.joinMission(playerId, playerData, guildCache, obs.keySet().iterator().next());

        HashMap<MissionData, List<ObjectiveData>> dashboard = missionService.findPlayerDashBoard(playerId);

        dashboard.forEach((key, value) ->  missionService.addProgress(playerId, value.get(0).getTarget(), value.get(0).getLevels()));

        HashMap<MissionData, List<ObjectiveData>> newDashboard = missionService.findPlayerDashBoard(playerId);

        assertEquals(newDashboard.size(), 1);
        assertEquals(joinResult.toString() , "Mission Accepted");
        assertEquals(newDashboard.entrySet().iterator().next().getValue().get(0).getAmount(), 1, 0.0);

        dataService.deleteGuild(guildId);

    }

    @Test
    public void testMissionServiceByRates() {

        UUID playerId = UUID.randomUUID();

        dataService.createGuild(guildId, "kName");
        PlayerData playerData = dataService.getPlayerData(playerId);
        playerData.setGuildId(guildId);
        dataService.savePlayer(playerId);

        GuildCache guildCache = dataService.getGuildByPlayer(playerId, playerData);

        missionService.fillGuildDashBoard(guildId, guildCache);

        HashMap<MissionData, List<ObjectiveData>> obs = missionService.findGuildDashBoard(guildId);

        missionService.initPlayer(playerId);

        missionService.joinMission(playerId, playerData, guildCache, obs.keySet().iterator().next());

        HashMap<String, CompletionData> rates = missionService.getPlayerRates(playerId);

        assertEquals(rates.size(), 1);
        assertEquals(rates.entrySet().iterator().next().getValue().getPercentage(), 0.0, 0.0);

        dataService.deleteGuild(guildId);

    }

    @Test
    public void testMissionServiceByEmptyUpdatedMissions() {

        UUID playerId = UUID.randomUUID();

        dataService.createGuild(guildId, "kName");
        PlayerData playerData = dataService.getPlayerData(playerId);
        playerData.setGuildId(guildId);
        dataService.savePlayer(playerId);

        GuildCache guildCache = dataService.getGuildByPlayer(playerId, playerData);

        missionService.fillGuildDashBoard(guildId, guildCache);

        HashMap<MissionData, List<ObjectiveData>> obs = missionService.findGuildDashBoard(guildId);

        missionService.initPlayer(playerId);

        missionService.joinMission(playerId, playerData, guildCache, obs.keySet().iterator().next());

        missionService.findPlayerDashBoard(playerId);

        List<FailedMission> updated = missionService.updateFailedMissions(guildId);

        assertEquals(updated.size() , 0);
        dataService.deleteGuild(guildId);

    }

}
