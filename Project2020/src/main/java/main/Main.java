package main;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

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

    public void run() throws SQLException {
        CategoryCtrl categoryCtrl = new CategoryCtrl();
        CompanyCtrl companyCtrl = new CompanyCtrl();
        PersonCtrl personCtrl = new PersonCtrl();
        ReviewCtrl reviewCtrl = new ReviewCtrl();
        ScoreCtrl scoreCtrl = new ScoreCtrl();
        TitleCtrl titleCtrl = new TitleCtrl();
        UserCtrl userCtrl = new UserCtrl();

        categoryCtrl.connect();
        companyCtrl.connect();
        personCtrl.connect();
        reviewCtrl.connect();
        scoreCtrl.connect();
        titleCtrl.connect();
        userCtrl.connect();

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

                case 5: // Insert review
                    reviewCtrl.registerReview();
                case 6: // Register user
                    userCtrl.registerUser();
                case 7: // Exit
                    return;


            }
        }

    }


    public static void main(String[] args){
        Main main = new Main();
        try {
            main.run();
        }
        catch (SQLException e){
            System.out.println("There is a problem with SQL");
        }

    }
}
