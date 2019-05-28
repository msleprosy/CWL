package com.epam.cwlhub.storage.initor;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DBInitor {
    private static final String DDL_SCRIPT_PATH = "database/ddl/create_tables.sql";
    private static final String DML_SCRIPT_PATH = "database/dml/init_data.sql";

    public void initDataBase() {
        try {
            createDataBaseStructure();
            fillDataBaseWithData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getDBConnection() throws SQLException {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setJdbcUrl("jdbc:postgresql://localhost:5432/CWL_db");
        cpds.setUser("postgres");
        cpds.setPassword("postgres");
        return cpds.getConnection();
    }

    private void createDataBaseStructure() throws Exception {
        ClassLoader classLoader = this.getClass().getClassLoader();
        List<String> ddl = Files.readAllLines(Paths.get(classLoader.getResource(DDL_SCRIPT_PATH).toURI()));
        Statement statement = getDBConnection().createStatement();
        statement.execute(String.join("", ddl));
    }

    private void fillDataBaseWithData() throws Exception {
        ClassLoader classLoader = this.getClass().getClassLoader();
        List<String> dml = Files.readAllLines(Paths.get(classLoader.getResource(DML_SCRIPT_PATH).toURI()));
        Statement statement = getDBConnection().createStatement();
        statement.executeUpdate(String.join("", dml));
    }
}
