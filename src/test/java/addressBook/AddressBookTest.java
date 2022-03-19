package addressBook;


import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AddressBookTest {


    @Test
    void getAddressAndAddHuman() {
        AddressBook addressBook = new AddressBook();
        assertNull(addressBook.getAddress("Симоновский"));
        assertNull(addressBook.getAddressStr("Симоновский"));
        assertTrue(addressBook.addHuman("Симоновский",
                new Address("Светлановский пр-т", 79, 201)));
        assertThrows(IllegalArgumentException.class, ()->
                addressBook.addHuman("Симоновский", new Address("", 79, 201)));
        assertThrows(IllegalArgumentException.class, ()->
                addressBook.addHuman("Симоновский", new Address("Светлановский пр-т", 0, 201)));
        assertThrows(IllegalArgumentException.class, ()->
                addressBook.addHuman("Симоновский", new Address("Светлановский пр-т", 79, 0)));
        assertFalse(addressBook.addHuman("Симоновский" , null));
        assertEquals(new Address("Светлановский пр-т", 79, 201),
                addressBook.getAddress("Симоновский"));
        assertEquals(new Address("Светлановский пр-т", 79, 201).toString(),
                addressBook.getAddressStr("Симоновский"));
        assertEquals("Светлановский пр-т, д.79, кв.201",addressBook.getAddressStr("Симоновский"));
        assertFalse(addressBook.addHuman("", new Address("Светлановский пр-т", 79, 201)));
    }

    @Test
    void changeAddress() {
        AddressBook addressBook = new AddressBook();
        assertTrue(addressBook.addHuman("Симоновский",
                new Address("Светлановский пр-т", 79, 201)));
        assertTrue(addressBook.addHuman("Леонидов",
                new Address("ул.Морской пехоты", 4, 159)));
        assertTrue(addressBook.changeAddress("Симоновский",
                new Address("ул.Морской пехоты", 4, 159)));
        assertTrue(addressBook.changeAddress("Леонидов",
                new Address("Светлановский пр-т", 79, 201)));
        assertFalse(addressBook.changeAddress("",
                new Address("Светлановский пр-т", 79, 201)));
        assertFalse(addressBook.changeAddress("Симоновский", null));
        assertFalse(addressBook.changeAddress("Курятников",
                new Address("Светлановский пр-т", 79, 201)));
    }

    @Test
    void deleteHuman() {
        AddressBook addressBook = new AddressBook();
        assertFalse(addressBook.deleteHuman(""));
        assertFalse(addressBook.deleteHuman("Курятников"));
        assertTrue(addressBook.addHuman("Симоновский",
                new Address("Светлановский пр-т", 79, 201)));
        assertTrue(addressBook.addHuman("Леонидов",
                new Address("ул.Морской пехоты", 4, 159)));
        assertTrue(addressBook.deleteHuman("Симоновский"));
        assertTrue(addressBook.deleteHuman("Леонидов"));
    }

    @Test
    void peopleOnStreet() {
        AddressBook addressBook = new AddressBook();
        assertTrue(addressBook.addHuman("Симоновский",
                new Address("Светлановский пр-т", 79, 201)));
        assertTrue(addressBook.addHuman("Леонидов",
                new Address("ул.Морской пехоты", 4, 159)));
        assertTrue(addressBook.addHuman("Давыдов",
                new Address("Светлановский пр-т", 78, 201)));
        assertTrue(addressBook.addHuman("Козырев",
                new Address("Светлановский пр-т", 78, 228)));
        assertTrue(addressBook.addHuman("Солодовник",
                new Address("ул.Морской пехоты", 78, 159)));
        assertTrue(addressBook.addHuman("Буглаев",
                new Address("Светлановский пр-т", 4, 228)));
        assertNull(addressBook.peopleOnStreet(""));
        assertEquals(List.of(),addressBook.peopleOnStreet("Кутозовский пр-т"));
        assertEquals(List.of("Симоновский","Козырев", "Давыдов", "Буглаев"),
                addressBook.peopleOnStreet("Светлановский пр-т"));
        assertEquals(List.of("Солодовник", "Леонидов"),addressBook.peopleOnStreet("ул.Морской пехоты"));
    }

    @Test
    void peopleInHouse() {
        AddressBook addressBook = new AddressBook();
        assertTrue(addressBook.addHuman("Симоновский",
                new Address("Светлановский пр-т", 79, 201)));
        assertTrue(addressBook.addHuman("Леонидов",
                new Address("ул.Морской пехоты", 4, 159)));
        assertTrue(addressBook.addHuman("Давыдов",
                new Address("Светлановский пр-т", 78, 201)));
        assertTrue(addressBook.addHuman("Козырев",
                new Address("Светлановский пр-т", 78, 228)));
        assertTrue(addressBook.addHuman("Солодовник",
                new Address("ул.Морской пехоты", 78, 159)));
        assertTrue(addressBook.addHuman("Буглаев",
                new Address("Светлановский пр-т", 4, 227)));
        assertNull(addressBook.peopleInHouse("", 78));
        assertNull(addressBook.peopleInHouse("Светлановский пр-т", 0));
        assertEquals(List.of(),addressBook.peopleInHouse("Кутозовский пр-т", 78));
        assertEquals(List.of(),addressBook.peopleInHouse("Светлановский пр-т", 5));
        assertEquals(List.of("Козырев", "Давыдов"),addressBook.peopleInHouse("Светлановский пр-т", 78));
        assertEquals(List.of("Леонидов"),addressBook.peopleInHouse("ул.Морской пехоты", 4));
        assertEquals(List.of("Симоновский"),addressBook.peopleInHouse("Светлановский пр-т", 79));
    }
}