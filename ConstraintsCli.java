import java.sql.*;
import java.util.Scanner;

public class ConstraintsCLI {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- ConstraintsCLI ---");
                System.out.println("1. Add Student");
                System.out.println("2. List Students");
                System.out.println("3. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Age: ");
                    int age = Integer.parseInt(scanner.nextLine());

                    String sql = "INSERT INTO students (name, email, age) VALUES (?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, name);
                        stmt.setString(2, email);
                        stmt.setInt(3, age);
                        stmt.executeUpdate();
                        System.out.println("Student added!");
                    } catch (SQLException e) {
                        System.out.println("Constraint violation: " + e.getMessage());
                    }
                } else if (choice.equals("2")) {
                    String sql = "SELECT id, name, email, age, enrolled FROM students ORDER BY id";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%d | %s | %s | Age: %d | Enrolled: %s%n",
                                    rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getString("email"),
                                    rs.getInt("age"),
                                    rs.getBoolean("enrolled"));
                        }
                    }
                } else if (choice.equals("3")) {
                    System.out.println("Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid choice.");
                }
            }

