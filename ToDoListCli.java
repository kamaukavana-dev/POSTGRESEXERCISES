import java.sql.*;
import java.util.Scanner;

public class ToDoListCli {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            conn.setAutoCommit(false); // manual transaction control

            while (true) {
                System.out.println("\n--- TodoListCLI ---");
                System.out.println("1. Add Task");
                System.out.println("2. List Pending Tasks");
                System.out.println("3. Mark Task Complete");
                System.out.println("4. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Task description: ");
                    String task = scanner.nextLine();
                    System.out.print("Due date (YYYY-MM-DD): ");
                    String dueDate = scanner.nextLine();

                    String sql = "INSERT INTO todos (task, due_date) VALUES (?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, task);
                        stmt.setDate(2, Date.valueOf(dueDate));
                        stmt.executeUpdate();
                        conn.commit();
                        System.out.println("Task added!");
                    } catch (SQLException e) {
                        conn.rollback();
                        System.out.println("Error: " + e.getMessage());
                    }
                } else if (choice.equals("2")) {
                    String sql = "SELECT id, task, due_date, completed FROM todos WHERE completed = FALSE ORDER BY due_date";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%d | %s | Due: %s | Completed: %s%n",
                                    rs.getInt("id"),
                                    rs.getString("task"),
                                    rs.getDate("due_date"),
                                    rs.getBoolean("completed"));
                        }
                    }
                } else if (choice.equals("3")) {
                    System.out.print("Task ID to mark complete: ");
                    int id = Integer.parseInt(scanner.nextLine());

                    String sql = "UPDATE todos SET completed = TRUE WHERE id = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, id);
                        stmt.executeUpdate();
                        conn.commit();
                        System.out.println("Task marked complete!");
                    } catch (SQLException e) {
                        conn.rollback();
                        System.out.println("Error: " + e.getMessage());
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
