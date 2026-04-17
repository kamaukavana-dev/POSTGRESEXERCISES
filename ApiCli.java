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

                if (choice.equals("1")) {
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String pwd = scanner.nextLine();
                    System.out.print("Role (admin/editor/viewer): ");
                    String role = scanner.nextLine();

                    String sql = "INSERT INTO api_users (username, password, role) VALUES (?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, username);
                        stmt.setString(2, pwd); // hash in real apps
                        stmt.setString(3, role);
                        stmt.executeUpdate();
                        System.out.println("User registered!");
                    }
                } else if (choice.equals("2")) {
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String pwd = scanner.nextLine();

                    String sql = "SELECT id, role FROM api_users WHERE username = ? AND password = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, username);
                        stmt.setString(2, pwd);
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                            System.out.println("Authenticated! Role: " + rs.getString("role"));
                        } else {
                            System.out.println("Invalid credentials.");
                        }
                    }
                } else if (choice.equals("3")) {
                    System.out.print("User ID: ");
                    int userId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Action (create/read/update/delete): ");
                    String action = scanner.nextLine();

                    String sql = "INSERT INTO api_logs (user_id, action) VALUES (?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, userId);
                        stmt.setString(2, action);
                        stmt.executeUpdate();
                        System.out.println("Action logged!");
                    }



