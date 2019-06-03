package com.epam.cwlhub.services;

import com.epam.cwlhub.entities.group.Group;
import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.services.BaseService;

import java.util.List;

public interface GroupService extends BaseService<Group> {

    List<Group> findUsersGroups(long id);

    void joinGroup(UserEntity user, Group group);
}
