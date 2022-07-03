package com.deerwalk.LMS.model;

import com.deerwalk.LMS.services.DbConnection;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by Dell on 10/12/2017.
 */
public class Transaction {
    private int id;
    private int user_id;
    private int book_id;
    private Date date_of_issue;
    private Date deadline;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public Date getDate_of_issue() {
        return date_of_issue;
    }

    public void setDate_of_issue(Date date_of_issue) {
        this.date_of_issue = date_of_issue;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

public void issueBook(){
    Scanner scanner=new Scanner(System.in);
    DbConnection dbConnection=new DbConnection();
    Connection connection=dbConnection.getDbconnection();
    Book book=bookSearch();
    if(book!=null){
        scanner=new Scanner(System.in);
        System.out.println("\nEnter the email of the student you want to issue the book to:");
        String email=scanner.next();
        User user=new User();
        User data=user.getUserFromEmail(email);
        String currentDate=setCurrentDate();
        String deadlineDate=setDeadline();

        String sql="insert into transaction(user_id,book_id,date_of_issue,deadline,status)values(?,?,?,?,?)";
        try {
            PreparedStatement statement=connection.prepareStatement(sql);
            int user_id=data.getId();
            int book_id=book.getId();
            String date_of_issue=currentDate;
            String deadline=deadlineDate;
            int status=1;
            statement.setInt(1,user_id);
            statement.setInt(2,book_id);
            statement.setString(3,date_of_issue);
            statement.setString(4,deadline);
            statement.setInt(5,status);
            statement.execute();
            String sql1 = "update book set remaining_no_of_books=? where name=?";
            int remNoOfBooks=book.getRemaining_number_of_books();
            remNoOfBooks=remNoOfBooks-1;

            PreparedStatement statement1 = null;
            try {
                statement1 = connection.prepareStatement(sql1);
                statement1.setInt(1,remNoOfBooks );
                statement1.setString(2, book.getName());
                int affected = statement1.executeUpdate();
                System.out.println("\n"+affected + " " + "affected");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }else{
        System.out.println("Book Not Found!!");
    }






    }

    private Book bookSearch() {
        Scanner scanner=new Scanner(System.in);
        DbConnection dbConnection=new DbConnection();
        Connection connection=dbConnection.getDbconnection();
        String sql="select * from book where name=?";
        Book book=null;
        try {
            PreparedStatement statement=connection.prepareStatement(sql);
            scanner=new Scanner(System.in);
            System.out.println("\nEnter the name of the book you want to issue:");
            String name=scanner.nextLine();
            statement.setString(1,name);
            boolean val=statement.execute();
            if(val){
                ResultSet resultSet=statement.getResultSet();
                while(resultSet.next()){
                    int id=resultSet.getInt("id");
                    String name1=resultSet.getString("name");
                    String author = resultSet.getString("author");
                    int price = resultSet.getInt("price");
                    int noOfBooks = resultSet.getInt("number_of_books");
                    int remNoOfBooks = resultSet.getInt("remaining_no_of_books");

                    book=new Book();
                    book.setId(id);
                    book.setName(name1);
                    book.setNumber_of_books(noOfBooks);
                    book.setRemaining_number_of_books(remNoOfBooks);


                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    private String setCurrentDate(){
        Date date=new Date();
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String currentDate=dateFormat.format(date);
        return currentDate;
    }

    private String setDeadline(){
        Date date=new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.DATE,7);
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date newDate=calendar.getTime();
        String deadlineDate=dateFormat.format(newDate);
        return deadlineDate;



    }
    private Date currentDate(){
        Date date=new Date();
        return date;
    }


    public void returnBook() {
        DbConnection dbConnection=new DbConnection();
        Connection connection=dbConnection.getDbconnection();
        Scanner scanner=new Scanner(System.in);

        System.out.println("\nEnter the email of the student who returned the book:");
        String email=scanner.next();
        User user=new User();
        User data=user.getUserFromEmail(email);
        if(data!=null){
            String sql="select * from transaction where user_id=?";
            try {
                PreparedStatement statement=connection.prepareStatement(sql);
                statement.setInt(1, data.getId());
                boolean val=statement.execute();
                if(val){
                    ResultSet resultSet=statement.getResultSet();
                    while(resultSet.next()) {
                        int id = resultSet.getInt("id");
                        int user_id = resultSet.getInt("user_id");
                        int book_id = resultSet.getInt("book_id");
                        Date dateOfIssue = resultSet.getDate("date_of_issue");
                        Date deadline = resultSet.getDate("deadline");
                        int status = resultSet.getInt("status");
                        Transaction transaction = new Transaction();
                        System.out.println("ID=" + id + "\nUser ID=" + user_id + "\nBook ID=" + book_id + "\nDate of Issue=" + dateOfIssue + "\nDeadline=" + deadline);
                        if (status!= 0){
                             transaction = new Transaction();
                        transaction.setId(id);
                        transaction.setUser_id(user_id);
                        transaction.setBook_id(book_id);
                        transaction.setDate_of_issue(dateOfIssue);
                        transaction.setDeadline(deadline);
                        transaction.setStatus(0);

                        Date currentDate = currentDate();
                        deadline = transaction.getDeadline();
                        if (currentDate.after(deadline)) {
                            int fine = 10;
                            int fineDate = currentDate.compareTo(deadline);
                            int totalFine = fineDate * fine;
                            System.out.println("\nThe fine that you have to pay is:" + totalFine);
                        } else {
                            System.out.println("\nNo fine required!!");
                        }
                        String sql2 = "select * from book where id=?";
                        try {
                            PreparedStatement statement1 = connection.prepareStatement(sql2);

                            statement1.setInt(1, transaction.getBook_id());
                            boolean val1 = statement1.execute();
                            if (val1) {
                                resultSet = statement1.getResultSet();
                                while (resultSet.next()) {
                                    String name1 = resultSet.getString("name");
                                    int remNoOfBooks = resultSet.getInt("remaining_no_of_books");
                                    int noOfBooks = resultSet.getInt("number_of_books");
                                    String sql1 = "update book set remaining_no_of_books=?  where name=?";
                                    remNoOfBooks = remNoOfBooks + 1;

                            /*if(remNoOfBooks>noOfBooks){
                                noOfBooks=remNoOfBooks;
                            }
                            else{
                                noOfBooks=noOfBooks;
                            }*/


                                    PreparedStatement statement2 = null;
                                    try {
                                        statement2 = connection.prepareStatement(sql1);
                                        statement2.setInt(1, remNoOfBooks);
                                        //statement1.setInt(2,noOfBooks);
                                        statement2.setString(2, name1);
                                        int affected = statement2.executeUpdate();
                                        System.out.println(affected + " " + "affected");
                                        if (remNoOfBooks > noOfBooks) {
                                            noOfBooks = remNoOfBooks;
                                        }


                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    String sql3 = "update transaction set status=0 where user_id=?";

                                    try {
                                        PreparedStatement statement3 = connection.prepareStatement(sql3);
                                        statement3.setInt(1, data.getId());
                                        statement3.execute();
                                        System.out.println("\nStatus updated");
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }


                                }
                            } else {
                                System.out.println("\nBook Not Found!!!");
                            }


                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }else{
                            System.out.println("\nBook already returned!!!");
                        }

                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


}
