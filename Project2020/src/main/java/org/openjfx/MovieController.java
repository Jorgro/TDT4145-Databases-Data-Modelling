package org.openjfx;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieController extends DBConnector {
    public List<String> getMoviesByActorID(int actorID) throws SQLException {
        List<String> result = new ArrayList<>();
        PreparedStatement prep = conn.prepareStatement(
                "SELECT DISTINCT t.Name " +
                        "FROM personTitle INNER JOIN title t on personTitle.TitleID = t.TitleID " +
                        "WHERE Actor = TRUE AND PersonID = ?"
        );
        prep.setInt(1, actorID);
        ResultSet rs = prep.executeQuery();
        while (rs.next()){
            result.add(rs.getString("Name"));
        }
        return result;
    }

    public Map<String, List<String>> getMoviesByActorName(String name) throws SQLException{
        Map<String, List<String>> result = new HashMap<>();
        PreparedStatement prep = conn.prepareStatement(
                "SELECT t.Name as TitleName, p.Name as ActorName " +
                        "FROM personTitle pt NATURAL JOIN title t INNER JOIN person p ON p.PersonID = pt.PersonID " +
                        "WHERE Actor = TRUE AND p.Name LIKE ?"
        );
        prep.setString(1, "%" + name + "%");
        ResultSet rs = prep.executeQuery();
        while (rs.next()){
            String title = rs.getString("TitleName");
            String actor = rs.getString("ActorName");
            if (!result.containsKey(actor)) {
                result.put(actor, new ArrayList<>());
            }
            result.get(actor).add(title);
        }
        return result;
    }


}
