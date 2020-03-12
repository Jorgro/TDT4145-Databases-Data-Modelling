package main;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UserCtrl extends DBConnector {
    private Scanner scanner = new Scanner(System.in);
    private PreparedStatement userstatement;
    public void insertUser(String username, String password, String email){
        try {
            userstatement = conn.prepareStatement("INSERT INTO user (Username, Password, Email) VALUES (?,?,?);");
        }
        catch (SQLException e){
            System.out.println("Error during prepare statement");
        }

        try {
            userstatement.setString(1, username);
            userstatement.setString(2, password);
            userstatement.setString(3, email);
            userstatement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Illegal values, most likely a user with this username already exist");
        }

    }

    public void registerUser(){
        System.out.println("Welcome to user-registration. \n");
        System.out.println("Type in username: ");
        String username = scanner.nextLine().strip();
        System.out.println("Type in password: ");
        String password = scanner.nextLine().strip();
        System.out.println("Type in email: ");
        String email = scanner.nextLine().strip();

        insertUser(username, password, email);
    }

    public static void main(String[] args) {
        UserCtrl u = new UserCtrl();
        u.connect();
        u.registerUser();
    }

}
