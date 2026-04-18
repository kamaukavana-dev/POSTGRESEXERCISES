import java.sql.*;
import java.util.Scanner;

public class NotificationCLI {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- NotificationCLI ---");
                System.out.println("1. Create Notification");
                System.out.println("2. Send Pending Notifications");
                System.out.println("3. Show All Notifications");
                System.out.println("4. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Recipient (phone/email): ");
                    String recipient = scanner.nextLine();
                    System.out.print("Channel (sms/email/whatsapp): ");
                    String channel = scanner.nextLine();
                    System.out.print("Message: ");
                    String message = scanner.nextLine();

                    String sql = "INSERT INTO notifications (recipient, channel, message) VALUES (?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, recipient);
                        stmt.setString(2, channel);
                        stmt.setString(3, message);
                        stmt.executeUpdate();
                        System.out.println("Notification created!");
                    }
                } else if (choice.equals("2")) {
                    String sql = "SELECT id, recipient, channel, message FROM notifications WHERE sent = FALSE";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            int id = rs.getInt("id");
                            String recipient = rs.getString("recipient");
                            String channel = rs.getString("channel");
                            String message = rs.getString("message");

                            // Simulate sending via API
                            System.out.printf("Sending %s to %s: %s%n", channel, recipient, message);

                            String updateSql = "UPDATE notifications SET sent = TRUE WHERE id = ?";
                            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                                updateStmt.setInt(1, id);
                                updateStmt.executeUpdate();
                            }
                        }
                        System.out.println("All pending notifications sent!");
                    }
                } else if (choice.equals("3")) {
                    String sql = "SELECT id, recipient, channel, message, sent, created_at FROM notifications ORDER BY created_at DESC";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {



