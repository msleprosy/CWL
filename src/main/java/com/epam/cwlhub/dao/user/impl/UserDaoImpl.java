package com.epam.cwlhub.dao.user.impl;

import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.entities.user.UserType;
import com.epam.cwlhub.storage.dbconnection.DBConnection;
import com.epam.cwlhub.storage.dbconnection.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl {
    private final DBConnection dbConnection = DBConnector.getInstance();

    private static volatile UserDaoImpl INSTANCE;

    private static final String INSERT_USER_SQL_STATEMENT = "INSERT INTO users (firstname, lastname, email, "
                                                            + " password, banned, user_type)"
                                                            + "Values (?, ?, ?, ?, ?, ?)";

    public static UserDaoImpl getInstance(){
        UserDaoImpl localInstance = INSTANCE;
        if (localInstance == null){
            synchronized (UserDaoImpl.class){
                localInstance = INSTANCE;
                if (localInstance == null){
                    INSTANCE = localInstance = new UserDaoImpl();
                }
            }
        }
        return localInstance;
    }

    public UserEntity insert(UserEntity user) {
        String userType = String.valueOf(UserType.SIMPLE_USER);
        String sql = "INSERT INTO users (firstname, lastname, email, password, banned, user_type) Values (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = dbConnection.getDBConnection().prepareStatement(sql);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setBoolean(5, user.isBanned());
            preparedStatement.setString(6, userType);
            int rows = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<UserEntity> findById(long id) throws Exception {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        PreparedStatement preparedStatement = dbConnection.getDBConnection().prepareStatement(sql);
        preparedStatement.setLong(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next())
            return Optional.of(UserMapper.mapUser(rs));
        else
            return Optional.empty();
    }

    public List<UserEntity> findByGroupId(long id) {
    }

    public List<UserEntity> findAll(long id) {
    }

    private List<UserEntity> getUsersFromResultSet(ResultSet rs) throws SQLException {
    }

    private void appendPreparedStatementParametersToInsetUser(PreparedStatement ps, UserEntity userEntity) throws SQLException {
    }

    private UserEntity mapUser(ResultSet rs) {
    }

    private void appendPreparedStatementParametersToUpdateUser(PreparedStatement ps, UserEntity userEntity) throws SQLException {
    }

    public Optional<UserEntity> findByEmail(String email) throws Exception {
        String sql = "SELECT * FROM users WHERE email = ?";
        PreparedStatement preparedStatement = dbConnection.getDBConnection().prepareStatement(sql);
        preparedStatement.setString(1, email);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next())
            return Optional.of(UserMapper.mapUser(rs));
        else
            return Optional.empty();
    }

    public void deleteById(long id) {
        try {
            Optional<UserEntity> found = findById(id);
            if (found.isPresent()) {
                String sql = "DELETE FROM users WHERE user_id = ?";
                PreparedStatement preparedStatement = dbConnection.getDBConnection().prepareStatement(sql);
                preparedStatement.setLong(1, id);
                int rows = preparedStatement.executeUpdate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteByEmail(String email) {
        try {
            Optional<UserEntity> found = findByEmail(email);
            if (found.isPresent()) {
                String sql = "DELETE FROM users WHERE email = ?";
                PreparedStatement preparedStatement = dbConnection.getDBConnection().prepareStatement(sql);
                preparedStatement.setString(1, email);
                int rows = preparedStatement.executeUpdate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void update(UserEntity user) {
        String sql = "UPDATE users SET firstname = ?, lastname = ?, email = ?, password = ?, banned = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.getDBConnection().prepareStatement(sql);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setBoolean(5, user.isBanned());
            int rows = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
