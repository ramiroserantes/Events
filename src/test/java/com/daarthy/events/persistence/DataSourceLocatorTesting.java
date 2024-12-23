package com.daarthy.events.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSourceLocatorTesting {

    private static HikariDataSource dataSource;
    private static DataSourceLocatorTesting instance;
    private Properties properties;

    private DataSourceLocatorTesting() throws IOException {
        loadDatabaseProperties();
        initializeDataSource();
    }

    public static synchronized DataSourceLocatorTesting getInstance() throws IOException {
        if (instance == null) {
            instance = new DataSourceLocatorTesting();
        }
        return instance;
    }

    private void initializeDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty("url"));
        config.setUsername(properties.getProperty("username"));
        config.setPassword(properties.getProperty("password"));
        config.addDataSourceProperty("serverTimezone", "UTC");

        config.setMaximumPoolSize(10);

        dataSource = new HikariDataSource(config);
    }

    public synchronized HikariDataSource getDataSource() {
        return dataSource;
    }

    public void close() {

        try {
            Connection connection = dataSource.getConnection();
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error on connection close", e);
        }
    }

    private void loadDatabaseProperties() throws IOException {
        properties = new Properties();
        InputStream input = getClass().getClassLoader().getResourceAsStream("liquibase-test.properties");

        if (input == null) {
            return;
        }

        properties.load(input);
    }

}

