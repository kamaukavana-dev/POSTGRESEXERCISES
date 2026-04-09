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






