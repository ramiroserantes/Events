package com.daarthy.events.persistence.daos.player.dao;

import com.daarthy.events.persistence.daos.player.entities.EventsPlayer;
import com.daarthy.mini.hibernate.jdbc.MySqlMiniCrud;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerJdbc extends MySqlMiniCrud<EventsPlayer, UUID>
        implements PlayerDao {

    public PlayerJdbc(HikariDataSource dataSource) {
        super(dataSource, EventsPlayer.class);
    }

    @Override
    public void removeAllPlayersFromGuild(Long guildId) {

        String queryString = "UPDATE Player p SET p.guildId = 1 " +
                "WHERE p.guildId = ?";

        try (Connection connection = datasource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(queryString)){

            preparedStatement.setLong(1, guildId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // Events.logInfo(  "removeAllPlayersFromGuild");
        }
    }

}
