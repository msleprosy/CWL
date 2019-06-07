package com.epam.cwlhub.services;

import com.epam.cwlhub.entities.group.Group;
import com.epam.cwlhub.entities.user.UserEntity;

import java.util.List;

public interface GroupService extends BaseService<Group> {

    List<Group> findUsersGroups(Long id);

    void joinGroup(Long userId, Long groupId);

    void leaveGroup(Long userId, Long groupId);

    boolean checkMembership(Long userId, Long groupId);
}
