package org.openjfx;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class FindTitlesActorCtrl extends DBConnector {

    private Scanner scanner = new Scanner(System.in);

    public HashMap<Integer, List<String>> getActorByName(String name) throws SQLException {
        HashMap<Integer, List<String>> result = new HashMap<>();
        PreparedStatement prep = conn.prepareStatement("SELECT * FROM person p WHERE p.Name LIKE ?");
        name = "%" + name + "%";
        prep.setString(1, name);
        ResultSet rs = prep.executeQuery();
        while (rs.next()) {
            result.put(rs.getRow(), new ArrayList<>(Arrays.asList(rs.getString("PersonID"), rs.getString("Name"), rs.getString("Birthyear"))));
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

    public List<String> getActorTitlesById(int id) throws SQLException {
        PreparedStatement prep = conn.prepareStatement("SELECT DISTINCT t.Name FROM personTitle INNER JOIN title t on personTitle.TitleID = t.TitleID WHERE Actor = TRUE AND PersonID = ?");
        prep.setInt(1, id);
        ResultSet rs = prep.executeQuery();
        List<String> result = new ArrayList<>();
        while (rs.next()){
            String s = rs.getString("Name");
            if (!(result.contains(s)))
                result.add(rs.getString("Name"));
        }
        return result;
    }


    public void searchForTitles() throws SQLException{
        System.out.println("Please write the name of an actor of which you want to find participated titles: ");
        String s = scanner.nextLine();
        HashMap<Integer, List<String>> list = getActorByName(s);
        if (list.isEmpty()) {
            System.out.println("There exist no registered actor with this name who acts in a registered title");
        }
        else if (list.size() == 1){
            System.out.println("Titles for " + list.get(1).get(1) + ": " + getActorTitlesById(Integer.parseInt(list.get(1).get(0))));
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
            System.out.println("Titles for " + list.get(key).get(1) + ": " + getActorTitlesById(personID));
        }
    }

    public static void main(String[] args) {
        FindTitlesActorCtrl c = new FindTitlesActorCtrl();
        c.connect();

        try {
            c.searchForTitles();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
