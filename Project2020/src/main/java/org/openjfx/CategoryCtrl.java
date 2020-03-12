package org.openjfx;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CategoryCtrl extends DBConnector {

    /**
     * Part of usecase 4, insert category
     * @param name
     * @return
     * @throws SQLException
     */
    public int insertCategory(String name) throws SQLException {
        PreparedStatement prep = conn.prepareStatement("INSERT INTO category (Name) VALUES (?);", Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, name);
        int updatedColumns = prep.executeUpdate();
        ResultSet rs = prep.getGeneratedKeys();
        if (rs.next()){
            return rs.getInt(1);
        }
        return -1;
    }
}
