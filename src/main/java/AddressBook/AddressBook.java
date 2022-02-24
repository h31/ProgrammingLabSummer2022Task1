package AddressBook;
import java.util.*;

public class AddressBook {

    private final Map<String, Address> personalData = new HashMap<>();

    public int size() {
        return personalData.size();
    }

    // Добавление пары Человек-Адрес в список
    void addPerson(String surname, Address address) {
        if (surname.isBlank() || personalData.containsKey(surname))
            throw new IllegalArgumentException();
        personalData.put(surname, address);

    }

    // Удаление пары Человек-Адрес по фамилии
    void delPerson(String surname) {
        if (surname.isBlank() || !personalData.containsKey(surname))
            throw new IllegalArgumentException();
        personalData.remove(surname);
    }

    // Поиск адреса по фамилии
    public String search(String surname) {
        if (surname.isBlank() || !personalData.containsKey(surname))
            throw new IllegalArgumentException();
        return personalData.get(surname).toString();
    }

    //Изменение адреса заданного человека
    void change(String surname, Address address) {
        if (surname.isBlank() || !personalData.containsKey(surname))
            throw new IllegalArgumentException();
        personalData.replace(surname, address);
    }

    //Получение списка людей, живущих на одной улице
    List<String> sameStreet(String street) {
        List<String> sameStreetList = new ArrayList<>();
        if (street.isBlank()) throw new IllegalArgumentException();
        for (Map.Entry<String, Address> addressBook : personalData.entrySet()) {
            if (Objects.equals(addressBook.getValue().getStreet(), street))
                sameStreetList.add(addressBook.getKey());
        }
        return sameStreetList;
    }

    //Получение списка людей, живущих в одном доме
    List<String> sameHouse(String street, int house) {
        List<String> sameHouseList = new ArrayList<>();
        if (street.isBlank() || house <= 0) throw new IllegalArgumentException();
        for (Map.Entry<String, Address> addressBook : personalData.entrySet()) {
            if (Objects.equals(addressBook.getValue().getStreet(), street) &&
            Objects.equals(addressBook.getValue().getHouse(), house))
                sameHouseList.add(addressBook.getKey());
        }
        return sameHouseList;
    }
}



