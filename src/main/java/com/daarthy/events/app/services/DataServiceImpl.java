package com.daarthy.events.app.services;

import com.daarthy.events.app.modules.GuildCache;
import com.daarthy.events.app.modules.GuildLevel;
import com.daarthy.events.persistence.guildDao.GuildDao;
import com.daarthy.events.persistence.guildDao.GuildData;
import com.daarthy.events.persistence.playerDao.PlayerDao;
import com.daarthy.events.persistence.playerDao.PlayerData;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class DataServiceImpl implements DataService {

    private HashMap<UUID, PlayerData> playersData = new HashMap<>();
    private HashMap<Long, GuildCache> guildsData = new HashMap<>();

    private final HikariDataSource dataSource;
    private final PlayerDao playerDao;
    private final GuildDao guildDao;

    public DataServiceImpl(HikariDataSource dataSource, PlayerDao playerDao, GuildDao guildDao) {
        this.dataSource = dataSource;
        this.playerDao = playerDao;
        this.guildDao = guildDao;
    }

    @Override
    public PlayerData getPlayerData(UUID playerId) {

        if(playersData.containsKey(playerId)) {
            return playersData.get(playerId);
        } else {
            try (Connection connection = dataSource.getConnection()) {

                PlayerData playerData = playerDao.findPlayerData(playerId, connection);

                if(playerData == null) {
                    playerData = playerDao.createPlayer(playerId, connection);
                }

                playersData.put(playerId, playerData);
                return playerData;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public Long createGuild(Long guildId, String kName) {

        try (Connection connection = dataSource.getConnection()) {

            return guildDao.createGuild(guildId, kName, connection).getGuildId();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GuildCache getGuildByPlayer(UUID playerId, PlayerData playerData) {

        if(guildsData.containsKey(playerData.getGuildId())) {
            return guildsData.get(playerData.getGuildId());
        } else {
            try (Connection connection = dataSource.getConnection()) {

                GuildData guildData = guildDao.findGuildByPlayer(playerId, connection);

                if(guildData != null) {
                    guildsData.put(guildData.getGuildId(), new GuildCache(guildData.getkName(), guildData.getAmpMissions(),
                            guildData.getAmpBasicRewards(), guildData.getLastTimeUpdated(), new GuildLevel(
                            guildData.getExperience(), guildData.getLvl(), guildData.getMaxLvL(),
                            guildData.getLevelUpMod()), guildData.getLevelUpMod()));

                    return guildsData.get(guildData.getGuildId());
                } else {return null;}

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public void savePlayer(UUID playerId) {

        try (Connection connection = dataSource.getConnection()) {

            playerDao.savePlayer(playerId, playersData.get(playerId), connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveGuild(Long guildId) {

        try (Connection connection = dataSource.getConnection()) {

            GuildCache guildCache = guildsData.get(guildId);
            guildDao.saveGuild(new GuildData(guildId, guildCache.getkName(), guildCache.getGuildLevel().getCurrentLevel(),
                    guildCache.getGuildLevel().getCurrentExp(), guildCache.getGuildLevel().getMaxLevel(),
                    guildCache.getAmpMissions(), guildCache.getAmpBasicRewards(), guildCache.getLevelUpMod(),
                    guildCache.getLastTimeUpdated()), connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removePlayerFromCache(UUID playerId) {
        savePlayer(playerId);
        playersData.remove(playerId);
    }

    @Override
    public void removeGuildFromCache(Long guildId) {
        saveGuild(guildId);
        guildsData.remove(guildId);
    }

    @Override
    public void deleteGuild(Long guildId) {

        guildsData.remove(guildId);

        try (Connection connection = dataSource.getConnection()){

            removePlayersFromCacheGuild(guildId);
            playerDao.removeAllPlayersFromGuild(guildId, connection);
            guildDao.deleteGuild(guildId, connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void removePlayersFromCacheGuild(Long guildId) {
        Iterator<Map.Entry<UUID, PlayerData>> iterator = playersData.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<UUID, PlayerData> entry = iterator.next();
            PlayerData playerData = entry.getValue();

            if (playerData.getGuildId() != null && playerData.getGuildId().equals(guildId)) {
                playerData.setGuildId(1L);
            }
        }
    }


}
