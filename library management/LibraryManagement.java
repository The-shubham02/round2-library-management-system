import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryManagement {

    private static final String JDBC_URL = "jdbc:h2:./librarydb";
    private static final String JDBC_USERNAME = "sa";
    private static final String JDBC_PASSWORD = "";

    private Connection connection;

    public LibraryManagement() {
        try {
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
            createTableIfNotExists();
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            System.exit(1);
        }
    }

    private void createTableIfNotExists() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS books (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    title VARCHAR(255) NOT NULL,
                    author VARCHAR(255) NOT NULL,
                    year INT NOT NULL
                );
                """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public boolean bookExists(String title, String author) throws SQLException {
        String sql = "SELECT COUNT(*) FROM books WHERE title = ? AND author = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);

            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    public void addBook(String title, String author, int year) throws SQLException {

        if (bookExists(title, author)) {
            System.out.println("⚠ This book already exists. Duplicate entry blocked.");
            return;
        }

        String sql = "INSERT INTO books (title, author, year) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setInt(3, year);

            pstmt.executeUpdate();
            System.out.println("Book added successfully.");
        }
    }

    public void listBooks() throws SQLException {
        String sql = "SELECT * FROM books";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("\n%-5s %-30s %-30s %-5s\n", "ID", "Title", "Author", "Year");
            System.out.println("--------------------------------------------------------------------");

            boolean hasBooks = false;

            while (rs.next()) {
                hasBooks = true;
                System.out.printf("%-5d %-30s %-30s %-5d\n",
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("year"));
            }

            if (!hasBooks) {
                System.out.println("No books in the library.");
            }
        }
    }

    public List<String> searchBooks(String keyword) throws SQLException {
        List<String> results = new ArrayList<>();

        String sql = "SELECT * FROM books WHERE LOWER(title) LIKE ? OR LOWER(author) LIKE ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword.toLowerCase() + "%");
            pstmt.setString(2, "%" + keyword.toLowerCase() + "%");

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                results.add(rs.getInt("id") + " | " +
                        rs.getString("title") + " | " +
                        rs.getString("author") + " | " +
                        rs.getInt("year"));
            }
        }

        return results;
    }

    public void updateBook(int id, String title, String author, int year) throws SQLException {
        String sql = "UPDATE books SET title=?, author=?, year=? WHERE id=?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setInt(3, year);
            pstmt.setInt(4, id);

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Book updated successfully.");
            } else {
                System.out.println("Book with ID " + id + " not found.");
            }
        }
    }

    public void deleteBook(int id) throws SQLException {
        String sql = "DELETE FROM books WHERE id=?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Book deleted successfully.");
            } else {
                System.out.println("Book with ID " + id + " not found.");
            }
        }
    }

    public void close() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException ignored) {}
    }
}

