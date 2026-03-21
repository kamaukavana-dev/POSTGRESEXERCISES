import java.sql.*;
import java.util.Scanner;

public class MovieRatingsCLI {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- MovieRatingsCLI ---");
                System.out.println("1. Add Movie");
                System.out.println("2. List All Movies");
                System.out.println("3. Rank Movies by Genre");
                System.out.println("4. Show Top 3 Movies per Genre");
                System.out.println("5. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();





