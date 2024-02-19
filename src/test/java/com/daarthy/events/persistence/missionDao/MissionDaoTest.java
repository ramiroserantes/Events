package com.daarthy.events.persistence.missionDao;

import com.daarthy.events.persistence.guildDao.GuildDao;
import com.daarthy.events.persistence.guildDao.GuildJdbc;
import com.daarthy.events.persistence.playerDao.PlayerDao;
import com.daarthy.events.persistence.playerDao.PlayerJdbc;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class MissionDaoTest {

    private Connection connection = com.daarthy.guilds.persistence.SqlConnectionsTest.getInstance().getConnection();
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

    public MissionDaoTest() throws SQLException, IOException {
    }

    @After
    public void tearDown() {

        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cerrar la conexión", e);
        }
    }

    @Test
    public void testMissionsByCreate() throws SQLException {

        Long guildId = 2L;

        guildDao.createGuild(guildId, "kName", connection);
        MissionData missionData = getMissionData(guildId);

        Long missionId = missionDao.createMission(missionData, connection);
        assertNotNull(missionId);

        guildDao.deleteGuild(guildId, connection);

    }

    @Test
    public void testMissionsByFindMissions() throws SQLException {

        Long guildId = 2L;

        guildDao.createGuild(guildId, "kName", connection);
        MissionData missionData = getMissionData(guildId);
        MissionData missionData1 = getMissionData(guildId);
        missionData1.setExpiration(LocalDate.now().minusDays(1L));

        missionDao.createMission(missionData, connection);
        missionDao.createMission(missionData1, connection);

        List<MissionData> missionDataList = missionDao.findGuildMissions(guildId, connection);

        assertEquals(missionDataList.size(), 1);
        assertEquals(missionDataList.get(0).getCurrentPlayers(), 0, 0.0);
        guildDao.deleteGuild(guildId, connection);

    }

    @Test
    public void testMissionsByObjectives() throws SQLException {

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

    @Test
    public void testMissionsByFindObjectivesByGuild() throws SQLException {

        Long guildId = 2L;

        guildDao.createGuild(guildId, "kName", connection);
        MissionData missionData = getMissionData(guildId);

        Long missionId = missionDao.createMission(missionData, connection);

        ObjectiveData objectiveData = getObjectives(missionId);
        ObjectiveData objectiveData1 = getObjectives(missionId);

        missionDao.createMissionObjective(missionId, objectiveData, connection);
        missionDao.createMissionObjective(missionId, objectiveData1, connection);

        List<ObjectiveData> objectives = missionDao.findMissionObjectivesByGuild(missionId, connection);

        assertEquals(objectives.size(), 2);

        guildDao.deleteGuild(guildId, connection);

    }

    @Test
    public void testMissionsByAcceptMissionEmptyByPlayer() throws SQLException {

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
    @Test
    public void testMissionsByAcceptMissionByPlayer() throws SQLException {

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

        assertEquals(missionDataList.size(), 1);

        guildDao.deleteGuild(guildId, connection);

    }

    @Test
    public void testMissionsBySavePlayerObjectives() throws SQLException {

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

        assertEquals(objectiveDataList.get(0).getAmount(),  9, 0.0);
        assertEquals(objectiveDataList.get(1).getAmount(), 0, 0.0);

        guildDao.deleteGuild(guildId, connection);

    }


    @Test
    public void testMissionsByPlayerCompletionsRate() throws SQLException {

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

        assertEquals(completionData.getPercentage(), 100.0, 0.0);
        assertEquals(completionData.getFinalized(), 1,  0.0);
        assertEquals(completionData.getTotalAccepted(), 1, 0.0);

        guildDao.deleteGuild(guildId, connection);

    }
}
