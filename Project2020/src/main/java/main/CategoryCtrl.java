package main;

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
    public int insertCategory(String name)  {
        try {
            PreparedStatement prep = conn.prepareStatement("INSERT INTO category (Name) VALUES (?);", Statement.RETURN_GENERATED_KEYS);
            prep.setString(1, name);
            int updatedColumns = prep.executeUpdate();
            ResultSet rs = prep.getGeneratedKeys();
            if (rs.next()){
                return rs.getInt(1);
            }
            return -1;
        }
        catch (SQLException e){
            try {
               return getID(name);
            }
            catch (SQLException ex){
                System.out.println("Something went wrong");
                return -1;
            }

        }


    }

    private int getID(String name) throws SQLException{
        PreparedStatement prep = conn.prepareStatement("SELECT CategoryID from category where Name = ?");
        prep.setString(1, name);
        ResultSet rs = prep.executeQuery();

        if (rs.next()){
            return rs.getInt(1);
        }
        return -1;
    }

    public static void main(String[] args) {
        CategoryCtrl c = new CategoryCtrl();
        c.connect();
        try {
            System.out.println(c.getID("Drama"));
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
