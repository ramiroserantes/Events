package com.daarthy.events.app.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSourceLocatorImpl {

    private static DataSourceLocatorImpl instance;
    private HikariDataSource dataSource;
    private Properties properties;

    public DataSourceLocatorImpl() throws IOException {
        loadDatabaseProperties();
        initializeDataSource();
    }

    public static synchronized DataSourceLocatorImpl getInstance() throws IOException {
        if (instance == null) {
            instance = new DataSourceLocatorImpl();
        }
        return instance;
    }

    private void initializeDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty("database.url"));
        config.setUsername(properties.getProperty("database.username"));
        config.setPassword(properties.getProperty("database.password"));
        config.addDataSourceProperty("serverTimezone", "UTC");

        config.setMaximumPoolSize(20);

        this.dataSource = new HikariDataSource(config);
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private void loadDatabaseProperties() throws IOException {
        properties = new Properties();
        InputStream input = getClass().getClassLoader().getResourceAsStream("database.properties");

        properties.load(input);
    }
}
