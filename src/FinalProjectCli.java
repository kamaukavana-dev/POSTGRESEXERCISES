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















