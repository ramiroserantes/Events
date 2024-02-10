package com.daarthy.events.persistence.eventDao;

import com.daarthy.events.persistence.missionDao.ActionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AbstractEventDao implements EventDao{

    @Override
    public List<EventData> findCurrentEvents(Connection connection, ScopeEnum scopeEnum) {

        List<EventData> currentEvents = new ArrayList<>();

        String queryString = "SELECT e.id, e.worldScope, e.nameLink, e.descriptionLink " +
                "FROM Events e " +
                "JOIN Task t ON e.id = t.eventId " +
                "WHERE e.worldScope = ? AND t.endDate <= (SELECT MAX(endDate) FROM Task WHERE eventId = e.id) " +
                "AND t.initDate >= (SELECT MIN(initDate) FROM Task WHERE eventId = e.id)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            preparedStatement.setString(1, scopeEnum.name());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    currentEvents.add(mapEventData(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return currentEvents;
    }

    private EventData mapEventData(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String worldScope = resultSet.getString("worldScope");
        String nameLink = resultSet.getString("nameLink");
        String descriptionLink = resultSet.getString("descriptionLink");

        return new EventData(id, ScopeEnum.valueOf(worldScope), nameLink, descriptionLink);
    }

    @Override
    public List<TaskData> findEventTasks(Connection connection, Long eventId) {

        List<TaskData> eventTasks = new ArrayList<>();

        String queryString = "SELECT id, initDate, endDate, target, level, amount, reqAmount, medals " +
                "FROM Task " +
                "WHERE eventId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            preparedStatement.setLong(1, eventId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    eventTasks.add(mapTaskData(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return eventTasks;
    }

    private TaskData mapTaskData(ResultSet resultSet) throws SQLException {
        Long taskId = resultSet.getLong("id");
        LocalDateTime initDate = resultSet.getTimestamp("initDate").toLocalDateTime();
        LocalDateTime endDate = resultSet.getTimestamp("endDate").toLocalDateTime();
        String target = resultSet.getString("target");
        String actionType = resultSet.getString("actionType");
        Integer level = resultSet.getInt("level");
        Integer amount = resultSet.getInt("amount");
        Integer reqAmount = resultSet.getInt("reqAmount");
        Integer medals = resultSet.getInt("medals");

        return new TaskData(taskId, initDate, endDate, target, ActionType.valueOf(actionType),
                level, amount, reqAmount, medals);
    }

    @Override
    public int findGuildMedals(Connection connection, Long guildId, Long eventId) {

        int medals = 0;

        String queryString = "SELECT medals FROM GuildMedals WHERE guildId = ? AND eventId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            preparedStatement.setLong(1, guildId);
            preparedStatement.setLong(2, eventId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    medals = resultSet.getInt("medals");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return medals;
    }



    @Override
    public int findPlayerContribution(Connection connection, Long taskId, UUID playerId) {

        String queryString = "SELECT quantity FROM PlayerContribution WHERE taskId = ? AND playerId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            preparedStatement.setLong(1, taskId);
            preparedStatement.setString(2, playerId.toString());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("quantity");
                } else {
                    return 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
