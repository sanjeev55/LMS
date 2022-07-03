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
public class Book {
    Scanner scanner = new Scanner(System.in);
    private int id;
    private String name;
    private String author;
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    private int number_of_books;
    private int remaining_number_of_books;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getNumber_of_books() {
        return number_of_books;
    }

    public void setNumber_of_books(int number_of_books) {
        this.number_of_books = number_of_books;
    }

    public int getRemaining_number_of_books() {
        return remaining_number_of_books;
    }

    public void setRemaining_number_of_books(int remaining_number_of_books) {
        this.remaining_number_of_books = remaining_number_of_books;
    }
    public void queryBook(){
        DbConnection dbConnection=new DbConnection();
        Connection connection = dbConnection.getDbconnection();
        scanner=new Scanner(System.in);
        System.out.println("\nEnter the name of the Book:");
        String name = scanner.nextLine();
        String sql = "select *from book where name=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            boolean val = statement.execute();
            if (val) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name1 = resultSet.getString("name");
                    int noOfBooks = resultSet.getInt("number_of_books");
                    int remNoOfBooks = resultSet.getInt("remaining_no_of_books");

                    if (name1.equals(name) && remNoOfBooks != 0) {
                        System.out.println("\nBook is available!");
                        System.out.println("\nNumber of book available=" + remNoOfBooks);
                    } else {
                        System.out.println("\nBook not available!");
                    }

                }

            } else {
                System.out.println("\nBook Not Found!!!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public void viewAllBook(){
        DbConnection dbConnection=new DbConnection();
        Connection connection = dbConnection.getDbconnection();
        String sql = "select * from book";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            boolean val = statement.execute();
            if (val) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String author = resultSet.getString("author");
                    int price = resultSet.getInt("price");
                    int noOfBooks = resultSet.getInt("number_of_books");
                    int remNoOfBooks = resultSet.getInt("remaining_no_of_books");

                    System.out.println("\nBook Id=" + id + "\nName=" + name + "\nPrice=" + price + "\nNumber of Books=" + noOfBooks + "\nRemaining number of Books=" + remNoOfBooks);
                    System.out.println("\n----------------------");
                }
            } else {
                System.out.println("\nNo Books in Database!!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
