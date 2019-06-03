package com.epam.cwlhub.services.user;

import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.services.BaseService;

import java.util.Optional;

public interface UserService extends BaseService<UserEntity> {
    Optional<UserEntity> findByEmail(String email);
    void deleteByEmail(String email);
    Optional<UserEntity> signInUser(String email, String password);
}
