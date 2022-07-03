package com.deerwalk.LMS.model;

import com.deerwalk.LMS.services.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by Dell on 10/12/2017.
 */
public class User {
    private int id;
    private String full_name;
    private String email;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUserFromEmail(String email){
        Scanner scanner=new Scanner(System.in);
        DbConnection dbConnection=new DbConnection();
        Connection connection=dbConnection.getDbconnection();

        String sql="select * from user where email=?";
        User user=null;
        try {
            PreparedStatement statement=connection.prepareStatement(sql);

            statement.setString(1,email);
            boolean val=statement.execute();
            if(val){
                ResultSet resultSet=statement.getResultSet();
                while(resultSet.next()){
                    int id=resultSet.getInt("id");
                    String name=resultSet.getString("full_name");
                    String email1=resultSet.getString("email");
                    String password=resultSet.getString("password");

                    user=new User();
                    user.setId(id);
                    user.setFull_name(name);
                    user.setEmail(email1);
                    user.setPassword(password);
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

            return user;
    }
}
