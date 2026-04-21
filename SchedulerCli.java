import java.sql.*;
import java.util.Scanner;

public class SchedulerCli {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- SchedulerCLI ---");
                System.out.println("1. Add Task");
                System.out.println("2. Show Upcoming Tasks");
                System.out.println("3. Show Overdue Tasks");
                System.out.println("4. Mark Task Complete");
                System.out.println("5. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Task description: ");
                    String task = scanner.nextLine();
                    System.out.print("Run at (YYYY-MM-DD HH:MM): ");
                    String runAt = scanner.nextLine();

                    String sql = "INSERT INTO scheduled_tasks (task, run_at) VALUES (?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, task);
                        stmt.setTimestamp(2, Timestamp.valueOf(runAt.replace(" ", "T")));
                        stmt.executeUpdate();
                        System.out.println("Task scheduled!");
                    } catch (SQLException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                } else if (choice.equals("2")) {
                    String sql = "SELECT id, task, run_at FROM scheduled_tasks WHERE completed = FALSE AND run_at > CURRENT_TIMESTAMP ORDER BY run_at";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%d | %s | Scheduled for: %s%n",
                                    rs.getInt("id"),
                                    rs.getString("task"),
                                    rs.getTimestamp("run_at"));
                        }
                    }
                } else if (choice.equals("3")) {
                    String sql = "SELECT id, task, run_at FROM scheduled_tasks WHERE completed = FALSE AND run_at < CURRENT_TIMESTAMP ORDER BY run_at";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%d | %s | Was due: %s%n",
                                    rs.getInt("id"),
                                    rs.getString("task"),
                                    rs.getTimestamp("run_at"));
                        }
                    }
                } else if (choice.equals("4")) {
                    System.out.print("Task ID to mark complete: ");
                    int id = Integer.parseInt(scanner.nextLine());

                    String sql = "UPDATE scheduled_tasks SET completed = TRUE WHERE id = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, id);
                        stmt.executeUpdate();
                        System.out.println("Task marked complete!");
                    }
                } else if (choice.equals("5")) {
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
