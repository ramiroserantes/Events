package com.daarthy.events.persistence.eventDao;

import com.daarthy.events.Events;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class EventJdbc extends AbstractEventDao {

    private static final String ERROR = "DB Error";

    /*
CREATE TABLE Events (
id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
worldScope VARCHAR(20) NOT NULL ,
name VARCHAR(20) NOT NULL ,
startDate DATE NOT NULL,
endDate DATE NOT NULL,
maxMedals INT NOT NULL DEFAULT 200
);
CREATE TABLE GuildMedals (
eventId BIGINT NOT NULL,
guildId BIGINT NOT NULL,
medals INT NOT NULL DEFAULT 0,
PRIMARY KEY (eventId, guildId),
FOREIGN KEY (eventId) REFERENCES Events(id),
FOREIGN KEY (guildId) REFERENCES Guild(id)
);
CREATE TABLE PlayerContribution (
playerId VARCHAR(36) NOT NULL,
eventId BIGINT NOT NULL,
quantity INT NOT NULL DEFAULT 0,
medals INT NOT NULL DEFAULT 0,
PRIMARY KEY (playerId, eventId),
FOREIGN KEY (playerId) REFERENCES Player(playerId),
FOREIGN KEY (eventId) REFERENCES Events(id)
);*/
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
