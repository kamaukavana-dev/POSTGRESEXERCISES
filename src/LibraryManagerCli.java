import java.sql.*;
import java.util.Scanner;

public class LibraryManagerCLI {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/contactdb"; // use your DB
        String user = "postgres";
        String password = "yourpassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n--- LibraryManagerCLI ---");
                System.out.println("1. Add Member");
                System.out.println("2. Add Book");
                System.out.println("3. Borrow Book");
                System.out.println("4. Return Book");
                System.out.println("5. Show Borrow Records");
                System.out.println("6. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("Member name: ");
                    String name = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();

                    String sql = "INSERT INTO members (name, email) VALUES (?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, name);
                        stmt.setString(2, email);
                        stmt.executeUpdate();
                        System.out.println("Member added!");
                    }
                } else if (choice.equals("2")) {
                    System.out.print("Book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Author: ");
                    String author = scanner.nextLine();

                    String sql = "INSERT INTO books (title, author) VALUES (?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, title);
                        stmt.setString(2, author);
                        stmt.executeUpdate();
                        System.out.println("Book added!");
                    }
                } else if (choice.equals("3")) {
                    System.out.print("Member ID: ");
                    int memberId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Book ID: ");
                    int bookId = Integer.parseInt(scanner.nextLine());

                    String sql = "INSERT INTO borrow_records (member_id, book_id) VALUES (?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, memberId);
                        stmt.setInt(2, bookId);
                        stmt.executeUpdate();
                        System.out.println("Book borrowed!");
                    }




