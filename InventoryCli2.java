import java.sql.*;
import java.util.Scanner;

public class InventoryCLI {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- InventoryCLI ---");
                System.out.println("1. Add Product");
                System.out.println("2. Update Stock");
                System.out.println("3. Show Inventory");
                System.out.println("4. Show Restock Alerts");
                System.out.println("5. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Product name: ");
                    String name = scanner.nextLine();
                    System.out.print("Stock: ");
                    int stock = Integer.parseInt(scanner.nextLine());
                    System.out.print("Reorder level: ");
                    int reorder = Integer.parseInt(scanner.nextLine());

                    String sql = "INSERT INTO inventory (product_name, stock, reorder_level) VALUES (?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, name);
                        stmt.setInt(2, stock);
                        stmt.setInt(3, reorder);
                        stmt.executeUpdate();
                        System.out.println("Product added!");
                    }
                } else if (choice.equals("2")) {
                    System.out.print("Product ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("New stock: ");
                    int stock = Integer.parseInt(scanner.nextLine());

                    String sql = "UPDATE inventory SET stock = ? WHERE id = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, stock);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();
                        System.out.println("Stock updated!");
                    }
                } else if (choice.equals("3")) {
                    String sql = "SELECT id, product_name, stock, reorder_level FROM inventory ORDER BY id";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%d | %s | Stock: %d | Reorder Level: %d%n",
                                    rs.getInt("id"),
                                    rs.getString("product_name"),
                                    rs.getInt("stock"),
                                    rs.getInt("reorder_level"));
                        }
                    }
                } else if (choice.equals("4")) {
                    String sql = "SELECT r.id, i.product_name, r.alert_message, r.created_at " +
                            "FROM restock_alerts r JOIN inventory i ON r.product_id = i.id " +
                            "ORDER BY r.created_at DESC";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%d | %s | %s | %s%n",
                                    rs.getInt("id"),
                                    rs.getString("product_name"),
                                    rs.getString("alert_message"),
                                    rs.getTimestamp("created_at"));
                        }
                    }
                } else if (choice.equals("5")) {
                    System.out.println("Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid choice.");
                }
            }

