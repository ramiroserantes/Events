package com.daarthy.events.persistence.mission_dao;

import com.daarthy.events.Events;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.UUID;

public class MissionJdbc extends AbstractMissionDao {

    private static final String ERROR = "DB Error";
    @Override
    public Long createMission(MissionData missionData, Connection connection) {

        String queryString = "INSERT INTO Missions (guildId, title, grade, expiration, maxCompletions) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString,
                Statement.RETURN_GENERATED_KEYS)) {

            int i = 1;

            preparedStatement.setLong(i++, missionData.getGuildId());

            preparedStatement.setString(i++, missionData.getTitle());
            preparedStatement.setString(i++, missionData.getGrade().name());
            preparedStatement.setDate(i++, java.sql.Date.valueOf(missionData.getExpiration()));

            if(missionData.getMaxCompletions() != null) {
                preparedStatement.setInt(i, missionData.getMaxCompletions());
            } else {
                preparedStatement.setNull(i, Types.INTEGER);
            }

            return execute(preparedStatement);

        } catch (SQLException e) {
            Events.logInfo(ERROR);
            return null;
        }

    }

    private Long execute(PreparedStatement preparedStatement) throws SQLException {

        preparedStatement.executeUpdate();

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

        if(generatedKeys.next()) {
            return generatedKeys.getLong(1);
        } else { return null;}

    }

    @Override
    public Long createMissionObjective(Long missionId, ObjectiveData objectiveData, Connection connection) {

        String queryString = "INSERT INTO Objective(missionId, actionType, reqAmount, " +
                " target, levels) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS)) {

            int i = 1;
            preparedStatement.setLong(i++, missionId);
            preparedStatement.setString(i++, objectiveData.getActionType().getActionTypeString());
            preparedStatement.setInt(i++, objectiveData.getReqAmount());
            preparedStatement.setString(i++, objectiveData.getTarget());
            if(objectiveData.getLevels() != null) {
                preparedStatement.setInt(i, objectiveData.getLevels());
            } else {
                preparedStatement.setNull(i, Types.INTEGER);
            }

            return execute(preparedStatement);

        } catch (SQLException e) {
            Events.logInfo(ERROR);
            return null;
        }

    }

    @Override
    public void saveMissionStatus(UUID playerId, Long missionId, MissionStatus status, Connection connection) {

        String queryString = "INSERT INTO MissionAccept (playerId, missionId, status, acceptDate) " +
                "VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE status = VALUES(status)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            preparedStatement.setString(1, playerId.toString());
            preparedStatement.setLong(2, missionId);
            preparedStatement.setString(3, status.toString());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Events.logInfo(ERROR);
        }
    }

    @Override
    public void saveObjectiveProgress(UUID playerId, Long objectiveId, int amount, Connection connection) {
        String queryString = "INSERT INTO ObjectiveProgress (playerId, objectiveId, amount) " +
                "VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE amount = VALUES(amount)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            preparedStatement.setString(1, playerId.toString());
            preparedStatement.setLong(2, objectiveId);
            preparedStatement.setInt(3, amount);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Events.logInfo(ERROR);
        }
    }



}
