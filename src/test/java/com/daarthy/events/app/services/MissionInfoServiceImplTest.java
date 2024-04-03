package com.daarthy.events.app.services;

import com.daarthy.events.app.modules.guilds.Guild;
import com.daarthy.events.app.modules.guilds.Penalty;
import com.daarthy.events.persistence.SqlConnections;
import com.daarthy.events.persistence.event_dao.EventDao;
import com.daarthy.events.persistence.event_dao.EventJdbc;
import com.daarthy.events.persistence.guild_dao.GuildDao;
import com.daarthy.events.persistence.guild_dao.GuildJdbc;
import com.daarthy.events.persistence.mission_dao.*;
import com.daarthy.events.persistence.player_dao.PlayerDao;
import com.daarthy.events.persistence.player_dao.PlayerData;
import com.daarthy.events.persistence.player_dao.PlayerJdbc;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;

public class MissionInfoServiceImplTest {


    private static final Long GUILD_ID = 4L;
    private static final String K_NAME = "name";

    private HikariDataSource dataSource = SqlConnections.getInstance().getDataSource();

    private PlayerDao playerDao = new PlayerJdbc();
    private MissionDao missionDao = new MissionJdbc();
    private GuildDao guildDao = new GuildJdbc();

    private DataService dataService = new DataServiceImpl(dataSource, playerDao, guildDao);

    private MissionInfoService missionInfoService = new MissionInfoServiceImpl(missionDao, dataSource);
    private MissionFunctionalService missionFunctionalService = new MissionFunctionalServiceImpl(dataSource, missionDao);


    public MissionInfoServiceImplTest() throws IOException {}


    @Test
    public void testMissionServiceByPenalty() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);

        dataService.createGuild(playerId, GUILD_ID, K_NAME);

        Penalty penalty = missionFunctionalService.initPlayer(playerId);

        assertTrue(penalty.getPenalties().isEmpty());
    }

    @Test
    public void testMissionServiceByGuildDashBoard() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);

        dataService.createGuild(playerId, GUILD_ID, K_NAME);
        Guild guild = dataService.getGuild(GUILD_ID);

        missionFunctionalService.initPlayer(playerId);

        missionFunctionalService.fillGuildDashBoard(GUILD_ID, guild);

        Map<MissionData, List<ObjectiveData>> missionDataListMap = missionInfoService.findGuildDashBoard(GUILD_ID);

        assertEquals(4, missionDataListMap.size());
        dataService.deleteGuild(GUILD_ID);
    }

    @Test
    public void testMissionServiceByGuildDashBoardNoUpdate() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);

        dataService.createGuild(playerId, GUILD_ID, K_NAME);
        Guild guild = dataService.getGuild(GUILD_ID);

        missionFunctionalService.initPlayer(playerId);

        missionFunctionalService.fillGuildDashBoard(GUILD_ID, guild);

        Map<MissionData, List<ObjectiveData>> missionDataListMap = missionInfoService.findGuildDashBoard(GUILD_ID);

        missionFunctionalService.fillGuildDashBoard(GUILD_ID, guild);

        Map<MissionData, List<ObjectiveData>> missionDataListMap2 = missionInfoService.findGuildDashBoard(GUILD_ID);

        assertEquals(missionDataListMap.size(), missionDataListMap2.size());

        dataService.deleteGuild(GUILD_ID);
    }

    @Test
    public void testMissionServiceByPlayerCompletions() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);

        dataService.createGuild(playerId, GUILD_ID, K_NAME);
        Guild guild = dataService.getGuild(GUILD_ID);
        PlayerData playerData = dataService.getPlayerData(playerId);

        missionFunctionalService.initPlayer(playerId);
        missionFunctionalService.fillGuildDashBoard(GUILD_ID, guild);

        Map<MissionData, List<ObjectiveData>> missionDataListMap = missionInfoService.findGuildDashBoard(GUILD_ID);

        MissionData missionData = missionDataListMap.keySet().stream().findAny().orElse(null);

        StringBuilder result = missionFunctionalService.joinMission(playerId, playerData, guild, missionData.getMissionId());

        Map<String, CompletionData> completions = missionInfoService.getPlayerRates(playerId);

        assertEquals("Mission Accepted", result.toString());
        assertEquals(1, completions.size());

        dataService.deleteGuild(GUILD_ID);
    }


}
