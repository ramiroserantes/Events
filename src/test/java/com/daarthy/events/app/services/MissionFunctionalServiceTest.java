package com.daarthy.events.app.services;

import com.daarthy.events.app.modules.guilds.Guild;
import com.daarthy.events.app.modules.guilds.Penalty;
import com.daarthy.events.app.modules.missions.Objective;
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
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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

    private void updateMissionTime(Long missionId) {

        String queryString = "UPDATE Missions m SET m.expiration = DATE_SUB(NOW(), INTERVAL 1 DAY) WHERE m.id = ?";

        try(Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(queryString);
            preparedStatement.setLong(1, missionId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

        List<Objective> ob = new ArrayList<>(ms.getObjectives().values());

        List<Grade> grades = new ArrayList<>();
        while (grades.isEmpty()) {
            for(Objective o : ob) {
                grades.addAll(missionFunctionalService.addProgress(playerId, o.getTarget(), o.getLevel()));
            }
        }

        missionFunctionalService.removeNonWatchedObjectives();
        int afterSize = ms.getObjectives().size();
        int afterPlayerProgress = ms.getPlayersObjs().get(playerId).getProgress().size();
        int afterPlayersObjs = ms.getPlayersObjs().get(playerId).getObjectives().size();

        assertEquals(0, afterSize);
        assertEquals(0, afterPlayerProgress);
        assertEquals(0, afterPlayersObjs);
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

    @Test
    public void testMissionServiceByJoinExpiredMission() {

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
        updateMissionTime(m1.getMissionId());
        StringBuilder result = missionFunctionalService.joinMission(playerId, playerData, guild, m1.getMissionId());

        assertEquals(">> This mission is expired.", result.toString());
    }
    @Test
    public void testMissionServiceByRemoveFailed() {

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
        updateMissionTime(m1.getMissionId());

        MissionFunctionalServiceImpl ms = (MissionFunctionalServiceImpl) missionFunctionalService;

        ms.getObjectives().forEach((key, value) -> value.setExpirationDate(LocalDate.now().minusDays(1L)));

        Penalty penalty = missionFunctionalService.removeFailedMissions(playerId);
        missionFunctionalService.removeNonWatchedObjectives();

        assertEquals(1, penalty.getPenalties().size());
        assertEquals(0, ms.getObjectives().size());
        assertEquals(0, ms.getPlayersObjs().get(playerId).getObjectives().size());
        assertEquals(0, ms.getPlayersObjs().get(playerId).getProgress().size());
    }

    @Test
    public void testMissionServiceByInitFailed() {

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
        updateMissionTime(m1.getMissionId());

        missionFunctionalService.removePlayer(playerId);
        missionFunctionalService.removeNonWatchedObjectives();

        MissionFunctionalServiceImpl ms = (MissionFunctionalServiceImpl) missionFunctionalService;

        Penalty penalty = missionFunctionalService.initPlayer(playerId);

        assertEquals(1, penalty.getPenalties().size());
        assertEquals(0, ms.getObjectives().size());
        assertEquals(0, ms.getPlayersObjs().get(playerId).getObjectives().size());
        assertEquals(0, ms.getPlayersObjs().get(playerId).getProgress().size());
    }

}
