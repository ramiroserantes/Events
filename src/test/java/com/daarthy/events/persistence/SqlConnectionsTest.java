package com.daarthy.events.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class SqlConnectionsTest {

    private Properties properties;
    private static HikariDataSource dataSource;

    private static SqlConnectionsTest instance;

    private SqlConnectionsTest() throws IOException {
        loadDatabaseProperties();
        initializeDataSource();
    }

    public static synchronized SqlConnectionsTest getInstance() throws IOException {
        if (instance == null) {
            instance = new SqlConnectionsTest();
        }
        return instance;
    }

    private void initializeDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty("database.url.test"));
        config.setUsername(properties.getProperty("database.username.test"));
        config.setPassword(properties.getProperty("database.password.test"));
        config.addDataSourceProperty("serverTimezone", "UTC");

        config.setMaximumPoolSize(10);

        dataSource = new HikariDataSource(config);
    }

    public synchronized HikariDataSource getDataSource() {
        return dataSource;
    }
    public synchronized Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close() {

        try {
            Connection connection = dataSource.getConnection();
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cerrar la conexión", e);
        }
    }

    private void loadDatabaseProperties() throws IOException {
        properties = new Properties();
        InputStream input = getClass().getClassLoader().getResourceAsStream("database.properties");

        if (input == null) {
            System.out.println("Sorry, unable to find databaseTest.properties");
            return;
        }

        properties.load(input);
    }

}

