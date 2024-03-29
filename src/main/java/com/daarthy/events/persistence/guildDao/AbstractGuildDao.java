package com.daarthy.events.persistence.guildDao;

import com.daarthy.events.Events;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class AbstractGuildDao implements GuildDao {

    private static final String ERROR = "DB Error";
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

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Events.logInfo(ERROR);
        }

    }

    @Override
    public void deleteGuild(Long guildId, Connection connection) {


        String queryString = "DELETE FROM Guild g WHERE g.id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {


            preparedStatement.setLong(1, guildId);

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            Events.logInfo(ERROR);
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

            ResultSet resultSet = preparedStatement.executeQuery();
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

        } catch (SQLException e) {
            Events.logInfo(ERROR);
            return null;
        }


    }

    @Override
    public Long findDefaultGuildId(Connection connection) {

        String queryString = "SELECT id FROM Guild WHERE maxLvl = 0";

        try(PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {return null;}

        } catch (SQLException e) {
            Events.logInfo(ERROR);
            return null;
        }
    }
}
