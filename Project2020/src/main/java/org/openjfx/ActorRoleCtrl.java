package org.openjfx;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ActorRoleCtrl extends DBConnector {

    Scanner scanner = new Scanner(System.in);

    public List<String> getActorRolesById(int id) throws SQLException {
        PreparedStatement prep = conn.prepareStatement("SELECT Role FROM personTitle WHERE PersonID = ?");
        prep.setInt(1, id);
        ResultSet rs = prep.executeQuery();
        List<String> result = new ArrayList<>();
        while (rs.next()){
            String s = rs.getString("Role");
            if (!(result.contains(s)))
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

    public HashMap<Integer, List<String>> getActorByName(String name) throws SQLException{
        HashMap<Integer, List<String>> result = new HashMap<>();
        PreparedStatement prep = conn.prepareStatement("SELECT * FROM person p WHERE p.Name LIKE ?");
        name = "%" + name + "%";
        prep.setString(1, name);
        ResultSet rs = prep.executeQuery();
        while (rs.next()){
            result.put(rs.getRow(),  new ArrayList<>(Arrays.asList(rs.getString("PersonID"), rs.getString("Name"), rs.getString("Birthyear"))));
        }
        return result;
    }
    private boolean testInteger(String s, int max){
        try {
            int i = Integer.parseInt(s);
            return i <= max && i > 0;
        }
        catch (NumberFormatException e){
            return false;
        }
    }


    public void searchForRoles() throws SQLException{
        System.out.println("Enter the name of a person you want to find roles for: ");
        String s = scanner.nextLine();
        HashMap<Integer, List<String>> list = getActorByName(s);

        if (list.isEmpty()) {
            System.out.println("There exist no registered actor with this name who acts in a registered title");
        }
        else if (list.size() == 1){
            System.out.println("Roles for " + list.get(1).get(1) + ": " + getActorRolesById(Integer.parseInt(list.get(1).get(0))));
        }

        else{
            list.forEach((key, value) -> System.out.println(key + ": "+ value.get(1) + ", " + value.get(2)));
            System.out.println("There appears to be multiple matches. Please enter the number of the wanted person: ");
            s = scanner.nextLine();
            while (!testInteger(s, list.size())){
                System.out.println("Invalid entry, please enter a number between 1 and " + list.size());
                s = scanner.nextLine();
            }
            int key = Integer.parseInt(s);
            int personID = Integer.parseInt(list.get(key).get(0));
            System.out.println("Roles for " + list.get(key).get(1) + ": " + getActorRolesById(personID));
        }





    }

    public static void main(String[] args) {
        ActorRoleCtrl Actor = new ActorRoleCtrl();
        Actor.connect();
        try {
            //System.out.println(Actor.getActorRolesByName("Kit"));
            Actor.searchForRoles();

            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
