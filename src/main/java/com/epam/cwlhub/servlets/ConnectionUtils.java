package com.epam.cwlhub.servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {
    public static Connection getConnection()
            throws ClassNotFoundException, SQLException {

        String jdbcURL = "jdbc:postgresql://ECSC00A04EEC.epam.com:5432/postgres";
        String dbUser = "postgres";
        String dbPassword = "CWLHubHardPassword228";
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);


        return conn;
    }


    public static void closeQuietly(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
        }
    }

    public static void rollbackQuietly(Connection conn) {
        try {
            conn.rollback();
        } catch (Exception e) {
        }
    }
}

