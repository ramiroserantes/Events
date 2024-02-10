package com.daarthy.events.persistence.playerDao;

import java.sql.*;
import java.util.UUID;

public abstract class AbstractPlayerDao implements PlayerDao {

    @Override
    public PlayerData findPlayerData(UUID playerId, Connection connection) {

        String queryString = "SELECT p.maxMissions, p.ampBasicRewards, p.guildId " +
                "FROM Player p WHERE p.playerId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            preparedStatement.setString(1, playerId.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (!resultSet.next()) {
                    System.out.println("Data for the user with uuid: " + playerId + ", Not Found");
                    return null;
                }

                int i = 1;
                int maxMissions = resultSet.getInt(i++);
                Float ampBasicRewards = resultSet.getFloat(i++);
                Long guildId = resultSet.getLong(i);

                return new PlayerData(maxMissions, ampBasicRewards, guildId);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
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

            if (preparedStatement.executeUpdate() == 0) {
                System.out.println("Player with uuid: " + playerId + ", couldnt be saved");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }
}
