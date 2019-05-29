package com.epam.cwlhub.dao.impl;

import com.epam.cwlhub.dao.SnippetDao;
import com.epam.cwlhub.entities.snippet.Snippet;
import com.epam.cwlhub.exceptions.checked.ResultSetMappingException;
import com.epam.cwlhub.exceptions.checked.SqlError;
import com.epam.cwlhub.storage.initor.DBInitor;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import static com.epam.cwlhub.exceptions.ExceptionMeta.SQL_EXECUTION_ERROR;

public class SnippetDefaultDaoImpl implements SnippetDao {
    private static final String SNIPPET_CLASS_NAME = Snippet.class.getSimpleName();
    private static final String INSERT_SNIPPET_SQL_STATEMENT = "INSERT INTO snippets (name, owner_id, creation_date, " +
                                                               "modification_date, content, tag, group_id) " +
                                                               "VALUE (?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL_SNIPPETS = "SELECT * FROM snippets";
    private static final String SELECT_SNIPPET_SQL_STATEMENT = "SELECT FROM snippets" +
                                                               "WHERE ";

    private static final String SELECT_SNIPPET_BY_ID_SQL_STATEMENT = SELECT_SNIPPET_SQL_STATEMENT + "snippet_id = ?";

    private static final String DELETE_SNIPPET_BY_ID_SQL_STATEMENT = "DELETE FROM TABLE snippets" +
                                                                     "WHERE snippet_id = ?";

    @Override
    public void insert(Snippet snippet) throws SqlError {
        try (Connection connection = DBInitor.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_SNIPPET_SQL_STATEMENT)) {
            appendPreparedStatementParametersToInsertSnippet(ps, snippet);
            ps.executeQuery();
        } catch (SQLException e) {
            throw new SqlError(SQL_EXECUTION_ERROR, e);
        }
    }

    @Override
    public void update(Snippet snippet) {

    }

    @Override
    public void deleteById(long id) throws SqlError {
        try (Connection connection = DBInitor.getDBConnection();
            PreparedStatement ps = connection.prepareStatement(DELETE_SNIPPET_BY_ID_SQL_STATEMENT)) {
            appendSnippetIdToPreparedStatementParameters(ps, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SqlError(SQL_EXECUTION_ERROR, e);
        }
    }

    @Override
    public Optional<Snippet> findById(long id) throws ResultSetMappingException, SqlError {
        try (Connection connection = DBInitor.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_SNIPPET_BY_ID_SQL_STATEMENT)) {
            appendSnippetIdToPreparedStatementParameters(ps, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.ofNullable(mapSnippet(rs));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new SqlError(SQL_EXECUTION_ERROR, e);
        }
    }

    @Override
    public List<Snippet> findAll() throws ResultSetMappingException, SqlError {
        try (Connection connection = DBInitor.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL_SNIPPETS)) {
            ResultSet rs = ps.executeQuery();
            List<Snippet> result = new ArrayList<>();
            while (rs.next()) {
                result.add(mapSnippet(rs));
            }

            return result;
        } catch (SQLException e) {
            throw new SqlError(SQL_EXECUTION_ERROR, e);
        }
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

    private Snippet mapSnippet(ResultSet rs) throws ResultSetMappingException {
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
        } catch (SQLException e) {
            throw new ResultSetMappingException(SNIPPET_CLASS_NAME, e);
        }
    }
}
