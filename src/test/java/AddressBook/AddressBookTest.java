package AddressBook;

import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AddressBookTest {

        //Основа списка
        private AddressBook testBook() {
            AddressBook exampleBook = new AddressBook();
            exampleBook.addPerson("Глушкова", new Address("Садовая", 13, 77));
            exampleBook.addPerson("Жуков", new Address("Софийская", 4, 116));
            exampleBook.addPerson("Волков", new Address("Садовая", 13, 85));
            exampleBook.addPerson("Лисичкина", new Address("Садовая", 100, 2));
            exampleBook.addPerson("Петров", new Address("Чекистов", 52, 1025));
            return exampleBook;
        }

    @Test
    void addPerson() {
        AddressBook exampleBook = testBook();
        exampleBook.addPerson("Щукин", new Address("Спасская", 45, 104));
        assertEquals(new Address("Спасская", 45, 104).toString(),
                exampleBook.search("Щукин"));
        exampleBook.addPerson("Зубарев", new Address("Маяковского", 3, 105));
        assertEquals(new Address("Маяковского", 3, 105).toString(),
                exampleBook.search("Зубарев"));
        assertThrows(IllegalArgumentException.class, () -> exampleBook.addPerson("Щукин",
                new Address("Спасская", 45, 104)));
        assertThrows(IllegalArgumentException.class, () -> exampleBook.addPerson("",
                new Address("Пушкина", 36, 98)));
        assertThrows(IllegalArgumentException.class, () -> exampleBook.addPerson("Мышкин",
                new Address("", 36, 98)));
        assertThrows(IllegalArgumentException.class, () -> exampleBook.addPerson("Мышкин",
                new Address("Пушкина", 0, 98)));
        assertThrows(IllegalArgumentException.class, () -> exampleBook.addPerson("Мышкин",
                new Address("Пушкина", -36, 98)));
        assertThrows(IllegalArgumentException.class, () -> exampleBook.addPerson("Мышкин",
                new Address("Пушкина", 36, 0)));
        assertThrows(IllegalArgumentException.class, () -> exampleBook.addPerson("Мышкин",
                new Address("Пушкина", 17, -98)));
        assertEquals(7, exampleBook.size());
    }

    @Test
    void delPerson() {
        AddressBook exampleBook = testBook();
        exampleBook.delPerson("Петров");
        assertEquals(4, exampleBook.size());
        assertThrows(IllegalArgumentException.class, () -> exampleBook.delPerson("Петров"));
        assertThrows(IllegalArgumentException.class, () -> exampleBook.delPerson(""));
    }

    @Test
    void change() {
        AddressBook exampleBook = testBook();
        exampleBook.change("Лисичкина", new Address("Ленина", 15, 101));
        assertEquals(new Address("Ленина", 15, 101).toString(),
        exampleBook.search("Лисичкина"));
        assertThrows(IllegalArgumentException.class, () -> exampleBook.change("Глушков",
            new Address("Строителей", 13, 77)));
        assertThrows(IllegalArgumentException.class, () -> exampleBook.change("",
            new Address("Строителей", 13, 77)));
        assertThrows(IllegalArgumentException.class, () -> exampleBook.change("Глушкова",
            new Address("", 13, 77)));
        assertEquals(5, exampleBook.size());
        assertThrows(IllegalArgumentException.class, () -> exampleBook.change("Глушкова",
            new Address("Пушкина", -13, 77)));
        assertThrows(IllegalArgumentException.class, () -> exampleBook.change("Глушкова",
            new Address("Пушкина", 13, -77)));
    }

    @Test
    void search() {
        AddressBook exampleBook = testBook();
        assertEquals(exampleBook.search("Волков"), new Address("Садовая", 13, 85).toString());
        exampleBook.addPerson("Щукин", new Address("Спасская", 45, 104));
        assertEquals(exampleBook.search("Щукин"), new Address("Спасская", 45, 104).toString());
        assertThrows(IllegalArgumentException.class, () -> exampleBook.search(""));
        assertThrows(IllegalArgumentException.class, () -> exampleBook.search("Есенин"));
    }

    @Test
    void sameStreet() {
        AddressBook exampleBook = testBook();
        assertEquals(List.of("Глушкова", "Волков", "Лисичкина"), exampleBook.sameStreet("Садовая"));
        assertEquals(List.of("Петров"), exampleBook.sameStreet("Чекистов"));
        assertEquals(Collections.emptyList(), exampleBook.sameStreet("Седова"));
        assertThrows(IllegalArgumentException.class, () -> exampleBook.sameStreet(""));
    }

    @Test
    void sameHouse() {
        AddressBook exampleBook = testBook();
        assertEquals(List.of("Глушкова", "Волков"), exampleBook.sameHouse("Садовая", 13));
        assertEquals(List.of("Жуков"), exampleBook.sameHouse("Софийская", 4));
        assertEquals(Collections.emptyList(), exampleBook.sameHouse("Седова", 23));
        assertThrows(IllegalArgumentException.class, () -> exampleBook.sameHouse("", 42));
        assertThrows(IllegalArgumentException.class, () -> exampleBook.sameHouse("Чекистов", -52));
        assertThrows(IllegalArgumentException.class, () -> exampleBook.sameHouse("Чекистов", 0));
    }
}