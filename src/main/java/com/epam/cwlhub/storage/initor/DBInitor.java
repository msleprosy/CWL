package com.epam.cwlhub.storage.initor;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
        File file = new File(classLoader.getResource(DDL_SCRIPT_PATH).getFile());
        String content = new String(Files.readAllBytes(file.toPath()));
        Statement statement = getDBConnection().createStatement();
        statement.execute(content);
    }

    private void fillDataBaseWithData() throws Exception {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource(DML_SCRIPT_PATH).getFile());
        String content = new String(Files.readAllBytes(file.toPath()));
        Statement statement = getDBConnection().createStatement();
        statement.executeUpdate(content);
    }
}
