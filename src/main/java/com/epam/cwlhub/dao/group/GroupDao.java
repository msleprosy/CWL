package com.epam.cwlhub.dao.group;

import com.epam.cwlhub.dao.BaseDao;
import com.epam.cwlhub.entities.group.Group;
import com.epam.cwlhub.entities.user.UserEntity;

import java.util.List;

public interface GroupDao extends BaseDao<Group> {

    List<Group> findUsersGroups(long id);

    void joinGroup(UserEntity user, Group group);
}
