package Library;


import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Library {

    static HashSet<Book> library = new HashSet<>();

    public Library() {
    }

    // Добавление книги
    void add(Book book) {
        if (book.title.isBlank() || book.author.isBlank() || book.genre.isBlank() || book.shelf_code.isBlank())
            throw new IllegalArgumentException();
        library.add(book);
    }

    // Удаление книги
    void delete(Book book) {
        if (book.title.isBlank() || book.author.isBlank() || book.genre.isBlank() || book.shelf_code.isBlank())
            throw new IllegalArgumentException();
        library.removeIf(book1 -> (book.title == book1.title) && (book.author == book1.author)
                && (book.genre == book1.genre) && (book.shelf_code == book1.shelf_code));
    }

    // Изменение книги
    void change(Book book1, Book book2) {
        if (book1.title.isBlank() || book1.author.isBlank() || book1.genre.isBlank() || book1.shelf_code.isBlank())
            throw new IllegalArgumentException();
        if (book2.title.isBlank() || book2.author.isBlank() || book2.genre.isBlank() || book2.shelf_code.isBlank())
            throw new IllegalArgumentException();
        for (Book book11: library) {
            if ((Objects.equals(book11.title, book1.title))
                    && (Objects.equals(book11.author, book1.author))
                    && (Objects.equals(book11.genre, book1.genre))
                    && (Objects.equals(book11.shelf_code, book1.shelf_code))) {
                library.remove(book11);
                library.add(book2);
            }
        }
    }

    // Проверка наличия книги
    public Boolean existence(Book books) {
        boolean f = false;
        for (Book book: library) {
            if ((book.title == books.title) && (book.author == books.author)
                    && (book.genre == books.genre) && (book.shelf_code == books.shelf_code)) {
                f = true;
                break;
            }
        }
        return f;
    }

    // Изменение кода полки книги
    void moving(Book book, String new_shelf_code) {
        if (book.title.isBlank() || book.author.isBlank() || book.genre.isBlank() || book.shelf_code.isBlank())
            throw new IllegalArgumentException();
        for (Book books: library) {
            if ((book.title == books.title) &&
                    (book.title == books.title) &&
                    (book.title == books.title) &&
                    (book.title == books.title)) {
                library.remove(books);
                library.add(new Book(book.title, book.author, book.genre, new_shelf_code));
            }
        }
    }

    // Поисковик книг по любым признакам
    public HashSet searcher(String words) {
        if (words.isBlank())
            throw new IllegalArgumentException();
        HashSet<String> search = new HashSet<>();
        List<String> words1 = List.of(words.split(" "));
        for (Book books: library) {
            String book = books.title + ", " + books.author + ", " + books.genre + ", " + books.shelf_code;
            for (String word: words1) {
                if (book.contains(word))
                    search.add(books.title + ", " + books.author + ", " + books.genre + ", " + books.shelf_code);
            }
        }
        return search;
    }
}
