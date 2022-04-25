package PriceList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Price {
    String rubles;
    String pennies;

    public Price(String price) {
        ArrayList<String> array = new ArrayList<>(
                Arrays.asList(price.split("[.,]")));
        if (array.size() == 1) {
            array.add("0");
        }
        this.rubles = array.get(0);
        this.pennies = array.get(1);
        if (this.pennies.length() == 1) {
            this.pennies = this.pennies + "0";
        }
    }


    public String asPennies() {
        String str = "";
        str = str + this.rubles;
        str = str + this.pennies;
        return str;
    }

    public String asRubles() {
        String str = "";
        str = str + this.rubles;
        str = str + ".";
        str = str + this.pennies;
        return str;
    }

    public Price change(String price) {
        ArrayList<String> array = new ArrayList<>(
                Arrays.asList(price.split("[.,]")));
        if (array.size() == 1) {
            array.add("0");
        }
        this.rubles = array.get(0);
        this.pennies = array.get(1);
        if (this.pennies.length() == 1) {
            this.pennies = this.pennies + "0";
        }
        return this;
    }

    @Override
    public String toString() {
        return this.rubles + "." + this.pennies;
    }

    @Override
    public int hashCode() {
        return this.rubles.hashCode() * 29 + this.pennies.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (this.getClass() != o.getClass())
            return false;
        Price price = (Price) o;
        return Objects.equals(price.rubles, this.rubles) && Objects.equals(price.pennies, this.pennies);
    }
}
