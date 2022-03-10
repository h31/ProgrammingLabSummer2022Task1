package addressBook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Хранит список адресов различных людей. Адрес состоит из улицы, номера дома и
номера квартиры. Человек задаётся фамилией, для каждого человека хранится лишь
один адрес.

Методы:
1. Добавление пары человек-адрес  V
2. Удаление человека  V
3. Изменение адреса человека  V
4. Получение адреса человека  V
5. Получение списка людей, живущих на заданной улице или в заданном доме  X
*/

public class AddressBook {
    private final Map<String, Address> people = new HashMap<>();

    // Добавление пары человек-адрес
    public boolean addHuman(String surname, Address address) {
        if (surname == null || surname.isBlank() || address == null) return false;
        people.put(surname, address);
        return true;
    }

    // Получение адреса человека
    public Address getAddress(String surname){
        return people.get(surname);
    }
    public String getAddressStr(String surname){
        Address address = getAddress(surname);
        return address != null ? address.toString() : null;
    }

    // Изменение адреса человека
    public boolean changeAddress(String surname, Address newAddress) {
        if (surname == null || surname.isBlank() || newAddress == null || !people.containsKey(surname)) return false;
        people.put(surname, newAddress);
        return true;
    }

    // Удаление человека
    public boolean deleteHuman(String surname){
        if (surname == null || surname.isBlank() || !people.containsKey(surname)) return false;
        people.remove(surname);
        return true;
    }

    // Получение списка людей, живущих на заданной улице
    public List<String> peopleOnStreet(String street) {
        if (street == null || street.isBlank()) return null;
        List<String> answer = new ArrayList<>();
        for (Map.Entry<String, Address> human : people.entrySet())
            if(human.getValue().getStreet().equals(street))
                answer.add(human.getKey());
        return answer;
    }

    // Получение списка людей, живущих на заданной улице, в заданном доме
    public List<String> peopleInHouse(String street, int house) {
        if(street == null || street.isBlank() || house <= 0) return null;
        List<String> answer = new ArrayList<>();
        for (Map.Entry<String, Address> human : people.entrySet())
            if(human.getValue().getStreet().equals(street) && human.getValue().getHouse() == house)
                answer.add(human.getKey());
        return answer;
    }
}