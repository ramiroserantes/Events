package com.daarthy.events.persistence.daos.guild;

import com.daarthy.events.app.modules.guilds.Guild;
import com.daarthy.events.app.modules.guilds.GuildModifiers;
import com.daarthy.events.app.modules.guilds.Level;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class AbstractGuildDao implements GuildDao {

    private static final String ERROR = "DB Error on AbstractGuildDao ";
    @Override
    public void saveGuild(Long guildId, Guild guild, Connection connection) {

        String queryString = "UPDATE Guild g SET g.kName = ?, g.lvl = ?, g.experience = ?," +
                "g.maxLvl = ?, g.ampMissions = ?, g.ampBasicRewards = ?, g.levelUpMod = ? ," +
                "g.lastTimeUpdated = ? " +
                "WHERE g.id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            int i = 1;
            if(guild.getkName() != null) {
                preparedStatement.setString(i++, guild.getkName());
            } else {
                preparedStatement.setNull(i++, Types.VARCHAR);
            }
            preparedStatement.setInt(i++, guild.getLevel().getCurrentLevel());
            preparedStatement.setFloat(i++, guild.getLevel().getCurrentExp());
            preparedStatement.setInt(i++, guild.getLevel().getMaxLevel());
            preparedStatement.setInt(i++, guild.getGuildModifiers().getAmpMissions());
            preparedStatement.setFloat(i++, guild.getGuildModifiers().getAmpBasicRewards());
            preparedStatement.setFloat(i++, guild.getLevel().getLevelUpMod());
            preparedStatement.setDate(i++, Date.valueOf(guild.getLastTimeUpdated().toLocalDate()));

            preparedStatement.setLong(i, guildId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            //  Events.logInfo(ERROR + "saveGuild");
        }

    }

    @Override
    public void deleteGuild(Long guildId, Connection connection) {


        String queryString = "DELETE FROM Guild g WHERE g.id = ?";
        String secondQuery = "DELETE FROM GuildMedals m WHERE m.guildId = ?";

        try (PreparedStatement secondStatement = connection.prepareStatement(secondQuery);) {
            secondStatement.setLong(1, guildId);

            secondStatement.executeUpdate();
        } catch (SQLException e) {
            //   Events.logInfo(ERROR);
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            preparedStatement.setLong(1, guildId);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            //  Events.logInfo(ERROR);
        }

    }

    @Override
    public Guild findGuildByPlayer(UUID playerId, Connection connection) {

        String queryString = "SELECT g.lvl, g.experience, g.maxLvl, g.kName, g.ampMissions, " +
                "g.ampBasicRewards, g.levelUpMod, g.lastTimeUpdated " +
                "FROM Guild g JOIN Player p ON p.guildId = g.id " +
                "WHERE p.playerId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            preparedStatement.setString(1, playerId.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            return getGuild(resultSet);

        } catch (SQLException e) {
            //   Events.logInfo(ERROR + "findGuildByPlayer");
            return null;
        }


    }

    @Override
    public Guild findGuildById(Long guildId, Connection connection) {

        String queryString = "SELECT g.lvl, g.experience, g.maxLvl, g.kName, g.ampMissions, g.ampBasicRewards," +
                " g.levelUpMod, g.lastTimeUpdated " +
                "FROM Guild g WHERE g.id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            preparedStatement.setLong(1, guildId);

            ResultSet resultSet = preparedStatement.executeQuery();

            return getGuild(resultSet);

        } catch (SQLException e) {
            //   Events.logInfo(ERROR + "findGuildById");
            return null;
        }

    }

    private Guild getGuild(ResultSet resultSet) throws SQLException {

        int i;
        if (resultSet.next()) {
            i = 1;
            int lvl = resultSet.getInt(i++);
            Float experience = resultSet.getFloat(i++);
            int maxLvl = resultSet.getInt(i++);
            String kName = resultSet.getString(i++);
            int ampMissions = resultSet.getInt(i++);
            Float ampBasicRewards = resultSet.getFloat(i++);
            Float levelUpMod = resultSet.getFloat(i++);
            LocalDateTime lastTimeUpdated = resultSet.getTimestamp(i).toLocalDateTime();

            return new Guild(kName, lastTimeUpdated, new Level(experience, lvl, maxLvl, levelUpMod)
                    , new GuildModifiers(ampMissions, ampBasicRewards));
        } else {
            return null;
        }
    }

}
