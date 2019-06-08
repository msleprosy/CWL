package com.epam.cwlhub.services.impl;

import com.epam.cwlhub.dao.UserDao;
import com.epam.cwlhub.dao.impl.UserDaoImpl;
import com.epam.cwlhub.entities.user.UserByLastnameComparator;
import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.exceptions.unchecked.UserException;
import com.epam.cwlhub.services.UserService;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    public Boolean checkUserPassword(String password, UserEntity user) {
        Boolean check = false;
        String passwordWithHash = DigestUtils.md5Hex(password);
        if (passwordWithHash.equals(user.getPassword())) {
            check = true;
        }
        return check;
    }

    @Override
    public UserEntity findByEmail(String email) {
        if (email == null) {
            throw new UserException("Email can't be empty");
        }
        return userDao.findByEmail(email);
    }

    @Override
    public void deleteByEmail(String email) {
        if (email == null) {
            throw new UserException("Email can't be empty");
        }
        userDao.deleteByEmail(email);
    }

    @Override
    public UserEntity findUserByEmailAndPassword(String email, String password) {
        if (email == null || password == null) {
            throw new UserException("Email and password can't be empty");
        }
        String passwordWithHash = DigestUtils.md5Hex(password);
        return userDao.findUserByEmailAndPassword(email, passwordWithHash);
    }

    @Override
    public UserEntity insert(UserEntity user) {
        if (user == null) {
            throw new UserException("User entity can't be empty");
        }
        String password = user.getPassword();
        String passwordWithHash = DigestUtils.md5Hex(password);
        user.setPassword(passwordWithHash);
        userDao.insert(user);
        return user;
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new UserException("ID can't be empty");
        }
        userDao.deleteById(id);
    }

    @Override
    public UserEntity findById(Long id) {
        if (id == null) {
            throw new UserException("ID can't be empty");
        }
        return userDao.findById(id);
    }

    @Override
    public void update(UserEntity user) {
        if (user == null) {
            throw new UserException("User entity can't be empty");
        }
        userDao.update(user);
    }

    @Override
    public List<UserEntity> findAll() {
        List<UserEntity> result = userDao.findAll();
        Collections.sort(result, new UserByLastnameComparator());
        return result;
    }
}
