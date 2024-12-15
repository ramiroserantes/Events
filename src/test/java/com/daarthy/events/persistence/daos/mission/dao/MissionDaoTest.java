package com.daarthy.events.persistence.daos.mission.dao;

import com.daarthy.events.persistence.daos.DaoContext;
import com.daarthy.events.persistence.daos.guild.entities.Guild;
import com.daarthy.events.persistence.daos.mission.entities.Mission;
import org.junit.After;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class MissionDaoTest {

    @Test
    public void testMissionByCreation() {

        Guild guild = ctx.getGuild(3L);
        Mission mission = ctx.getMission(guild.getId(), LocalDate.now().plusDays(1));

        Mission foundMission = ctx.missionDao().findById(mission.getId());

        assertEquals(mission, foundMission);
    }

   /* @Test
    public void testMissionByFindGuildMissions() {

        Guild guild = ctx.getGuild(2L);
        Mission mission = ctx.getMission(guild.getId(), LocalDate.now().plusDays(1));

        // This is because it will compute the current players on this mission for the guild
        mission.setCurrentPlayers(0);

        MiniCriteria<Mission> criteria = MySQLCriteria.<Mission>builder()
                .selector(FestivalSelector.GUILD_MISSIONS_TODAY)
                .params(List.of(2L))
                .resultClass(Mission.class)
                .build();

        List<Mission> foundMissions = ctx.missionDao().findByCriteria(criteria);

        assertEquals(1, foundMissions.size());
        assertEquals(mission, foundMissions.get(0));

    }

    @Test
    public void testMissionByFindPlayerMissions() {

        UUID playerId = UUID.randomUUID();
        ctx.getPlayer(playerId, 1L);
        Mission mission = ctx.getMission(1L, LocalDate.now().plusDays(1));
        ctx.getMissionAcceptance(mission.getId(), playerId);

        MiniCriteria<Mission> criteria = MySQLCriteria.<Mission>builder()
                .selector(FestivalSelector.FIND_CURRENT_PLAYER_MISSIONS)
                .params(List.of(playerId))
                .resultClass(Mission.class)
                .build();

        List<Mission> playerMissions = ctx.missionDao().findByCriteria(criteria);

        assertEquals(1, playerMissions.size());
        assertEquals(mission, playerMissions.get(0));
    }
*/
    // *****************************************************
    // Internal Methods And Variables
    // *****************************************************

    private DaoContext ctx = new DaoContext();

    @After
    public void cleanUp() {
        ctx.cleanUp();
    }


/*
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

    @Test
    public void testMissionDataByNullMax() {

        MissionData missionData = new MissionData();
        missionData.setCurrentPlayers(0);

        assertTrue(missionData.addPlayer(2));

    }
*/
}
