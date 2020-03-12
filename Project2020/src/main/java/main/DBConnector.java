package main;

import java.sql.*;
import java.util.*;

public class DBConnector {

    protected Connection conn;

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


    public static void main(String[] args){
        DBConnector db1 = new DBConnector();
        db1.connect();
        try {
            System.out.println("hello");
//            System.out.println(db1.insertCategory("Hello world"));
//            System.out.println(db1.insertMovie("The Kingsroad", "S1E2, walk on road", 1, 2011, 2011));
//            db1.linkActorTitle(5, 1, "Jon Snow");
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
