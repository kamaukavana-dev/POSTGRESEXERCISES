import java.sql.*;
import java.util.Scanner;

public class LibraryManagementCli {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- LibraryManagerCLI ---");
                System.out.println("1. Add Author");
                System.out.println("2. Add Book");
                System.out.println("3. List Books with Authors");
                System.out.println("4. Delete Author");
                System.out.println("5. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Author name: ");
                    String name = scanner.nextLine();
                    String sql = "INSERT INTO authors (name) VALUES (?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, name);
                        stmt.executeUpdate();
                        System.out.println("Author added!");
                    }

