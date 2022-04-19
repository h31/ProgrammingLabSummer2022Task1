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
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AddressBook {
    final Map<String, PersonalAddress> book = new HashMap<>();

    //Добавление человека
    public void add(String person, PersonalAddress address) {
        if (person == null || person.isBlank() || book.containsKey(person))
            throw new IllegalArgumentException("err");
        book.put(person, address);
    }

    //Удаление человека
    public void delete(String person) {
        if (person == null || person.isBlank() || !book.containsKey(person))
            throw new IllegalArgumentException("err");
        book.remove(person);
    }

    //Изменение адреса человека
    public void changeAddress(String person, PersonalAddress newAddress) {
        if (person == null || person.isBlank() || !book.containsKey(person))
            throw new IllegalArgumentException("err");
        book.put(person, newAddress);
    }

    //Получение адреса человека
    public PersonalAddress getAddress(String person) {
        if (person == null || person.isBlank())
            throw new IllegalArgumentException("err");
        return book.get(person);
    }

    //Получение списка людей
    public List<String> coChecker(String street) {
        if (street == null || street.isBlank())
            throw new IllegalArgumentException();
        return filterFunc(it -> it.getValue().getStreet().equals(street));
    }

    public List<String> coChecker(String street, int house) {
        if (street == null || street.isBlank() || house <= 0)
            throw new IllegalArgumentException();
        return filterFunc(it -> it.getValue().getStreet().equals(street) && it.getValue().getHouse() == house);
    }

    private List<String> filterFunc(Predicate<Map.Entry<String,PersonalAddress>> x) {
        return book.entrySet().stream()
                .filter(x).map(Map.Entry::getKey).collect(Collectors.toList());
    }

}