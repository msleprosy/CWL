package com.epam.cwlhub.storage.dbconnection;

import java.sql.Connection;

public interface DBConnection {
    Connection getDBConnection() throws Exception;
}
