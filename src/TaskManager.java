import java.sql.*;
import java.util.Scanner;

public class TaskManager {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // or your chosen DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- TaskManagerCLI ---");
                System.out.println("1. Add Task");
                System.out.println("2. List All Tasks");
                System.out.println("3. List Pending Tasks");
                System.out.println("4. List Upcoming Deadlines");
                System.out.println("5. Mark Task as Done");
                System.out.println("6. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Deadline (YYYY-MM-DD): ");
                    String deadline = scanner.nextLine();

                    String sql = "INSERT INTO tasks (title, deadline) VALUES (?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, title);
                        stmt.setDate(2, Date.valueOf(deadline));
                        stmt.executeUpdate();
                        System.out.println("Task added!");
                    }
                } else if (choice.equals("2")) {
                    String sql = "SELECT id, title, status, deadline FROM tasks ORDER BY created_at DESC";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%d | %s | %s | %s%n",
                                    rs.getInt("id"),
                                    rs.getString("title"),
                                    rs.getString("status"),
                                    rs.getDate("deadline"));
                        }
                    }
                } else if (choice.equals("3")) {
                    String sql = "SELECT id, title, deadline FROM tasks WHERE status = 'pending' ORDER BY deadline ASC";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%d | %s | %s%n",
                                    rs.getInt("id"),
                                    rs.getString("title"),
                                    rs.getDate("deadline"));
                        }
                    }
                } else if (choice.equals("4")) {
                    String sql = "SELECT id, title, deadline FROM tasks ORDER BY deadline ASC LIMIT 5";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%d | %s | %s%n",
                                    rs.getInt("id"),
                                    rs.getString("title"),
                                    rs.getDate("deadline"));
                        }
                    }

