import java.sql.*;
import java.util.Scanner;

public class SurveyCLI {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- SurveyCLI ---");
                System.out.println("1. Add Question");
                System.out.println("2. Record Response");
                System.out.println("3. Show Average Ratings");
                System.out.println("4. Show Questions with >= 5 Responses");
                System.out.println("5. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Question text: ");
                    String text = scanner.nextLine();
                    String sql = "INSERT INTO questions (text) VALUES (?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, text);
                        stmt.executeUpdate();
                        System.out.println("Question added!");
                    }
                } else if (choice.equals("2")) {
                    System.out.print("Question ID: ");
                    int qid = Integer.parseInt(scanner.nextLine());
                    System.out.print("Respondent name: ");
                    String respondent = scanner.nextLine();
                    System.out.print("Rating (1-5): ");
                    int rating = Integer.parseInt(scanner.nextLine());

                    String sql = "INSERT INTO responses (question_id, respondent, rating) VALUES (?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, qid);
                        stmt.setString(2, respondent);
                        stmt.setInt(3, rating);
                        stmt.executeUpdate();
                        System.out.println("Response recorded!");
                    } catch (SQLException e) {
                        System.out.println("Error: " + e.getMessage());
                    }





