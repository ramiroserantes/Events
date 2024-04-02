package com.daarthy.events.persistence.mission_dao;

import com.daarthy.events.persistence.SqlConnections;
import com.daarthy.events.persistence.guild_dao.GuildDao;
import com.daarthy.events.persistence.guild_dao.GuildJdbc;
import com.daarthy.events.persistence.player_dao.PlayerDao;
import com.daarthy.events.persistence.player_dao.PlayerJdbc;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class MissionDaoTest {

    private HikariDataSource dataSource = SqlConnections.getInstance().getDataSource();
    private PlayerDao playerDao = new PlayerJdbc();

    private GuildDao guildDao = new GuildJdbc();
    private MissionDao missionDao = new MissionJdbc();

    private MissionData getMissionData(Long guildId) {
        return new MissionData(null, guildId, "title", "S", LocalDate.now().plusDays(1L),
                10);
    }

    private ObjectiveData getObjectives(Long missionId) {
        return new ObjectiveData(null, missionId, 10, "ZOMBIE", 12, ActionType.KILL);

    }

    public MissionDaoTest() throws IOException {
    }
    @Test
    public void testMissionsByCreate() {

        try (Connection connection = dataSource.getConnection()) {
            Long guildId = 2L;

            guildDao.createGuild(guildId, "kName", connection);
            MissionData missionData = getMissionData(guildId);

            Long missionId = missionDao.createMission(missionData, connection);
            assertNotNull(missionId);

            guildDao.deleteGuild(guildId, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testMissionsById() {

        try (Connection connection = dataSource.getConnection()) {
            Long guildId = 2L;

            guildDao.createGuild(guildId, "kName", connection);
            MissionData missionData = getMissionData(guildId);

            Long missionId = missionDao.createMission(missionData, connection);

            MissionData missionData1 = missionDao.findMissionById(missionId, connection);

            assertNotNull(missionData1);
            guildDao.deleteGuild(guildId, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testMissionsByFindMissions() {

        try (Connection connection = dataSource.getConnection()) {
            Long guildId = 2L;

            guildDao.createGuild(guildId, "kName", connection);
            MissionData missionData = getMissionData(guildId);
            MissionData missionData1 = getMissionData(guildId);
            missionData1.setExpiration(LocalDate.now().minusDays(1L));

            missionDao.createMission(missionData, connection);
            missionDao.createMission(missionData1, connection);

            List<MissionData> missionDataList = missionDao.findGuildMissions(guildId, connection);

            assertEquals(1, missionDataList.size());
            assertEquals(0, missionDataList.get(0).getCurrentPlayers(), 0.0);
            guildDao.deleteGuild(guildId, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testMissionsByObjectives() throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            Long guildId = 2L;

            guildDao.createGuild(guildId, "kName", connection);
            MissionData missionData = getMissionData(guildId);

            Long missionId = missionDao.createMission(missionData, connection);

            ObjectiveData objectiveData = getObjectives(missionId);
            ObjectiveData objectiveData1 = getObjectives(missionId);

            Long objectiveId = missionDao.createMissionObjective(missionId, objectiveData, connection);
            Long objectiveId1 = missionDao.createMissionObjective(missionId, objectiveData1, connection);

            assertNotNull(objectiveId);
            assertNotNull(objectiveId1);

            guildDao.deleteGuild(guildId, connection);
        }

    }

    @Test
    public void testMissionsByFindObjectivesByGuild() throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            Long guildId = 2L;

            guildDao.createGuild(guildId, "kName", connection);
            MissionData missionData = getMissionData(guildId);

            Long missionId = missionDao.createMission(missionData, connection);

            ObjectiveData objectiveData = getObjectives(missionId);
            ObjectiveData objectiveData1 = getObjectives(missionId);

            missionDao.createMissionObjective(missionId, objectiveData, connection);
            missionDao.createMissionObjective(missionId, objectiveData1, connection);

            List<ObjectiveData> objectives = missionDao.findMissionObjectivesByGuild(missionId, connection);

            assertEquals(2, objectives.size());

            guildDao.deleteGuild(guildId, connection);
        }

    }

    @Test
    public void testMissionsByAcceptMissionEmptyByPlayer() throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            Long guildId = 2L;
            UUID playerId = UUID.randomUUID();

            playerDao.createPlayer(playerId, connection);

            guildDao.createGuild(guildId, "kName", connection);
            MissionData missionData = getMissionData(guildId);

            Long missionId = missionDao.createMission(missionData, connection);

            ObjectiveData objectiveData = getObjectives(missionId);
            ObjectiveData objectiveData1 = getObjectives(missionId);

            missionDao.createMissionObjective(missionId, objectiveData, connection);
            missionDao.createMissionObjective(missionId, objectiveData1, connection);

            List<MissionData> missionDataList = missionDao.findPlayerMissions(playerId, connection);

            assertTrue(missionDataList.isEmpty());

            guildDao.deleteGuild(guildId, connection);
        }

    }
    @Test
    public void testMissionsByAcceptMissionByPlayer() throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            Long guildId = 2L;
            UUID playerId = UUID.randomUUID();

            playerDao.createPlayer(playerId, connection);

            guildDao.createGuild(guildId, "kName", connection);
            MissionData missionData = getMissionData(guildId);

            Long missionId = missionDao.createMission(missionData, connection);

            ObjectiveData objectiveData = getObjectives(missionId);
            ObjectiveData objectiveData1 = getObjectives(missionId);

            missionDao.createMissionObjective(missionId, objectiveData, connection);
            missionDao.createMissionObjective(missionId, objectiveData1, connection);

            missionDao.saveMissionStatus(playerId, missionId, MissionStatus.ACCEPTED, connection);

            List<MissionData> missionDataList = missionDao.findPlayerMissions(playerId, connection);

            assertEquals(1, missionDataList.size() );

            guildDao.deleteGuild(guildId, connection);
        }

    }

    @Test
    public void testMissionsBySavePlayerObjectives() throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            Long guildId = 2L;
            UUID playerId = UUID.randomUUID();

            playerDao.createPlayer(playerId, connection);

            guildDao.createGuild(guildId, "kName", connection);
            MissionData missionData = getMissionData(guildId);

            Long missionId = missionDao.createMission(missionData, connection);

            ObjectiveData objectiveData = getObjectives(missionId);
            ObjectiveData objectiveData1 = getObjectives(missionId);

            Long objectiveId = missionDao.createMissionObjective(missionId, objectiveData, connection);
            Long objectiveId1 = missionDao.createMissionObjective(missionId, objectiveData1, connection);

            missionDao.saveMissionStatus(playerId, missionId, MissionStatus.ACCEPTED, connection);

            missionDao.saveObjectiveProgress(playerId, objectiveId, 9, connection);
            missionDao.saveObjectiveProgress(playerId, objectiveId1, 0, connection);

            List<ObjectiveData> objectiveDataList = missionDao.findMissionObjectivesByPlayer(playerId, missionId, connection);

            assertEquals(9, objectiveDataList.get(0).getAmount(), 0.0);
            assertEquals(0, objectiveDataList.get(1).getAmount(),  0.0);

            guildDao.deleteGuild(guildId, connection);
        }

    }


    @Test
    public void testMissionsByPlayerCompletionsRate() throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            Long guildId = 2L;
            UUID playerId = UUID.randomUUID();

            playerDao.createPlayer(playerId, connection);

            guildDao.createGuild(guildId, "kName", connection);
            MissionData missionData = getMissionData(guildId);

            Long missionId = missionDao.createMission(missionData, connection);

            ObjectiveData objectiveData = getObjectives(missionId);
            ObjectiveData objectiveData1 = getObjectives(missionId);

            Long objectiveId = missionDao.createMissionObjective(missionId, objectiveData, connection);
            Long objectiveId1 = missionDao.createMissionObjective(missionId, objectiveData1, connection);

            missionDao.saveMissionStatus(playerId, missionId, MissionStatus.FINALIZED, connection);

            HashMap<String, CompletionData> completionDataHashMap = missionDao.findPlayerCompletionsRate(playerId, connection);

            CompletionData completionData = completionDataHashMap.get("S");

            assertEquals(100 ,completionData.getPercentage(), 0.0);
            assertEquals(1, completionData.getFinalized(),  0.0);
            assertEquals(1, completionData.getTotalAccepted(),  0.0);

            guildDao.deleteGuild(guildId, connection);
        }

    }
}
