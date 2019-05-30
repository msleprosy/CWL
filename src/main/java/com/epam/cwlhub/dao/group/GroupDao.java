package com.epam.cwlhub.dao.group;

import com.epam.cwlhub.dao.BaseDao;
import com.epam.cwlhub.entities.group.Group;

import java.util.List;

public interface GroupDao extends BaseDao<Group> {
    List<Group> findUsersGroups(long id);
}
