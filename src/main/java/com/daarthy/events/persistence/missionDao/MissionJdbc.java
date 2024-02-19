package com.daarthy.events.persistence.missionDao;

import com.daarthy.events.app.modules.missions.Grade;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MissionJdbc extends AbstractMissionDao {

    @Override
    public Long createMission(MissionData missionData, Connection connection) {

        String queryString = "INSERT INTO Missions (guildId, title, grade, expiration, maxCompletions) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString,
                Statement.RETURN_GENERATED_KEYS)) {

            int i = 1;
            if(missionData.getGuildId() != null) {
                preparedStatement.setLong(i++, missionData.getGuildId());
            } else {preparedStatement.setLong(i++, Types.BIGINT);}

            preparedStatement.setString(i++, missionData.getTitle());
            preparedStatement.setString(i++, missionData.getGrade());
            preparedStatement.setDate(i++, java.sql.Date.valueOf(missionData.getExpiration()));

            if(missionData.getMaxCompletions() != null) {
                preparedStatement.setInt(i, missionData.getMaxCompletions());
            } else {
                preparedStatement.setInt(i, Types.INTEGER);
            }

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if(generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else { return null;}
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {return null;}
            }

        } catch (SQLException e) {
            throw new RuntimeException();
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }



}
