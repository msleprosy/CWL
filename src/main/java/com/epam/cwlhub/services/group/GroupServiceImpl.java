package com.epam.cwlhub.services.group;

import com.epam.cwlhub.dao.group.GroupDao;
import com.epam.cwlhub.dao.group.GroupDaoImpl;
import com.epam.cwlhub.entities.group.Group;
import com.epam.cwlhub.entities.user.UserEntity;

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
        return groupDao.findUsersGroups(id);
    }

    @Override
    public void joinGroup(UserEntity user, Group group) {
        groupDao.joinGroup(user, group);
    }
}
