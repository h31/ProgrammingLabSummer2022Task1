package AddressBook;

import java.util.Objects;

public class Address {
    private final String street;
    private final int house;
    private final int flat;

    public Address(String street, int house, int flat) {
        if (street.isBlank() || house <= 0 || flat <= 0) {
            throw new IllegalArgumentException();
        }
        this.street = street;
        this.house = house;
        this.flat = flat;
        }

        public String getStreet() {
            return this.street;
        }

        public int getHouse() {
            return this.house;
        }

        @Override
        public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return house == address.house && flat == address.flat && Objects.equals(street, address.street);
        }

        @Override
        public int hashCode() {
        return Objects.hash(street, house, flat);
        }

        @Override
        public String toString() {
            return "Улица " + street + ", дом " + house + ", квартира " + flat;
        }
}
