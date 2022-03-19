package AddressBook;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class AddressBookTest {
    AddressBook x = new AddressBook();

    @Test
    void add() {
        x.add("Стасов", new PersonalAddress("Космонавтов", 47, 4));
        assertEquals(new PersonalAddress("Космонавтов", 47, 4).toString(),
                x.book.get("Стасов").toString());
        assertThrows(IllegalArgumentException.class, () ->
                x.add(null, new PersonalAddress("Московская", 77 , 7)));
        assertThrows(IllegalArgumentException.class, () ->
                x.add("Иванов", new PersonalAddress(null, 77 , 7)));
        assertThrows(IllegalArgumentException.class, () ->
                x.add("Иванов", new PersonalAddress("", 77 , 7)));
        assertThrows(IllegalArgumentException.class, () ->
                x.add("", new PersonalAddress("Московская", 77 , 7)));
        assertThrows(IllegalArgumentException.class, () ->
                x.add("Стасов", new PersonalAddress("Космонавтов", 47 , 4)));
        assertThrows(IllegalArgumentException.class, () ->
                x.add("Самсонов", new PersonalAddress("пр-кт Ветеранов", 0 , 7)));
        assertThrows(IllegalArgumentException.class, () ->
                x.add("Самсонов", new PersonalAddress("пр-кт Ветеранов", 10 , 0)));
    }
    @Test
    void delete() {
        x.add("Абрамов", new PersonalAddress("Апрельская", 1, 22));
        x.add("Адамов", new PersonalAddress("Февральская", 28, 29));
        x.delete("Абрамов");
        assertEquals(1, x.book.size());
        assertThrows(IllegalArgumentException.class, () ->
                x.delete("Амогусов"));
        assertThrows(IllegalArgumentException.class, () ->
                x.delete(""));
        assertThrows(IllegalArgumentException.class, () ->
                x.delete(null));
    }
    @Test
    void rework() {
        x.add("Абрамов", new PersonalAddress("Апрельская", 1, 22));
        x.add("Адамов", new PersonalAddress("Февральская", 28, 29));
        x.rework("Адамов", new PersonalAddress("Апрельская", 1, 21));
        assertEquals(new PersonalAddress("Апрельская", 1, 21).toString(),
                x.book.get("Адамов").toString());
        assertThrows(IllegalArgumentException.class, () ->
                x.rework("", new PersonalAddress("Апрельская", 1, 21)));
        assertThrows(IllegalArgumentException.class, () ->
                x.rework("Гурбанов", new PersonalAddress("Апрельская", 1, 21)));
        assertThrows(IllegalArgumentException.class, () ->
                x.rework(null, new PersonalAddress("Апрельская", 1, 21)));
    }
    @Test
    void getAddress() {
        x.add("Абрамов", new PersonalAddress("Апрельская", 1, 22));
        assertEquals(new PersonalAddress("Апрельская", 1, 22).toString(),
                x.getAddress("Абрамов"));
        assertThrows(IllegalArgumentException.class, () ->
                x.getAddress(null));
        assertThrows(IllegalArgumentException.class, () ->
                x.getAddress(""));
    }
    @Test
    void checker() {
        x.add("Абрамов", new PersonalAddress("Апрельская", 1, 22));
        x.add("Солодовник", new PersonalAddress("Февральская", 22, 110));
        x.add("Адамов", new PersonalAddress("Февральская", 22, 29));
        x.add("Гурбанов", new PersonalAddress("Апрельская", 22, 1));
        assertEquals(List.of("Абрамов", "Гурбанов"), x.checker("Апрельская"));
        assertEquals(List.of("Солодовник", "Адамов"), x.checker("Февральская", 22));
        assertThrows(IllegalArgumentException.class, () ->
                x.checker("", 22));
        assertThrows(IllegalArgumentException.class, () ->
                x.checker(""));
        assertThrows(IllegalArgumentException.class, () ->
                x.checker("Февральская", -1));
        assertThrows(IllegalArgumentException.class, () ->
                x.checker(null, 22));
        assertThrows(IllegalArgumentException.class, () ->
                x.checker(null));
    }
}