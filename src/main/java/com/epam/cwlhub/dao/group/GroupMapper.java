package com.epam.cwlhub.dao.group;

import com.epam.cwlhub.entities.group.Group;

import java.sql.ResultSet;
import java.sql.SQLException;

final class GroupMapper {

    private GroupMapper() {}

    static Group mapGroup(ResultSet rs) {
        try {
            Group group = new Group();
            group.setId(rs.getLong("group_id"));
            group.setName(rs.getString("name"));
            group.setDescription(rs.getString("description"));

            return group;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
