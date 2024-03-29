package com.daarthy.events.persistence.playerDao;

import com.daarthy.events.Events;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerJdbc extends AbstractPlayerDao {

    @Override
    public PlayerData createPlayer(UUID playerId, Connection connection) throws SQLException {

        String queryString = "INSERT INTO Player (playerId, guildId) VALUES (?, 1)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)){

            preparedStatement.setString(1, playerId.toString());

            preparedStatement.executeUpdate();

            return new PlayerData(3, 0F, 1L);

        } catch (SQLException e) {
            Events.logInfo("DB Error");
            return null;
        }

    }

}
