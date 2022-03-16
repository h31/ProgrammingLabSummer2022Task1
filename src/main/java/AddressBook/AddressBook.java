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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class AddressBook {
    final Map<String, PersonalAddress> book = new HashMap<>();

    public void add(String person, PersonalAddress address) {
        if (person == null || person.isBlank() || book.containsKey(person) ) throw new IllegalArgumentException("Поле не может быть пустым");
        book.put(person, address);
    }

    public void delete(String person) {
        if (person == null || person.isBlank() || !book.containsKey(person)) throw new IllegalArgumentException("Поле не может быть пустым");
        book.remove(person);
    }

    public void rework(String person, PersonalAddress newAddress) {
        if (person == null || person.isBlank() || !book.containsKey(person)) throw new IllegalArgumentException("Поле не может быть пустым");
        book.put(person, newAddress);
    }

    public String getAddress(String person) {
        if (person == null || person.isBlank()) throw new IllegalArgumentException("Поле не может быть пустым");
        return book.get(person).toString();
    }

    public List<String> streetCheck(String street) {
        if (street == null || street.isBlank()) throw new IllegalArgumentException("Поле не может быть пустым");
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, PersonalAddress> x : book.entrySet()) {
            if (x.getValue().separateStreet().equals(street)) result.add(x.getKey());
        }
        return result;
    }

    public List<String> houseCheck(String street, int house) {
        if (street == null || street.isBlank() || house <= 0) throw new IllegalArgumentException("Улица или номер дома указаны неверно");
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, PersonalAddress> x : book.entrySet()) {
            if (x.getValue().separateStreet().equals(street) && x.getValue().separateHouse() == house)
                result.add(x.getKey());
        }
        return result;
    }
}