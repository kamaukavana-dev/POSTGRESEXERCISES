import java.sql.*;
import java.util.Scanner;

public class AttendanceCLI {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- AttendanceCLI ---");
                System.out.println("1. Add Student");
                System.out.println("2. Add Class");
                System.out.println("3. Record Attendance");
                System.out.println("4. Show Attendance Records");
                System.out.println("5. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();








