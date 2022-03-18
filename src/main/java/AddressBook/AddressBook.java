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

public class AddressBook {
    final Map<String, PersonalAddress> book = new HashMap<>();

    //Добавление человека
    public void add(String person, PersonalAddress address) {
        if (person == null || person.isBlank() || book.containsKey(person) )
            throw new IllegalArgumentException("Поле не может быть пустым");
        book.put(person, address);
    }

    //Удаление человека
    public void delete(String person) {
        if (person == null || person.isBlank() || !book.containsKey(person))
            throw new IllegalArgumentException("Поле не может быть пустым");
        book.remove(person);
    }

    //Изменение адреса человека
    public void rework(String person, PersonalAddress newAddress) {
        if (person == null || person.isBlank() || !book.containsKey(person))
            throw new IllegalArgumentException("Поле не может быть пустым");
        book.put(person, newAddress);
    }

    //Получение адреса человека
    public String getAddress(String person) {
        if (person == null || person.isBlank())
            throw new IllegalArgumentException("Поле не может быть пустым");
        return book.get(person).toString();
    }

    /*
    Получение списка людей
    Выполняет две функции
        1. При house = 0 выдается список совпадений только по улице
        2. При house > 0 выдается список совпадений по улице и дому
    */
    public List<String> checker(String street, int house) {
        if (street == null || street.isBlank() || house < 0)
            throw new IllegalArgumentException();
        List<String> result = new ArrayList<>();
        if (house == 0)
            for (Map.Entry<String, PersonalAddress> x : book.entrySet()) {
                if (x.getValue().separateStreet().equals(street)) result.add(x.getKey());
            }
        else
            for (Map.Entry<String, PersonalAddress> x : book.entrySet()) {
                if (x.getValue().separateStreet().equals(street) && x.getValue().separateHouse() == house)
                    result.add(x.getKey());
            }
        return result;
    }
}