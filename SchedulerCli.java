import java.sql.*;
import java.util.Scanner;

public class SchedulerCLI {
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






}
