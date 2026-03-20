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

                if (choice.equals("1")) {
                    System.out.print("Student name: ");
                    String name = scanner.nextLine();
                    String sql = "INSERT INTO students (name) VALUES (?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, name);
                        stmt.executeUpdate();
                        System.out.println("Student added!");
                    }
                } else if (choice.equals("2")) {
                    System.out.print("Student ID: ");
                    int studentId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Subject: ");
                    String subject = scanner.nextLine();
                    System.out.print("Score: ");
                    double score = Double.parseDouble(scanner.nextLine());

                    String sql = "INSERT INTO grades (student_id, subject, score) VALUES (?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, studentId);
                        stmt.setString(2, subject);
                        stmt.setDouble(3, score);
                        stmt.executeUpdate();
                        System.out.println("Grade added!");
                    }
                } else if (choice.equals("3")) {
                    String sql = "SELECT s.name, g.subject, g.score FROM students s JOIN grades g ON s.id = g.student_id ORDER BY s.name";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%s | %s | %.2f%n",
                                    rs.getString("name"),
                                    rs.getString("subject"),
                                    rs.getDouble("score"));
                        }
                    }
                } else if (choice.equals("4")) {
                    String sql = "SELECT s.name, g.subject, g.score " +
                                 "FROM students s JOIN grades g ON s.id = g.student_id " +
                                 "WHERE g.score > (SELECT AVG(score) FROM grades)";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%s | %s | %.2f%n",
                                    rs.getString("name"),
                                    rs.getString("subject"),
                                    rs.getDouble("score"));
                        }
                    }
                } else if (choice.equals("5")) {
                    String sql = "SELECT DISTINCT s.name " +
                                 "FROM students s WHERE EXISTS " +
                                 "(SELECT 1 FROM grades g WHERE g.student_id = s.id AND g.score < 50)";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%s%n", rs.getString("name"));
                        }
                    }

