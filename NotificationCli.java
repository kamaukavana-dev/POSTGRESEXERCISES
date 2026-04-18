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









