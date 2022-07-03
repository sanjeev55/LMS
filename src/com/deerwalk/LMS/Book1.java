package com.deerwalk.LMS;

import java.sql.*;
import java.util.Scanner;

/**
 * Created by Dell on 10/12/2017.
 */
public class Book1 {
    public Connection getDbConnection(){
        String URL="jdbc:mysql://localhost:3306/test_db";
        String USERNAME="root";
        String PASSWORD="";
        Connection connection=null;
        try {
            connection= DriverManager.getConnection(URL,USERNAME,PASSWORD);
            System.out.println("Connected to Database");
            System.out.println("----------------------");

        } catch (SQLException e) {
            e.printStackTrace();
        }
            return connection;
    }
    public void addNewBook() {
        Connection connection=getDbConnection();
        String sql="insert into book(name,author,price)values(?,?,?)";
        try {
            PreparedStatement statement=connection.prepareStatement(sql);
            Scanner scanner=new Scanner(System.in);
            System.out.println("Enter the name of the book you want to add:");
            String name=scanner.nextLine();
            System.out.println("Enter the name of tha author of the Book:");
            String author=scanner.nextLine();
            System.out.println("Enter the price of the Book:");
            int price=scanner.nextInt();
            statement.setString(1,name);
            statement.setString(2,author);
            statement.setInt(3, price);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void viewAllBook() {
        Connection connection=getDbConnection();
        String sql="select * from book";
        try {
            PreparedStatement statement=connection.prepareStatement(sql);
            boolean val=statement.execute();
            if(val){
                ResultSet resultSet=statement.getResultSet();
                while(resultSet.next()){
                    int id=resultSet.getInt("id");
                    String name=resultSet.getString("name");
                    String author=resultSet.getString("author");
                    int price=resultSet.getInt("price");
                    System.out.println("Book ID="+id+"\nName="+name+"\nAuthor="+author+"\nprice="+price);
                    System.out.println("---------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
