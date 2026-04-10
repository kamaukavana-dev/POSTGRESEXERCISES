import java.sql.*;
import java.util.Scanner;

public class AttendanceCli {
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
                    System.out.print("Class subject: ");
                    String subject = scanner.nextLine();
                    String sql = "INSERT INTO classes (subject) VALUES (?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, subject);
                        stmt.executeUpdate();
                        System.out.println("Class added!");
                    }
                } else if (choice.equals("3")) {
                    System.out.print("Student ID: ");
                    int studentId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Class ID: ");
                    int classId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    System.out.print("Present (true/false): ");
                    boolean present = Boolean.parseBoolean(scanner.nextLine());

                    String sql = "INSERT INTO attendance (student_id, class_id, attendance_date, present) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, studentId);
                        stmt.setInt(2, classId);
                        stmt.setDate(3, Date.valueOf(date));
                        stmt.setBoolean(4, present);
                        stmt.executeUpdate();
                        System.out.println("Attendance recorded!");
                    } catch (SQLException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                } else if (choice.equals("4")) {
                    String sql = "SELECT s.name, c.subject, a.attendance_date, a.present " +
                            "FROM attendance a " +
                            "JOIN students s ON a.student_id = s.id " +
                            "JOIN classes c ON a.class_id = c.id " +
                            "ORDER BY a.attendance_date";
                    try (Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql)) {
                        while (rs.next()) {
                            System.out.printf("%s | %s | Date: %s | Present: %s%n",
                                    rs.getString("name"),
                                    rs.getString("subject"),
                                    rs.getDate("attendance_date"),
                                    rs.getBoolean("present"));
                        }
                    }
                } else if (choice.equals("5")) {
                    System.out.println("Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid choice.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
