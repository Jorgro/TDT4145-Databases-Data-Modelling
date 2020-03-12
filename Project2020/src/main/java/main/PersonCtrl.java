package main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class PersonCtrl extends DBConnector {

    /**
     * Part of usecase 4: Inserts a person in the database.
     * @param name
     * @param birthYear
     * @param country
     * @return Primary key of the inserted person
     * @throws SQLException
     */
    public int insertPerson(String name, int birthYear, String country) throws SQLException {
        PreparedStatement prep = conn.prepareStatement("INSERT INTO person (Name, Birthyear, Country) VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, name);
        prep.setInt(2, birthYear);
        prep.setString(3, country);
        int updatedColumns = prep.executeUpdate();
        ResultSet rs = prep.getGeneratedKeys();

        if (rs.next()){
            return rs.getInt(1);
        }
        return -1;
    }

    /**
     *
     * @param name
     * @return Map with personID as key, list of roles as values.
     * @throws SQLException
     */
    public HashMap<Integer, List<String>> getActorByName(String name) throws SQLException{
        HashMap<Integer, List<String>> result = new HashMap<>();
        PreparedStatement prep = conn.prepareStatement(
                "SELECT DISTINCT p.PersonID, Name, Birthyear " +
                    "FROM person p INNER JOIN personTitle pT on p.PersonID = pT.PersonID " +
                    "WHERE p.Name LIKE ? AND pT.Actor = TRUE");
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

    public List<String> getMoviesByActorID(int PersonID) throws SQLException {
        List<String> result = new ArrayList<>();
        PreparedStatement prep = conn.prepareStatement(
                "SELECT DISTINCT t.Name " +
                        "FROM personTitle INNER JOIN title t on personTitle.TitleID = t.TitleID " +
                        "WHERE Actor = TRUE AND PersonID = ?"
        );
        prep.setInt(1, PersonID);
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

    /**
     * Part of usecase 1, by using personID
     * @param personID
     * @return List of roles for a given actor
     * @throws SQLException
     */
    public List<String> getActorRolesById(int personID) throws SQLException {
        PreparedStatement prep = conn.prepareStatement(
                "SELECT Role " +
                        "FROM personTitle INNER JOIN person p on personTitle.PersonID = p.PersonID " +
                        "WHERE p.PersonID = ? " +
                            "AND Actor = TRUE");
        prep.setInt(1, personID);
        ResultSet rs = prep.executeQuery();
        List<String> result = new ArrayList<>();
        while (rs.next()){
            String s = rs.getString("Role");
            if (!(result.contains(s)))
                result.add(rs.getString("Role"));
        }
        return result;
    }

    /**
     * Part of usecase 1, by using actor name
     * @param name
     * @return Map consisting of all actors with a matching name, and a list of roles for all the actors.
     * @throws SQLException
     */
    public Map<String, List<String>> getActorRolesByName(String name) throws SQLException {
        PreparedStatement prep = conn.prepareStatement(
                "SELECT DISTINCT Role, Name " +
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

    private String getAnswer(String question){
        System.out.println(question);
        Scanner scanner = new Scanner(System.in);
        String answer = "0";
        if (scanner.hasNext()){
            answer = scanner.next();
        }
        return answer;
    }

    /**
     * Usecase 2, by ID or actor name
     * @return
     * @throws SQLException
     */
    public List<String> getMoviesStarringActor() throws SQLException {
        int answer = Integer.parseInt(getAnswer("How do you want to identify the actor?\nSelect 1 for ID and 2 for name"));
        int personID = 0;
        String name = null;
        List<String> movies = null;
        switch (answer) {
            case 1: personID = Integer.parseInt(getAnswer("ID:"));
                break;
            case 2: name = getAnswer("Name:");
                break;
            default: throw new UnsupportedOperationException(answer + " is not supported");
        }
        if (personID != 0){
            movies = getMoviesByActorID(personID);
        }

        if (name != null) {
            Map<String, List<String>> potentialActors = getMoviesByActorName(name);
            if (potentialActors.size() > 0) {
                int forloopcounter = 0;
                Map<Integer, List<String>> selectActorMap = new HashMap<>();
                for (String act : potentialActors.keySet()) {
                    forloopcounter++;
                    System.out.println("[" + forloopcounter + "]: " + act);
                    selectActorMap.put(forloopcounter, potentialActors.get(act));
                }
                int selectedActor = Integer.parseInt(getAnswer("Which of these did you mean?"));

                if (selectActorMap.containsKey(selectedActor)){
                    movies = selectActorMap.get(selectedActor);
                } else {
                    System.out.println("Illegal key");
                }
            } else {
                System.out.println("No actors by that name.");
            }
        }
        return movies;
    }

    Scanner scanner = new Scanner(System.in);


    /**
     * Usecase 1, by actor name. Prints the result.
     * @throws SQLException
     */
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
        PersonCtrl pc = new PersonCtrl();
        pc.connect();
        try {
            System.out.println(pc.getMoviesStarringActor());
            pc.searchForRoles();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
