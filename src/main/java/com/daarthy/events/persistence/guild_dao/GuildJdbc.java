package com.daarthy.events.persistence.guild_dao;

import com.daarthy.events.Events;
import com.daarthy.events.app.modules.guilds.EventMedals;
import com.daarthy.events.app.modules.guilds.Guild;
import com.daarthy.events.app.modules.guilds.GuildModifiers;
import com.daarthy.events.app.modules.guilds.Level;

import java.sql.*;
import java.time.LocalDate;

public class GuildJdbc extends AbstractGuildDao {

    @Override
    public Guild createGuild(Long guildId, String kName, Connection connection) {

        String queryString = "INSERT INTO Guild (id, kName, lastTimeUpdated) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            int i = 1;
            preparedStatement.setLong(i++, guildId);
            preparedStatement.setString(i++, kName);
            preparedStatement.setDate(i, Date.valueOf(LocalDate.now()
                    .atStartOfDay().minusDays(1L).toLocalDate()));

            preparedStatement.executeUpdate();

            return new Guild(kName, LocalDate.now().atStartOfDay().minusDays(1L), new Level(0F, 0, 6, 0F),
                    new EventMedals(), new GuildModifiers(0, 0F));

        } catch (SQLException e) {
            Events.logInfo("Error on DB");
            return null;
        }

    }


}
