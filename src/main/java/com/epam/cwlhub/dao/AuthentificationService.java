package com.epam.cwlhub.dao;

import com.epam.cwlhub.entities.user.UserEntity;

import java.sql.Connection;

public interface AuthentificationService {
    UserEntity signInUser(Connection conn, String email, String password);
}
