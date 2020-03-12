package main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    CategoryCtrl categoryCtrl = new CategoryCtrl();
    CompanyCtrl companyCtrl = new CompanyCtrl();
    PersonCtrl personCtrl = new PersonCtrl();
    ReviewCtrl reviewCtrl = new ReviewCtrl();
    ScoreCtrl scoreCtrl = new ScoreCtrl();
    TitleCtrl titleCtrl = new TitleCtrl();
    UserCtrl userCtrl = new UserCtrl();


    private void connect() {
        categoryCtrl.connect();
        companyCtrl.connect();
        personCtrl.connect();
        reviewCtrl.connect();
        scoreCtrl.connect();
        titleCtrl.connect();
        userCtrl.connect();
    }

    Scanner scanner = new Scanner(System.in);

    public void printMenu(){
        System.out.println("Please choose an option:\n" +
                "1: Find roles played by a given actor\n" +
                "2: Find titles a given actor has played in\n" +
                "3: Find which company has made most titles in each category\n" +
                "4: Insert new movie\n" +
                "5: Insert review\n" +
                "6: Register user\n" +
                "7: Exit");
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


    public int insertTitle() throws SQLException {
        System.out.println("Choose titlename: ");
        String name = scanner.nextLine().strip();
        System.out.println("Type in title-content: ");
        String content = scanner.nextLine().strip();
        System.out.println("Type in duration: ");
        String s = scanner.nextLine();
        while (!testInteger(s, Integer.MAX_VALUE)){
            System.out.println("Value must be a positive integer, try again: ");
            s = scanner.nextLine().strip();
        }
        int duration = Integer.parseInt(s);

        System.out.println("Type in publishyear: ");
        s = scanner.nextLine();
        while (!testInteger(s, Integer.MAX_VALUE)){
            System.out.println("Value must be a positive integer, try again: ");
            s = scanner.nextLine().strip();
        }
        int publishyear = Integer.parseInt(s);

        System.out.println("Type in launchyear: ");
        s = scanner.nextLine();
        while (!testInteger(s, Integer.MAX_VALUE)){
            System.out.println("Value must be a positive integer, try again: ");
            s = scanner.nextLine().strip();
        }
        int launchyear = Integer.parseInt(s);

        return titleCtrl.insertTitle(name,content,duration,publishyear,launchyear);

    }

    public int addScore() throws SQLException {
        System.out.println("Title: ");
        String title = scanner.nextLine().strip();

        return scoreCtrl.insertScore(title);
    }



    public int addPerson() throws SQLException{

        System.out.println("Name: ");
        String name = scanner.nextLine().strip();

        System.out.println("Birthyear: ");
        String s = scanner.nextLine();
        while (!testInteger(s, 2020)){
            System.out.println("Enter a valid year: ");
            s = scanner.nextLine().strip();
        }
        int birthyear = Integer.parseInt(s);
        System.out.println("Country: ");
        String country = scanner.nextLine().strip();

        return personCtrl.insertPerson(name, birthyear, country);


    }


    public int addCompany() throws SQLException{
        System.out.println("Name: ");
        String name = scanner.nextLine().strip();
        System.out.println("URL: ");
        String url = scanner.nextLine().strip();
        System.out.println("Country: ");
        String country = scanner.nextLine();
        return companyCtrl.insertCompany(name, url, country);
    }

    public void linkStaff(int titleID, List<Integer> ids, String role) throws SQLException{
        for (int director : ids){
            if (director >= 0) {
            titleCtrl.linkPersonTitle(titleID, director, role, false); }
        }
    }

    public void linkActorTitle(int titleID, List<Integer> actorIDs, List<String> roles) throws SQLException{
        for (int i = 0; i < actorIDs.size(); i++){
            if (actorIDs.get(i) >= 0){
                titleCtrl.linkPersonTitle(titleID, actorIDs.get(i), roles.get(i), true);
            }
        }
    }

    public void linkCategoryTitle(int titleID, List<Integer> categories) throws SQLException{
        for (int cat: categories){
            if (cat > 0)
                titleCtrl.linkCategoryTitle(titleID, cat);
        }
    }

    public void linkCompanyTitle(int titleID, List<Integer> companyIDs, List<String> roles) throws SQLException{
        for (int i = 0; i < companyIDs.size(); i++){
            if (companyIDs.get(i) >= 0){
                companyCtrl.linkCompanyTitle(companyIDs.get(i), titleID, roles.get(i));
            }
        }
    }

    public void linkScorePerson(int scoreID, List<Integer> personIDs, List<String> scoreRoles) throws SQLException{
        for (int i = 0; i < personIDs.size(); i++){
            if (personIDs.get(i) >= 0){
                scoreCtrl.linkPersonScore(personIDs.get(i), scoreID, scoreRoles.get(i));
            }
        }
    }

    public void linkScoreTitle(int titleID, List<Integer> scoreIDs) throws SQLException{
        for (int i = 0; i < scoreIDs.size(); i++){
            if (scoreIDs.get(i) >= 0){
                scoreCtrl.linkScoreTitle(scoreIDs.get(i), titleID);
            }
        }
    }
    


    public void addTitleWithMore() throws SQLException {
        int titleID = this.insertTitle();
        List<Integer> directorIDs = new ArrayList<Integer>();
        List<Integer> actorIDs = new ArrayList<Integer>();
        List<Integer> writerIDs = new ArrayList<Integer>();
        List<String> roles = new ArrayList<>();
        List<Integer> categoyIDs = new ArrayList<>();
        List<Integer> companyIDs = new ArrayList<>();
        List<String> comRoles = new ArrayList<>();

        System.out.println("Do you want to add a director? (y/n)");
        String s = scanner.nextLine().strip();

        while (s.toLowerCase().equals("y")){
            directorIDs.add(addPerson());

            System.out.println("Do you want to add another director? (y/n)");
            s = scanner.nextLine().strip();
        }

        linkStaff(titleID, directorIDs, "Director");



        System.out.println("Do you want to add a screenwriter? (y/n)");
        s = scanner.nextLine().strip();

        while (s.toLowerCase().equals("y")){
            writerIDs.add(addPerson());

            System.out.println("Do you want to add another screenwriter? (y/n)");
            s = scanner.nextLine().strip();
        }

        linkStaff(titleID, writerIDs, "Screenwriter");

        System.out.println("Do you want to add a an actor? (y/n)");
        s = scanner.nextLine().strip();

        while (s.toLowerCase().equals("y")){
            directorIDs.add(addPerson());
            System.out.println("Role: ");
            String role = scanner.nextLine().strip();
            roles.add(role);
            System.out.println("Do you want to add another actor? (y/n)");
            s = scanner.nextLine().strip();
        }

        linkActorTitle(titleID, actorIDs, roles);

        System.out.println("Do you want to add a category? (y/n)");
        s = scanner.nextLine().strip();

        while (s.toLowerCase().equals("y")){

            System.out.println("Category name: ");
            s = scanner.nextLine().strip();
            categoyIDs.add(categoryCtrl.insertCategory(s));

            System.out.println("Do you want to add another category? (y/n)");
            s = scanner.nextLine().strip();

        }

        linkCategoryTitle(titleID, categoyIDs);



        System.out.println("Do you want to add a company? (y/n)");
        s = scanner.nextLine().strip();

        while (s.toLowerCase().equals("y")){
            companyIDs.add(addCompany());
            System.out.println("Role: ");
            String role = scanner.nextLine().strip();
            comRoles.add(role);
            System.out.println("Do you want to add another company? (y/n)");
            s = scanner.nextLine().strip();
        }

        linkCompanyTitle(titleID, companyIDs, comRoles);

        System.out.println("Do you want to add a score? (y/n)");
        s = scanner.nextLine().strip();
        List<Integer> scoreIDs = new ArrayList<>();
        int scoreID;
        while (s.toLowerCase().equals("y")){
            scoreID = addScore();
            scoreIDs.add(scoreID);

            List<Integer> scorePersonsIDs = new ArrayList<>();
            List<String> scoreRoles = new ArrayList<>();
            System.out.println("Do you want to add a person to this score? (y/n)");
            while (s.toLowerCase().equals("y")) {
                scorePersonsIDs.add(addPerson());
                System.out.println("Role: ");
                String scoreRole = scanner.nextLine().strip();
                scoreRoles.add(scoreRole);

                System.out.println("Do you want to add another person to this score? (y/n)");
                s = scanner.nextLine().strip();
            }

            if (scoreID >= 0) {
                linkScorePerson(scoreID, scorePersonsIDs, scoreRoles);
            }

            System.out.println("Do you want to add another score? (y/n)");
            s = scanner.nextLine().strip();
        }

        linkScoreTitle(titleID, scoreIDs);

        System.out.println("Everything worked!");

    }
    public void run() throws SQLException {


        while (true) {
            printMenu();
            String s = scanner.nextLine();


            while (!testInteger(s, 7)) {
                System.out.println("Please pick an integer between 1 and 7");
                s = scanner.nextLine().strip();
            }

            int action = Integer.parseInt(s);

            switch (action) {
                case 1: // Find roles played by a given actor
                    personCtrl.searchForRoles();
                    break;
                case 2: // Find titles a given actor has played in
                    personCtrl.getMoviesStarringActor();
                    break;
                case 3: // Find which company has made most titles in each category
                    System.out.println(companyCtrl.getCompaniesWithMostMoviesInCategory());
                    break;
                case 4: // Insert new movie
                    addTitleWithMore();
                    break;
                case 5: // Insert review
                    reviewCtrl.registerReview();
                    break;
                case 6: // Register user
                    userCtrl.registerUser();
                    break;
                case 7: // Exit
                    return;


            }
        }

    }


    public static void main(String[] args){

        Main main = new Main();
        main.connect();
        try {
            main.run();
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("There is a problem with SQL.");
        }

    }
}
