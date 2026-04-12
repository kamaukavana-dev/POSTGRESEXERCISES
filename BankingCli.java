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

                    String sql = "INSERT INTO accounts (owner, balance) VALUES (?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, owner);
                        stmt.setDouble(2, balance);
                        stmt.executeUpdate();
                        conn.commit();
                        System.out.println("Account created!");
                    } catch (SQLException e) {
                        conn.rollback();
                        System.out.println("Error: " + e.getMessage());
                    }
                } else if (choice.equals("2")) {
                    String sql = "SELECT id, owner, balance FROM accounts ORDER BY id";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%d | %s | Balance: %.2f%n",
                                    rs.getInt("id"),
                                    rs.getString("owner"),
                                    rs.getDouble("balance"));
                        }
                    }
                } else if (choice.equals("3")) {
                    System.out.print("From Account ID: ");
                    int fromId = Integer.parseInt(scanner.nextLine());
                    System.out.print("To Account ID: ");
                    int toId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Amount: ");
                    double amount = Double.parseDouble(scanner.nextLine());

                    try {
                        // Check balance
                        String checkSql = "SELECT balance FROM accounts WHERE id = ?";
                        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                            checkStmt.setInt(1, fromId);
                            ResultSet rs = checkStmt.executeQuery();
                            if (rs.next() && rs.getDouble("balance") >= amount) {
                                // Deduct from sender
                                String deductSql = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
                                try (PreparedStatement deductStmt = conn.prepareStatement(deductSql)) {
                                    deductStmt.setDouble(1, amount);
                                    deductStmt.setInt(2, fromId);
                                    deductStmt.executeUpdate();
                                }
                                // Add to receiver
                                String addSql = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
                                try (PreparedStatement addStmt = conn.prepareStatement(addSql)) {
                                    addStmt.setDouble(1, amount);
                                    addStmt.setInt(2, toId);
                                    addStmt.executeUpdate();
                                }
                                // Log transaction
                                String logSql = "INSERT INTO transactions (from_account, to_account, amount) VALUES (?, ?, ?)";
                                try (PreparedStatement logStmt = conn.prepareStatement(logSql)) {
                                    logStmt.setInt(1, fromId);
                                    logStmt.setInt(2, toId);
                                    logStmt.setDouble(3, amount);
                                    logStmt.executeUpdate();
                                }
                                conn.commit();
                                System.out.println("Transfer successful!");
                            } else {
                                conn.rollback();
                                System.out.println("Insufficient funds.");
                            }
                        }
                    } catch (SQLException e) {
                        conn.rollback();
                        System.out.println("Error: " + e.getMessage());
                    }
                } else if (choice.equals("4")) {
                    String sql = "SELECT id, from_account, to_account, amount, created_at FROM transactions ORDER BY created_at DESC";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%d | From: %d | To: %d | Amount: %.2f | %s%n",
                                    rs.getInt("id"),
                                    rs.getInt("from_account"),
                                    rs.getInt("to_account"),
                                    rs.getDouble("amount"),
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

