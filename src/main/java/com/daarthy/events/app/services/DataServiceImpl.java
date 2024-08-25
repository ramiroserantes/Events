package com.daarthy.events.app.services;

import com.daarthy.events.Events;
import com.daarthy.events.app.modules.guilds.Guild;
import com.daarthy.events.persistence.daos.guild.dao.GuildDao;
import com.daarthy.events.persistence.daos.player.dao.PlayerDao;
import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataServiceImpl implements DataService {

    /*private HashMap<UUID, EventsPlayer> playersData = new HashMap<>();
    private HashMap<Long, Guild> guildsData = new HashMap<>();

    private static final String ERROR = "DB Error on DataService ";

    private final HikariDataSource dataSource;
    private final PlayerDao playerDao;
    private final GuildDao guildDao;

    public DataServiceImpl(HikariDataSource dataSource, PlayerDao playerDao, GuildDao guildDao) {
        this.dataSource = dataSource;
        this.playerDao = playerDao;
        this.guildDao = guildDao;
    }

    @Override
    public EventsPlayer getPlayerData(UUID playerId) {
        return playersData.getOrDefault(playerId, null);
    }

    @Override
    public void createGuild(UUID playerId, Long guildId, String kName) {

        try (Connection connection = dataSource.getConnection()) {

            EventsPlayer eventsPlayer = playersData.get(playerId);
            guildsData.get(eventsPlayer.getGuildId()).modifyWatch(-1);
            removeGuild(eventsPlayer.getGuildId());
            eventsPlayer.setGuildId(guildId);

            Guild guild = guildDao.createGuild(guildId, kName, connection);
            guildsData.put(guildId, guild);

            savePlayer(playerId);

        } catch (SQLException e) {
            //Events.logInfo(ERROR + "createGuild");
        }
    }

    @Override
    public Guild getGuild(Long guildId) {
        return guildsData.getOrDefault(guildId, null);
    }

    @Override
    public Guild findDBGuild(Long guildId) {

        Guild guild = guildsData.getOrDefault(guildId, null);

        if(guild == null) {

            try(Connection connection = dataSource.getConnection()) {

                guild = guildDao.findGuildById(guildId, connection);

            } catch (SQLException e) {
                //Events.logInfo("Error on guild retrieve findDBGuild");
            }
        }
        return guild;
    }

    @Override
    public int getJobMaxLevel(UUID playerId) {

        try (Connection connection = dataSource.getConnection()) {

            return guildDao.findGuildByPlayer(playerId, connection)
                    .getLevel().getMaxJobLevel();

        } catch (SQLException e) {
            // Events.logInfo(ERROR + "getJobMaxLevel");
        }

        return 15;
    }

    @Override
    public void savePlayer(UUID playerId) {

        /*try (Connection connection = dataSource.getConnection()) {
            playerDao.savePlayer(playerId, playersData.get(playerId), connection);
        } catch (SQLException e) {
            Events.logInfo(ERROR + "savePlayer");
        }*/
    /*}

    @Override
    public void saveGuild(Long guildId) {

        try (Connection connection = dataSource.getConnection()) {

            Guild guild = guildsData.get(guildId);

            guildDao.saveGuild(guildId, guild, connection);

        } catch (SQLException e) {
            // Events.logInfo(ERROR + "saveGuild");
        }
    }

    @Override
    public void removePlayer(UUID playerId) {
        savePlayer(playerId);
        guildsData.get(playersData.get(playerId).getGuildId()).modifyWatch(-1);
        playersData.remove(playerId);
    }

    @Override
    public void removeGuild(Long guildId) {

        Guild guild = guildsData.get(guildId);
        if(!guild.isWatched()) {
            saveGuild(guildId);
            guildsData.remove(guildId);
        }
    }

    @Override
    public void deleteGuild(Long guildId) {

       /* guildsData.remove(guildId);
        try (Connection connection = dataSource.getConnection()){

            removePlayersFromCacheGuild(guildId);
            playerDao.removeAllPlayersFromGuild(guildId, connection);
            guildDao.deleteGuild(guildId, connection);

        } catch (SQLException e) {
            Events.logInfo(ERROR + "deleteGuild");
        }*/
   /* }

    @Override
    public void initPlayer(UUID playerId) {
       /* try (Connection connection = dataSource.getConnection()) {

            EventsPlayer eventsPlayer = playerDao.findPlayerData(playerId, connection);
            if (eventsPlayer == null) {
                eventsPlayer = playerDao.createPlayer(playerId, connection);
            }
            playersData.put(playerId, eventsPlayer);

            Long guildId = eventsPlayer.getGuildId();

            Guild guild = guildsData.getOrDefault(guildId, null);
            if(guild == null) {
                guildsData.put(guildId, guildDao.findGuildByPlayer(playerId, connection));
            } else {
                guild.modifyWatch(+1);
            }

        } catch (SQLException e) {
            Events.logInfo(ERROR + "initPlayer");
        }*/
  /*  }

    private void removePlayersFromCacheGuild(Long guildId) {

        for (Map.Entry<UUID, EventsPlayer> entry : playersData.entrySet()) {
            EventsPlayer eventsPlayer = entry.getValue();

            if (eventsPlayer.getGuildId().equals(guildId)) {
                eventsPlayer.setGuildId(Events.getBasicGuildId());
                if(!guildsData.containsKey(Events.getBasicGuildId())) {
                    try (Connection connection = dataSource.getConnection()) {
                        guildsData.put(Events.getBasicGuildId(), guildDao.findGuildById(Events.getBasicGuildId(),
                                connection));
                    } catch (SQLException e) {
                        //    Events.logInfo("Error on retrieve of Basic Guild");
                    }
                }
            }

        }
    }
*/

}
