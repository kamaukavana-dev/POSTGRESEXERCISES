import java.sql.*;
import java.util.Scanner;

public class BlogCLI {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- BlogCLI ---");
                System.out.println("1. Add Blog Post");
                System.out.println("2. List All Posts");
                System.out.println("3. Search Posts by Keyword");
                System.out.println("4. Filter Posts by Author");
                System.out.println("5. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Content: ");
                    String content = scanner.nextLine();
                    System.out.print("Author: ");
                    String author = scanner.nextLine();
                    System.out.print("Tags (comma separated): ");
                    String tags = scanner.nextLine();

                    String metadata = String.format("{\"tags\":[\"%s\"],\"author\":\"%s\"}",
                            tags.replace(",", "\",\""), author);

                    String sql = "INSERT INTO blog_posts (title, content, metadata) VALUES (?, ?, ?::jsonb)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, title);
                        stmt.setString(2, content);
                        stmt.setString(3, metadata);
                        stmt.executeUpdate();
                        System.out.println("Blog post added!");
                    }
                } else if (choice.equals("2")) {
                    String sql = "SELECT id, title, metadata, created_at FROM blog_posts ORDER BY created_at DESC";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%d | %s | %s | %s%n",
                                    rs.getInt("id"),
                                    rs.getString("title"),
                                    rs.getString("metadata"),
                                    rs.getTimestamp("created_at"));
                        }
                    }
