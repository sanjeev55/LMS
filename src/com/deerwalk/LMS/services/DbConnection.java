package com.deerwalk.LMS.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Dell on 10/12/2017.
 */
public class DbConnection {
    public Connection getDbconnection(){
        String DBURL="jdbc:mysql://localhost:3306/test_db";
        String USERNAME="root";
        String PASSWORD="";
        Connection connection=null;
        try {
            connection= DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
            //System.out.println("Database Connected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
