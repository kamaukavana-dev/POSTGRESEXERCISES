import java.sql.*;
import java.util.Scanner;

public class AnalyticsCLI {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- AnalyticsCLI ---");
                System.out.println("1. Add Employee");
                System.out.println("2. List All Employees");
                System.out.println("3. Show Hierarchy (Recursive)");
                System.out.println("4. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();





