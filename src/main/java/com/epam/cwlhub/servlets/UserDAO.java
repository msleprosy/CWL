package com.epam.cwlhub.servlets;

import com.epam.cwlhub.entities.user.UserEntity;

import java.sql.*;

public class UserDAO {

    public int registerUser(UserEntity user)  {
        String INSERT_USERS_SQL = "INSERT INTO users" +
                "  (email, password, firstname, lastname, user_type) VALUES " +
                " (?, ?, ?, ?, ?);";

        int result = 0;

        try{        Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException e){e.printStackTrace();}

        try (Connection connection = DriverManager
                .getConnection("jdbc:postgresql://ECSC00A04EEC.epam.com:5432/postgres", "postgres", "CWLHubHardPassword228");

             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getUserType().toString());


            System.out.println(preparedStatement);
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {

            printSQLException(e);
        }
        return result;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
