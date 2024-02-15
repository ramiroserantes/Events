package com.daarthy.events.persistence.guildDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class GuildJdbc extends AbstractGuildDao {

    @Override
    public GuildData createGuild(Long guildId, String kName, Connection connection) {

        String queryString = "INSERT INTO Guild (id, kName, lastTimeUpdated) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            int i = 1;
            preparedStatement.setLong(i++, guildId);
            preparedStatement.setString(i++, kName);
            preparedStatement.setTimestamp(i, Timestamp.valueOf(LocalDate.now().atStartOfDay().minusDays(1)));

            if(preparedStatement.executeUpdate() == 0 ) {
                System.out.println("Could not create the guild with id: " + guildId);
            }

            return new GuildData(guildId, kName, 0, 0F, 6, 0,
                    0F, 0F, LocalDateTime.now());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
