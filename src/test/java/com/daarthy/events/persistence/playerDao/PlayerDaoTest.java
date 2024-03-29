package com.daarthy.events.persistence.playerDao;

import com.daarthy.events.persistence.SqlConnectionsTest;
import com.daarthy.events.persistence.guildDao.GuildDao;
import com.daarthy.events.persistence.guildDao.GuildJdbc;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import static org.junit.Assert.*;

public class PlayerDaoTest {

    private Connection connection = SqlConnectionsTest.getInstance().getConnection();
    private PlayerDao playerDao = new PlayerJdbc();

    private GuildDao guildDao = new GuildJdbc();

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

    public PlayerDaoTest() throws SQLException, IOException {
    }

    @Test
    public void testPlayerByCreate() throws SQLException {

        UUID playerId = UUID.randomUUID();

        PlayerData playerData = playerDao.createPlayer(playerId, connection);

        assertEquals(playerData.getAmpBasicRewards(), 0F, 0.0);
        assertEquals(playerData.getGuildId(), 1L, 0.0);
        assertEquals(playerData.getMaxMissions(), 3);

    }

    @Test
    public void testPlayerByNoData() {

        UUID playerId = UUID.randomUUID();

        PlayerData playerData = playerDao.findPlayerData(playerId, connection);

        assertNull(playerData);
    }

    @Test
    public void testPlayerByData() throws SQLException {

        UUID playerId = UUID.randomUUID();

        PlayerData expected = playerDao.createPlayer(playerId, connection);

        PlayerData result = playerDao.findPlayerData(playerId, connection);

        assertEquals(expected.getMaxMissions(), result.getMaxMissions());
        assertEquals(expected.getGuildId(), result.getGuildId());
        assertEquals(expected.getAmpBasicRewards(), result.getAmpBasicRewards());
    }

    @Test
    public void testPlayerBySave() throws SQLException {

        UUID playerId = UUID.randomUUID();

        PlayerData playerData = playerDao.createPlayer(playerId, connection);
        playerData.setMaxMissions(90);
        playerData.setAmpBasicRewards(0.03F);
        playerDao.savePlayer(playerId, playerData, connection);
        PlayerData result = playerDao.findPlayerData(playerId, connection);

        assertEquals(90, result.getMaxMissions());
        assertEquals(0.03F, result.getAmpBasicRewards(), 0.0);
    }

    @Test
    public void testPlayerByRemoveAllAnd() throws SQLException {

        Long guildId1 = 2L;
        Long guildId2 = 3L;

        UUID player1 = UUID.randomUUID();
        UUID player2 = UUID.randomUUID();
        UUID player3 = UUID.randomUUID();

        PlayerData playerData1 = playerDao.createPlayer(player1, connection);
        PlayerData playerData2 = playerDao.createPlayer(player2, connection);
        PlayerData playerData3 = playerDao.createPlayer(player3, connection);

        guildDao.createGuild(guildId1, "kName", connection);
        guildDao.createGuild(guildId2, "kno", connection);

        playerData1.setGuildId(guildId1); playerDao.savePlayer(player1, playerData1, connection);
        playerData2.setGuildId(guildId1); playerDao.savePlayer(player2, playerData2, connection);
        playerData3.setGuildId(guildId2); playerDao.savePlayer(player3, playerData3, connection);

        playerDao.removeAllPlayersFromGuild(guildId1, connection);

        PlayerData result1 = playerDao.findPlayerData(player1, connection);
        PlayerData result2 = playerDao.findPlayerData(player2, connection);
        PlayerData result3 = playerDao.findPlayerData(player3, connection);

        assertEquals(1L, result1.getGuildId(), 0.0);
        assertEquals(1L, result2.getGuildId(), 0.0);
        assertEquals(guildId2, result3.getGuildId(), 0.0);
        playerDao.removeAllPlayersFromGuild(guildId2, connection);

        guildDao.deleteGuild(2L, connection);
        guildDao.deleteGuild( 3L, connection);
    }
}
