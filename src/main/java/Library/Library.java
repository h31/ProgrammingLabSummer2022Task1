package Library;


import java.util.HashSet;
import java.util.Objects;

public class Library {

    static HashSet<Book> library = new HashSet<>();

    public Boolean equal(Book book1, Book book2) {
        boolean f = (Objects.equals(book1.title, book2.title)) &&
                (Objects.equals(book1.author, book2.author)) &&
                (Objects.equals(book1.genre, book2.genre)) &&
                (Objects.equals(book1.shelfCode, book2.shelfCode));
        return f;
    }

    // Добавление книги
    void add(Book book) {
        library.add(book);
    }

    // Удаление книги
    void delete(Book book) {
        library.removeIf(book1 -> equal(book1, book));
    }

    // Изменение книги
    void change(Book book1, Book book2) {
        if (check(book1)) {
            delete(book1);
            add(book2);
        } else throw new IllegalArgumentException("Книга не найдена");
    }

    // Проверка наличия книги
    public Boolean check(Book books) {
        boolean f = false;
        for (Book book: library) {
            if (equal(books, book)) {
                f = true;
                break;
            }
        }
        return f;
    }

    // Изменение кода полки книги
    void replace(Book book, String newShelfCode) {
        if (check(book)) {
            delete(book);
            add(new Book(book.title, book.author, book.genre, newShelfCode));
        } else throw new IllegalArgumentException("Книга не найдена");
    }

    public String str(Book book) {
        return book.title + ", " + book.author + ", " + book.genre + ", " + book.shelfCode;
    }

    // Поисковик книг
    public HashSet search(String title, String author, String genre, String shelfCode, String words) {
        HashSet<String> search = new HashSet<>();
        for (Book books: library) {
            boolean f;
            String title1 = title;
            String author1 = author;
            String genre1 = genre;
            String shelfCode1 = shelfCode;
            f = words == null;
            if (!f) if (books.title.contains(words)) f = true;
            if (title1 == null) title1 = books.title;
            if (author1 == null) author1 = books.author;
            if (genre1 == null) genre1 = books.genre;
            if (shelfCode1 == null) shelfCode1 = books.shelfCode;
            Book book = new Book(title1, author1, genre1, shelfCode1);
            if (equal(books, book) && (f))
                search.add(str(book));
        }
        return search;
    }
}
