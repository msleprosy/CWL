package com.epam.cwlhub.storage.dbconnection;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector implements DBConnection {

    private static final String HOST = "db.host";
    private static final String LOGIN = "db.login";
    private static final String PASSWORD = "db.password";
    private static final String PROPERTIES_PATH = "database/config/config.properties";
    private static ComboPooledDataSource cpds = new ComboPooledDataSource();
    private static DBConnector dbConnector = null;

    private DBConnector(){
    }

    {
        Properties property = new Properties();
        try (InputStream fis = this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_PATH)) {
            property.load(fis);
            cpds.setJdbcUrl(property.getProperty(HOST));
            cpds.setUser(property.getProperty(LOGIN));
            cpds.setPassword(property.getProperty(PASSWORD));
        } catch (Exception e) {
            throw new RuntimeException("Error loading resource: ", e);
        }
    }

    public static DBConnector getInstance() {
        if (dbConnector == null) {
            dbConnector = new DBConnector();
        }
        return dbConnector;
    }

    public Connection getDBConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return cpds.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Could not retrieve connection: ", e);
        }
    }
}
