package com.daarthy.events.persistence.event_dao;

import com.daarthy.events.persistence.SqlConnections;
import com.daarthy.events.persistence.player_dao.PlayerDao;
import com.daarthy.events.persistence.player_dao.PlayerJdbc;
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

    private HikariDataSource dataSource = SqlConnections.getInstance().getDataSource();
    private EventDao eventDao = new EventJdbc();
    private PlayerDao playerDao = new PlayerJdbc();
    private static final Long DEFAULT_ID = 1L;

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

        String queryDeletePlayerContribution = "DELETE FROM PlayerContribution";
        String queryDeleteMedals = "DELETE FROM GuildMedals";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement2 = connection.prepareStatement(queryDeletePlayerContribution);
            PreparedStatement preparedStatement3 = connection.prepareStatement(queryDeleteMedals);

            preparedStatement3.executeUpdate();
            preparedStatement2.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Test
    public void testEventDaoByFindEmptyButDefault() {

        try (Connection connection = dataSource.getConnection()) {

            List<EventData> eventDataList = eventDao.findCurrentEvents(connection, ScopeEnum.ALL);

            assertFalse(eventDataList.isEmpty());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testEventDaoByFindEmptyByInvalidStart() {

        try (Connection connection = dataSource.getConnection()) {

            setUpInvalidEventStart(connection);
            List<EventData> eventDataList = eventDao.findCurrentEvents(connection, ScopeEnum.ALL);

            assertEquals(3, eventDataList.size());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testEventDaoByFindEmptyByInvalidEnd() {

        try (Connection connection = dataSource.getConnection()) {

            setUpInvalidEventEnd(connection);
            List<EventData> eventDataList = eventDao.findCurrentEvents(connection, ScopeEnum.ALL);

            assertFalse(eventDataList.isEmpty());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testEventDaoByFind() {

        try (Connection connection = dataSource.getConnection()) {

            List<EventData> eventDataList = eventDao.findCurrentEvents(connection, ScopeEnum.ALL);

            assertEquals(3, eventDataList.size());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testEventDaoByFindMedalsNoAvailable() {

        try (Connection connection = dataSource.getConnection()) {

            List<EventData> eventDataList = eventDao.findCurrentEvents(connection, ScopeEnum.ALL);
            Long eventId = eventDataList.get(0).getEventId();

            int medals = eventDao.findGuildMedals(connection, DEFAULT_ID, eventId);

            assertEquals(0, medals);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testEventDaoByFindMedalsAvailableAndSave() {

        try (Connection connection = dataSource.getConnection()) {

            List<EventData> eventDataList = eventDao.findCurrentEvents(connection, ScopeEnum.ALL);
            Long eventId = eventDataList.get(0).getEventId();

            eventDao.saveGuildMedals(connection, DEFAULT_ID, eventId, 900);

            int medals = eventDao.findGuildMedals(connection, DEFAULT_ID, eventId);

            assertEquals(900, medals);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testEventDaoByFindNullContributions() {

        try (Connection connection = dataSource.getConnection()) {

            UUID playerID = UUID.randomUUID();
            playerDao.createPlayer(playerID, connection);

            List<EventData> eventDataList = eventDao.findCurrentEvents(connection, ScopeEnum.ALL);
            Long eventId = eventDataList.get(0).getEventId();

            Contribution contribution = eventDao.findPlayerContribution(connection, eventId, playerID);

            assertNull(contribution);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testEventDaoByFindContributions() {

        try (Connection connection = dataSource.getConnection()) {

            UUID playerID = UUID.randomUUID();
            playerDao.createPlayer(playerID, connection);

            List<EventData> eventDataList = eventDao.findCurrentEvents(connection, ScopeEnum.ALL);
            EventData event = eventDataList.get(0);

            eventDao.savePlayerContribution(connection, playerID, event.getEventId(), new Contribution(9, 9), event.getMaxMedals());

            Contribution contribution = eventDao.findPlayerContribution(connection, event.getEventId(), playerID);

            assertEquals(9, contribution.getItems());
            assertEquals(9, contribution.getMedals());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
