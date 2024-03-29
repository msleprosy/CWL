package com.epam.cwlhub.dao.impl;

import com.epam.cwlhub.dao.UserDao;
import com.epam.cwlhub.entities.user.UserEntity;
import com.epam.cwlhub.entities.user.UserType;
import com.epam.cwlhub.storage.dbconnection.DBConnection;
import com.epam.cwlhub.storage.dbconnection.DBConnector;
import com.epam.cwlhub.exceptions.unchecked.UserException;

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
    private static final String SELECT_USER_SQL_STATEMENT = "SELECT * FROM users WHERE ";
    private static final String SELECT_USER_BY_EMAIL_SQL_STATEMENT = SELECT_USER_SQL_STATEMENT + "email = ?";
    private static final String SELECT_ALL_USERS_SQL_STATEMENT = "SELECT * FROM users";
    private static final String DELETE_USER_SQL_STATEMENT = "DELETE FROM users WHERE ";
    private static final String DELETE_USER_BY_ID_SQL_STATEMENT = DELETE_USER_SQL_STATEMENT + "user_id = ?";
    private static final String DELETE_USER_BY_EMAIL_SQL_STATEMENT = DELETE_USER_SQL_STATEMENT + "email = ?";
    private static final String UPDATE_USER_SQL_STATEMENT = "UPDATE users SET firstname = ?, lastname = ?, "
                                                                    + "email = ?, password = ?, banned = ?, user_type = ? WHERE user_id = ?";
    private static final String SELECT_USER_BY_EMAIL_AND_PASSWORD = SELECT_USER_SQL_STATEMENT + "email = ? AND password = ?";

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
            appendPreparedStatementParameters(preparedStatement, user);
            preparedStatement.setString(6, UserType.SIMPLE_USER.toString());
            preparedStatement.executeUpdate();
            try (ResultSet generatedId = preparedStatement.getGeneratedKeys()) {
                if (generatedId.next()) {
                    user.setId(generatedId.getLong(1));
                }
            }
            return user;
        } catch (SQLException ex) {
            throw new UserException("Can't insert new user ", ex);
        }
    }

    @Override
    public UserEntity findById(long id) {
        try (Connection connection = dbConnection.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID_SQL_STATEMENT)) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return Optional.of(mapUser(rs)).orElseThrow(() -> new UserException("Can't find the user with id " + id));
        } catch (SQLException ex) {
            throw new UserException("Can't find the user with id " + id, ex);
        }
    }

    @Override
    public List<UserEntity> findAll() {
        try (Connection connection = dbConnection.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS_SQL_STATEMENT)) {
            ResultSet rs = preparedStatement.executeQuery();
            return getUsersFromResultSet(rs);
        } catch (SQLException ex) {
            throw new UserException("Can't find any users ", ex);
        }
    }

    @Override
    public UserEntity findByEmail(String email) {
        try (Connection connection = dbConnection.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL_SQL_STATEMENT)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()){
                return null;
            }
            return mapUser(rs);
        } catch (SQLException ex) {
            throw new UserException("Can't find the user with email " + email, ex);
        }
    }

    @Override
    public UserEntity findUserByEmailAndPassword(String email, String password){
        try (Connection connection = dbConnection.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL_AND_PASSWORD)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return Optional.of(mapUser(rs))
                    .orElseThrow(() ->
                            new UserException("Can't find the user with email " + email +"and password " + password));
        } catch (SQLException ex) {
            throw new UserException("Can't find user with email " + email, ex);
        }
    }

    @Override
    public void deleteById(long id) {
        try (Connection connection = dbConnection.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_ID_SQL_STATEMENT)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new UserException("Can't delete the user with id " + id, ex);
        }
    }

    @Override
    public void deleteByEmail(String email) {
        try (Connection connection = dbConnection.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_EMAIL_SQL_STATEMENT)) {
            preparedStatement.setString(1, email);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new UserException("Can't delete the user with email " + email, ex);
        }
    }

    @Override
    public void update(UserEntity user) {
        try (Connection connection = dbConnection.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL_STATEMENT)) {
            appendPreparedStatementParameters(preparedStatement, user);
            preparedStatement.setString(6, UserType.SIMPLE_USER.toString());
            preparedStatement.setLong(7, user.getId());
            preparedStatement.executeUpdate();
    } catch (SQLException ex) {
            throw new UserException("Can't update the user with id " + user.getId(), ex);
        }
    }

    private List<UserEntity> getUsersFromResultSet(ResultSet rs) throws SQLException {
        List<UserEntity> result = new ArrayList<>();
        while (rs.next()) {
            result.add(mapUser(rs));
        }
        return result;
    }

    private void appendPreparedStatementParameters(PreparedStatement preparedStatement, UserEntity user) throws SQLException {
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setBoolean(5, user.isBanned());
    }

    private UserEntity mapUser(ResultSet rs) {
        UserEntity user = new UserEntity();
        try {
            long id = rs.getLong("user_id");
            user.setId(id);
            user.setLastName(rs.getString("lastname"));
            user.setFirstName(rs.getString("firstname"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setBanned(rs.getBoolean("banned"));
            if (id == 1) {
                user.setUserType(UserType.ADMINISTRATOR);
            } else {
                user.setUserType(UserType.SIMPLE_USER);
            }
            return user;
        } catch (SQLException ex) {
            throw new UserException("can't map resultset to the user entity", ex);
        }
    }
}

