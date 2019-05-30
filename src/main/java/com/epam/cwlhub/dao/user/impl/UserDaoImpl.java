package com.epam.cwlhub.dao.user.impl;

import com.epam.cwlhub.dao.user.UserDao;
import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.entities.user.UserType;
import com.epam.cwlhub.storage.dbconnection.DBConnection;
import com.epam.cwlhub.storage.dbconnection.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private final DBConnection dbConnection = DBConnector.getInstance();

    private static volatile UserDaoImpl INSTANCE;

    private static final String INSERT_USER_SQL_STATEMENT = "INSERT INTO users (firstname, lastname, email, "
                                                                            + " password, banned, user_type)"
                                                                            + "Values (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_USER_BY_ID_SQL_STATEMENT = "SELECT * FROM users WHERE user_id = ?";
    private static final String SELECT_USER_SQL_STATEMENT = "SELECT * FROM users WHERE";
    private static final String SELECT_USER_BY_GROUP_ID_SQL_STATEMENT = SELECT_USER_SQL_STATEMENT + "group_id = ?";
    private static final String SELECT_USER_BY_EMAIL_SQL_STATEMENT = SELECT_USER_SQL_STATEMENT + "email = ?";
    private static final String SELECT_ALL_USERS_SQL_STATEMENT = "SELECT * FROM users";
    private static final String DELETE_USER_SQL_STATEMENT = "DELETE FROM users WHERE";
    private static final String DELETE_USER_BY_ID_SQL_STATEMENT = DELETE_USER_SQL_STATEMENT + "user_id = ?";
    private static final String DELETE_USER_BY_EMAIL_SQL_STATEMENT = DELETE_USER_SQL_STATEMENT + "email = ?";
    private static final String UPDATE_USER_SQL_STATEMENT = "UPDATE users SET firstname = ?, lastname = ?, "
                                                                      + "email = ?, password = ?, banned = ?";

    public static UserDaoImpl getInstance() {
        UserDaoImpl localInstance = INSTANCE;
        if (localInstance == null) {
            synchronized (UserDaoImpl.class) {
                localInstance = INSTANCE;
                if (localInstance == null) {
                    INSTANCE = localInstance = new UserDaoImpl();
                }
            }
        }
        return localInstance;
    }

    @Override
    public UserEntity insert(UserEntity user) {
        try (Connection connection = dbConnection.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL_STATEMENT,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            appendPreparedStatementParametersToInsertUser(preparedStatement, user);
            preparedStatement.executeUpdate();
            try (ResultSet generatedId = preparedStatement.getGeneratedKeys()) {
                if (generatedId.next()) {
                    user.setId(generatedId.getLong(1));
                }
            }
            return user;
        } catch (Exception ex) {
            throw new UserException("Can't insert new user", ex);
        }
    }

    @Override
    public Optional<UserEntity> findById(long id) {
        try (Connection connection = dbConnection.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID_SQL_STATEMENT)){
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                return Optional.ofNullable(mapUser(rs));
            } else {
                return Optional.empty();
            }
        } catch (Exception ex) {
            throw new UserException("Can't find the user with id" + id, ex);
        }
    }
    
    public List<UserEntity> findByGroupId(long id) {
        try (Connection connection = dbConnection.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_GROUP_ID_SQL_STATEMENT)){
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            return getUsersFromResultSet(rs);
        } catch (Exception ex) {
            throw new UserException("Can't find the user with id" + id, ex);
        }
    }

    @Override
    public List<UserEntity> findAll(/*long id*/) {
        try (Connection connection = dbConnection.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS_SQL_STATEMENT)){
           // preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            return getUsersFromResultSet(rs);
        } catch (Exception ex) {
            throw new UserException("Can't find any users", ex);
        }
    }

    public Optional<UserEntity> findByEmail(String email) throws Exception {
        try (Connection connection = dbConnection.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL_SQL_STATEMENT)){
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                return Optional.ofNullable(mapUser(rs));
            } else {
                return Optional.empty();
            }
        } catch (Exception ex) {
            throw new UserException("Can't find the user with email" + email, ex);
        }
    }

    @Override
    public void deleteById(long id) {
        try (Connection connection = dbConnection.getDBConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_ID_SQL_STATEMENT)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            throw new UserException("Can't delete the user with id" + id, ex);
        }
    }

    public void deleteByEmail(String email) {
        try (Connection connection = dbConnection.getDBConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_EMAIL_SQL_STATEMENT)) {
            preparedStatement.setString(1, email);
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            throw new UserException("Can't delete the user with email" + email, ex);
        }
    }

    @Override
    public void update(UserEntity user) {
        try (Connection connection = dbConnection.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL_STATEMENT)) {
            appendPreparedStatementParametersToIUpdateUser(preparedStatement, user);
             preparedStatement.executeUpdate();
        } catch (Exception ex) {
            throw new UserException("Can't update the user with id" + user.getId(), ex);
        }
    }

    private List<UserEntity> getUsersFromResultSet(ResultSet rs) throws SQLException {
        List<UserEntity> result = new ArrayList<>();
        while (rs.next()){
            result.add(mapUser(rs));
        }
        return result;
    }

    private void appendPreparedStatementParametersToInsertUser(PreparedStatement preparedStatement, UserEntity user) throws SQLException {
        String userType = String.valueOf(UserType.SIMPLE_USER);
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setBoolean(5, user.isBanned());
        preparedStatement.setString(6, userType);
    }

    private void appendPreparedStatementParametersToIUpdateUser(PreparedStatement preparedStatement, UserEntity user) throws SQLException {
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setBoolean(5, user.isBanned());
    }

    private UserEntity mapUser(ResultSet rs) {
        UserEntity user = new UserEntity();
        try{
            long id = rs.getLong("user_id");
            if (id != 1) {
                user.setId(id);
                user.setLastName(rs.getString("lastname"));
                user.setFirstName(rs.getString("firstname"));
                user.setEmail(rs.getString("email"));
                user.setBanned(rs.getBoolean("banned"));
                user.setUserType(UserType.SIMPLE_USER);
            }
            return user;
        }catch (SQLException ex){
            throw new UserException("can't map resultset to the user entity", ex);
        }
    }
}

