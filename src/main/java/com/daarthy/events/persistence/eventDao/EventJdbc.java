package com.daarthy.events.persistence.eventDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class EventJdbc extends AbstractEventDao{

    @Override
    public void saveGuildMedals(Connection connection, Long guildId, Long eventId, int amount) {

        String queryString = "INSERT INTO GuildMedals (guildId, eventId, medals) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE medals = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            preparedStatement.setLong(1, guildId);
            preparedStatement.setLong(2, eventId);
            preparedStatement.setInt(3, amount);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void savePlayerContribution(Connection connection, UUID playerId, Long taskId, int contribution) {

        String queryString = "INSERT INTO PlayerContribution (playerId, taskId, quantity) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE quantity = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            preparedStatement.setString(1, playerId.toString());
            preparedStatement.setLong(2, taskId);
            preparedStatement.setInt(3, contribution);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
