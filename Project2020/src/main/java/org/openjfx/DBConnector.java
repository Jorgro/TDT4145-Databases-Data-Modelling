package org.openjfx;

import java.sql.*;
import java.util.*;

public class DBConnector {

    protected Connection conn;

    public DBConnector () {
    }

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // when you are using MySQL 8.0.
        } catch (Exception e) {
            throw new RuntimeException("No classname", e);
        }

        Properties p = new Properties();
        p.put("user", "larswwa_tdt4145");
        p.put("password", "xIy8mkm1");

        try {
            conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no/larswwa_tdt4145?autoReconnect=true&useSSL=false",p);
        } catch (Exception e)
        {
            throw new RuntimeException("Unable to connect", e);
        }
    }
}
