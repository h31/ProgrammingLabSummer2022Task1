package Library;


import java.util.*;


public class Library {

    private final Set<Book> library = new LinkedHashSet<>();


    // Добавление книги
    public void add(Book book) {
        if (book == null) throw new IllegalArgumentException("Книга null");
        library.add(book);
    }

    // Удаление книги
    public void delete(Book book) {
        if (book == null) throw new IllegalArgumentException("Книга null");
        library.remove(book);
    }

    // Изменение книги
    public void change(Book book1, Book book2) {
        if (book1 == null) throw new IllegalArgumentException("Книга null");
        if (book2 == null) throw new IllegalArgumentException("Книга null");
        if (!contains(book1)) {
            throw new IllegalArgumentException("Книга для замены не найдена");
        }
        delete(book1);
        add(book2);
    }

    // Проверка наличия книги
    public boolean contains(Book book) {
        if (book == null) throw new IllegalArgumentException("Книга null");
        return library.contains(book);
    }


    // Изменение кода полки книги
    public void replaceCode(Book book, String newCode) {
        if (book == null) throw new IllegalArgumentException("Книга null");
        if (!contains(book)) {
            throw new IllegalArgumentException("Книга не найдена");
        }
        delete(book);
        add(new Book(book.getTitle(), book.getAuthor(), book.getGenre(), newCode));
    }

    // Поисковик книг
    public List<Book> search(String title, String author, String genre, String shelfCode, String words) {
        ArrayList<Book> search = new ArrayList<>();
        for (Book book : library) {
            if (((words == null) || (book.getTitle().contains(words)))
                    && ((title == null) || (title.equals(book.getTitle())))
                    && ((author == null) || (author.equals(book.getAuthor())))
                    && ((genre == null) || (genre.equals(book.getGenre())))
                    && ((shelfCode == null) || (shelfCode.equals(book.getShelfCode())))) {
                search.add(book);
            }
        }
        return search;
    }
}
