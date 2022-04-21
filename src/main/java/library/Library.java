package library;

import java.util.ArrayList;
import java.util.Objects;

public class Library {
    private ArrayList<Book> books = new ArrayList();

    public Library(Book... b) {
        for (int i = 0; i < b.length; i++) {
            books.add(b[i]);
        }
    }

    public void addBook(Book b) {
        books.add(b);
    }

    public void deleteBook(Book x) {
        books.remove(x);
    }

    public Library searchName(String n) {
        Library mas = new Library();
        for (int i = 0; i < books.size(); i++) {
            String s = books.get(i).getName();
            if (s.contains(n)) {
                mas.addBook(books.get(i));
            }
        }
        return mas;
    }

    public Library searchAuthor(String n) {
        Library mas = new Library();
        for (int i = 0; i < books.size(); i++) {
            String s = books.get(i).getAuthor();
            if (s.contains(n)) {
                mas.addBook(books.get(i));
            }
        }
        return mas;
    }

    public Library searchGenre(String n) {
        Library mas = new Library();
        for (int i = 0; i < books.size(); i++) {
            String s = books.get(i).getGenre();
            if (s.contains(n)) {
                mas.addBook(books.get(i));
            }
        }
        return mas;
    }

    public Library searchNumber(String n) {
        Library mas = new Library();
        for (int i = 0; i < books.size(); i++) {
            String s = books.get(i).getNumber();
            if (s.contains(n)) {
                mas.addBook(books.get(i));
            }
        }
        return mas;
    }


    public Library search(String... arr) {
        Library mas = new Library();
        ArrayList<String> listArgs = new ArrayList<>();
        String str = "";
        int count = 0;
        for (String a : arr) {
            listArgs.add(a);
        }
        for (int i = 0; i < books.size(); i++) {
            count = 0;
            str=books.get(i).getName()+" "+books.get(i).getAuthor()+" "+books.get(i).getGenre()+" "+books.get(i).getNumber();
            for (int j = 0; j < listArgs.size(); j++) {
                if (str.contains(listArgs.get(j))) {
                    count += 1;
                }
            }
            if (listArgs.size() == count) {
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