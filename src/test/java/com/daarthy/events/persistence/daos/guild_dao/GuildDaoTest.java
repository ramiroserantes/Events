package com.daarthy.events.persistence.daos.guild_dao;

public class GuildDaoTest {
/*
    private HikariDataSource dataSource = SqlConnections.getInstance().getDataSource();
    private PlayerDao playerDao = new PlayerJdbc();

    private GuildDao guildDao = new GuildJdbc();

    public GuildDaoTest() throws IOException {
    }

    @Test
    public void testGuildByCreate() throws SQLException {

        try (Connection connection = dataSource.getConnection()) {

            Long guildId = 2L;

            Guild guildData = guildDao.createGuild(guildId, "kName", connection);
            assertEquals(0.0, guildData.getGuildModifiers().getAmpBasicRewards(),  0.0);
            assertEquals(0, guildData.getLevel().getCurrentLevel());
            assertEquals("kName", guildData.getkName());
            assertEquals(0, guildData.getGuildModifiers().getAmpMissions(), 0);
            assertEquals(0.0, guildData.getLevel().getLevelUpMod(), 0.0);
            assertEquals(6, guildData.getLevel().getMaxLevel());
            assertEquals(0F, guildData.getLevel().getCurrentExp(), 0.0);

            guildDao.deleteGuild(guildId, connection);
        }

    }

    @Test
    public void testGuildByUpdate() throws SQLException {

        try (Connection connection = dataSource.getConnection()) {

            Long guildId = 39L;
            UUID playerId = UUID.randomUUID();

            Guild guildData = guildDao.createGuild(guildId, "kName", connection);
            guildData.getLevel().addExperience(900F);
            guildData.getGuildModifiers().setAmpBasicRewards(9.0F);
            guildData.getLevel().setMaxLevel(9);
            guildData.getGuildModifiers().setAmpMissions(4);

            guildDao.saveGuild(guildId, guildData, connection);

            EventsPlayer eventsPlayer = playerDao.createPlayer(playerId, connection);
            eventsPlayer.setGuildId(guildId);
            playerDao.savePlayer(playerId, eventsPlayer, connection);

            Guild expectedData = guildDao.findGuildByPlayer(playerId, connection);

            assertEquals(9.0, expectedData.getGuildModifiers().getAmpBasicRewards(),  0.0);
            assertEquals(0, expectedData.getLevel().getCurrentLevel());
            assertEquals("kName", expectedData.getkName());
            assertEquals(4, expectedData.getGuildModifiers().getAmpMissions(), 0);
            assertEquals(0.0, expectedData.getLevel().getLevelUpMod(), 0.0);
            assertEquals(9, expectedData.getLevel().getMaxLevel());
            assertEquals(900F, expectedData.getLevel().getCurrentExp(), 0.0);

            playerDao.removeAllPlayersFromGuild(guildId, connection);
            guildDao.deleteGuild(guildId, connection);
        }

    }
*/

}
