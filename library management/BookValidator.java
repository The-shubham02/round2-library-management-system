public class BookValidator {

    public static boolean isValidTitle(String title) {
        return title != null && !title.trim().isEmpty();
    }

    public static boolean isValidAuthor(String author) {
        return author != null && !author.trim().isEmpty();
    }

    public static boolean isValidYear(int year) {
        return year >= 1500 && year <= 2025;
    }
}
