package com.epam.cwlhub.storage.dbconnection;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.SQLException;
import java.sql.Connection;

public class DBConnector implements DBConnection{
    public Connection getDBConnection() throws SQLException {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setJdbcUrl("jdbc:postgresql://localhost:5432/CWL_db");
        cpds.setUser("postgres");
        cpds.setPassword("postgres");
        return cpds.getConnection();

    }
}
