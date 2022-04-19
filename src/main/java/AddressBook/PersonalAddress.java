package AddressBook;

import java.util.Objects;

public class PersonalAddress {
    private final String street;
    private final int house;
    private final int flat;

    public PersonalAddress(String street, int house, int flat) {
        if(street == null || street.isBlank() || house <= 0 || flat <= 0) throw new IllegalArgumentException();
        this.street = street;
        this.house = house;
        this.flat = flat;
    }

    public String getStreet(){
        return street;
    }

    public int getHouse(){
        return house;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalAddress that = (PersonalAddress) o;
        return house == that.house && flat == that.flat && Objects.equals(street, that.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, house, flat);
    }

    @Override
    public String toString() {
        return street + " " + house + "/" + flat;
    }

}