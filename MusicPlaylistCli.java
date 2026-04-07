import java.sql.*;
import java.util.Scanner;

public class MusicPlaylistCli {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- MusicPlaylistCLI ---");
                System.out.println("1. Add Playlist");
                System.out.println("2. Add Song to Playlist");
                System.out.println("3. List Playlists");
                System.out.println("4. List Songs in Playlist");
                System.out.println("5. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Playlist name: ");
                    String name = scanner.nextLine();
                    String sql = "INSERT INTO playlists (name) VALUES (?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, name);
                        stmt.executeUpdate();
                        System.out.println("Playlist added!");
                    } catch (SQLException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                } else if (choice.equals("2")) {
                    System.out.print("Song title: ");
                    String title = scanner.nextLine();
                    System.out.print("Artist: ");
                    String artist = scanner.nextLine();
                    System.out.print("Duration (seconds): ");
                    int duration = Integer.parseInt(scanner.nextLine());
                    System.out.print("Playlist ID: ");
                    int playlistId = Integer.parseInt(scanner.nextLine());

                    String sql = "INSERT INTO songs (title, artist, duration, playlist_id) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, title);
                        stmt.setString(2, artist);
                        stmt.setInt(3, duration);
                        stmt.setInt(4, playlistId);
                        stmt.executeUpdate();
                        System.out.println("Song added!");
                    } catch (SQLException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                } else if (choice.equals("3")) {
                    String sql = "SELECT id, name, created_at FROM playlists ORDER BY created_at DESC";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%d | %s | %s%n",
                                    rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getTimestamp("created_at"));
                        }
                    }
                } else if (choice.equals("4")) {
                    System.out.print("Playlist ID: ");
                    int playlistId = Integer.parseInt(scanner.nextLine());
                    String sql = "SELECT id, title, artist, duration FROM songs WHERE playlist_id = ? ORDER BY title";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, playlistId);
                        ResultSet rs = stmt.executeQuery();
                        while (rs.next()) {
                            System.out.printf("%d | %s | %s | %d sec%n",
                                    rs.getInt("id"),
                                    rs.getString("title"),
                                    rs.getString("artist"),
                                    rs.getInt("duration"));
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
