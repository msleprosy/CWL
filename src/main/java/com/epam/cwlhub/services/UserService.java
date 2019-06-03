package com.epam.cwlhub.services;

import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.services.BaseService;

import java.util.Optional;

public interface UserService extends BaseService<UserEntity> {
    Optional<UserEntity> findByEmail(String email);
    Boolean checkUserPassword(String password, UserEntity user);
    void deleteByEmail(String email);
}
