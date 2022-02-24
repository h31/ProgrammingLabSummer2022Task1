package AddressBook;

class Address {
    private final String street;
    private final int house;
    private final int flat;

    Address(String street, int house, int flat) {
            this.street = street;
            this.house = house;
            this.flat = flat;
            if (street.isBlank() || house <= 0 || flat <= 0) {
                throw new IllegalArgumentException();
            }
        }
        String getStreet() {
            return this.street;
        }

        int getHouse() {
            return this.house;
        }

        @Override
        public String toString() {
            return "Улица " + street + ", дом " + house + ", квартира " + flat;
        }
}
