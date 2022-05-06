package PriceList;

import java.util.List;
import java.util.Objects;

public class Price {
    public int kopeck;

    public Price(String price) {
        List<String> array = List.of(price.split("[.,]"));
        this.kopeck = Integer.parseInt(array.get(0)) * 100;
        if (array.size() == 2) {
            this.kopeck += Integer.parseInt(array.get(1));
        }
    }

    public String asRubles() {
        return String.format("%d.%02d",this.kopeck / 100, this.kopeck % 100);
    }

    public Price sum(Price price) {
        int s = this.kopeck + price.kopeck;
        return new Price(String.format("%d.%02d",s / 100, s % 100));
    }

    @Override
    public String toString() {
        return String.format("%d.%02d руб.",this.kopeck / 100, this.kopeck % 100);
    }

    @Override
    public int hashCode() {
        return this.kopeck;
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
        return Objects.equals(price.kopeck, this.kopeck);
    }
}
