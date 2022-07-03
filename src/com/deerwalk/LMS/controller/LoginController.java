package com.deerwalk.LMS.controller;

import com.deerwalk.LMS.model.Role;
import com.deerwalk.LMS.model.Transaction;
import com.deerwalk.LMS.model.User;
import com.deerwalk.LMS.services.DbConnection;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Dell on 10/12/2017.
 */
public class LoginController {
    public User login(String email, String password){
        DbConnection dbConnection=new DbConnection();
        Connection connection=dbConnection.getDbconnection();
        String sql="select * from user where email=? and password=?";
        User user=null;
        try {
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(1,email);
            statement.setString(2,password);
            boolean val=statement.execute();
            if(val){
                ResultSet resultSet=statement.getResultSet();
                while(resultSet.next()){
                    int id=resultSet.getInt("id");
                    String fullName=resultSet.getString("full_name");
                    String emailID=resultSet.getString("email");
                    String passwd=resultSet.getString("password");

                    user=new User();
                    user.setEmail(emailID);
                    user.setFull_name(fullName);
                    user.setId(id);
                    user.setPassword(passwd);





                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return user;
    }


        public Role getUserRole(int user_id){
                DbConnection dbConnection=new DbConnection();
                Connection connection=dbConnection.getDbconnection();
            String sql="select * from role where user_id=?";
            Role role=null;
            try {
                PreparedStatement statement=connection.prepareStatement(sql);
                statement.setInt(1, user_id);
                boolean val=statement.execute();
                if(val){
                    ResultSet resultSet=statement.getResultSet();
                    while(resultSet.next()){
                        int id=resultSet.getInt("id");
                        int userId=resultSet.getInt("user_id");
                        String role1=resultSet.getString("role");

                        role=new Role();
                        role.setId(id);
                        role.setRole(role1);
                        role.setUser_id(userId);





                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return role;

        }




}
