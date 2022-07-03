package com.deerwalk.LMS;

import java.util.Scanner;

/**
 * Created by Dell on 10/12/2017.
 */
public class Main1 {
    public static void main(String[] args) {
        Book1 book=new Book1();
        int input=0;
        Scanner scanner=new Scanner(System.in);
        System.out.println("Are you a Student or the Librarian?\n1.For Librarian\n2.For Student");
        int status=scanner.nextInt();
        if(status==1) {
            System.out.println("Enter your username:");
            String username=scanner.next();
            if(username.equals("lib")) {
                do {
                    System.out.println("Enter your choice:\n1.Add new Book\n2.View all book\n3.Exit");
                    int choice = scanner.nextInt();
                    input = choice;
                    switch (choice) {
                        case 1:
                            book.addNewBook();
                            break;
                        case 2:
                            book.viewAllBook();
                            break;
                        default:
                            break;

                    }
                } while (input != 3);
            }
            else
            {
                System.out.println("Incorrect Username!!!!");
                return;
            }
        }
        else if(status==2){
            System.out.println("Enter your username:");
            String username=scanner.next();

            if(username.equals("std")){
                do {
                    System.out.println("Enter your choice:\n1.View all book\n2.Exit");
                    int choice = scanner.nextInt();
                    input = choice;
                    switch (choice) {

                        case 1:
                            book.viewAllBook();
                            break;
                        default:
                            break;

                    }
                } while (input != 2);

            }
            else
            {
                System.out.println("Invalid Username!!!!");
                return;
            }

        }
        else
        {
            System.out.println("Invalid choice!!!");
            return;
        }
    }
}
