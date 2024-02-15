package com.daarthy.events.persistence.missionDao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public abstract class AbstractMissionDao implements MissionDao {



    public List<MissionData> findGuildMissions(Long guildId, Connection connection) {

        List<MissionData> missions = new ArrayList<>();

        String queryString = "SELECT m.*, COUNT(DISTINCT ma.playerId) AS userCount " +
                "FROM Missions m " +
                "LEFT JOIN MissionAccept ma ON m.id = ma.missionId " +
                "WHERE m.guildId = ? AND NOW() < m.expiration " +
                "GROUP BY m.id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            preparedStatement.setLong(1, guildId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    MissionData missionData = mapResultSetToMissionData(resultSet);
                    int userCount = resultSet.getInt("userCount");
                    missionData.setCurrentPlayers(userCount);
                    missions.add(missionData);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return missions;
    }

    @Override
    public List<MissionData> findPlayerMissions(UUID playerId, Connection connection) {

        List<MissionData> missions = new ArrayList<>();

        String queryString = "SELECT m.* " +
                "FROM MissionAccept ma " +
                "JOIN Missions m ON m.id = ma.missionId " +
                "WHERE ma.playerId = ? AND ma.status = ? ";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            int i = 1;
            preparedStatement.setString(i++, playerId.toString());
            preparedStatement.setString(i, MissionStatus.ACCEPTED.getStatusString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    MissionData missionData = mapResultSetToMissionData(resultSet);
                    missions.add(missionData);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return missions;
    }


    @Override
    public boolean wasMissionAcceptedByPlayer(Long missionId, UUID playerId, Connection connection) {

        String queryString = "SELECT COUNT(*) FROM MissionAccept WHERE missionId = ? AND playerId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            preparedStatement.setLong(1, missionId);
            preparedStatement.setString(2, playerId.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }



    private ObjectiveData mapObjectiveData(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        Long missionIdent = resultSet.getLong("missionId");
        ActionType actionType = ActionType.valueOf(resultSet.getString("actionType").toUpperCase());
        int reqAmount = resultSet.getInt("reqAmount");
        String target = resultSet.getString("target");
        Integer levels = resultSet.getObject("levels", Integer.class);
        return new ObjectiveData(id, missionIdent, reqAmount, target, levels, actionType);
    }

    @Override
    public List<ObjectiveData> findMissionObjectivesByGuild(Long missionId, Connection connection) {
        String queryString = "SELECT id, missionId, actionType, reqAmount, target, levels " +
                "FROM Objective WHERE missionId = ?";

        List<ObjectiveData> objectives = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            preparedStatement.setLong(1, missionId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    objectives.add(mapObjectiveData(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return objectives;
    }

    @Override
    public List<ObjectiveData> findMissionObjectivesByPlayer(UUID playerId, Long missionId, Connection connection) {

        String queryString = "SELECT o.id, o.missionId, o.actionType, o.reqAmount, o.target, o.levels, op.amount " +
                "FROM Objective o LEFT JOIN ObjectiveProgress op ON o.id = op.objectiveId " +
                "WHERE o.missionId = ? AND op.playerId = ?";

        List<ObjectiveData> objectives = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            preparedStatement.setLong(1, missionId);
            preparedStatement.setString(2, playerId.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ObjectiveData od = mapObjectiveData(resultSet);
                    od.setAmount(resultSet.getInt("amount"));
                    objectives.add(od);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return objectives;
    }

    @Override
    public HashMap<String, CompletionData> findPlayerCompletionsRate(UUID playerId, Connection connection) {
        String queryString = "SELECT m.grade, " +
                "COUNT(ma.status) AS totalMissions, " +
                "SUM(CASE WHEN ma.status = 'FINALIZED' THEN 1 ELSE 0 END) AS completedMissions " +
                "FROM Missions m " +
                "LEFT JOIN MissionAccept ma ON m.id = ma.missionId " +
                "WHERE ma.playerId = ? " +
                "GROUP BY m.grade";

        HashMap<String, CompletionData> completionsData = new HashMap<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            preparedStatement.setString(1, playerId.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String grade = resultSet.getString("grade");
                    int totalMissions = resultSet.getInt("totalMissions");
                    int completedMissions = resultSet.getInt("completedMissions");

                    CompletionData completionData = new CompletionData(totalMissions, completedMissions);
                    completionsData.put(grade, completionData);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return completionsData;
    }



    private MissionData mapResultSetToMissionData(ResultSet resultSet) throws SQLException {

        Long id = resultSet.getLong("id");
        Long guildId = resultSet.getLong("guildId");
        String title = resultSet.getString("title");
        String grade = resultSet.getString("grade");
        LocalDateTime expiration = resultSet.getTimestamp("expiration").toLocalDateTime();
        Integer maxCompletions = resultSet.getInt("maxCompletions");
        if(resultSet.wasNull()) {
            maxCompletions = null;
        }

        return new MissionData(id, guildId, title, grade, expiration, maxCompletions);
    }

    @Override
    public MissionData findMissionById(Long missionId, Connection connection) {

        String queryString = "SELECT m.* " +
                "FROM Missions m WHERE m.id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            preparedStatement.setLong(1, missionId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    return mapResultSetToMissionData(resultSet);
                } else { return null;}
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int findAcceptedMissions(UUID playerId, Connection connection) {

        String queryString = "SELECT COUNT(DISTINCT missionId) AS total" +
                    "FROM MissionAccept " +
                    "WHERE playerId = ? AND (DATE(acceptDate) = CURDATE() OR status = 'ACCEPTED')";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            preparedStatement.setString(1, playerId.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    return resultSet.getInt(1);
                } else {return 0;}
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
