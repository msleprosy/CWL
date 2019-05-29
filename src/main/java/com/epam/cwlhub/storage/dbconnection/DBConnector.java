package com.epam.cwlhub.storage.dbconnection;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

public class DBConnector implements DBConnection {

    private ComboPooledDataSource cpds = new ComboPooledDataSource();

    public Connection getDBConnection() throws Exception {
        FileInputStream fis;
        Properties property = new Properties();
        fis = new FileInputStream("src/main/resources/config.properties");
        property.load(fis);
        String host = property.getProperty("db.host");
        String login = property.getProperty("db.login");
        String password = property.getProperty("db.password");
        cpds.setJdbcUrl(host);
        cpds.setUser(login);
        cpds.setPassword(password);
        return cpds.getConnection();

    }
}
