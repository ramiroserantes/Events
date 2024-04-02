package com.daarthy.events.persistence.event_dao;

import com.daarthy.events.Events;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class EventJdbc extends AbstractEventDao {

    private static final String ERROR = "DB Error";

    @Override
    public void saveGuildMedals(Connection connection, Long guildId, Long eventId, int amount) {

        String queryString = "INSERT INTO GuildMedals (guildId, eventId, medals) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE medals = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            int i = 1;
            preparedStatement.setLong(i++, guildId);
            preparedStatement.setLong(i++, eventId);
            preparedStatement.setInt(i++, amount);
            preparedStatement.setInt(i, amount);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Events.logInfo(ERROR);
        }
    }

    @Override
    public void savePlayerContribution(Connection connection, UUID playerId, Long eventId, Contribution contribution) {

        String queryString = "INSERT INTO PlayerContribution (playerId, eventId, quantity, medals) VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE quantity = ?, medals = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            int i = 1;
            preparedStatement.setString(i++, playerId.toString());
            preparedStatement.setLong(i++, eventId);
            preparedStatement.setInt(i++, contribution.getItems());
            preparedStatement.setInt(i++, contribution.getMedals());
            preparedStatement.setInt(i++, contribution.getItems());
            preparedStatement.setInt(i, contribution.getMedals());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Events.logInfo(ERROR);
        }
    }
}
