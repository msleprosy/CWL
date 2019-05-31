package com.epam.cwlhub;

import com.epam.cwlhub.storage.dbinitialization.DBInitor;

public class Main {
    public static void main(String[] args) throws Exception{
        DBInitor initor = new DBInitor();
        initor.initDataBase();
    }
}
