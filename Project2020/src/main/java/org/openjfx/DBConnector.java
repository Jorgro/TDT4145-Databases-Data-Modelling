package org.openjfx;

import java.sql.*;
import java.util.*;

public class DBConnector {

    protected Connection conn;

    int pjusValue;
    public DBConnector () {
    }
    public void connect() {
        try {
            // Class.forName("com.mysql.jdbc.Driver").newInstance();
            Class.forName("com.mysql.cj.jdbc.Driver"); // when you are using MySQL 8.0.
            // Properties for user and password.
        } catch (Exception e) {
            throw new RuntimeException("No classname", e);
        }

        Properties p = new Properties();
        p.put("user", "larswwa_tdt4145");
        p.put("password", "xIy8mkm1");

        try {
            conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no/larswwa_tdt4145?autoReconnect=true&useSSL=false",p);
//            PreparedStatement prep = conn.prepareStatement("SELECT * FROM person");
//            ResultSet rs = prep.executeQuery();
//            while (rs.next()) {
//                System.out.println(rs.getString("Name"));
//            }
            //conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/?user=root",p);
        } catch (Exception e)
        {
            throw new RuntimeException("Unable to connect", e);
        }
    }

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
            if (result.containsKey(actor)) {
                result.get(actor).add(title);
            } else {
                result.put(actor, new ArrayList<>());
                result.get(actor).add(title);
            }
        }
        return result;
    }

    public int insertCategory(String name) throws SQLException{
        PreparedStatement prep = conn.prepareStatement("INSERT INTO category (Name) VALUES (?);", Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, name);
        int updatedColumns = prep.executeUpdate();
        ResultSet rs = prep.getGeneratedKeys();
        if (rs.next()){
            return rs.getInt(1);
        }
        return -1;
    }


    public int insertMovie(String name, String content, int duration, int publishYear, int launchYear) throws SQLException{

        PreparedStatement prep = conn.prepareStatement(
                "INSERT INTO title (Name, Content, Duration, PublishYear, LaunchYear) VALUES (?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, name);
        prep.setString(2, content);
        prep.setInt(3, duration);
        prep.setInt(4, publishYear);
        prep.setInt(5, launchYear);
        int updatedColums = prep.executeUpdate();
        ResultSet rs = prep.getGeneratedKeys();

        if (rs.next()){
            return rs.getInt(1);
        }
        return -1;
    }


    public void linkActorTitle(int TitleID, int ActorID, String Role) throws SQLException{
        PreparedStatement prep = conn.prepareStatement(
                "INSERT INTO personTitle (TitleID, PersonID, Role, Actor) VALUES (?,?,?,?);");
        prep.setInt(1, TitleID);
        prep.setInt(2, ActorID);
        prep.setString(3, Role);
        prep.setBoolean(4, true);
        prep.executeUpdate();
    }

    public static void main(String[] args){
        DBConnector db1 = new DBConnector();
        db1.connect();
        try {
            System.out.println(db1.getActorRolesByName("Kit"));
//            System.out.println(db1.getMoviesByActorID(1));
//            System.out.println(db1.getMoviesByActorName("Kris"));
//            System.out.println(db1.insertCategory("Hello world"));
//            System.out.println(db1.insertMovie("The Kingsroad", "S1E2, walk on road", 1, 2011, 2011));
//            db1.linkActorTitle(5, 1, "Jon Snow");
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
