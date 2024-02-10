package com.daarthy.events.persistence.playerDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public interface PlayerDao {

    PlayerData createPlayer(UUID playerId, Connection connection) throws SQLException;

    PlayerData findPlayerData(UUID playerId, Connection connection);

    void savePlayer(UUID playerId, PlayerData playerData, Connection connection) throws SQLException;

    void removeAllPlayersFromGuild(Long guildId, Connection connection) throws SQLException;

}
