import java.sql.*;
import java.util.Scanner;

public class UserManagementCli {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- UserManagementCLI ---");
                System.out.println("1. Add User");
                System.out.println("2. Authenticate User");
                System.out.println("3. List Users");
                System.out.println("4. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String pwd = scanner.nextLine();
                    System.out.print("Role (admin/editor/viewer): ");
                    String role = scanner.nextLine();

                    String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, username);
                        stmt.setString(2, pwd); // in real apps, hash this!
                        stmt.setString(3, role);
                        stmt.executeUpdate();
                        System.out.println("User added!");
                    } catch (SQLException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                } else if (choice.equals("2")) {
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String pwd = scanner.nextLine();

                    String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, username);
                        stmt.setString(2, pwd);
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                            String role = rs.getString("role");
                            System.out.println("Authenticated! Role: " + role);
                        } else {
                            System.out.println("Invalid credentials.");
                        }
                    }
                } else if (choice.equals("3")) {
                    String sql = "SELECT id, username, role FROM users ORDER BY id";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%d | %s | %s%n",
                                    rs.getInt("id"),
                                    rs.getString("username"),
                                    rs.getString("role"));
                        }
                    }
                } else if (choice.equals("4")) {
                    System.out.println("Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid choice.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
