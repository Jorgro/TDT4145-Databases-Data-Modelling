package org.openjfx;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ActorRoleCtrl extends DBConnector {

    public List<String> getActorRolesById(int id) throws SQLException {
        PreparedStatement prep = conn.prepareStatement("SELECT Role FROM personTitle WHERE PersonID = ?");
        prep.setInt(1, id);
        ResultSet rs = prep.executeQuery();
        List<String> result = new ArrayList<>();
        while (rs.next()){
            result.add(rs.getString("Role"));
        }
        return result;
    }

    public Map<String, List<String>> getActorRolesByName(String name) throws SQLException {
        PreparedStatement prep = conn.prepareStatement(
                "SELECT Role, Name " +
                        "FROM personTitle INNER JOIN person ON personTitle.PersonID = person.PersonID " +
                        "WHERE Actor = TRUE AND Name LIKE ?");
        prep.setString(1, "%" + name + "%");
        ResultSet rs = prep.executeQuery();
        Map<String, List<String>> result = new HashMap<>();

        while (rs.next()){
            String role = rs.getString("Role");
            String actor = rs.getString("Name");
            if (result.containsKey(actor)) {
                result.get(actor).add(role);
            } else {
                result.put(actor, new ArrayList<>());
                result.get(actor).add(role);
            }
        }
        return result;
    }

    public Map<Integer, List<String>> getActorByName(String name) throws SQLException{
        Map<Integer, List<String>> result = new HashMap<>();
        PreparedStatement prep = conn.prepareStatement("SELECT * FROM person p WHERE p.Name LIKE ?");
        name = "%" + name + "%";
        prep.setString(1, name);
        ResultSet rs = prep.executeQuery();
        while (rs.next()){
            result.put(rs.getRow(),  new ArrayList<>(Arrays.asList(rs.getString("PersonID"), rs.getString("Name"), rs.getString("Birthyear"))));
        }
        return result;
    }

    public static void main(String[] args) {
        ActorRoleCtrl Actor = new ActorRoleCtrl();
        Actor.connect();
        try {
            System.out.println(Actor.getActorRolesByName("Kit"));

            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
