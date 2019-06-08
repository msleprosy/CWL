package com.epam.cwlhub.services.impl;

import com.epam.cwlhub.dao.GroupDao;
import com.epam.cwlhub.dao.impl.GroupDaoImpl;
import com.epam.cwlhub.entities.group.Group;
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
        if (group == null) {
            throw new GroupException("Group entity can't be empty");
        }
        Group newGroup = groupDao.insert(group);
        groupDao.joinGroup(newGroup.getCreatorId(), newGroup.getId());
        return newGroup;
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new GroupException("ID can't be empty");
        }
        groupDao.deleteById(id);
    }

    @Override
    public Group findById(Long id) {
        if (id == null) {
            throw new GroupException("ID can't be empty");
        }
        return groupDao.findById(id);
    }

    @Override
    public void update(Group group) {
        if (group == null) {
            throw new GroupException("Group entity can't be empty");
        }
        groupDao.update(group);
    }

    @Override
    public List<Group> findAll() {
        return groupDao.findAll();
    }

    @Override
    public List<Group> findUsersGroups(Long id) {
        if (id == null) {
            throw new GroupException("ID can't be empty");
        }
        return groupDao.findUserGroupsByUserId(id);
    }

    @Override
    public Group findGroupByName(String groupName) {
        if (groupName == null) {
            throw new GroupException("Name can't be empty");
        }
        return groupDao.findGroupByName(groupName);
    }

    @Override
    public void joinGroup(Long userId, Long groupId) {
        if (userId == null || groupId == null) {
            throw new GroupException("ID can't be empty");
        }
        groupDao.joinGroup(userId, groupId);
    }

    @Override
    public void leaveGroup(Long userId, Long groupId) {
        groupDao.leaveGroup(userId, groupId);
    }

    @Override
    public boolean checkMembership(Long userId, Long groupId) {
        if (userId == null || groupId == null) {
            throw new GroupException("ID can't be empty");
        }
        return groupDao.checkMembership(userId, groupId);
    }
}
