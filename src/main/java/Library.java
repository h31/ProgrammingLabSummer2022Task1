import java.util.ArrayList;
import java.util.Objects;

public class Library {
    private ArrayList<Book> books = new ArrayList();

    public Library(){}

    public Library(Book b) {
        books.add(b);
    }

    public Library(Book b, Book c) {
        books.add(b);
        books.add(c);
    }

    public Library(Book b, Book c, Book d) {
        books.add(b);
        books.add(c);
        books.add(d);
    }

    public void addBook(Book b) {
        books.add(b);
    }

    public void deleteBook(Book x) {
        books.remove(x);
    }

    public void allBooks() {
        for (int i = 0; i < books.size(); i++) {
            System.out.println(books.get(i));
        }
    }

    public Library searchN(String n) {
        Library mas = new Library();
        for (int i = 0; i < books.size(); i++) {
            String s = books.get(i).getName();
            if (s.contains(n)) {
                mas.addBook(books.get(i));
            }
        }
        return mas;
    }

    public Library searchA(String n) {
        Library mas = new Library();
        for (int i = 0; i < books.size(); i++) {
            String s = books.get(i).getAuthor();
            if (s.contains(n)) {
                mas.addBook(books.get(i));
            }
        }
        return mas;
    }

    public Library searchG(String n) {
        Library mas = new Library();
        for (int i = 0; i < books.size(); i++) {
            String s = books.get(i).getGenre();
            if (s.contains(n)) {
                mas.addBook(books.get(i));
            }
        }
        return mas;
    }

    public Library searchNum(String n) {
        Library mas = new Library();
        for (int i = 0; i < books.size(); i++) {
            String s = books.get(i).getNumber();
            if (s.contains(n)) {
                mas.addBook(books.get(i));
            }
        }
        return mas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Library library = (Library) o;
        return Objects.equals(books, library.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(books);
    }
}