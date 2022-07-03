package com.deerwalk.LMS;

import com.deerwalk.LMS.controller.LoginController;
import com.deerwalk.LMS.controller.TransactionController;
import com.deerwalk.LMS.model.Role;
import com.deerwalk.LMS.model.Transaction;
import com.deerwalk.LMS.model.User;

import java.sql.Connection;
import java.util.Scanner;

/**
 * Created by Dell on 10/12/2017.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("*************LMS**************");
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter your email:");
        String email=scanner.next();
        scanner=new Scanner(System.in);
        System.out.println("Enter your Password:");
        String password=scanner.next();
        LoginController loginController=new LoginController();
        User user=loginController.login(email,password);
        if(user!=null){
            Role role=loginController.getUserRole(user.getId());

            if(role.getRole().equals("Student")){
                TransactionController transactionController=new TransactionController();
                System.out.println("\n*********Student DashBoard********\n");
                transactionController.getStudentDashboard();

            }
            else if(role.getRole().equals("Teacher")){
                TransactionController transactionController=new TransactionController();
                System.out.println("\n********Teacher DashBoard*********\n");
                transactionController.getTeacherDashBoard();
            }
            else{
                TransactionController transactionController=new TransactionController();
                System.out.println("\n********Admin Dashboard**********\n");
                transactionController.getAdminDashboard();
            }


        }
        else
        {
            System.out.println("\nUser not found!!\n");
        }


    }
}
