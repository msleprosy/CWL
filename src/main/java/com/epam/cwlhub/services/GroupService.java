package com.epam.cwlhub.services;

import com.epam.cwlhub.entities.group.Group;
import com.epam.cwlhub.entities.user.UserEntity;

import java.util.List;

public interface GroupService extends BaseService<Group> {

    List<Group> findUsersGroups(long id);

    void joinGroup(UserEntity user, Group group);

    void leaveGroup(UserEntity user, Group group);

    boolean checkMembership(Long user_id, Long group_id);
}
