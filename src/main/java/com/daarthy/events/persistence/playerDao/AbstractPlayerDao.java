package com.daarthy.events.persistence.playerDao;

import com.daarthy.events.Events;

import java.sql.*;
import java.util.UUID;

public abstract class AbstractPlayerDao implements PlayerDao {

    private static final String ERROR = "DB Error";
    @Override
    public PlayerData findPlayerData(UUID playerId, Connection connection) {

        String queryString = "SELECT p.maxMissions, p.ampBasicRewards, p.guildId " +
                "FROM Player p WHERE p.playerId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            preparedStatement.setString(1, playerId.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            int i = 1;
            int maxMissions = resultSet.getInt(i++);
            Float ampBasicRewards = resultSet.getFloat(i++);
            Long guildId = resultSet.getLong(i);

            return new PlayerData(maxMissions, ampBasicRewards, guildId);


        } catch (SQLException e) {
            Events.logInfo(ERROR);
            return null;
        }
    }

    @Override
    public void savePlayer(UUID playerId, PlayerData playerData, Connection connection) throws SQLException {

        String queryString = "UPDATE Player p SET p.maxMissions = ?, p.ampBasicRewards = ?, p.guildId = ? " +
                "WHERE p.playerId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)){

            int i = 1;
            preparedStatement.setInt(i++, playerData.getMaxMissions());
            preparedStatement.setFloat(i++, playerData.getAmpBasicRewards());
            preparedStatement.setLong(i++, playerData.getGuildId());
            preparedStatement.setString(i, playerId.toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Events.logInfo(ERROR);
        }

    }


    @Override
    public void removeAllPlayersFromGuild(Long guildId, Connection connection) throws SQLException {

        String queryString = "UPDATE Player p SET p.guildId = 1 " +
                "WHERE p.guildId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)){

            preparedStatement.setLong(1, guildId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Events.logInfo(ERROR);
        }
    }
}
