import java.sql.*;
import java.util.Scanner;

public class ExpenseTrackerCli {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- ExpenseTrackerCLI ---");
                System.out.println("1. Add Expense");
                System.out.println("2. List All Expenses");
                System.out.println("3. Show Total per Category");
                System.out.println("4. Show Average per Category");
                System.out.println("5. Show Monthly Totals");
                System.out.println("6. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Category: ");
                    String category = scanner.nextLine();
                    System.out.print("Amount: ");
                    double amount = Double.parseDouble(scanner.nextLine());

                    String sql = "INSERT INTO expenses (category, amount) VALUES (?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, category);
                        stmt.setDouble(2, amount);
                        stmt.executeUpdate();
                        System.out.println("Expense added!");
                    }
                } else if (choice.equals("2")) {
                    String sql = "SELECT id, category, amount, expense_date FROM expenses ORDER BY expense_date DESC";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%d | %s | %.2f | %s%n",
                                    rs.getInt("id"),
                                    rs.getString("category"),
                                    rs.getDouble("amount"),
                                    rs.getDate("expense_date"));
                        }
                    }
                } else if (choice.equals("3")) {
                    String sql = "SELECT category, SUM(amount) AS total_spent FROM expenses GROUP BY category";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%s | Total: %.2f%n",
                                    rs.getString("category"),
                                    rs.getDouble("total_spent"));
                        }
                    }
                } else if (choice.equals("4")) {
                    String sql = "SELECT category, AVG(amount) AS avg_spent FROM expenses GROUP BY category";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%s | Average: %.2f%n",
                                    rs.getString("category"),
                                    rs.getDouble("avg_spent"));
                        }
                    }
                } else if (choice.equals("5")) {
                    String sql = "SELECT DATE_TRUNC('month', expense_date) AS month, SUM(amount) AS total " +
                                 "FROM expenses GROUP BY month ORDER BY month";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%s | Total: %.2f%n",
                                    rs.getDate("month"),
                                    rs.getDouble("total"));
                        }

