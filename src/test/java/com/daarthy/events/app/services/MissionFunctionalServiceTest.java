package com.daarthy.events.app.services;

import com.daarthy.events.app.modules.guilds.Guild;
import com.daarthy.events.app.modules.missions.Objective;
import com.daarthy.events.persistence.SqlConnections;
import com.daarthy.events.persistence.guild_dao.GuildDao;
import com.daarthy.events.persistence.guild_dao.GuildJdbc;
import com.daarthy.events.persistence.mission_dao.*;
import com.daarthy.events.persistence.player_dao.PlayerDao;
import com.daarthy.events.persistence.player_dao.PlayerData;
import com.daarthy.events.persistence.player_dao.PlayerJdbc;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class MissionFunctionalServiceTest {

    private HikariDataSource dataSource = SqlConnections.getInstance().getDataSource();
    private static final Long GUILD_ID = 2L;

    private GuildDao guildDao = new GuildJdbc();
    private PlayerDao playerDao = new PlayerJdbc();
    private MissionDao missionDao = new MissionJdbc();
    private DataService dataService = new DataServiceImpl(dataSource, playerDao, guildDao);

    private MissionFunctionalService missionFunctionalService = new MissionFunctionalServiceImpl(dataSource, missionDao);
    private MissionInfoService missionInfoService = new MissionInfoServiceImpl(missionDao, dataSource);

    public MissionFunctionalServiceTest() throws IOException {
    }

    private Guild fillGuildDashBoard() {
        Guild guild = dataService.getGuild(GUILD_ID);
        missionFunctionalService.fillGuildDashBoard(GUILD_ID, guild);
        return guild;
    }

    private PlayerData setUpPlayer(UUID playerId) {
        dataService.initPlayer(playerId);
        PlayerData playerData = dataService.getPlayerData(playerId);
        playerData.setGuildId(GUILD_ID);
        dataService.savePlayer(playerId);
        return playerData;
    }

    @After
    public void cleanUp() {
        dataService.deleteGuild(2L);
    }
    @Test
    public void testMissionServiceByPlayerJoinMission() {

        UUID playerId = UUID.randomUUID();
        UUID playerId2 = UUID.randomUUID();
        dataService.initPlayer(playerId);
        dataService.createGuild(playerId, GUILD_ID, "name");

        PlayerData playerData1 = dataService.getPlayerData(playerId);
        PlayerData playerData2 = setUpPlayer(playerId2);

        Guild guild = fillGuildDashBoard();

        missionFunctionalService.initPlayer(playerId);
        missionFunctionalService.initPlayer(playerId2);

        Map<MissionData, List<ObjectiveData>> missionDataListMap = missionInfoService.findGuildDashBoard(GUILD_ID);

        MissionData missionData = missionDataListMap.keySet().stream().findAny().orElse(null);

        missionFunctionalService.joinMission(playerId, playerData1, guild, missionData.getMissionId());
        missionFunctionalService.joinMission(playerId2, playerData2, guild, missionData.getMissionId());

        MissionFunctionalServiceImpl ms = (MissionFunctionalServiceImpl) missionFunctionalService;

        Objective ob = ms.getObjectives().values().stream().findFirst().get();
        assertTrue(ob.isObserved());

        missionFunctionalService.removePlayer(playerId2);
        missionFunctionalService.removePlayer(playerId);

        assertFalse(ob.isObserved());

        missionFunctionalService.removeNonWatchedObjectives();

        assertTrue(ms.getPlayersObjs().isEmpty());
        assertTrue(ms.getObjectives().isEmpty());

    }

    @Test
    public void testMissionServiceByPlayerJoinTooManyMissionToday() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);
        dataService.createGuild(playerId, GUILD_ID, "name");

        PlayerData playerData1 = dataService.getPlayerData(playerId);

        Guild guild = fillGuildDashBoard();

        missionFunctionalService.initPlayer(playerId);

        Map<MissionData, List<ObjectiveData>> missionDataListMap = missionInfoService.findGuildDashBoard(GUILD_ID);
        Set<MissionData> missionDataSet = missionDataListMap.keySet();
        Iterator<MissionData> iterator = missionDataSet.iterator();

        MissionData m1 = iterator.next();
        MissionData m2 = iterator.next();
        MissionData m3 = iterator.next();
        MissionData m4 = iterator.next();

        missionFunctionalService.joinMission(playerId, playerData1, guild, m1.getMissionId());
        missionFunctionalService.joinMission(playerId, playerData1, guild, m2.getMissionId());
        missionFunctionalService.joinMission(playerId, playerData1, guild, m3.getMissionId());
        StringBuilder result = missionFunctionalService.joinMission(playerId, playerData1, guild, m4.getMissionId());

        assertEquals(">> Max missions reached today.", result.toString());

    }

    @Test
    public void testMissionServiceByPlayerJoinTooManyMissionPlayers() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);
        dataService.createGuild(playerId, GUILD_ID, "name");

        dataService.getPlayerData(playerId);

        Guild guild = fillGuildDashBoard();

        missionFunctionalService.initPlayer(playerId);

        Map<MissionData, List<ObjectiveData>> missionDataListMap = missionInfoService.findGuildDashBoard(GUILD_ID);
        Set<MissionData> missionDataSet = missionDataListMap.keySet();
        Iterator<MissionData> iterator = missionDataSet.iterator();

        MissionData m1 = iterator.next();

        PlayerData p;
        UUID pId;
        for(int i = 0; i < m1.getMaxCompletions(); i++) {
            pId = UUID.randomUUID();
            p = setUpPlayer(pId);
            missionFunctionalService.initPlayer(pId);
            missionFunctionalService.joinMission(pId, p, guild, m1.getMissionId());
        }
        pId = UUID.randomUUID();
        p = setUpPlayer(pId);
        StringBuilder result = missionFunctionalService.joinMission(pId, p, guild, m1.getMissionId());

        assertEquals(">> Max Mission capacity reached", result.toString());

    }

    @Test
    public void testMissionServiceByPlayerAddProgress() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);
        dataService.createGuild(playerId, GUILD_ID, "name");

        PlayerData playerData = dataService.getPlayerData(playerId);

        Guild guild = fillGuildDashBoard();

        missionFunctionalService.initPlayer(playerId);

        Map<MissionData, List<ObjectiveData>> missionDataListMap = missionInfoService.findGuildDashBoard(GUILD_ID);
        Set<MissionData> missionDataSet = missionDataListMap.keySet();
        Iterator<MissionData> iterator = missionDataSet.iterator();

        MissionData m1 = iterator.next();
        missionFunctionalService.joinMission(playerId, playerData, guild, m1.getMissionId());

        MissionFunctionalServiceImpl ms = (MissionFunctionalServiceImpl) missionFunctionalService;

        Objective ob = ms.getObjectives().values().stream().findFirst().get();

        int objSize = ms.getObjectives().size();
        int playerProgress = ms.getPlayersObjs().get(playerId).getProgress().size();
        int playersObjs = ms.getPlayersObjs().get(playerId).getObjectives().size();

        List<Grade> grades = new ArrayList<>();
        for(int i = 0; i < ob.getReqAmount(); i++) {
            grades.addAll(missionFunctionalService.addProgress(playerId, ob.getTarget(), ob.getLevel()));
        }

        missionFunctionalService.removeNonWatchedObjectives();
        int afterSize = ms.getObjectives().size();
        int afterPlayerProgress = ms.getPlayersObjs().get(playerId).getProgress().size();
        int afterPlayersObjs = ms.getPlayersObjs().get(playerId).getObjectives().size();

        assertEquals(afterSize, objSize - 1);
        assertEquals(afterPlayerProgress, playerProgress -1);
        assertEquals(afterPlayersObjs, playersObjs -1);
        assertEquals(1, grades.size());

    }

    @Test
    public void testMissionServiceByPlayerDashBoard() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);
        dataService.createGuild(playerId, GUILD_ID, "name");

        PlayerData playerData = dataService.getPlayerData(playerId);

        Guild guild = fillGuildDashBoard();

        missionFunctionalService.initPlayer(playerId);

        Map<MissionData, List<ObjectiveData>> missionDataListMap = missionInfoService.findGuildDashBoard(GUILD_ID);
        Set<MissionData> missionDataSet = missionDataListMap.keySet();
        Iterator<MissionData> iterator = missionDataSet.iterator();

        MissionData m1 = iterator.next();
        missionFunctionalService.joinMission(playerId, playerData, guild, m1.getMissionId());

        MissionFunctionalServiceImpl ms = (MissionFunctionalServiceImpl) missionFunctionalService;

        Objective ob = ms.getObjectives().values().stream().findFirst().get();

        List<Grade> grades = new ArrayList<>();
        for(int i = 0; i < ob.getReqAmount() - 100; i++) {
            grades.addAll(missionFunctionalService.addProgress(playerId, ob.getTarget(), ob.getLevel()));
        }

        Map<MissionData, List<ObjectiveData>> dash = missionFunctionalService.findPlayerDashBoard(playerId);

        AtomicInteger value = new AtomicInteger();
        dash.values().forEach(item -> {
            for(ObjectiveData o : item) {
                if(ob.getReqAmount() == o.getReqAmount()) {
                    value.set(o.getAmount());
                }
            }
        });

        assertEquals(value.get(), ob.getReqAmount() -100);
        assertEquals(1, dash.size());


    }

}
