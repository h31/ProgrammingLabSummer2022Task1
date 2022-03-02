package Library;

import org.testng.annotations.Test;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LibraryTest {


    private Library LibraryTest() {
        Library newLibrary = new Library();
        newLibrary.add(new Book("Гарри Поттер и философский камень", "Дж.К.Роулинг", "Роман", "А1"));
        newLibrary.add(new Book("Гарри Поттер и Тайная комната", "Дж.К.Роулинг", "Роман", "А2"));
        newLibrary.add(new Book("Гарри Поттер и дары Смерти", "Дж.К.Роулинг", "Роман", "А3"));
        newLibrary.add(new Book("Голодные игры", "С.Коллинз", "Фантастика", "А4"));
        newLibrary.add(new Book("Десять негритят", "Агата Кристи", "Детектив", "А5"));
        newLibrary.add(new Book("Вельд", "Р.Брэдбери", "Фантастика", "А4"));
        newLibrary.add(new Book("Мастер и Маргарита", "М.Булгакова", "Роман", "А6"));
        return newLibrary;
    }

    @Test
    void add() {
        Library newLibrary = LibraryTest();
        newLibrary.add(new Book("Каждый сам миллионер", "А.Грин", "Рассказ", "А8"));
        assertTrue(newLibrary.existence(
                       new Book("Каждый сам миллионер", "А.Грин", "Рассказ", "А8")));
        newLibrary.add(new Book("Шерлок Холмс", "А.К.Дойл", "Детектив", "А9"));
        assertTrue(newLibrary.existence(
                new Book("Шерлок Холмс", "А.К.Дойл", "Детектив", "А9")));
        assertFalse(newLibrary.existence(
                new Book("Три мушкетёра", "А.Дюма", "Роман", "Б1")));
        assertThrows(IllegalArgumentException.class, () ->
                newLibrary.add(new Book("", "Ф.Достоевский", "Роман", "Б2")));
    }

    @Test
    void delete() {
        Library newLibrary = LibraryTest();
        newLibrary.delete(new Book("Гарри Поттер и философский камень", "Дж.К.Роулинг", "Роман", "А1"));
        assertFalse(newLibrary.existence(
                new Book("Гарри Поттер и философский камень", "Дж.К.Роулинг", "Роман", "А1")));
        assertThrows(IllegalArgumentException.class, () ->
                newLibrary.delete(new Book("Преступление и наказание", "", "Роман", "Б2")));
    }

    @Test
    void change() {
        Library newLibrary = LibraryTest();
        newLibrary.change(new Book("Гарри Поттер и Тайная комната", "Дж.К.Роулинг", "Роман", "А2"),
                new Book("Гарри Поттер и узник Азкабана", "Дж.К.Роулинг", "Роман", "А3"));
        assertTrue(newLibrary.existence(
                new Book("Гарри Поттер и узник Азкабана", "Дж.К.Роулинг", "Роман", "А3")));
        assertFalse(newLibrary.existence(
                new Book("Гарри Поттер и Тайная комната", "Дж.К.Роулинг", "Роман", "А2")));
    }

    @Test
    void moving() {
        Library newLibrary = LibraryTest();
        newLibrary.moving(new Book("Голодные игры", "С.Коллинз", "Фантастика", "А4"), "А2");
        assertTrue(newLibrary.existence(
                new Book("Голодные игры", "С.Коллинз", "Фантастика", "А2")));
        assertFalse(newLibrary.existence(
                new Book("Голодные игры", "С.Коллинз", "Фантастика", "А4")));
    }

    @Test
    void searcher() {
        Library newLibrary = LibraryTest();
        assertEquals(Set.of("Гарри Поттер и Тайная комната, Дж.К.Роулинг, Роман, А2",
                        "Гарри Поттер и философский камень, Дж.К.Роулинг, Роман, А1",
                        "Гарри Поттер и дары Смерти, Дж.К.Роулинг, Роман, А3"),
                newLibrary.searcher("Гарри Поттер")
        );
        assertEquals(Set.of("Десять негритят, Агата Кристи, Детектив, А5"),
                newLibrary.searcher("Детектив")
        );
        assertEquals(Set.of("Голодные игры, С.Коллинз, Фантастика, А4", "Вельд, Р.Брэдбери, Фантастика, А4"),
                newLibrary.searcher("Фантастика, А4")
        );
    }

}