package com.epam.cwlhub.dao;

import com.epam.cwlhub.entities.snippet.Snippet;
import com.epam.cwlhub.exceptions.checked.ResultSetMappingException;
import com.epam.cwlhub.exceptions.checked.SqlError;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface SnippetDao {
    void insert(Snippet snippet) throws SqlError;
    void update(Snippet snippet);
    void deleteById(long id) throws SqlError;
    Optional<Snippet> findById(long id) throws ResultSetMappingException, SQLException, SqlError;
    List<Snippet> findAll() throws ResultSetMappingException, SqlError;
}
