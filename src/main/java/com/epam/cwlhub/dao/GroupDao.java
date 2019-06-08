package com.epam.cwlhub.dao;

import com.epam.cwlhub.entities.group.Group;
import com.epam.cwlhub.entities.user.UserEntity;

import java.util.List;

public interface GroupDao extends BaseDao<Group> {

    List<Group> findUserGroupsByUserId(Long id);

    void joinGroup(Long userId, Long groupId);

    void leaveGroup(Long userId, Long groupId);

    boolean checkMembership(Long userId, Long groupId);

    Group findGroupByName(String groupName);
}