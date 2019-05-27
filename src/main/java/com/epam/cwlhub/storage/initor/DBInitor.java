package com.epam.cwlhub.storage.initor;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.io.File;
import java.io.RandomAccessFile;
import java.sql.Statement;

public class DBInitor {
    private static final String DDL_SCRIPT_PATH = "C:\\Users\\veronika\\IdeaProjects\\CWL\\src\\main\\java\\com\\epam\\cwlhub\\database\\ddl\\create_tables.sql";
    private static final String DML_SCRIPT_PATH = "C:\\Users\\veronika\\IdeaProjects\\CWL\\src\\main\\java\\com\\epam\\cwlhub\\database\\dml\\init_data.sql";

    public void initDataBase() {
        try {
            createDataBaseStructure();
            fillDataBaseWithData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static ComboPooledDataSource getDataSource() {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setJdbcUrl("jdbc:postgresql://localhost:5432/CWL_db");
        cpds.setUser("postgres");
        cpds.setPassword("postgres");
        return cpds;
    }

    private void createDataBaseStructure() throws Exception {
        File DataBaseCreatingFile = new File(DDL_SCRIPT_PATH);
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(DataBaseCreatingFile, "r");
            StringBuilder stringBuilder = new StringBuilder();
            String string;
            while ((string = randomAccessFile.readLine()) != null) {
                stringBuilder.append(string);
            }
            randomAccessFile.close();
            Statement statement = getDataSource().getConnection().createStatement();
            statement.execute(stringBuilder.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void fillDataBaseWithData() throws Exception {
        File DataBasefillingFile = new File(DML_SCRIPT_PATH);
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(DataBasefillingFile, "r");
            StringBuilder stringBuilder = new StringBuilder();
            String string;
            while ((string = randomAccessFile.readLine()) != null) {
                stringBuilder.append(string);
            }
            randomAccessFile.close();
            Statement statement = getDataSource().getConnection().createStatement();
            statement.executeUpdate(stringBuilder.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
