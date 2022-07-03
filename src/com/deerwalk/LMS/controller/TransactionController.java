package com.deerwalk.LMS.controller;

import com.deerwalk.LMS.model.Book;
import com.deerwalk.LMS.model.Transaction;
import com.deerwalk.LMS.model.User;
import com.deerwalk.LMS.services.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by Dell on 10/13/2017.
 */
public class TransactionController {

    public void getStudentDashboard() {
        DbConnection dbConnection=new DbConnection();
        Scanner scanner=new Scanner(System.in);
        int input=0;
        do {
            System.out.println("\nEnter the operation you want to perform:\n1.View all Book\n2.Query Book\n3.Exit");
            int choice = scanner.nextInt();
            input=choice;
            switch (choice) {
                case 1:
                  Book book=new Book();
                    book.viewAllBook();;
                    break;
                case 2:
                   book=new Book();
                    book.queryBook();

                    break;
                default:
                    break;

            }

        }while(input!=3);

    }

    public void getTeacherDashBoard() {
        DbConnection dbConnection=new DbConnection();
        Scanner scanner=new Scanner(System.in);
        int input=0;
        do {
            System.out.println("\nEnter the operation you want to perform:\n1.View all Book\n2.Query Book\n3.Exit");
            int choice = scanner.nextInt();
            input=choice;
            switch (choice) {
                case 1:
                   Book book=new Book();
                    book.viewAllBook();;
                    break;
                case 2:
                   book=new Book();
                    book.queryBook();
                    break;
                default:
                    break;

            }

        }while (input!=3);

    }

    public void getAdminDashboard() {
        DbConnection dbConnection=new DbConnection();
        Scanner scanner=new Scanner(System.in);
        int input=0;
        do {
            System.out.println("\nEnter the operation you want to perform:\n1.Add Book\n2.Return Book\n3.View all Book\n4.Issue a Book\n5.Edit Book\n6.Exit");
            int choice = scanner.nextInt();
            input=choice;
            switch (choice) {
                case 1:
                    Connection connection = dbConnection.getDbconnection();
                    String sql = "insert into book(name,author,price,number_of_books,remaining_no_of_books)values(?,?,?,?,?)";
                    try {
                        PreparedStatement statement = connection.prepareStatement(sql);
                        Book book = getBookInfo();

                        int remNoOfBooks = book.getNumber_of_books();
                        statement.setString(1, book.getName());
                        statement.setString(2, book.getAuthor());
                        statement.setInt(3, book.getPrice());
                        statement.setInt(4, book.getNumber_of_books());
                        statement.setInt(5, remNoOfBooks);
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                      Transaction transaction=new Transaction();
                      transaction.returnBook();
                  break;
                case 3:
                  Book book=new Book();
                    book.viewAllBook();
                    break;
                case 4:
                    transaction=new Transaction();
                    transaction.issueBook();



                    break;
                case 5:
                    connection = dbConnection.getDbconnection();
                    sql = "select * from book where name=?";
                    try {
                        PreparedStatement statement = connection.prepareStatement(sql);
                        scanner=new Scanner(System.in);
                        System.out.println("\nEnter the name of the book you want to edit:");
                        String name = scanner.nextLine();
                        statement.setString(1, name);
                        boolean val = statement.execute();
                        if (val) {
                            ResultSet resultSet = statement.getResultSet();
                            while (resultSet.next()) {
                                int id = resultSet.getInt("id");
                                String name1 = resultSet.getString("name");
                                String author = resultSet.getString("author");
                                int noOfBooks = resultSet.getInt("number_of_books");


                                System.out.println("\nDetails of the book you want to edit:\nID=" + id + "\nName=" + name1 + "\nAuthor=" + author + "\nTotal number of books=" + noOfBooks + "\n");
                                int loop=0;
                                do {

                                    System.out.println("\nWhat do you want to edit?\n1.Edit name of the book\n2.Edit author of the book\n3.Edit total number of books\n4.Exit");
                                    int edit = scanner.nextInt();
                                    loop=edit;
                                    switch (edit) {
                                        case 1:
                                            String sql1 = "update book set name=? where name=?";

                                            try {
                                                PreparedStatement statement1 = connection.prepareStatement(sql1);
                                                scanner=new Scanner(System.in);
                                                System.out.println("\nEnter the new name of the book:");
                                                String newName = scanner.nextLine();
                                                statement1.setString(1, newName);
                                                statement1.setString(2, name1);
                                                statement1.execute();
                                                name1=newName;
                                                System.out.println("\nBook name updated!!!");
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }

                                            break;
                                        case 2:
                                            sql1 = "update book set author=? where name=?";

                                            try {
                                                PreparedStatement statement1 = connection.prepareStatement(sql1);
                                                scanner=new Scanner(System.in);
                                                System.out.println("\nEnter the new author of the book:");
                                                String newAuthor = scanner.nextLine();
                                                statement1.setString(1, newAuthor);
                                                statement1.setString(2, name1);
                                                statement1.execute();
                                                System.out.println("\nAuthor name updated!!!");
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                            break;
                                        case 3:
                                            sql1 = "update book set number_of_books=? where name=?";

                                            try {
                                                PreparedStatement statement1 = connection.prepareStatement(sql1);
                                                System.out.println("\nEnter the new number of the book:");
                                                int newNumber = scanner.nextInt();
                                                statement1.setInt(1, newNumber);
                                                statement1.setString(2, name1);
                                                statement1.execute();
                                                System.out.println("\nBook number updated!!!");
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                            break;
                                        default:
                                            break;

                                    }
                                }while(loop!=4);
                            }

                        } else {
                            System.out.println("\nBook Not Found!!!");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                    break;

                default:
                    break;

            }
        }while(input!=6);
    }

    private Book getBookInfo() {
        Scanner scanner = new Scanner(System.in);


        System.out.println("\nEnter the name of the Book:");
        String name=scanner.nextLine();
        scanner=new Scanner(System.in);
        System.out.println("\nEnter the author of the Book:");
        String author=scanner.nextLine();
        scanner=new Scanner(System.in);
        System.out.println("\nEnter the price of the Book:");
        int price=scanner.nextInt();
        scanner=new Scanner(System.in);
        System.out.println("\nEnter the total number of Books:");
        int noOfBooks=scanner.nextInt();

        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setPrice(price);
        book.setNumber_of_books(noOfBooks);
        return  book;
    }
}
