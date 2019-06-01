package com.epam.cwlhub.dao;

import com.epam.cwlhub.entities.user.UserEntity;

import java.sql.*;

public class AuthentificationServiceImpl implements AuthentificationService {
    private static final String LASTNAME = "lastName";

    @Override
    public UserEntity signInUser(Connection conn, String email, String password) {
        try {

            String sql = "SELECT * FROM users WHERE email = ? and password = ?";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, email);
            pstm.setString(2, password);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String lastName = rs.getString(LASTNAME);
                UserEntity user = new UserEntity();
                user.setEmail(email);
                user.setPassword(password);
                user.setLastName(lastName);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int registerUser(Connection connection, UserEntity user) {
        String INSERT_USERS_SQL = "INSERT INTO users" +
                "  (email, password, firstname, lastname, user_type) VALUES " +
                " (?, ?, ?, ?, ?);";

        int result = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
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
        for (Throwable e : ex) {
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


