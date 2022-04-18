package Library;


import java.util.*;


public class Library {

    static LinkedHashSet<Book> library = new LinkedHashSet<>();


    // Добавление книги
    void add(Book book) {
        library.add(book);
    }

    // Удаление книги
    void delete(Book book) {
        library.removeIf(book1 -> book1.equals(book));
    }

    // Изменение книги
    void change(Book book1, Book book2) {
        if (contains(book1)) {
            delete(book1);
            add(book2);
        }
        else {
            throw new IllegalArgumentException("Книга для замены не найдена");
        }
    }

    // Проверка наличия книги
    public Boolean contains(Book books) {
        boolean f = false;
        for (Book book: library) {
            if (books.equals(book)) {
                f = true;
                break;
            }
        }
        return f;
    }

    // Изменение кода полки книги
    void replaceCode(Book book, String newCode) {
        if (contains(book)) {
            delete(book);
            add(new Book(book.title, book.author, book.genre, newCode));
        }
        else {
            throw new IllegalArgumentException("Книга не найдена");
        }
    }

    // Поисковик книг
    public List<Book> search(String title, String author, String genre, String shelfCode, String words) {
        ArrayList<Book> search = new ArrayList<>();
        for (Book book: library) {
            if ((words == null) || (book.title.contains(words)))
            if ((title == null) || (title.equals(book.title)))
            if ((author == null) || (author.equals(book.author)))
            if ((genre == null) || (genre.equals(book.genre)))
            if ((shelfCode == null) || (shelfCode.equals(book.shelfCode)))
            search.add(book);
        }
        return search;
    }
}
