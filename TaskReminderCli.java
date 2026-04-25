import java.sql.*;
import java.util.Scanner;

public class TaskReminderCLI {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- TaskReminderCLI ---");
                System.out.println("1. Add Reminder");
                System.out.println("2. Show Upcoming Reminders");
                System.out.println("3. Show Overdue Reminders");
                System.out.println("4. Mark Reminder Complete");
                System.out.println("5. Send Notifications");
                System.out.println("6. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Task description: ");
                    String task = scanner.nextLine();
                    System.out.print("Due at (YYYY-MM-DD HH:MM): ");
                    String dueAt = scanner.nextLine();








