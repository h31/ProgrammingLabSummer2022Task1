import static org.junit.jupiter.api.Assertions.assertEquals;

import library.Book;
import library.Library;
import org.junit.jupiter.api.Test;

public class Tests {
    @Test
    public void addBook() {
        Book first = new Book("GG", "Egor", "comedy", "A3");
        Library expected = new Library(first);
        Library n = new Library();
        n.addBook(first);
        assertEquals(expected, n);
    }

    @Test
    public void deleteBook() {
        Book first = new Book("GG", "Egor", "comedy", "A3");
        Book second = new Book("Sasha Lukichev", "Egor", "comedy", "A3");
        Library expected = new Library(second);
        Library n = new Library(first, second);
        n.deleteBook(first);
        assertEquals(expected, n);
    }

    @Test
    public void changeBook() {
        Book expected = new Book("GG", "Egor", "comedy", "A3");
        Book expected1 = new Book("GG", "Egorka", "comedy", "A3");
        Book expected2 = new Book("GG", "Egorka", "drama", "A3");
        Book expected3 = new Book("GG", "Egorka", "drama", "A4");
        Book n = new Book("Sasha Lukichev", "Egor", "comedy", "A3");
        n.setName("GG");
        assertEquals(expected,n);
        n.setAuthor("Egorka");
        assertEquals(expected1,n);
        n.setGenre("drama");
        assertEquals(expected2,n);
        n.setNumber("A4");
        assertEquals(expected3,n);
    }

    @Test
    public void findBookNameAndNumber(){
        Book first = new Book("GG", "Egor", "comedy", "A3");
        Book second = new Book("Sasha Lukichev", "Egor", "comedy", "A3");
        Book third = new Book("GG", "Egor", "comedy", "A4");
        Library lib = new Library(first, second, third);
        String str = "G";
        String str1 = "A3";
        Library expected = new Library(first);
        Library n = new Library();
        n = lib.searchN(str).searchNum(str1);
        assertEquals(expected,n);
    }

    @Test
    public void findBookAuthorAndGenre(){
        Book first = new Book("GG", "Egor", "comedy", "A3");
        Book second = new Book("GG", "Egorka", "drama", "A3");
        Book third = new Book("GG", "Egorka", "comedy", "A4");
        Library lib = new Library(first, second, third);
        String str = "Egor";
        String str1 = "comedy";
        Library expected = new Library(first,third);
        Library n = new Library();
        n = lib.searchA(str).searchG(str1);
        assertEquals(expected,n);
    }
}