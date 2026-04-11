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






