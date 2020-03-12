package org.openjfx;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TitleCtrl extends DBConnector {

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

    /**
     * Part of usecase 4, insert title
     * @param name
     * @param content
     * @param duration
     * @param publishYear
     * @param launchYear
     * @throws SQLException
     */
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

    /**
     * Part of usecase 4, link person to a given title.
     * @param TitleID
     * @param PersonID
     * @param Role
     * @throws SQLException
     */
    public void linkPersonTitle(int TitleID, int PersonID, String Role, boolean actor) throws SQLException {
        PreparedStatement prep = conn.prepareStatement(
                "INSERT INTO personTitle (TitleID, PersonID, Role, Actor) VALUES (?,?,?,?);");
        prep.setInt(1, TitleID);
        prep.setInt(2, PersonID);
        prep.setString(3, Role);
        prep.setBoolean(4, actor);
        prep.executeUpdate();
    }

    /**
     * Part of usecase 4, link category to a given title.
     * @param titleID
     * @param categoryID
     * @throws SQLException
     */
    public void linkCategoryTitle(int titleID, int categoryID) throws SQLException {
        PreparedStatement prep = conn.prepareStatement("INSERT INTO categoryInTitle (TitleID, CategoryID) VALUES (?, ?);");
        prep.setInt(1, titleID);
        prep.setInt(2, categoryID);
        prep.executeUpdate();
    }
}
