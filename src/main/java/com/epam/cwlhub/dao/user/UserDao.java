package com.epam.cwlhub.dao.user;

import com.epam.cwlhub.dao.BaseDao;
import com.epam.cwlhub.entities.user.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<UserEntity> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByEmailAndPassword(String email, String password);
    void deleteByEmail(String email);
}
