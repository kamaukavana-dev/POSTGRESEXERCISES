import java.sql.*;
import java.util.Scanner;

public class MessagingCLI {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- MessagingCLI ---");
                System.out.println("1. Add User");
                System.out.println("2. Send Message");
                System.out.println("3. Show Conversation");
                System.out.println("4. Delete User");
                System.out.println("5. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    String sql = "INSERT INTO users (username) VALUES (?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, username);
                        stmt.executeUpdate();
                        System.out.println("User added!");
                    }
                } else if (choice.equals("2")) {
                    System.out.print("Sender ID: ");
                    int senderId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Receiver ID: ");
                    int receiverId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Message: ");
                    String content = scanner.nextLine();

                    String sql = "INSERT INTO messages (sender_id, receiver_id, content) VALUES (?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, senderId);
                        stmt.setInt(2, receiverId);
                        stmt.setString(3, content);
                        stmt.executeUpdate();
                        System.out.println("Message sent!");
                    }
                } else if (choice.equals("3")) {
                    System.out.print("User1 ID: ");
                    int user1 = Integer.parseInt(scanner.nextLine());
                    System.out.print("User2 ID: ");
                    int user2 = Integer.parseInt(scanner.nextLine());

                    String sql = "SELECT u1.username AS sender, u2.username AS receiver, m.content, m.sent_at " +
                            "FROM messages m " +
                            "JOIN users u1 ON m.sender_id = u1.id " +
                            "JOIN users u2 ON m.receiver_id = u2.id " +
                            "WHERE (u1.id = ? AND u2.id = ?) OR (u1.id = ? AND u2.id = ?) " +
                            "ORDER BY m.sent_at";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, user1);
                        stmt.setInt(2, user2);
                        stmt.setInt(3, user2);
                        stmt.setInt(4, user1);
                        ResultSet rs = stmt.executeQuery();
                        while (rs.next()) {
                            System.out.printf("%s -> %s | %s | %s%n",
                                    rs.getString("sender"),
                                    rs.getString("receiver"),
                                    rs.getString("content"),
                                    rs.getTimestamp("sent_at"));
                        }
                    }
                } else if (choice.equals("4")) {
                    System.out.print("User ID to delete: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    String sql = "DELETE FROM users WHERE id = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, id);
                        stmt.executeUpdate();
                        System.out.println("User deleted (messages cascaded)!");
                    }
                } else if (choice.equals("5")) {
                    System.out.println("Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid choice.");
                }
            }

