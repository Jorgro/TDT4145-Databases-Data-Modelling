package org.openjfx;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ActorTitleCtrl extends DBConnector {
    public void linkActorTitle(int TitleID, int ActorID, String Role) throws SQLException {
        PreparedStatement prep = conn.prepareStatement(
                "INSERT INTO personTitle (TitleID, PersonID, Role, Actor) VALUES (?,?,?,?);");
        prep.setInt(1, TitleID);
        prep.setInt(2, ActorID);
        prep.setString(3, Role);
        prep.setBoolean(4, true);
        prep.executeUpdate();
    }}
