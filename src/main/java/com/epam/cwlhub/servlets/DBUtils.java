package com.epam.cwlhub.servlets;

import com.epam.cwlhub.entities.user.UserEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {

    public static UserEntity findUser(Connection conn, //
                                       String email, String password) throws SQLException {

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
        return null;
    }

}


