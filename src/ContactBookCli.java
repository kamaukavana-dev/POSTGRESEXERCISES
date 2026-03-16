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







