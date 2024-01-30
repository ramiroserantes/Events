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

    public void close() {
        try {
            dataSource.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al cerrar la conexión", e);
        }
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public Connection getConnection() throws SQLException {
        System.out.println(dataSource.getHikariPoolMXBean().getActiveConnections());
        return dataSource.getConnection();
    }

    private void loadDatabaseProperties() throws IOException {
        properties = new Properties();
        InputStream input = getClass().getClassLoader().getResourceAsStream("database.properties");

        if (input == null) {
            System.out.println("Sorry, unable to find database.properties");
            return;
        }

        properties.load(input);
    }
}
