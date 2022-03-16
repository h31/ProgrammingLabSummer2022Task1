package AddressBook;

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

    public String separateStreet(){
        return street;
    }

    public int separateHouse(){
        return house;
    }

    @Override
    public String toString() {
        return "" + street + " " + house + "/" + flat;
    }
}