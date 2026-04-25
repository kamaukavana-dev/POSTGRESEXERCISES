import java.sql.*;
import java.util.Scanner;

public class TaskReminderCLI {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- TaskReminderCLI ---");
                System.out.println("1. Add Reminder");
                System.out.println("2. Show Upcoming Reminders");
                System.out.println("3. Show Overdue Reminders");
                System.out.println("4. Mark Reminder Complete");
                System.out.println("5. Send Notifications");
                System.out.println("6. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Task description: ");
                    String task = scanner.nextLine();
                    System.out.print("Due at (YYYY-MM-DD HH:MM): ");
                    String dueAt = scanner.nextLine();

                    String sql = "INSERT INTO reminders (task, due_at) VALUES (?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, task);
                        stmt.setTimestamp(2, Timestamp.valueOf(dueAt.replace(" ", "T")));
                        stmt.executeUpdate();
                        System.out.println("Reminder added!");
                    } catch (SQLException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                } else if (choice.equals("2")) {
                    String sql = "SELECT id, task, due_at FROM reminders WHERE completed = FALSE AND due_at > CURRENT_TIMESTAMP ORDER BY due_at";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%d | %s | Due: %s%n",
                                    rs.getInt("id"),
                                    rs.getString("task"),
                                    rs.getTimestamp("due_at"));
                        }
                    }
                } else if (choice.equals("3")) {
                    String sql = "SELECT id, task, due_at FROM reminders WHERE completed = FALSE AND due_at < CURRENT_TIMESTAMP ORDER BY due_at";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%d | %s | Overdue: %s%n",
                                    rs.getInt("id"),
                                    rs.getString("task"),
                                    rs.getTimestamp("due_at"));
                        }
                    }
                } else if (choice.equals("4")) {
                    System.out.print("Reminder ID to mark complete: ");
                    int id = Integer.parseInt(scanner.nextLine());




