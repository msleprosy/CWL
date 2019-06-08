package com.epam.cwlhub.dao.impl;

import com.epam.cwlhub.dao.GroupDao;
import com.epam.cwlhub.entities.group.Group;
import com.epam.cwlhub.exceptions.unchecked.GroupException;
import com.epam.cwlhub.storage.dbconnection.DBConnection;
import com.epam.cwlhub.storage.dbconnection.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GroupDaoImpl implements GroupDao {

    private static volatile GroupDaoImpl daoInstance;

    private GroupDaoImpl() {
    }

    private DBConnection dbConnector = DBConnector.getInstance();

    public static GroupDaoImpl getInstance() {
        if (daoInstance == null) {
            daoInstance = new GroupDaoImpl();
        }
        return daoInstance;
    }

    private static final String SQL_ADD_GROUP = "INSERT INTO groups (name, description, creator_id) VALUES (?, ?, ?)";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM groups WHERE group_id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM groups WHERE group_id  = ?";
    private static final String SQL_UPDATE = "UPDATE groups SET name = ?, description = ?, creator_id = ? " +
            " WHERE group_id = ?";
    private static final String SQL_FIND_ALL_GROUPS = "SELECT * FROM groups";
    private static final String SQL_FIND_USER_GROUPS
            = "SELECT groups.group_id, groups.name, groups.description, groups.creator_id " +
            " FROM groups JOIN user_group " +
            " ON groups.group_id = user_group.group_id " +
            " JOIN users " +
            " ON user_group.user_id = users.user_id " +
            " WHERE user_group.user_id = ?";
    private static final String SQL_ADD_USER_TO_GROUP = "INSERT INTO user_group (user_id, group_id) VALUES (?, ?)";
    private static final String SQL_DELETE_USER_GROUP_GROUP = "DELETE FROM user_group WHERE user_id = ? AND group_id = ?";
    private static final String SQL_CHECK_MEMBERSHIP = "SELECT user_id, group_id  FROM user_group WHERE user_id = ? AND group_id = ?";
    private static final String SQL_FIND_GROUP_BY_NAME = "SELECT * FROM groups WHERE name = ?";


    @Override
    public Group insert(Group group) {
        try (Connection connection = dbConnector.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_GROUP,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            appendPreparedStatementParametersToInsertGroup(preparedStatement, group);
            preparedStatement.executeUpdate();
            try (ResultSet generatedId = preparedStatement.getGeneratedKeys()) {
                if (generatedId.next()) {
                    group.setId(generatedId.getLong(1));
                }
            }
            return group;
        } catch (SQLException e) {
            throw new GroupException("Error inserting group: " + group.getName(), e);
        }
    }

    @Override
    public Group findById(long id) {
        try (Connection connection = dbConnector.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return Optional.of(mapGroup(rs))
                    .orElseThrow(() -> new GroupException("Error while mapping group"));
        } catch (SQLException e) {
            throw new GroupException("Error while trying to find group with id: " + id, e);
        }
    }

    @Override
    public void deleteById(long id) throws GroupException {
        try (Connection connection = dbConnector.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            if (id == 0) {
                throw new IllegalArgumentException("You can't delete common group");
            }
            findById(id);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new GroupException("Error deleting group", e);
        }
    }

    @Override
    public void update(Group group) {
        try (Connection connection = dbConnector.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            appendPreparedStatementParametersToUpdateGroup(preparedStatement, group);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new GroupException("Can't update group " + group.getName());
        }
    }

    @Override
    public List<Group> findAll() {
        try (Connection connection = dbConnector.getDBConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_GROUPS)) {
            List<Group> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(mapGroup(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new GroupException("Can't select *", e);
        }
    }

    @Override
    public List<Group> findUserGroupsByUserId(Long id) {
        try (Connection connection = dbConnector.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_GROUPS)) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            List<Group> result = new ArrayList<>();
            while (rs.next()) {
                result.add(mapGroup(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new GroupException("Error while trying to find groups for user with id: " + id, e);
        }
    }

    @Override
    public Group findGroupByName(String groupName) {
        try (Connection connection = dbConnector.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_GROUP_BY_NAME)) {
            preparedStatement.setString(1, groupName);
            ResultSet rs = preparedStatement.executeQuery();
            Group result = new Group();
            if (rs.next()) {
                result = mapGroup(rs);
            } else {
                result = null;
            }
            return result;
        } catch (SQLException e) {
            throw new GroupException("Error while trying to find groups with name: " + groupName, e);
        }
    }

    @Override
    public void joinGroup(Long userId, Long groupId) {

        try (Connection connection = dbConnector.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_USER_TO_GROUP)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, groupId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new GroupException("Error adding user with ID " + userId
                    + " to group with ID " + groupId, e);
        }
    }

    @Override
    public void leaveGroup(Long userId, Long groupId) {
        try (Connection connection = dbConnector.getDBConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER_GROUP_GROUP);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, groupId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new GroupException("Error deleting user " + userId + " from group " + groupId, e);
        }
    }

    @Override
    public boolean checkMembership(Long userId, Long groupId) {
        try (Connection connection = dbConnector.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHECK_MEMBERSHIP)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, groupId);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new GroupException("Error checking user's membership in group", e);
        }
    }


    private static Group mapGroup(ResultSet rs) throws SQLException {
        Group group = new Group();
        group.setId(rs.getLong("group_id"));
        group.setName(rs.getString("name"));
        group.setDescription(rs.getString("description"));
        group.setCreatorId(rs.getLong("creator_id"));

        return group;
    }

    private void appendPreparedStatementParametersToInsertGroup(PreparedStatement preparedStatement, Group group)
            throws SQLException {
        preparedStatement.setString(1, group.getName());
        preparedStatement.setString(2, group.getDescription());
        preparedStatement.setLong(3, group.getCreatorId());
    }

    private void appendPreparedStatementParametersToUpdateGroup(PreparedStatement preparedStatement, Group group)
            throws SQLException {
        preparedStatement.setString(1, group.getName());
        preparedStatement.setString(2, group.getDescription());
        preparedStatement.setLong(3, group.getCreatorId());
        preparedStatement.setLong(4, group.getId());
    }
}