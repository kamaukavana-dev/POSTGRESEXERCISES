import java.sql.*;
import java.util.Scanner;

public class RepoCLI {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- RepoCLI ---");
                System.out.println("1. Add Repo");
                System.out.println("2. Add Branch");
                System.out.println("3. Add Contributor");
                System.out.println("4. Add Label");
                System.out.println("5. Show Repo Details");
                System.out.println("6. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Repo name: ");
                    String name = scanner.nextLine();
                    System.out.print("Description: ");
                    String desc = scanner.nextLine();

                    String sql = "INSERT INTO repos (name, description) VALUES (?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, name);
                        stmt.setString(2, desc);
                        stmt.executeUpdate();
                        System.out.println("Repo added!");
                    }
                } else if (choice.equals("2")) {
                    System.out.print("Repo ID: ");
                    int repoId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Branch name: ");
                    String branch = scanner.nextLine();

                    String sql = "INSERT INTO branches (repo_id, branch_name) VALUES (?, ?)";








}
