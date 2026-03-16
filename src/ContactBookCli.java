import java.sql.*;
import java.util.Scanner;

public class ContactBookCli {
    private static final String URL = "jdbc:postgresql://localhost:5432/contactdb";
    private static final String USER = "postgres"; // change to your username
    private static final String PASSWORD = "yourpassword"; // change to your password

    private Connection conn;

    public ContactBookCli() throws SQLException {
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // --- CRUD Methods ---
    public void addContact(String name, String email, String phone) throws SQLException {
        String sql = "INSERT INTO contacts (name, email, phone) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.executeUpdate();
            System.out.println("Contact added successfully!");
        }
    }

    public void listContacts() throws SQLException {
        String sql = "SELECT id, name, email, phone FROM contacts ORDER BY name";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %s%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"));
            }
        }
    }

    public void searchContact(String keyword) throws SQLException {
        String sql = "SELECT id, name, email, phone FROM contacts WHERE name ILIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                System.out.println("No contacts found.");
            }
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %s%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"));
            }
        }
    }

    public void updateContact(int id, String newPhone) throws SQLException {
        String sql = "UPDATE contacts SET phone = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPhone);
            stmt.setInt(2, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Contact updated successfully!");
            } else {
                System.out.println("Contact not found.");
            }
        }
    }

    public void deleteContact(int id) throws SQLException {
        String sql = "DELETE FROM contacts WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Contact deleted successfully!");
            } else {
                System.out.println("Contact not found.");
            }
        }
    }

    // --- CLI Menu ---
    public void runMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- ContactBookCLI ---");
            System.out.println("1. Add Contact");
            System.out.println("2. List Contacts");
            System.out.println("3. Search Contact");
            System.out.println("4. Update Contact");
            System.out.println("5. Delete Contact");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Phone: ");
                    String phone = scanner.nextLine();
                    addContact(name, email, phone);
                    break;
                case "2":
                    listContacts();
                    break;
                case "3":
                    System.out.print("Search keyword: ");
                    String keyword = scanner.nextLine();
                    searchContact(keyword);
                    break;
                case "4":
                    System.out.print("Contact ID to update: ");
                    int idUpdate = Integer.parseInt(scanner.nextLine());
                    System.out.print("New phone number: ");
                    String newPhone = scanner.nextLine();
                    updateContact(idUpdate, newPhone);
                    break;
                case "5":
                    System.out.print("Contact ID to delete: ");
                    int idDelete = Integer.parseInt(scanner.nextLine());
                    deleteContact(idDelete);
                    break;
                case "6":
                    conn.close();
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }

    public static void main(String[] args) {
        try {
            ContactBookCli app = new ContactBookCli();
            app.runMenu();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
