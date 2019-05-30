package com.epam.cwlhub.storage.dbconnection;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DBConnector implements DBConnection {

    private static final String HOST = "db.host";
    private static final String LOGIN = "db.login";
    private static final String PASSWORD = "db.password";
    private static final String PROPERTIES_PATH = "database/config/config.properties";
    private static DBConnector dbConnector = null;
    private static ComboPooledDataSource cpds;
    private InputStream fis = this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_PATH);

    static{
        cpds = new ComboPooledDataSource();
        Properties property = new Properties();
        try {
            property.load(new DBConnector().fis);
            cpds.setJdbcUrl(property.getProperty(HOST));
            cpds.setUser(property.getProperty(LOGIN));
            cpds.setPassword(property.getProperty(PASSWORD));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DBConnector(){
    }

    public static DBConnector getInstance() {
        if (dbConnector == null) {
            dbConnector = new DBConnector();
        }
        return dbConnector;
    }

    public Connection getDBConnection() throws Exception {
        return cpds.getConnection();
    }
}
