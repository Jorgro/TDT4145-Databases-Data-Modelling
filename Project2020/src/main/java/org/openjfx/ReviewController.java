package org.openjfx;

import javax.sound.midi.Soundbank;
import java.sql.*;

public class ReviewController extends DBConnector {

    private PreparedStatement reviewStatement;

    public void startReviewOfTitle() {

        try {
            reviewStatement = conn.prepareStatement("INSERT INTO reviewOfTitle (TitleID, UserID, Review, Rating) VALUES ( ?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
        } catch (Exception e) {
            System.out.println("db error during prepare of insert into reviewOfTitle");
        }


    }

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

//            catch (SQLException S){
//                System.out.println("Error during insert");
//
//            }

        }
        return -1;

    }

    public int getUserId(String username) throws SQLException {
        PreparedStatement userStatement = conn.prepareStatement("SELECT UserID FROM user WHERE ? = Username");
        userStatement.setString(1, username);
        ResultSet rs = userStatement.executeQuery();
        if (rs.next()){
            return rs.getInt("UserID");
        }
        else
            return -1;

    }

    public static void main(String[] args) {
        ReviewController c = new ReviewController();
        c.connect();
        try {
            int userId = c.getUserId("josteiht");
            System.out.println(userId);
            c.startReviewOfTitle();


            c.updateReview(4, userId, "Very nice", 9 );
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }
}


