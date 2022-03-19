package addressBook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        if (surname.isBlank() || newAddress == null || !people.containsKey(surname)) return false;
        people.put(surname, newAddress);
        return true;
    }

    // Удаление человека
    public boolean deleteHuman(String surname) {
        if (surname.isBlank() || !people.containsKey(surname)) return false;
        people.remove(surname);
        return true;
    }

    // Получение списка людей, живущих на заданной улице
    public List<String> peopleOnStreet(String street) {
        if (street.isBlank()) return null;
        return people.entrySet().stream()
                .filter(it -> it.getValue().getStreet().equals(street))
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    // Получение списка людей, живущих на заданной улице, в заданном доме
    public List<String> peopleInHouse(String street, int house) {
        if (street.isBlank() || house <= 0) return null;
        return people.entrySet().stream()
                .filter(it -> it.getValue().getStreet().equals(street) && it.getValue().getHouse() == house)
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }
}