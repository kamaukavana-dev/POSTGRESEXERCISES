import java.sql.*;
import java.util.Scanner;

public class ExpenseTrackerCLI {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            conn.setAutoCommit(false); // manual transaction control

            while (true) {
                System.out.println("\n--- ExpenseTrackerCLI ---");
                System.out.println("1. Add Expense");
                System.out.println("2. Show Totals by Category");
                System.out.println("3. Show Monthly Totals");
                System.out.println("4. Exit");
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
                        conn.commit();
                        System.out.println("Expense recorded!");
                    } catch (SQLException e) {
                        conn.rollback();
                        System.out.println("Error: " + e.getMessage());
                    }


