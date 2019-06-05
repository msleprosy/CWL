package com.epam.cwlhub.services.impl;

import com.epam.cwlhub.dao.GroupDao;
import com.epam.cwlhub.dao.impl.GroupDaoImpl;
import com.epam.cwlhub.entities.group.Group;
import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.exceptions.unchecked.GroupException;
import com.epam.cwlhub.services.GroupService;

import java.util.List;

public class GroupServiceImpl implements GroupService {

    private GroupDao groupDao = GroupDaoImpl.getInstance();
    private static volatile GroupServiceImpl serviceInstance;

    private GroupServiceImpl() {
    }

    public static GroupServiceImpl getInstance() {
        GroupServiceImpl localDaoInstance = serviceInstance;
        if (localDaoInstance == null) {
            synchronized (GroupServiceImpl.class) {
                localDaoInstance = serviceInstance;
                if (localDaoInstance == null) {
                    serviceInstance = localDaoInstance = new GroupServiceImpl();
                }
            }
        }
        return localDaoInstance;
    }

    @Override
    public Group insert(Group group) {
        if (group == null) {throw new GroupException("Group entity can't be empty");}
        return groupDao.insert(group);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {throw new GroupException("ID can't be empty");}
        groupDao.deleteById(id);
    }

    @Override
    public Group findById(Long id) {
        if (id == null) {throw new GroupException("ID can't be empty");}
        return groupDao.findById(id);
    }

    @Override
    public void update(Group group) {
        if (group == null) {throw new GroupException("Group entity can't be empty");}
        groupDao.update(group);
    }

    @Override
    public List<Group> findAll() {
        return groupDao.findAll();
    }

    @Override
    public List<Group> findUsersGroups(Long id) {
        if (id == null) {throw new GroupException("ID can't be empty");}
        return groupDao.findUserGroupsByUserId(id);
    }

    @Override
    public void joinGroup(Long userId, Long groupId) {
        if (userId == null || userId == null) {
            throw new GroupException("ID can't be empty");
        }
        groupDao.joinGroup(userId, userId);
    }

    @Override
    public void leaveGroup(Long userId, Long groupId) {
        //groupDao.leaveGroup(user, group);
    }

    @Override
    public boolean checkMembership(Long userIdd, Long groupId) {
        if (userIdd == null || groupId == null) {
            throw new GroupException("ID can't be empty");
        }
        return groupDao.checkMembership(userIdd, groupId);
    }
}
