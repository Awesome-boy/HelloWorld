package com.example.helloworld.db;

public class DBConstant {

    public static final String DB_NAME = "contacts_db";
    public static final int DB_VERSION = 1;

    public static final String CREATE_TABLE_READ = "create table contacts ("
            + "id Integer primary key autoincrement,"
            +"phone varchar(100),"
            +"name text";

}
