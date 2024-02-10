package com.daarthy.events.persistence.guildDao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class AbstractGuildDao implements GuildDao {


    @Override
    public void saveGuild(GuildData guildData, Connection connection) {

        String queryString = "UPDATE Guild g SET g.lvl = ?, g.experience = ?," +
                "g.maxLvl = ?, g.ampMissions = ?, g.ampBasicRewards = ?, g.levelUpMod = ? ," +
                "g.lastTimeUpdated = ?" +
                "WHERE g.id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            int i = 1;
            preparedStatement.setInt(i++, guildData.getLvl());
            preparedStatement.setFloat(i++, guildData.getExperience());
            preparedStatement.setInt(i++, guildData.getMaxLvL());
            preparedStatement.setInt(i++, guildData.getAmpMissions());
            preparedStatement.setFloat(i++, guildData.getAmpBasicRewards());
            preparedStatement.setFloat(i++, guildData.getLevelUpMod());
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(guildData.getLastTimeUpdated()));

            preparedStatement.setLong(i, guildData.getGuildId());

            if(preparedStatement.executeUpdate() == 0) {
                System.out.println("Guild with id: " + guildData.getGuildId() + " could not be saved");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteGuild(Long guildId, Connection connection) {


        String queryString = "DELETE FROM Guild g WHERE g.id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {


            preparedStatement.setLong(1, guildId);

            if(preparedStatement.executeUpdate() == 0) {
                System.out.println("Guild with id: " + guildId + " could not be deleted");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public GuildData findGuildByPlayer(UUID playerId, Connection connection) {

        String queryString = "SELECT g.id, g.lvl, g.experience, g.maxLvl, g.kName, g.ampMissions, " +
                "g.ampBasicRewards, g.levelUpMod, g.lastTimeUpdated " +
                "FROM Guild g JOIN Player p ON p.guildId = g.id " +
                "WHERE p.playerId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            preparedStatement.setString(1, playerId.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int i = 1;
                    Long guildId = resultSet.getLong(i++);
                    int lvl = resultSet.getInt(i++);
                    Float experience = resultSet.getFloat(i++);
                    int maxLvl = resultSet.getInt(i++);
                    String kName = resultSet.getString(i++);
                    int ampMissions = resultSet.getInt(i++);
                    Float ampBasicRewards = resultSet.getFloat(i++);
                    Float levelUpMod = resultSet.getFloat(i++);
                    LocalDateTime lastTimeUpdated = resultSet.getTimestamp(i).toLocalDateTime();

                    return new GuildData(guildId, kName, lvl, experience, maxLvl, ampMissions,
                            ampBasicRewards, levelUpMod, lastTimeUpdated);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public Long findDefaultGuildId(Connection connection) {

        String queryString = "SELECT id FROM Guild WHERE maxLvl = 0";

        try(PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                } else {return null;}
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
