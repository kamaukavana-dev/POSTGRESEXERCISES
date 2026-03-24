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

                if (choice.equals("1")) {
                    System.out.print("Employee name: ");
                    String name = scanner.nextLine();
                    System.out.print("Manager ID (or blank for none): ");
                    String managerInput = scanner.nextLine();
                    Integer managerId = managerInput.isEmpty() ? null : Integer.parseInt(managerInput);

                    String sql = "INSERT INTO employees (name, manager_id) VALUES (?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, name);
                        if (managerId == null) {
                            stmt.setNull(2, Types.INTEGER);
                        } else {
                            stmt.setInt(2, managerId);
                        }
                        stmt.executeUpdate();
                        System.out.println("Employee added!");
                    }
                } else if (choice.equals("2")) {
                    String sql = "SELECT id, name, manager_id FROM employees ORDER BY id";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%d | %s | Manager: %s%n",
                                    rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getObject("manager_id"));
                        }
                    }
                } else if (choice.equals("3")) {
                    String sql = "WITH RECURSIVE employee_hierarchy AS (" +
                                 "SELECT id, name, manager_id, 1 AS level " +
                                 "FROM employees WHERE manager_id IS NULL " +
                                 "UNION ALL " +
                                 "SELECT e.id, e.name, e.manager_id, eh.level + 1 " +
                                 "FROM employees e JOIN employee_hierarchy eh ON e.manager_id = eh.id) " +
                                 "SELECT * FROM employee_hierarchy ORDER BY level, name";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("Level %d | %s (ID: %d)%n",
                                    rs.getInt("level"),
                                    rs.getString("name"),
                                    rs.getInt("id"));
                        }
                    }

