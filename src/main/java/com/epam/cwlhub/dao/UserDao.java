package com.epam.cwlhub.dao;

import com.epam.cwlhub.entities.user.UserEntity;

import java.util.Optional;

import static com.epam.cwlhub.storage.Storage.usersList;

public class UserDao {

    public void add(UserEntity user) {
        usersList.add(user);
    }

    public void deleteById(long id) {
        findUserById(id).map(user -> usersList.remove(user));
    }

    public void update(UserEntity user){}

    public void printAll() {
        usersList.forEach(user -> System.out.println(user));
    }

    private Optional<UserEntity> findUserById(long userId) {
        return usersList.stream().filter(user -> userId == user.getId()).findAny();
    }
}
