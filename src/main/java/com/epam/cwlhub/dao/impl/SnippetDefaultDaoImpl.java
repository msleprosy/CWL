package com.epam.cwlhub.dao.impl;

import com.epam.cwlhub.dao.SnippetDao;
import com.epam.cwlhub.entities.snippet.Snippet;
import com.epam.cwlhub.exceptions.unchecked.SnippetException;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import static com.epam.cwlhub.storage.dbconnection.DBConnector.getDBConnectorInstance;

public class SnippetDefaultDaoImpl implements SnippetDao {
    private static volatile SnippetDefaultDaoImpl INSTANCE;

    private static final String INSERT_SNIPPET_SQL_STATEMENT = "INSERT INTO snippets (name, owner_id, creation_date, " +
                                                               "modification_date, content, tag, group_id) " +
                                                               "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL_SNIPPETS = "SELECT * FROM snippets";
    private static final String SELECT_SNIPPET_SQL_STATEMENT = "SELECT * FROM snippets " +
                                                               "WHERE ";

    private static final String SELECT_SNIPPET_BY_ID_SQL_STATEMENT = SELECT_SNIPPET_SQL_STATEMENT + "snippet_id = ?";
    private static final String SELECT_SNIPPET_BY_GROUP_ID_SQL_STATEMENT = SELECT_SNIPPET_SQL_STATEMENT + "group_id = ?";

    private static final String DELETE_SNIPPET_SQL_STATEMENT = "DELETE FROM snippets " +
                                                               "WHERE ";

    private static final String DELETE_SNIPPET_BY_ID_SQL_STATEMENT = DELETE_SNIPPET_SQL_STATEMENT + "snippet_id = ?";

    private static final String UPDATE_SNIPPET_SQL_STATEMENT = "UPDATE snippets SET " +
                                                               "name = ?, modification_date = ?, content = ?, tag = ? " +
                                                               "WHERE snippet_id = ?";

    public static SnippetDefaultDaoImpl getInstance() {
        SnippetDefaultDaoImpl localInstance = INSTANCE;
        if (localInstance == null) {
            synchronized (SnippetDefaultDaoImpl.class) {
                localInstance = INSTANCE;
                if (localInstance == null) {
                    INSTANCE = localInstance = new SnippetDefaultDaoImpl();
                }
            }
        }
        return localInstance;
    }

    @Override
    public Snippet insert(Snippet snippet) {
        try (Connection connection = getDBConnectorInstance().getDBConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_SNIPPET_SQL_STATEMENT,
                                                                PreparedStatement.RETURN_GENERATED_KEYS)) {
            appendPreparedStatementParametersToInsertSnippet(ps, snippet);
            ps.executeUpdate();

            try(ResultSet generatedId = ps.getGeneratedKeys()) {
                if (generatedId.next()) {
                    snippet.setId(generatedId.getLong(1));
                }
            }
            return snippet;
        } catch (Exception e) {
            throw new SnippetException("Can't insert new snippet", e);
        }
    }

    @Override
    public void update(Snippet snippet) {
        try (Connection connection = getDBConnectorInstance().getDBConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_SNIPPET_SQL_STATEMENT)) {
            appendPreparedStatementParametersToUpdateSnippet(ps, snippet);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new SnippetException("Can't update the snippet with id = " + snippet.getId(), e);
        }
    }

    @Override
    public void deleteById(long id) {
        try (Connection connection = getDBConnectorInstance().getDBConnection();
            PreparedStatement ps = connection.prepareStatement(DELETE_SNIPPET_BY_ID_SQL_STATEMENT)) {
            appendSnippetIdToPreparedStatementParameters(ps, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new SnippetException("Can't delete the snippet with id = " + id, e);
        }
    }

    @Override
    public Optional<Snippet> findById(long id)  {
        try (Connection connection = getDBConnectorInstance().getDBConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_SNIPPET_BY_ID_SQL_STATEMENT)) {
            appendSnippetIdToPreparedStatementParameters(ps, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.ofNullable(mapSnippet(rs));
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new SnippetException("Can't find the snippet with id = " + id, e);
        }
    }

    @Override
    public List<Snippet> findByGroupId(long id) {
        try (Connection connection = getDBConnectorInstance().getDBConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_SNIPPET_BY_GROUP_ID_SQL_STATEMENT)) {
            appendSnippetIdToPreparedStatementParameters(ps, id);
            ResultSet rs = ps.executeQuery();

            return getSnippetsFromResultSet(rs);
        } catch (Exception e) {
            throw new SnippetException("Can't find the snippet with id = " + id, e);
        }
    }

    @Override
    public List<Snippet> findAll() {
        try (Connection connection = getDBConnectorInstance().getDBConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL_SNIPPETS)) {
            ResultSet rs = ps.executeQuery();

            return getSnippetsFromResultSet(rs);
        } catch (Exception e) {
            throw new SnippetException("Can't find any snippets", e);
        }
    }

    private List<Snippet> getSnippetsFromResultSet(ResultSet rs) throws SQLException {
        List<Snippet> result = new ArrayList<>();
        while (rs.next()) {
            result.add(mapSnippet(rs));
        }

        return result;
    }

    private void appendPreparedStatementParametersToInsertSnippet(PreparedStatement ps, Snippet snippet) throws SQLException {
            ps.setString(1, snippet.getName());
            ps.setLong(2, snippet.getOwnerId());
            ps.setDate(3, Date.valueOf(snippet.getCreationDate()));
            ps.setDate(4, Date.valueOf(snippet.getModificationDate()));
            ps.setString(5, snippet.getContent());
            ps.setString(6, snippet.getTag());
            ps.setLong(7, snippet.getGroupId());
    }

    private void appendSnippetIdToPreparedStatementParameters(PreparedStatement ps, long id) throws SQLException {
        ps.setLong(1, id);
    }

    private Snippet mapSnippet(ResultSet rs) {
        try {
            Snippet snippet = new Snippet();
            snippet.setId(rs.getLong("snippet_id"));
            snippet.setName(rs.getString("name"));
            snippet.setOwnerId(rs.getLong("owner_id"));
            snippet.setGroupId(rs.getLong("group_id"));
            snippet.setCreationDate(rs.getDate("creation_date").toLocalDate());
            snippet.setModificationDate(rs.getDate("modification_date").toLocalDate());
            snippet.setContent(rs.getString("content"));
            snippet.setTag(rs.getString("tag"));

            return snippet;
        } catch (Exception e) {
            throw new SnippetException("Can't map result set to a snippet entity", e);
        }
    }

    private void appendPreparedStatementParametersToUpdateSnippet(PreparedStatement ps, Snippet snippet) throws SQLException {
        ps.setString(1, snippet.getName());
        ps.setDate(2, Date.valueOf(snippet.getModificationDate()));
        ps.setString(3, snippet.getContent());
        ps.setString(4, snippet.getTag());
        ps.setLong(5, snippet.getId());
    }
}
