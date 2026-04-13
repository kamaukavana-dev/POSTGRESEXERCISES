import java.sql.*;
import java.util.Scanner;

public class SurveyCLI {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- SurveyCLI ---");
                System.out.println("1. Add Question");
                System.out.println("2. Record Response");
                System.out.println("3. Show Average Ratings");
                System.out.println("4. Show Questions with >= 5 Responses");
                System.out.println("5. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

}








