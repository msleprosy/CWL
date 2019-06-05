package com.epam.cwlhub.services.impl;

import com.epam.cwlhub.dao.GroupDao;
import com.epam.cwlhub.dao.impl.GroupDaoImpl;
import com.epam.cwlhub.entities.group.Group;
import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.services.GroupService;

import java.util.List;
import java.util.Optional;

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
        return groupDao.insert(group);
    }

    @Override
    public void deleteById(Long id) {
        groupDao.deleteById(id);
    }

    @Override
    public Optional<Group> findById(Long id) {
        return groupDao.findById(id);
    }

    @Override
    public void update(Group group) {
        groupDao.update(group);
    }

    @Override
    public List<Group> findAll() {
        return groupDao.findAll();
    }

    @Override
    public List<Group> findUsersGroups(long id) {
        return groupDao.findUserGroupsByUserId(id);
    }

    @Override
    public void joinGroup(UserEntity user, Group group) {
        groupDao.joinGroup(user, group);
    }

    @Override
    public void leaveGroup(Long user_id, Long group_id) {
        groupDao.leaveGroup(user_id, group_id);
    }

    @Override
    public boolean checkMembership(Long user_id, Long group_id) {
        return groupDao.checkMembership(user_id, group_id);

    }
}
