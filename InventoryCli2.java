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




