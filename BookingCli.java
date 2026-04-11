import java.sql.*;
import java.util.Scanner;

public class BookingCLI {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            conn.setAutoCommit(false); // transaction control

            while (true) {
                System.out.println("\n--- BookingCLI ---");
                System.out.println("1. Add Room");
                System.out.println("2. Book Room");
                System.out.println("3. Show Bookings");
                System.out.println("4. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Room name: ");
                    String name = scanner.nextLine();
                    String sql = "INSERT INTO rooms (name) VALUES (?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, name);
                        stmt.executeUpdate();
                        conn.commit();
                        System.out.println("Room added!");
                    } catch (SQLException e) {
                        conn.rollback();
                        System.out.println("Error: " + e.getMessage());
                    }
                } else if (choice.equals("2")) {
                    System.out.print("Room ID: ");
                    int roomId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Start time (YYYY-MM-DD HH:MM): ");
                    String start = scanner.nextLine();
                    System.out.print("End time (YYYY-MM-DD HH:MM): ");
                    String end = scanner.nextLine();

                    String sql = "INSERT INTO bookings (room_id, start_time, end_time) VALUES (?, ?, ?)";




