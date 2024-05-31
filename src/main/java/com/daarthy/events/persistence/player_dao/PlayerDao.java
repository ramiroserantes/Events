package com.daarthy.events.persistence.player_dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public interface PlayerDao {

    PlayerData createPlayer(UUID playerId, Connection connection);

    PlayerData findPlayerData(UUID playerId, Connection connection);

    void savePlayer(UUID playerId, PlayerData playerData, Connection connection);

    void removeAllPlayersFromGuild(Long guildId, Connection connection);

}
