package com.epam.cwlhub.dao;

import com.epam.cwlhub.entities.user.UserEntity;

public interface UserDao extends BaseDao<UserEntity> {
    UserEntity findByEmail(String email);
    void deleteByEmail(String email);
    UserEntity findUserByEmailAndPassword(String email, String password);
}
