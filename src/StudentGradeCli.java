import java.sql.*;
import java.util.Scanner;

public class StudentGradesCLI {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- StudentGradesCLI ---");
                System.out.println("1. Add Student");
                System.out.println("2. Add Grade");
                System.out.println("3. List All Grades");
                System.out.println("4. Show Students Above Average");
                System.out.println("5. Show Students With Failing Grades");
                System.out.println("6. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();








