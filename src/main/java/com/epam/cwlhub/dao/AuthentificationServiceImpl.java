package com.epam.cwlhub.dao;

import com.epam.cwlhub.dao.AuthentificationService;
import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.storage.dbconnection.DBConnection;
import com.epam.cwlhub.storage.dbconnection.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthentificationServiceImpl implements AuthentificationService {
    private final DBConnection dbConnection = DBConnector.getInstance();

    @Override
    public UserEntity signInUser(Connection conn, String email, String password) {
        try {

            String sql = "SELECT * FROM users WHERE email = ? and password = ?";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, email);
            pstm.setString(2, password);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String lastName = rs.getString("lastName");
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
}


