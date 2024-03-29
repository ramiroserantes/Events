package com.daarthy.events.persistence.guildDao;

import com.daarthy.events.persistence.SqlConnectionsTest;
import com.daarthy.events.persistence.playerDao.PlayerDao;
import com.daarthy.events.persistence.playerDao.PlayerData;
import com.daarthy.events.persistence.playerDao.PlayerJdbc;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import static org.junit.Assert.*;

public class GuildDaoTest {

    private Connection connection = SqlConnectionsTest.getInstance().getConnection();
    private PlayerDao playerDao = new PlayerJdbc();

    private GuildDao guildDao = new GuildJdbc();

    public GuildDaoTest() throws SQLException, IOException {
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
    public void testGuildByCreate() throws SQLException {

        Long guildId = 2L;

        GuildData guildData = guildDao.createGuild(guildId, "kName", connection);
        assertEquals(guildData.getGuildId(), 2L, 0.0);
        assertEquals(guildData.getAmpBasicRewards(), 0.0, 0.0);
        assertEquals(guildData.getLvl(), 0);
        assertEquals(guildData.getkName(), "kName");
        assertEquals(guildData.getAmpMissions(), 0);
        assertEquals(guildData.getLevelUpMod(), 0.0, 0.0);
        assertEquals(guildData.getMaxLvL(), 6);
        assertEquals(guildData.getExperience(), 0.0F, 0.0);

        guildDao.deleteGuild(guildId, connection);

    }

    @Test
    public void testGuildByUpdate() throws SQLException {

        Long guildId = 2L;
        UUID playerId = UUID.randomUUID();

        GuildData guildData = guildDao.createGuild(guildId, "kName", connection);
        guildData.setExperience(900F);
        guildData.setAmpBasicRewards(9.0F);
        guildData.setMaxLvL(9);

        guildDao.saveGuild(guildData, connection);

        PlayerData playerData = playerDao.createPlayer(playerId, connection);
        playerData.setGuildId(guildId); playerDao.savePlayer(playerId, playerData, connection);

        GuildData expectedData = guildDao.findGuildByPlayer(playerId, connection);

        assertEquals(expectedData.getGuildId(), 2L, 0.0);
        assertEquals(expectedData.getAmpBasicRewards(), 9.0F, 0.0);
        assertEquals(expectedData.getLvl(), 0);
        assertEquals(expectedData.getkName(), "kName");
        assertEquals(expectedData.getAmpMissions(), 0);
        assertEquals(expectedData.getLevelUpMod(), 0.0, 0.0);
        assertEquals(expectedData.getMaxLvL(), 9);
        assertEquals(guildData.getExperience(), 900.0F, 0.0);

        playerDao.removeAllPlayersFromGuild(guildId, connection);
        guildDao.deleteGuild(guildId, connection);

    }


    @Test
    public void testGuildByFindDefaultGuild() throws SQLException {

        Long guildId = guildDao.findDefaultGuildId(connection);

        assertEquals(guildId, 1L, 0.0);

    }

}
