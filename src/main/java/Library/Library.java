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
        library.remove(book);
    }

    // Изменение книги
    void change(Book book1, Book book2) {
        if (!contains(book1)) {
            throw new IllegalArgumentException("Книга для замены не найдена");
        }
        delete(book1);
        add(book2);
    }

    // Проверка наличия книги
    public boolean contains(Book book) {
        return library.contains(book);
    }


    // Изменение кода полки книги
    void replaceCode(Book book, String newCode) {
        if (!contains(book)) {
            throw new IllegalArgumentException("Книга не найдена");
        }
        delete(book);
        add(new Book(book.title, book.author, book.genre, newCode));
    }

    // Поисковик книг
    public List<Book> search(String title, String author, String genre, String shelfCode, String words) {
        ArrayList<Book> search = new ArrayList<>();
        for (Book book: library) {
            if (((words == null) || (book.title.contains(words)))
              && ((title == null) || (title.equals(book.title)))
                && ((author == null) || (author.equals(book.author)))
                  && ((genre == null) || (genre.equals(book.genre)))
                    && ((shelfCode == null) || (shelfCode.equals(book.shelfCode)))) {
                search.add(book);
            }
        }
        return search;
    }
}
