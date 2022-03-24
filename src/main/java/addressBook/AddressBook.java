package addressBook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/*
Хранит список адресов различных людей. Адрес состоит из улицы, номера дома и
номера квартиры. Человек задаётся фамилией, для каждого человека хранится лишь
один адрес.

Методы:
1. Добавление пары человек-адрес  V
2. Удаление человека  V
3. Изменение адреса человека  V
4. Получение адреса человека  V
5. Получение списка людей, живущих на заданной улице или в заданном доме  V
*/

public class AddressBook {
    private final Map<String, Address> people = new HashMap<>();

    // Добавление пары человек-адрес
    public boolean addHuman(String surname, Address address) {
        if (surname.isBlank() || address == null) return false;
        people.put(surname, address);
        return true;
    }

    // Получение адреса человека
    public Address getAddress(String surname) {
        return people.get(surname);
    }

    public String getAddressStr(String surname) {
        Address address = getAddress(surname);
        return address != null ? address.toString() : null;
    }

    // Изменение адреса человека
    public boolean changeAddress(String surname, Address newAddress) {
        return newAddress != null && people.putIfAbsent(surname, newAddress) != null;
    }

    // Удаление человека
    public boolean deletePerson(String surname) {
        return people.remove(surname) != null;
    }

    // Получение списка людей, живущих на заданной улице
    public List<String> peopleOnStreet(String street) {
        if (street.isBlank()) return null;
        return filterPeople(it -> it.getValue().getStreet().equals(street));
    }

    // Получение списка людей, живущих на заданной улице, в заданном доме
    public List<String> peopleInHouse(String street, int house) {
        if (street.isBlank() || house <= 0) return null;
        return filterPeople(it -> it.getValue().getStreet().equals(street) && it.getValue().getHouse() == house);
    }

    public List<String> filterPeople(Predicate<Map.Entry<String, Address>> predicate) {
        return people.entrySet().stream()
                .filter(predicate).map(Map.Entry::getKey).collect(Collectors.toList());
    }
}