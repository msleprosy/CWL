package com.epam.cwlhub.storage.dbconnection;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

public class DBConnector implements DBConnection {

    private static final String HOST = "db.host";
    private static final String LOGIN = "db.login";
    private static final String PASSWORD = "db.password";
    private static final String PROPERTIES_PATH = "src/main/resources/database/config/config.properties";
    private static DBConnector dbConnector = null;
    private ComboPooledDataSource cpds;

    private DBConnector(){
        cpds = new ComboPooledDataSource();
    }

    public static DBConnector getInstance() {
        if (dbConnector == null) {
            dbConnector = new DBConnector();
        }
        return dbConnector;
    }

    public Connection getDBConnection() throws Exception {
        Properties property = new Properties();
        FileInputStream fis;
        fis = new FileInputStream(PROPERTIES_PATH);
        property.load(fis);
        cpds.setJdbcUrl(property.getProperty(HOST));
        cpds.setUser(property.getProperty(LOGIN));
        cpds.setPassword(property.getProperty(PASSWORD));
        return cpds.getConnection();
    }
}
