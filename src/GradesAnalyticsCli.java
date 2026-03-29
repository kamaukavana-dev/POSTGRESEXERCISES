import java.sql.*;
import java.util.Scanner;

public class GradesAnalyticsCLI {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- GradesAnalyticsCLI ---");
                System.out.println("1. Add Grade");
                System.out.println("2. Show Average Score per Subject");
                System.out.println("3. Show Subjects with Average > 70");
                System.out.println("4. Show Top Student per Subject");
                System.out.println("5. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Student name: ");
                    String student = scanner.nextLine();
                    System.out.print("Subject: ");
                    String subject = scanner.nextLine();
                    System.out.print("Score: ");
                    double score = Double.parseDouble(scanner.nextLine());

                    String sql = "INSERT INTO student_grades (student_name, subject, score) VALUES (?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, student);
                        stmt.setString(2, subject);
                        stmt.setDouble(3, score);
                        stmt.executeUpdate();
                        System.out.println("Grade added!");
                    }
                } else if (choice.equals("2")) {
                    String sql = "SELECT subject, AVG(score) AS avg_score FROM student_grades GROUP BY subject";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%s | Average: %.2f%n",
                                    rs.getString("subject"),
                                    rs.getDouble("avg_score"));
                        }
                    }
                } else if (choice.equals("3")) {
                    String sql = "SELECT subject, AVG(score) AS avg_score FROM student_grades GROUP BY subject HAVING AVG(score) > 70";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%s | Average: %.2f%n",
                                    rs.getString("subject"),
                                    rs.getDouble("avg_score"));
                        }
                    }
                } else if (choice.equals("4")) {
                    String sql = "SELECT subject, student_name, MAX(score) AS top_score FROM student_grades GROUP BY subject, student_name ORDER BY subject";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%s | %s | Top Score: %.2f%n",
                                    rs.getString("subject"),
                                    rs.getString("student_name"),
                                    rs.getDouble("top_score"));
                        }
                    }
                } else if (choice.equals("5")) {
                    System.out.println("Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid choice.");
                }

