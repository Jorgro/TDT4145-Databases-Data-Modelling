package org.openjfx;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NewTitleCtrl extends DBConnector {


    private PreparedStatement titleStatement;
    private int TitleID;

    public void startReg(){
        try {
            titleStatement = conn.prepareStatement(
                    "INSERT INTO title (Name, Content, Duration, PublishYear, LaunchYear) VALUES (?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertMovie(String name, String content, int duration, int publishYear, int launchYear) throws SQLException {

        titleStatement.setString(1, name);
        titleStatement.setString(2, content);
        titleStatement.setInt(3, duration);
        titleStatement.setInt(4, publishYear);
        titleStatement.setInt(5, launchYear);
        int updatedColums = titleStatement.executeUpdate();
        ResultSet rs = titleStatement.getGeneratedKeys();

        if (rs.next()){
           this.TitleID = rs.getInt(1);
        }
        else this.TitleID = -1;
    }




    public static void main(String[] args) {
        NewTitleCtrl ctrl = new NewTitleCtrl();
        ctrl.connect();
        try {
            ctrl.startReg();
            ctrl.insertMovie("Lord Snow", "Jon is at the Nightswatch", 1, 2011, 2011);
            ctrl.linkActorTitle(ctrl.TitleID, 1, "Jon Snow");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
