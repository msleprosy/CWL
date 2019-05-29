package com.epam.cwlhub.storage.dbconnection;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;

public class DBConnector implements DBConnection {

    private ComboPooledDataSource cpds = new ComboPooledDataSource();

    public Connection getDBConnection() throws Exception {
/*        FileInputStream fis;
        Properties property = new Properties();
        fis = new FileInputStream("src/main/resources/config.properties");
        property.load(fis);
        String host = property.getProperty("db.host");
        String login = property.getProperty("db.login");
        String password = property.getProperty("db.password");

        System.out.println("HOST: " + host
                + ", LOGIN: " + login
                + ", PASSWORD: " + password);*/

        cpds.setJdbcUrl("jdbc:postgresql://localhost:5432/CWL_db");
        cpds.setUser("postgres");
        cpds.setPassword("postgres");
        return cpds.getConnection();

    }
}
