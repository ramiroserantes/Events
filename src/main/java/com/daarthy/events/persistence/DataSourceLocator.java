package com.daarthy.events.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataSourceLocator {

    private static DataSourceLocator instance;
    private HikariDataSource dataSource;
    private Properties properties;

    public DataSourceLocator() throws IOException {
        loadDatabaseProperties();
        initializeDataSource();
    }

    public static synchronized DataSourceLocator getInstance() throws IOException {
        if (instance == null) {
            instance = new DataSourceLocator();
        }
        return instance;
    }

    private void initializeDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty("url"));
        config.setUsername(properties.getProperty("username"));
        config.setPassword(properties.getProperty("password"));
        config.addDataSourceProperty("serverTimezone", "UTC");

        config.setMaximumPoolSize(20);

        this.dataSource = new HikariDataSource(config);
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    private void loadDatabaseProperties() throws IOException {
        properties = new Properties();
        InputStream input = getClass().getClassLoader().getResourceAsStream("liquibase-prod.properties");

        properties.load(input);
    }
}
