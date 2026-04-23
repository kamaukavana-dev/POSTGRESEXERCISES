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
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, repoId);
                        stmt.setString(2, branch);
                        stmt.executeUpdate();
                        System.out.println("Branch added!");
                    }
                } else if (choice.equals("3")) {
                    System.out.print("Repo ID: ");
                    int repoId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Contributor name: ");
                    String contributor = scanner.nextLine();

                    String sql = "INSERT INTO contributors (repo_id, contributor_name) VALUES (?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, repoId);
                        stmt.setString(2, contributor);
                        stmt.executeUpdate();
                        System.out.println("Contributor added!");
                    }
                } else if (choice.equals("4")) {
                    System.out.print("Repo ID: ");
                    int repoId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Label name: ");
                    String label = scanner.nextLine();
                    System.out.print("Color: ");
                    String color = scanner.nextLine();

                    String sql = "INSERT INTO labels (repo_id, label_name, color) VALUES (?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, repoId);
                        stmt.setString(2, label);
                        stmt.setString(3, color);
                        stmt.executeUpdate();
                        System.out.println("Label added!");
                    }
                } else if (choice.equals("5")) {
                    System.out.print("Repo ID: ");
                    int repoId = Integer.parseInt(scanner.nextLine());

                    String sql = "SELECT r.name, r.description, b.branch_name, c.contributor_name, l.label_name, l.color " +
                            "FROM repos r " +
                            "LEFT JOIN branches b ON r.id = b.repo_id " +
                            "LEFT JOIN contributors c ON r.id = c.repo_id " +
                            "LEFT JOIN labels l ON r.id = l.repo_id " +
                            "WHERE r.id = ?";


}
