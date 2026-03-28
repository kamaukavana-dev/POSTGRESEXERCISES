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










