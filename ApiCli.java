import java.sql.*;
import java.util.Scanner;

public class ApiCli {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- APICLI ---");
                System.out.println("1. Register User");
                System.out.println("2. Authenticate User");
                System.out.println("3. Perform Action (CRUD)");
                System.out.println("4. Show Logs");
                System.out.println("5. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();












