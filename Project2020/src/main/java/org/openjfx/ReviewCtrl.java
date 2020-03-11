package org.openjfx;

import java.sql.*;

import java.util.Scanner;

public class ReviewCtrl extends DBConnector {

    private PreparedStatement reviewStatement;

    public Scanner scanner = new Scanner(System.in);

    public void alterReview(int titleID, int userID, String review, int rating) {
        try {
            PreparedStatement alter = conn.prepareStatement("UPDATE reviewOfTitle SET Review = ? WHERE TitleID = ? AND UserID = ?");
            alter.setString(1, review);
            alter.setInt(2, titleID);
            alter.setInt(3, userID);
            alter.executeUpdate();
            PreparedStatement alterRating = conn.prepareStatement("UPDATE reviewOfTitle SET Rating = ? WHERE TitleID = ? AND UserID = ?");
            alterRating.setInt(1, rating);
            alterRating.setInt(2, titleID);
            alterRating.setInt(3, userID);
            alterRating.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Something went wrong");


        }
    }
    public int updateReview(int titleID, int userID, String review, int rating ){
        try {
            reviewStatement = conn.prepareStatement("INSERT INTO reviewOfTitle (TitleID, UserID, Review, Rating) VALUES ( ?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
        } catch (Exception e) {
            System.out.println("db error during prepare of insert into reviewOfTitle");
        }
        try {
            reviewStatement.setInt(1, titleID);
            reviewStatement.setInt(2, userID);
            reviewStatement.setString(3, review);
            reviewStatement.setInt(4, rating);
            reviewStatement.executeUpdate();
            ResultSet rs = reviewStatement.getGeneratedKeys();
            if (rs.next())
                return rs.getInt(1);

        }
        catch (SQLException e){
            //e.printStackTrace();
            this.alterReview(titleID, userID, review, rating);
        }
        return -1;

    }
    private boolean testInteger(String s, int max){
        try {
            int i = Integer.parseInt(s);
            return i <= max && i > 0;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    public int getUserId(String username)  {
        try {


            PreparedStatement userStatement = conn.prepareStatement("SELECT UserID FROM user WHERE ? = Username");
            userStatement.setString(1, username);
            ResultSet rs = userStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("UserID");
            }
        }
        catch (SQLException e){
            return -1;
        }

        return -1;

    }

    private int printTitles() throws SQLException{
        PreparedStatement titleStatement = conn.prepareStatement("SELECT TitleID, Name, PublishYear FROM title");
        ResultSet rs = titleStatement.executeQuery();
        int max = 0;
        while (rs.next()){
            System.out.println(rs.getInt(1)+ ": " + rs.getString(2) + ", " + rs.getInt(3));
            max = rs.getRow();
        }
        return max;

    }

    public void registerReview() throws SQLException{
        System.out.println("Please enter username: ");
        String s = scanner.nextLine();

        int userID = getUserId(s);
        while (userID < 0){
            System.out.println("No such user exist, please try again: ");
            s = scanner.nextLine().strip();
            userID = getUserId(s);
        }

        int max = printTitles();
        System.out.println("These are titles in the database. Please enter the number of the one you would like to review: ");

        s = scanner.nextLine();
        while (!(testInteger(s, max))){
            System.out.println("No title with this number, please try again: ");
            s = scanner.nextLine();
        }
        int titleID = Integer.parseInt(s);

        System.out.println("Please enter your review: ");
        String review = scanner.nextLine();

        System.out.println("Please enter a rating from 1 to 5: ");
        s = scanner.nextLine();
        while (!(testInteger(s, 5))) {
            System.out.println("Must be an integer between 1 and 5. Try again: ");
            s = scanner.nextLine();
        }
        int rating = Integer.parseInt(s);


        updateReview(titleID, userID, review, rating);



    }





    public static void main(String[] args) {

        ReviewCtrl c = new ReviewCtrl();
        c.connect();
        try {
            c.registerReview();
        }
        catch (SQLException e){
            System.out.println("There is a problem somewhere with SQL");
        }

        //int userId = c.getUserId("josteiht");
        //System.out.println(userId);



        //c.updateReview(5, userId, "Very nice", 9 );

    }
}


