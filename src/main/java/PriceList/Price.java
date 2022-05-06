package PriceList;

import java.util.List;
import java.util.Objects;

public class Price {
    Integer kopek;

    public Price(String price) {
        List<String> array = List.of(price.split("[.,]"));
        this.kopek = Integer.parseInt(array.get(0)) * 100;
        if (array.size() == 2) {
            this.kopek += Integer.parseInt(array.get(1));
        }
    }

    @Override
    public String toString() {
        String str = "";
        str += this.kopek / 100;
        str += ".";
        if (this.kopek % 100 < 10) str += "0";
        str += this.kopek % 100;
        str += " руб.";
        return str;
    }

    @Override
    public int hashCode() {
        return this.kopek / 100 * 29 + this.kopek % 100;
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
        return Objects.equals(price.kopek, this.kopek);
    }
}
