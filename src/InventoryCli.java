import java.sql.*;
import java.util.Scanner;

public class InventoryCLI {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            conn.setAutoCommit(false); // we’ll control transactions manually

            while (true) {
                System.out.println("\n--- InventoryCLI ---");
                System.out.println("1. Add Product");
                System.out.println("2. List Inventory");
                System.out.println("3. Update Stock (Commit)");
                System.out.println("4. Update Stock (Rollback)");
                System.out.println("5. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Product name: ");
                    String product = scanner.nextLine();
                    System.out.print("Quantity: ");
                    int qty = Integer.parseInt(scanner.nextLine());

                    String sql = "INSERT INTO inventory (product, quantity) VALUES (?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, product);
                        stmt.setInt(2, qty);
                        stmt.executeUpdate();
                        conn.commit();
                        System.out.println("Product added!");
                    } catch (SQLException e) {
                        conn.rollback();
                        System.out.println("Error: " + e.getMessage());
                    }
                } else if (choice.equals("2")) {
                    String sql = "SELECT id, product, quantity FROM inventory ORDER BY product";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%d | %s | %d%n",
                                    rs.getInt("id"),
                                    rs.getString("product"),
                                    rs.getInt("quantity"));
                        }
                    }
                } else if (choice.equals("3")) {
                    System.out.print("Product ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("Quantity change (+/-): ");
                    int change = Integer.parseInt(scanner.nextLine());

                    String sql = "UPDATE inventory SET quantity = quantity + ? WHERE id = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, change);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();
                        conn.commit();
                        System.out.println("Stock updated and committed!");
                    } catch (SQLException e) {
                        conn.rollback();
                        System.out.println("Error: " + e.getMessage());
                    }
                } else if (choice.equals("4")) {
                    System.out.print("Product ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("Quantity change (+/-): ");
                    int change = Integer.parseInt(scanner.nextLine());


