// Хранит списки товаров и их цен, причём у каждого товара есть имя и целочисленный
// код, а цена представляется в рублях и копейках.
// Методы: добавление товара и его цены, изменение цены товара, изменение имени
// товара, удаление товара, определение цены покупки по коду и количеству
// экземпляров.

package PriceList;
import java.util.*;

import org.apache.commons.lang3.tuple.Pair;


public class PriceList {

    public static Boolean itsPrice(String price) {
        return price.matches("\\d+[.,]?\\d{0,2}") && !price.matches("\\d+[.,]\\d");
    }

    private final Map<Integer, Pair<String, Price>> products = new HashMap<>();


    public PriceList() {
    }

    public PriceList(int id, String name, String price) throws IllegalArgumentException {
        if (itsPrice(price)) {
            products.put(id, Pair.of(name, new Price(price)));
        } else throw new IllegalArgumentException();

    }

    public PriceList(Map<Integer, Pair<String, String>> x) throws IllegalArgumentException {
        for (Integer id : x.keySet()) {
                String name = x.get(id).getKey();
                String price = x.get(id).getValue();
                if (itsPrice(price)) {
                    products.put(id, Pair.of(name, new Price(price)));
                } else throw new IllegalArgumentException();
        }
    }

    public boolean add(int id, String name, String price) {
            if (!products.containsKey(id) && itsPrice(price)) {
                products.put(id, Pair.of(name, new Price(price)));
                return true;
            }
            return false;
    }

    public boolean delete(int id) {
        if (products.containsKey(id)) {
            products.remove(id);
            return true;
        }
        return false;
    }

    public boolean changePrice(int id, String price) {
            if (products.containsKey(id) && itsPrice(price)) {
                products.put(id, Pair.of(products.get(id).getKey(), new Price(price)));
                return true;
            }
            return false;
    }

    public boolean changeName(int id, String name) {
        if (products.containsKey(id)) {
            products.put(id, Pair.of(name, products.get(id).getValue()));
            return true;
        }
        return false;
    }

    public Price getCost(int id, Integer cnt) {
        int s = products.get(id).getValue().kopeck * cnt;
        return new Price(String.format("%d.%02d", s / 100, s % 100));
    }

    @Override
    public String toString() {
        StringBuilder mapAsString = new StringBuilder();
        for (int id : products.keySet()) {
            mapAsString.append(String.format("%d -> %s -> %s\n", id, products.get(id).getKey(),
                    products.get(id).getValue()));
        }
        mapAsString.delete(mapAsString.length() - 1, mapAsString.length());
        return mapAsString.toString();
    }

    public Map<Integer, Pair<String, Price>> getMap() {
        return products;
    }


    @Override
    public int hashCode() {
        return products.hashCode();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (this.getClass() != o.getClass())
            return false;
        PriceList priceList = (PriceList) o;
        return priceList.getMap().equals(this.getMap());
    }


}
