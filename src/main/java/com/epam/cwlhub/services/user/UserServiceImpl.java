package com.epam.cwlhub.services.user;

import com.epam.cwlhub.entities.user.UserEntity;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = UserDaoImpl.getInstance();

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public void deleteByEmail(String email) {

    }

    @Override
    public UserEntity insert(UserEntity entity) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void update(UserEntity entity) {

    }

    @Override
    public List<UserEntity> findAll() {
        return null;
    }
}
