package org.openjfx;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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

    public String getAnswer(String question){
        System.out.println(question);
        Scanner scanner = new Scanner(System.in);
        String answer = "0";
        if (scanner.hasNext()){
            answer = scanner.next();
        }
        return answer;
    }

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
            int forloopcounter = 0;
            for (List<String> a : potentialActors.values()) {
                forloopcounter++;
                System.out.println("[" + forloopcounter + "]: " + a);
            }
            int selectedActor = Integer.parseInt(getAnswer("Which of these did you mean?"));

        }
        return movies;
    }

    public static void main(String[] args){
        MovieController mc = new MovieController();
        mc.connect();
        try{
            List<String> movies = mc.getMoviesStarringActor();
            System.out.println(movies);
        } catch (SQLException e){
            System.out.println(e);
        }

    }


}
