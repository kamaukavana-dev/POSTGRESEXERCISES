import java.sql.*;
import java.util.Scanner;

public class GradesAnalyticsCLI {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- GradesAnalyticsCLI ---");
                System.out.println("1. Add Grade");
                System.out.println("2. Show Average Score per Subject");
                System.out.println("3. Show Subjects with Average > 70");
                System.out.println("4. Show Top Student per Subject");
                System.out.println("5. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();







