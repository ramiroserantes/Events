package com.daarthy.events.app.services;

public class DataServiceTest {

   /* private HikariDataSource dataSource = SqlConnections.getInstance().getDataSource();

    private GuildDao guildDao = new GuildJdbc();
    private PlayerDao playerDao = new PlayerJdbc();
    private DataService dataService = new DataServiceImpl(dataSource, playerDao, guildDao);

    public DataServiceTest() throws IOException {
    }

    @Test
    public void testDataServiceByPlayer() {

        UUID playerId = UUID.randomUUID();

        dataService.initPlayer(playerId);

        EventsPlayer eventsPlayer = dataService.getPlayerData(playerId);
        assertEquals(1, eventsPlayer.getGuildId(), 0.0);
        assertEquals(0, eventsPlayer.getAmpBasicRewards(), 0.0);
        assertEquals(3, eventsPlayer.getMaxMissions(), 0);

    }

    @Test
    public void testDataServiceByGuild() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);

        Guild guild = dataService.getGuild(1L);

        assertEquals(0, guild.getLevel().getMaxLevel());
        assertEquals(0, guild.getLevel().getCurrentExp(), 0.0);
        assertEquals(0, guild.getLevel().getLevelUpMod(), 0.0);

    }

    @Test
    public void testDataServiceBySaveAndRemovePlayer() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);

        EventsPlayer eventsPlayer = dataService.getPlayerData(playerId);
        eventsPlayer.setMaxMissions(10);
        dataService.savePlayer(playerId);
        dataService.removePlayer(playerId);

        dataService.initPlayer(playerId);
        EventsPlayer finalEventsPlayer = dataService.getPlayerData(playerId);
        assertEquals(10, finalEventsPlayer.getMaxMissions(), 0);

    }

    @Test
    public void testDataServiceBySaveAndRemoveGuild() {

        UUID playerId = UUID.randomUUID();
        UUID playerId2 = UUID.randomUUID();
        dataService.initPlayer(playerId);
        dataService.initPlayer(playerId2);

        dataService.removePlayer(playerId);
        dataService.removeGuild(1L);

        assertNotNull(dataService.getGuild(1L));
    }

    @Test
    public void testDataServiceBySaveAndRemoveGuildAfter() {

        UUID playerId = UUID.randomUUID();
        UUID playerId2 = UUID.randomUUID();
        dataService.initPlayer(playerId);
        dataService.initPlayer(playerId2);

        dataService.removePlayer(playerId);
        dataService.removePlayer(playerId2);
        dataService.removeGuild(1L);

        assertNull(dataService.getGuild(1L));
    }

    @Test
    public void testDataServiceByCreateAndDeleteGuild2() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);

        String kName = "Kname";
        Long guildId = 2L;

        dataService.createGuild(playerId, guildId, kName);

        dataService.deleteGuild(2L);

        assertNotNull(dataService.getGuild(1L));
    }

    @Test
    public void testDataServiceByCreateAndDeleteGuild() {

        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);

        EventsPlayer eventsPlayer = dataService.getPlayerData(playerId);
        String kName = "Kname";
        Long guildId = 2L;

        dataService.createGuild(playerId, guildId, kName);

        Guild guild = dataService.getGuild(2L);

        assertEquals(6, guild.getLevel().getMaxLevel());
        assertEquals(kName, guild.getkName());
        assertEquals(0, guild.getLevel().getLevelUpMod(),0.0);

        dataService.deleteGuild(2L);

        assertEquals(1L, eventsPlayer.getGuildId(), 0.0);
    }

    @Test
    public void testDateServiceGetGuildDBCache() {
        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);

        String kName = "Kname";
        Long guildId = 2L;

        dataService.createGuild(playerId, guildId, kName);

        assertNotNull(dataService.findDBGuild(guildId));

        dataService.deleteGuild(2L);
    }
    @Test
    public void testDateServiceGetGuildDBNoCache() {
        UUID playerId = UUID.randomUUID();
        dataService.initPlayer(playerId);

        String kName = "Kname";
        Long guildId = 2L;

        dataService.createGuild(playerId, guildId, kName);

        dataService.removePlayer(playerId);
        dataService.removeGuild(guildId);

        assertNotNull(dataService.findDBGuild(guildId));

        dataService.deleteGuild(2L);
    }


    @Test
    public void testDateServiceGetGuildDBNull() {

        assertNull(dataService.getGuild(293L));
    }*/
}
