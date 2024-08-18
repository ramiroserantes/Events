package com.daarthy.events.persistence.daos.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public abstract class AbstractEventDao implements EventDao{

    private static final String ERROR = "DB Error on EventDao ";

    @Override
    public List<EventData> findCurrentEvents(Connection connection, ScopeEnum scopeEnum) {

        List<EventData> currentEvents = new ArrayList<>();

        String queryString = "SELECT e.id, e.worldScope, e.name, e.startDate, e.endDate, e.maxMedals " +
                "FROM Events e " +
                "WHERE e.worldScope = ? AND e.startDate <= CURDATE() AND e.endDate >= CURDATE() ";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            preparedStatement.setString(1, scopeEnum.name());

            ResultSet resultSet = preparedStatement.executeQuery();

            int i;
            while (resultSet.next()) {
                i = 1;
                Long id = resultSet.getLong(i++);
                ScopeEnum foundScope = ScopeEnum.valueOf(resultSet.getString(i++));
                String name = resultSet.getString(i++);
                LocalDate startDate = resultSet.getDate(i++).toLocalDate();
                LocalDate endDate = resultSet.getDate(i++).toLocalDate();
                int maxMedals = resultSet.getInt(i);
                currentEvents.add(new EventData(id, foundScope, name, startDate, endDate, maxMedals));
            }

        } catch (SQLException e) {
            //  Events.logInfo(ERROR + "findCurrentEvents");
        }

        return currentEvents;
    }

    @Override
    public int findGuildMedals(Connection connection, Long guildId, Long eventId) {

        int medals = 0;

        String queryString = "SELECT medals FROM GuildMedals WHERE guildId = ? AND eventId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            int i = 1;
            preparedStatement.setLong(i++, guildId);
            preparedStatement.setLong(i, eventId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                medals = resultSet.getInt("medals");
            }

        } catch (SQLException e) {
            //  Events.logInfo(ERROR + "findGuildMedals");
        }

        return medals;
    }

    @Override
    public Contribution findPlayerContribution(Connection connection, Long eventId, UUID playerId) {

        String queryString = "SELECT quantity, medals FROM PlayerContribution WHERE eventId = ? AND playerId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            int i = 1;
            preparedStatement.setLong(i++, eventId);
            preparedStatement.setString(i, playerId.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            i = 1;
            if (resultSet.next()) {
                int quantity = resultSet.getInt(i++);
                int medals = resultSet.getInt(i);
                return new Contribution(quantity, medals);
            }

        } catch (SQLException e) {
            //   Events.logInfo(ERROR + "findPlayerContribution");
        }

        return null;
    }


}
