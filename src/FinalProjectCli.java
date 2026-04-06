import java.sql.*;
import java.util.Scanner;

public class FinalProjectCLI {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            conn.setAutoCommit(false); // transaction control

            while (true) {
                System.out.println("\n--- FinalProjectCLI ---");
                System.out.println("1. Add Customer");
                System.out.println("2. Add Product");
                System.out.println("3. Place Order");
                System.out.println("4. Show Sales Summary");
                System.out.println("5. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Customer name: ");
                    String name = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();

                    String sql = "INSERT INTO customers (name, email) VALUES (?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, name);
                        stmt.setString(2, email);
                        stmt.executeUpdate();
                        conn.commit();
                        System.out.println("Customer added!");
                    } catch (SQLException e) {
                        conn.rollback();
                        System.out.println("Error: " + e.getMessage());
                    }
                } else if (choice.equals("2")) {
                    System.out.print("Product name: ");
                    String name = scanner.nextLine();
                    System.out.print("Price: ");
                    double price = Double.parseDouble(scanner.nextLine());
                    System.out.print("Stock: ");
                    int stock = Integer.parseInt(scanner.nextLine());

                    String sql = "INSERT INTO products (name, price, stock) VALUES (?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, name);
                        stmt.setDouble(2, price);
                        stmt.setInt(3, stock);
                        stmt.executeUpdate();
                        conn.commit();
                        System.out.println("Product added!");
                    } catch (SQLException e) {
                        conn.rollback();
                        System.out.println("Error: " + e.getMessage());
                    }
                } else if (choice.equals("3")) {
                    System.out.print("Customer ID: ");
                    int customerId = Integer.parseInt(scanner.nextLine());

                    String sqlOrder = "INSERT INTO orders (customer_id) VALUES (?) RETURNING id";
                    try (PreparedStatement stmtOrder = conn.prepareStatement(sqlOrder)) {
                        stmtOrder.setInt(1, customerId);
                        ResultSet rs = stmtOrder.executeQuery();
                        rs.next();
                        int orderId = rs.getInt("id");

                        while (true) {
                            System.out.print("Product ID (or 0 to finish): ");
                            int productId = Integer.parseInt(scanner.nextLine());
                            if (productId == 0) break;
                            System.out.print("Quantity: ");
                            int qty = Integer.parseInt(scanner.nextLine());

                            String sqlItem = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";
                            try (PreparedStatement stmtItem = conn.prepareStatement(sqlItem)) {
                                stmtItem.setInt(1, orderId);
                                stmtItem.setInt(2, productId);
                                stmtItem.setInt(3, qty);
                                stmtItem.executeUpdate();
                            }
                        }
                        conn.commit();
                        System.out.println("Order placed!");
                    } catch (SQLException e) {
                        conn.rollback();
                        System.out.println("Error: " + e.getMessage());
                    }
                } else if (choice.equals("4")) {
                    String sql = "SELECT * FROM sales_summary";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%s | Sold: %d | Revenue: %.2f%n",
                                    rs.getString("product"),
                                    rs.getInt("total_sold"),
                                    rs.getDouble("revenue"));
                        }
                    }
                } else if (choice.equals("5")) {
                    System.out.println("Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid choice.");
                }
            }

