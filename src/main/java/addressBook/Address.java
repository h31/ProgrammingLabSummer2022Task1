package addressBook;

import java.util.Objects;

public class Address {
    private final String street;
    private final int house;
    private final int apartment;

    public Address(String street, int house, int apartment){
        if(street == null || street.isBlank() || house <= 0 || apartment <= 0) throw new IllegalArgumentException();
        this.street = street;
        this.house = house;
        this.apartment = apartment;
    }

    public String getStreet() {
        return street;
    }

    public int getHouse() {
        return house;
    }

    @Override
    public String toString() {
        return street + ", д." + house + ", кв." + apartment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return getHouse() == address.getHouse() && apartment == address.apartment && getStreet().equals(address.getStreet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStreet(), getHouse(), apartment);
    }
}
