import java.sql.*;
import java.util.Scanner;

public class BankingCLI {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            conn.setAutoCommit(false); // transaction control

            while (true) {
                System.out.println("\n--- BankingCLI ---");
                System.out.println("1. Create Account");
                System.out.println("2. Show Accounts");
                System.out.println("3. Transfer Money");
                System.out.println("4. Show Transactions");
                System.out.println("5. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Owner name: ");
                    String owner = scanner.nextLine();
                    System.out.print("Initial balance: ");
                    double balance = Double.parseDouble(scanner.nextLine());










