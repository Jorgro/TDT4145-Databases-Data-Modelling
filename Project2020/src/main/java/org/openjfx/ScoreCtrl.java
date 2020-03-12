package org.openjfx;

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

    public void linkScoreTitle(int scoreID, int titleID) throws SQLException {
        
    }
}
