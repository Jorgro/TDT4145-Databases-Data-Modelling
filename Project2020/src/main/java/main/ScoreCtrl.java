package main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ScoreCtrl extends DBConnector {

    /**
     * Part of usecase 4, insert score
     * @param title
     * @return ID of inserted score, -1 otherwise.
     * @throws SQLException
     */
    public int insertScore(String title) throws SQLException {
        PreparedStatement prep = conn.prepareStatement("INSERT INTO score (Title) VALUES (?);", Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, title);
        ResultSet rs = prep.getGeneratedKeys();
        if (rs.next()){
            return rs.getInt(1);
        }
        return -1;
    }

    /**
     * Part of usecase 4, link score to title
     * @param scoreID
     * @param titleID
     * @throws SQLException
     */
    public void linkScoreTitle(int scoreID, int titleID) throws SQLException {
        PreparedStatement prep = conn.prepareStatement("INSERT INTO scoreInTitle (TitleID, ScoreID) VALUES (?, ?);");
        prep.setInt(1, titleID);
        prep.setInt(2, scoreID);
    }

    /**
     * Part of usecase 4, link score and person with a given role
     * @param personID
     * @param scoreID
     * @param role
     * @throws SQLException
     */
    public void linkPersonScore(int personID, int scoreID, String role) throws SQLException {
        PreparedStatement prep = conn.prepareStatement("INSERT INTO roleInScore (PersonID, ScoreID, Role) VALUES (?, ?, ?)");
        prep.setInt(1, personID);
        prep.setInt(2, scoreID);
        prep.setString(3, role);
    }

    public void interactWithScoreThroughConsole(){
        //TODO
    }
}
