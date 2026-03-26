import java.sql.*;
import java.util.Scanner;

public class MusicPlaylistCLI {
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









