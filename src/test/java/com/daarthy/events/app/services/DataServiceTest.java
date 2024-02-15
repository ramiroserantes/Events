package com.daarthy.events.app.services;

import com.daarthy.events.app.modules.GuildCache;
import com.daarthy.events.persistence.guildDao.GuildDao;
import com.daarthy.events.persistence.guildDao.GuildJdbc;
import com.daarthy.events.persistence.playerDao.PlayerDao;
import com.daarthy.events.persistence.playerDao.PlayerData;
import com.daarthy.events.persistence.playerDao.PlayerJdbc;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.*;

public class DataServiceTest {

    private HikariDataSource dataSource = com.daarthy.guilds.persistence.SqlConnectionsTest.getInstance().getDataSource();

    private GuildDao guildDao = new GuildJdbc();
    private PlayerDao playerDao = new PlayerJdbc();
    private DataService dataService = new DataServiceImpl(dataSource, playerDao, guildDao);

    public DataServiceTest() throws IOException {
    }


    @Test
    public void testDataServiceByPlayer() {

        UUID playerId = UUID.randomUUID();

        PlayerData playerData = dataService.getPlayerData(playerId);

        assertEquals(playerData.getGuildId(), 1, 0.0);
        assertEquals(playerData.getAmpBasicRewards(), 0, 0.0);
        assertEquals(playerData.getMaxMissions(), 3, 0);

    }

    @Test
    public void testDataServiceByGuild() {

        UUID playerId = UUID.randomUUID();
        PlayerData playerData = dataService.getPlayerData(playerId);

        GuildCache guildCache = dataService.getGuildByPlayer(playerId, playerData);

        assertEquals(guildCache.getGuildLevel().getMaxLevel(), 0);
        assertEquals(guildCache.getGuildLevel().getCurrentExp(), 0, 0.0);
        assertEquals(guildCache.getLevelUpMod(), 0, 0.0);

    }

    @Test
    public void testDataServiceBySaveAndRemovePlayer() {

        UUID playerId = UUID.randomUUID();

        PlayerData playerData = dataService.getPlayerData(playerId);
        playerData.setMaxMissions(10);
        dataService.savePlayer(playerId);
        dataService.removePlayerFromCache(playerId);

        PlayerData finalPlayerData = dataService.getPlayerData(playerId);
        assertEquals(finalPlayerData.getMaxMissions(), 10, 0);

    }

    @Test
    public void testDataServiceByCreateAndDeleteGuild() {

        UUID playerId = UUID.randomUUID();
        PlayerData playerData = dataService.getPlayerData(playerId);
        String kName = "Kname";
        Long guildId = 2L;

        dataService.createGuild(guildId, kName);
        playerData.setGuildId(2L);
        dataService.savePlayer(playerId);

        GuildCache guildCache = dataService.getGuildByPlayer(playerId, playerData);

        assertEquals(guildCache.getGuildLevel().getMaxLevel(), 6);
        assertEquals(guildCache.getkName(), kName);
        assertEquals(guildCache.getLevelUpMod(), 0,0.0);

        dataService.deleteGuild(2L);

        assertEquals(playerData.getGuildId(), 1L, 0.0);
    }
}
