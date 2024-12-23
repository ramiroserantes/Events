package com.daarthy.events.model.services;

public class MissionInfoServiceImplTest {
    /*
    private static final Long GUILD_ID = 4L;
    private static final String K_NAME = "name";

    private HikariDataSource dataSource = SqlConnections.getInstance().getDataSource();

    private PlayerDao playerDao = new PlayerJdbc();
    private MissionDao missionDao = new MissionJdbc();
    private GuildDao guildDao = new GuildJdbc();

    private DataService dataService = new DataServiceImpl(dataSource, playerDao, guildDao);

    private MissionInfoService missionInfoService = new MissionInfoServiceImpl(missionDao, dataSource);
    private MissionFunctionalService missionFunctionalService = new MissionFunctionalServiceImpl(dataSource,
    missionDao);


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
        EventsPlayer eventsPlayer = dataService.getPlayerData(playerId);

        missionFunctionalService.initPlayer(playerId);
        missionFunctionalService.fillGuildDashBoard(GUILD_ID, guild);

        Map<MissionData, List<ObjectiveData>> missionDataListMap = missionInfoService.findGuildDashBoard(GUILD_ID);

        MissionData missionData = missionDataListMap.keySet().stream().findAny().orElse(null);

        StringBuilder result = missionFunctionalService.joinMission(playerId, eventsPlayer, guild, missionData
        .getMissionId());

        Map<String, CompletionData> completions = missionInfoService.getPlayerRates(playerId);

        assertEquals("Mission Accepted", result.toString());
        assertEquals(1, completions.size());

        dataService.deleteGuild(GUILD_ID);
    }*/


}
