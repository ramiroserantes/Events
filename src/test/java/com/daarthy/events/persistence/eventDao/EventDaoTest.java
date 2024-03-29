package com.daarthy.events.persistence.eventDao;

import com.daarthy.events.persistence.SqlConnectionsTest;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;

public class EventDaoTest {

    private HikariDataSource dataSource = SqlConnectionsTest.getInstance().getDataSource();
    private EventDao eventDao = new EventJdbc();

    private void setUpValidEvent(Connection connection) {
        String query = "INSERT INTO Events (worldScope, name, startDate, endDate) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){

            int i = 1;
            preparedStatement.setString(i++, ScopeEnum.ALL.name());
            preparedStatement.setString(i++,"HuntingEvent");
            preparedStatement.setDate(i++, Date.valueOf(LocalDate.now().minusDays(1)));
            preparedStatement.setDate(i, Date.valueOf(LocalDate.now().plusDays(1)));

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setUpInvalidEventStart(Connection connection) {
        String query = "INSERT INTO Events (worldScope, name, startDate, endDate) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){

            int i = 1;
            preparedStatement.setString(i++, ScopeEnum.ALL.name());
            preparedStatement.setString(i++,"HuntingEvent");
            preparedStatement.setDate(i++, Date.valueOf(LocalDate.now().plusDays(1)));
            preparedStatement.setDate(i, Date.valueOf(LocalDate.now().plusDays(10)));

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setUpInvalidEventEnd(Connection connection) {
        String query = "INSERT INTO Events (worldScope, name, startDate, endDate) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){

            int i = 1;
            preparedStatement.setString(i++, ScopeEnum.ALL.name());
            preparedStatement.setString(i++,"HuntingEvent");
            preparedStatement.setDate(i++, Date.valueOf(LocalDate.now().minusDays(2)));
            preparedStatement.setDate(i, Date.valueOf(LocalDate.now().minusDays(1)));

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public EventDaoTest() throws IOException {
    }

    @After
    public void cleanUp() {

        String queryUpdate = "DELETE FROM Events";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(queryUpdate);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Test
    public void testEventDaoByFindEmpty() {

        try (Connection connection = dataSource.getConnection()) {

            List<EventData> eventDataList = eventDao.findCurrentEvents(connection, ScopeEnum.ALL);

            assertTrue(eventDataList.isEmpty());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testEventDaoByFindEmptyByInvalidStart() {

        try (Connection connection = dataSource.getConnection()) {

            setUpInvalidEventStart(connection);
            List<EventData> eventDataList = eventDao.findCurrentEvents(connection, ScopeEnum.ALL);

            assertTrue(eventDataList.isEmpty());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testEventDaoByFindEmptyByInvalidEnd() {

        try (Connection connection = dataSource.getConnection()) {

            setUpInvalidEventEnd(connection);
            List<EventData> eventDataList = eventDao.findCurrentEvents(connection, ScopeEnum.ALL);

            assertTrue(eventDataList.isEmpty());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testEventDaoByFind() {

        try (Connection connection = dataSource.getConnection()) {

            setUpValidEvent(connection);
            List<EventData> eventDataList = eventDao.findCurrentEvents(connection, ScopeEnum.ALL);

            assertEquals(1, eventDataList.size());
            assertEquals("HuntingEvent", eventDataList.get(0).getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
