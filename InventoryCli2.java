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








