package com.epam.cwlhub.services.user;

import com.epam.cwlhub.dao.user.UserDao;
import com.epam.cwlhub.dao.user.impl.UserDaoImpl;
import com.epam.cwlhub.entities.user.UserEntity;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = UserDaoImpl.getInstance();

    private static volatile UserServiceImpl INSTANCE;

    public static UserServiceImpl getInstance() {
        UserServiceImpl localInstance = INSTANCE;
        if (localInstance == null) {
            synchronized (UserServiceImpl.class) {
                localInstance = INSTANCE;
                if (localInstance == null) {
                    INSTANCE = localInstance = new UserServiceImpl();
                }
            }
        }
        return localInstance;
    }

    @Override
    public Boolean checkUserPassword (String password, UserEntity user) {
        Boolean check = false;
            if (password.equals(user.getPassword())) {
                 check = true;
            }
            return check;
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        if (email != null){
            return userDao.findByEmail(email);
        }
        return Optional.empty();
    }

    @Override
    public void deleteByEmail(String email) {
        if (email != null) {
            userDao.deleteByEmail(email);
        }
    }

    @Override
    public UserEntity insert(UserEntity user) {
        if (user != null){
            userDao.insert(user);
        }
        return user;
    }

    @Override
    public void deleteById(Long id) {
        if (id != null) {
            userDao.deleteById(id);
        }
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        if (id != null){
            return userDao.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public void update(UserEntity user) {
        if (user != null){
            userDao.update(user);
        }
    }

    @Override
    public List<UserEntity> findAll() {
        return userDao.findAll();
    }
}
