package com.epam.cwlhub.storage.dbinitialization;

import com.epam.cwlhub.storage.dbconnection.DBConnection;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Statement;
import java.util.List;

public class DBInitor {
    private static final String DDL_SCRIPT_PATH = "database/ddl/create_tables.sql";
    private static final String DML_SCRIPT_PATH = "database/dml/init_data.sql";
    private final DBConnection dbConnection;

    public DBInitor(DBConnection dbConnection){
        this.dbConnection = dbConnection;
    }

    public static void main(String[] args){

    }

    public void initDataBase() {
        try {
           // createDataBaseStructure();
            fillDataBaseWithData();
        } catch (Exception ex) {
            throw new RuntimeException("Something went wrong while initializing the database");
        }
    }

    private void createDataBaseStructure() throws Exception {
        ClassLoader classLoader = this.getClass().getClassLoader();
        List<String> ddl = Files.readAllLines(Paths.get(classLoader.getResource(DDL_SCRIPT_PATH).toURI()));
        Statement statement = dbConnection.getDBConnection().createStatement();
        statement.execute(String.join("", ddl));
    }

    private void fillDataBaseWithData() throws Exception {
        ClassLoader classLoader = this.getClass().getClassLoader();
        List<String> dml = Files.readAllLines(Paths.get(classLoader.getResource(DML_SCRIPT_PATH).toURI()));
        Statement statement = dbConnection.getDBConnection().createStatement();
        statement.executeUpdate(String.join("", dml));
    }
}
