import java.sql.*;
import java.util.Scanner;

public class LibraryManagementCli {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- LibraryManagerCLI ---");
                System.out.println("1. Add Author");
                System.out.println("2. Add Book");
                System.out.println("3. List Books with Authors");
                System.out.println("4. Delete Author");
                System.out.println("5. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Author name: ");
                    String name = scanner.nextLine();
                    String sql = "INSERT INTO authors (name) VALUES (?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, name);
                        stmt.executeUpdate();
                        System.out.println("Author added!");
                    }
                } else if (choice.equals("2")) {
                    System.out.print("Book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Author ID: ");
                    int authorId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Published year: ");
                    int year = Integer.parseInt(scanner.nextLine());

                    String sql = "INSERT INTO books (title, author_id, published_year) VALUES (?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, title);
                        stmt.setInt(2, authorId);
                        stmt.setInt(3, year);
                        stmt.executeUpdate();
                        System.out.println("Book added!");
                    }
                } else if (choice.equals("3")) {
                    String sql = "SELECT b.id, b.title, a.name, b.published_year " +
                                 "FROM books b JOIN authors a ON b.author_id = a.id ORDER BY b.title";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%d | %s | %s | %d%n",
                                    rs.getInt("id"),
                                    rs.getString("title"),
                                    rs.getString("name"),
                                    rs.getInt("published_year"));
                        }
                    }
                } else if (choice.equals("4")) {
                    System.out.print("Author ID to delete: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    String sql = "DELETE FROM authors WHERE id = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, id);
                        int rows = stmt.executeUpdate();
                        if (rows > 0) {
                            System.out.println("Author deleted (books removed too).");
                        } else {
                            System.out.println("Author not found.");
                        }
                    }
                } else if (choice.equals("5")) {
                    System.out.println("Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid choice.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
