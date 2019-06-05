package com.epam.cwlhub.services;

import com.epam.cwlhub.entities.group.Group;
import com.epam.cwlhub.entities.user.UserEntity;

import java.util.List;

public interface GroupService extends BaseService<Group> {

    List<Group> findUsersGroups(long id);

    void joinGroup(Long user_id, Long group_id);

    void leaveGroup(Long user_id, Long group_id);

    boolean checkMembership(Long user_id, Long group_id);
}
