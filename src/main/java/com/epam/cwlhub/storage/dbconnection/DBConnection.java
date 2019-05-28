package com.epam.cwlhub.storage.dbconnection;

import java.sql.Connection;
import java.sql.SQLException;

public interface DBConnection {
    Connection getDBConnection() throws SQLException;
}
