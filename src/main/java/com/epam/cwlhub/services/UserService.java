package com.epam.cwlhub.services;

import com.epam.cwlhub.entities.user.UserEntity;

public interface UserService extends BaseService<UserEntity> {
    UserEntity findByEmail(String email);
    Boolean checkUserPassword(String password, UserEntity user);
    void deleteByEmail(String email);
    UserEntity findUserByEmailAndPassword(String email, String password);
}
