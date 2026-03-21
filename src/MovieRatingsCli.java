import java.sql.*;
import java.util.Scanner;

public class MovieRatingsCLI {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- MovieRatingsCLI ---");
                System.out.println("1. Add Movie");
                System.out.println("2. List All Movies");
                System.out.println("3. Rank Movies by Genre");
                System.out.println("4. Show Top 3 Movies per Genre");
                System.out.println("5. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Genre: ");
                    String genre = scanner.nextLine();
                    System.out.print("Rating (0–10): ");
                    double rating = Double.parseDouble(scanner.nextLine());

                    String sql = "INSERT INTO movies (title, genre, rating) VALUES (?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, title);
                        stmt.setString(2, genre);
                        stmt.setDouble(3, rating);
                        stmt.executeUpdate();
                        System.out.println("Movie added!");
                    }
                } else if (choice.equals("2")) {
                    String sql = "SELECT id, title, genre, rating FROM movies ORDER BY genre, rating DESC";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%d | %s | %s | %.1f%n",
                                    rs.getInt("id"),
                                    rs.getString("title"),
                                    rs.getString("genre"),
                                    rs.getDouble("rating"));
                        }
                    }
                } else if (choice.equals("3")) {
                    String sql = "SELECT title, genre, rating, " +
                                 "RANK() OVER (PARTITION BY genre ORDER BY rating DESC) AS rank " +
                                 "FROM movies ORDER BY genre, rank";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%s | %s | %.1f | Rank: %d%n",
                                    rs.getString("title"),
                                    rs.getString("genre"),
                                    rs.getDouble("rating"),
                                    rs.getInt("rank"));
                        }
                    }
                } else if (choice.equals("4")) {
                    String sql = "SELECT * FROM (" +
                                 "SELECT title, genre, rating, " +
                                 "ROW_NUMBER() OVER (PARTITION BY genre ORDER BY rating DESC) AS row_num " +
                                 "FROM movies) ranked WHERE row_num <= 3 ORDER BY genre, row_num";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%s | %s | %.1f | Top %d%n",
                                    rs.getString("title"),
                                    rs.getString("genre"),
                                    rs.getDouble("rating"),
                                    rs.getInt("row_num"));
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
