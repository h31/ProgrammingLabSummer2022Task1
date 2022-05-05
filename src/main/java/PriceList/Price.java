package PriceList;

import java.util.List;
import java.util.Objects;

public class Price {
    Integer ruble;
    Integer kopek = 0;

    public Price(String price) {
        List<String> array = List.of(price.split("[.,]"));
        this.ruble = Integer.parseInt(array.get(0));
        if (array.size() == 2) {
            this.kopek = Integer.parseInt(array.get(1));
        }
    }


    public Integer asKopek() {
        String str = "";
        str = str + this.ruble;
        if (this.kopek < 10) str += "0";
        str = str + this.kopek;
        return Integer.parseInt(str);
    }

    @Override
    public String toString() {
        String str = "";
        str += this.ruble;
        str += ".";
        if (this.kopek < 10) str += "0";
        str += this.kopek;
        str += " руб.";
        return str;
    }

    @Override
    public int hashCode() {
        return this.ruble * 29 + this.kopek;
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
        return Objects.equals(price.ruble, this.ruble) && Objects.equals(price.kopek, this.kopek);
    }
}
