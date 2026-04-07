import java.sql.*;
import java.util.Scanner;

public class AuditCli {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- AuditCLI ---");
                System.out.println("1. Add Sale");
                System.out.println("2. Show Sales Summary (View)");
                System.out.println("3. Show Monthly Sales (Materialized View)");
                System.out.println("4. Refresh Monthly Sales View");
                System.out.println("5. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Product: ");
                    String product = scanner.nextLine();
                    System.out.print("Quantity: ");
                    int qty = Integer.parseInt(scanner.nextLine());
                    System.out.print("Price: ");
                    double price = Double.parseDouble(scanner.nextLine());

                    String sql = "INSERT INTO sales (product, quantity, price) VALUES (?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, product);
                        stmt.setInt(2, qty);
                        stmt.setDouble(3, price);
                        stmt.executeUpdate();
                        System.out.println("Sale recorded!");
                    }
                } else if (choice.equals("2")) {
                    String sql = "SELECT * FROM sales_summary";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%s | Units: %d | Revenue: %.2f%n",
                                    rs.getString("product"),
                                    rs.getInt("total_units"),
                                    rs.getDouble("total_revenue"));
                        }
                    }
                } else if (choice.equals("3")) {
                    String sql = "SELECT * FROM monthly_sales ORDER BY month";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%s | Revenue: %.2f%n",
                                    rs.getDate("month"),
                                    rs.getDouble("revenue"));
                        }
                    }
                } else if (choice.equals("4")) {
                    String sql = "REFRESH MATERIALIZED VIEW monthly_sales";
                    try (Statement stmt = conn.createStatement()) {
                        stmt.executeUpdate(sql);
                        System.out.println("Monthly sales view refreshed!");
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
