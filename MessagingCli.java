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



}







