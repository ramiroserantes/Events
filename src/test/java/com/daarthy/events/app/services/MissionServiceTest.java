package com.daarthy.events.app.services;

import com.daarthy.events.persistence.guildDao.GuildDao;
import com.daarthy.events.persistence.guildDao.GuildJdbc;
import com.daarthy.events.persistence.missionDao.MissionDao;
import com.daarthy.events.persistence.missionDao.MissionJdbc;
import com.daarthy.events.persistence.playerDao.PlayerDao;
import com.daarthy.events.persistence.playerDao.PlayerData;
import com.daarthy.events.persistence.playerDao.PlayerJdbc;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

public class MissionServiceTest {

    private HikariDataSource dataSource = com.daarthy.guilds.persistence.SqlConnectionsTest.getInstance().getDataSource();

    private GuildDao guildDao = new GuildJdbc();
    private PlayerDao playerDao = new PlayerJdbc();
    private MissionDao missionDao = new MissionJdbc();
    private DataService dataService = new DataServiceImpl(dataSource, playerDao, guildDao);
    private MissionService missionService = new MissionServiceImpl(missionDao, dataSource);

    public MissionServiceTest() throws IOException {
    }

    @Test
    public void testMissionServiceByPlayer() {

        UUID playerId = UUID.randomUUID();

        dataService.getPlayerData(playerId);

        missionService.initPlayer(playerId);

    }
}
