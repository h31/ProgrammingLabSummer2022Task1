package AddressBook;
/*

Вариант 14 -- адресная книга [Java]
Хранит список адресов различных людей. Адрес состоит из улицы, номера дома и
номера квартиры. Человек задаётся фамилией, для каждого человека хранится лишь
один адрес.
Методы:
   добавление пары человек-адрес, удаление человека,
   изменение адреса человека, получение адреса человека,
   получение списка людей, живущих на заданной улице или в заданном доме.

 */

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


public class AddressBook {
    final Map<String, PersonalAddress> book = new HashMap<>();

    public void add(@NotNull String person, PersonalAddress address) {
        if (person.isBlank() || book.containsKey(person)) throw new IllegalArgumentException("Поле не может быть пустым");
        book.put(person, address);
    }

    public void delete(@NotNull String person) {
        if (person.isBlank() || !book.containsKey(person)) throw new IllegalArgumentException("Поле не может быть пустым");
        book.remove(person);
    }

    public void rework(@NotNull String person, PersonalAddress newAddress) {
        if (person.isBlank() || !book.containsKey(person)) throw new IllegalArgumentException("Поле не может быть пустым");
        book.put(person, newAddress);
    }

    public String getAddress(@NotNull String person) {
        if (person.isBlank()) throw new IllegalArgumentException("Поле не может быть пустым");
        return book.get(person).toString();
    }

    public List<String> streetCheck(@NotNull String street) {
        if (street.isBlank()) throw new IllegalArgumentException("Поле не может быть пустым");
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, PersonalAddress> x : book.entrySet()) {
            if (x.getValue().separateStreet().equals(street)) result.add(x.getKey());
        }
        return result;
    }

    public List<String> houseCheck(@NotNull String street, int house) {
        if (street.isBlank() || house <= 0) throw new IllegalArgumentException("Улица или номер дома указаны неверно");
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, PersonalAddress> x : book.entrySet()) {
            if (x.getValue().separateStreet().equals(street) && x.getValue().separateHouse() == house)
                result.add(x.getKey());
        }
        return result;
    }
}

class PersonalAddress {
    private final String street;
    private final int house;
    private final int flat;

    public PersonalAddress(@NotNull String street, int house, int flat) {
        if(street.isBlank() || house <= 0 || flat <= 0) throw new IllegalArgumentException();
        this.street = street;
        this.house = house;
        this.flat = flat;
    }

    public String separateStreet(){
        return street;
    }

    public int separateHouse(){
        return house;
    }

    @Override
    public String toString() {
        return "" + street + " " + house + "/" + flat;
    }
}