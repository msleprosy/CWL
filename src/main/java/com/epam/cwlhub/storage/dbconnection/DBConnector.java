package com.epam.cwlhub.storage.dbconnection;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DBConnector implements DBConnection {

    private static final String HOST = "db.host";
    private static final String LOGIN = "db.login";
    private static final String PASSWORD = "db.password";
    private static final String PROPERTIES_PATH = "config.properties";
    private static ComboPooledDataSource cpds = new ComboPooledDataSource();
    private static DBConnector dbConnector = null;

    private DBConnector(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cpds.setJdbcUrl("jdbc:postgresql://localhost:5432/CWL_db");
        cpds.setUser("postgres");
        cpds.setPassword("postgres");

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
