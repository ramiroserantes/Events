package com.daarthy.events.persistence.daos.player.dao;

import com.daarthy.events.persistence.SqlConnections;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

public class PlayerDaoTest {

    private HikariDataSource dataSource = SqlConnections.getInstance().getDataSource();

    public PlayerDaoTest() throws IOException {
    }

    @Test
    public void testingThings() {

        PlayerDao dao = new PlayerJdbc(dataSource);
        UUID playerId = UUID.randomUUID();
        EventsPlayer player = EventsPlayer.builder()
                .playerId(playerId)
                .ampBasicRewards(0F)
                .maxMissions(1)
                .guildId(1L)
                .isNew(true)
                .build();

        EventsPlayer savedPlayer = dao.save(player);

        System.out.println(savedPlayer);
        EventsPlayer found = dao.findById(playerId);
        System.out.println(found);

    }
   /* private PlayerDao playerDao = new PlayerJdbc();

    private GuildDao guildDao = new GuildJdbc();

    public PlayerDaoTest() throws IOException {
    }

    @Test
    public void testPlayerByCreate() throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            UUID playerId = UUID.randomUUID();

            EventsPlayer eventsPlayer = playerDao.createPlayer(playerId, connection);

            assertEquals(0F, eventsPlayer.getAmpBasicRewards(), 0.0);
            assertEquals(1L, eventsPlayer.getGuildId(), 0.0);
            assertEquals(3, eventsPlayer.getMaxMissions());
        }

    }

    @Test
    public void testPlayerByNoData() {

        try (Connection connection = dataSource.getConnection()) {
            UUID playerId = UUID.randomUUID();

            EventsPlayer eventsPlayer = playerDao.findPlayerData(playerId, connection);

            assertNull(eventsPlayer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testPlayerByData() throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            UUID playerId = UUID.randomUUID();

            EventsPlayer expected = playerDao.createPlayer(playerId, connection);

            EventsPlayer result = playerDao.findPlayerData(playerId, connection);

            assertEquals(expected.getMaxMissions(), result.getMaxMissions());
            assertEquals(expected.getGuildId(), result.getGuildId());
            assertEquals(expected.getAmpBasicRewards(), result.getAmpBasicRewards());
        }
    }

    @Test
    public void testPlayerBySave() throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            UUID playerId = UUID.randomUUID();

            EventsPlayer eventsPlayer = playerDao.createPlayer(playerId, connection);
            eventsPlayer.setMaxMissions(90);
            eventsPlayer.setAmpBasicRewards(0.03F);
            eventsPlayer.setGuildId(1L);
            playerDao.savePlayer(playerId, eventsPlayer, connection);
            EventsPlayer result = playerDao.findPlayerData(playerId, connection);

            assertEquals(90, result.getMaxMissions());
            assertEquals(0.03F, result.getAmpBasicRewards(), 0.0);
            assertEquals(1L, result.getGuildId(), 0.0);
        }
    }

    @Test
    public void testPlayerByRemoveAllAnd() throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            Long guildId1 = 2L;
            Long guildId2 = 3L;

            UUID player1 = UUID.randomUUID();
            UUID player2 = UUID.randomUUID();
            UUID player3 = UUID.randomUUID();

            EventsPlayer eventsPlayer1 = playerDao.createPlayer(player1, connection);
            EventsPlayer eventsPlayer2 = playerDao.createPlayer(player2, connection);
            EventsPlayer eventsPlayer3 = playerDao.createPlayer(player3, connection);

            guildDao.createGuild(guildId1, "kName", connection);
            guildDao.createGuild(guildId2, "kno", connection);

            eventsPlayer1.setGuildId(guildId1);
            playerDao.savePlayer(player1, eventsPlayer1, connection);
            eventsPlayer2.setGuildId(guildId1);
            playerDao.savePlayer(player2, eventsPlayer2, connection);
            eventsPlayer3.setGuildId(guildId2);
            playerDao.savePlayer(player3, eventsPlayer3, connection);

            playerDao.removeAllPlayersFromGuild(guildId1, connection);

            EventsPlayer result1 = playerDao.findPlayerData(player1, connection);
            EventsPlayer result2 = playerDao.findPlayerData(player2, connection);
            EventsPlayer result3 = playerDao.findPlayerData(player3, connection);

            assertEquals(1L, result1.getGuildId(), 0.0);
            assertEquals(1L, result2.getGuildId(), 0.0);
            assertEquals(guildId2, result3.getGuildId(), 0.0);
            playerDao.removeAllPlayersFromGuild(guildId2, connection);

            guildDao.deleteGuild(2L, connection);
            guildDao.deleteGuild(3L, connection);
        }
    }
*/
}
