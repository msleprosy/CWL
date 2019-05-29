package com.epam.cwlhub.storage.dbconnection;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

public class DBConnector implements DBConnection {

    private ComboPooledDataSource cpds = new ComboPooledDataSource();
    private static Properties property;
    private static final String HOST = property.getProperty("db.host");
    private static final String LOGIN = property.getProperty("db.login");
    private static final String PASSWORD = property.getProperty("db.password");
    private static final String PROPERTIES_PATH = "src/main/resources/config.properties";

    public Connection getDBConnection() throws Exception {
        FileInputStream fis;
        fis = new FileInputStream(PROPERTIES_PATH);
        property.load(fis);
        cpds.setJdbcUrl(HOST);
        cpds.setUser(LOGIN);
        cpds.setPassword(PASSWORD);
        return cpds.getConnection();
    }
}
