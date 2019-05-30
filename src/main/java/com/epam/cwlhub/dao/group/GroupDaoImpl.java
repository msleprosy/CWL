package com.epam.cwlhub.dao.group;

import com.epam.cwlhub.entities.group.Group;

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

    private DBConnection dbConnector = DBConnector.getDBConnectorInstance();

    public static GroupDaoImpl getInstance() {
        GroupDaoImpl localDaoInstance = daoInstance;
        if (localDaoInstance == null) {
            synchronized (GroupDaoImpl.class) {
                localDaoInstance = daoInstance;
                if (localDaoInstance == null) {
                    daoInstance = localDaoInstance = new GroupDaoImpl();
                }
            }
        }
        return localDaoInstance;
    }

    private static final String SQL_ADD = "INSERT INTO users (name, description, creator_id) Values (?, ?, ?)";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM groups WHERE group_id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM groups WHERE group_id  = ?";
    private static final String SQL_UPDATE = "UPDATE groups SET name = ?, description = ?, creator_id = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM groups";


    @Override
    public Group insert(Group group) {

        try (Connection connection = dbConnector.getDBConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD);
            preparedStatement.setString(1, group.getName());
            preparedStatement.setString(2, group.getDescription());
            preparedStatement.setLong(3, group.getCreatorId());
            preparedStatement.executeQuery();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            long id = generatedKeys.getLong(0);
            group.setId(id);
            return group;
        } catch (Exception e) {
            throw new GroupException("Error inserting group: " + group.getName(), e);
        }
    }

    @Override
    public Optional<Group> findById(long id) {
        try (Connection connection = dbConnector.getDBConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return Optional.ofNullable(mapGroup(rs));
            } else {
                return Optional.empty();
            }
        } catch (Exception e){
            throw new GroupException("Error while trying to find group with id: "+ id, e);
        }
    }

    @Override
    public void deleteById(long id)  {
        try (Connection connection = dbConnector.getDBConnection()) {
            Optional<Group> found = findById(id);
            if (found.isPresent()) {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID);
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Group group) {

        try (Connection connection = dbConnector.getDBConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, group.getName());
            preparedStatement.setString(2, group.getDescription());
            preparedStatement.setLong(3, group.getCreatorId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Group> findAll() {

        try (Connection connection = dbConnector.getDBConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL);
            List<Group> result = new ArrayList<>();

            while (resultSet.next()) {
                result.add(mapGroup(resultSet));
            }

            return result;
        } catch (Exception e) {
            throw new GroupException("Can't select *", e);
        }
    }
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
