import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        LibraryManagement app = new LibraryManagement();
        Scanner sc = new Scanner(System.in);

        System.out.println("===== Welcome to the Enhanced Library System =====");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. List books");
            System.out.println("2. Add a book");
            System.out.println("3. Update a book");
            System.out.println("4. Delete a book");
            System.out.println("5. Search books");
            System.out.println("6. Exit");
            System.out.print("Choose (1-6): ");

            String choice = sc.nextLine();

            try {
                switch (choice) {

                    case "1":
                        app.listBooks();
                        break;

                    case "2":
                        System.out.print("Enter title: ");
                        String title = sc.nextLine();
                        if (!BookValidator.isValidTitle(title)) {
                            System.out.println("Invalid title. Title cannot be blank.");
                            break;
                        }

                        System.out.print("Enter author: ");
                        String author = sc.nextLine();
                        if (!BookValidator.isValidAuthor(author)) {
                            System.out.println("Invalid author. Author cannot be blank.");
                            break;
                        }

                        System.out.print("Enter year: ");
                        int year = Integer.parseInt(sc.nextLine());
                        if (!BookValidator.isValidYear(year)) {
                            System.out.println("Invalid year. Enter a year between 1500 and 2025.");
                            break;
                        }

                        app.addBook(title, author, year);
                        break;

                    case "3":
                        System.out.print("Enter book ID to update: ");
                        int uid = Integer.parseInt(sc.nextLine());

                        System.out.print("New title: ");
                        String newTitle = sc.nextLine();

                        System.out.print("New author: ");
                        String newAuthor = sc.nextLine();

                        System.out.print("New year: ");
                        int newYear = Integer.parseInt(sc.nextLine());

                        app.updateBook(uid, newTitle, newAuthor, newYear);
                        break;

                    case "4":
                        System.out.print("Enter book ID to delete: ");
                        int did = Integer.parseInt(sc.nextLine());
                        app.deleteBook(did);
                        break;

                    case "5":
                        System.out.print("Search keyword: ");
                        String keyword = sc.nextLine();

                        List<String> results = app.searchBooks(keyword);

                        if (results.isEmpty()) {
                            System.out.println("No books found.");
                        } else {
                            System.out.println("\nSearch Results:");
                            results.forEach(System.out::println);
                        }
                        break;

                    case "6":
                        System.out.println("Goodbye!");
                        app.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Select 1–6.");
                }

            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            } 
            catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid numeric value.");
            }sc.close();
        }
    }
   
}
