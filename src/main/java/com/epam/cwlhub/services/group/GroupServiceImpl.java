package com.epam.cwlhub.services.group;

import com.epam.cwlhub.dao.group.GroupDao;
import com.epam.cwlhub.dao.group.GroupDaoImpl;
import com.epam.cwlhub.entities.group.Group;

import java.util.List;
import java.util.Optional;

public class GroupServiceImpl implements GroupService {

    private GroupDao groupDao = GroupDaoImpl.getInstance();

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
}
