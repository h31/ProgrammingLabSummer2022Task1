// Хранит списки товаров и их цен, причём у каждого товара есть имя и целочисленный
// код, а цена представляется в рублях и копейках.
// Методы: добавление товара и его цены, изменение цены товара, изменение имени
// товара, удаление товара, определение цены покупки по коду и количеству
// экземпляров.

package PriceList;
import java.util.*;

import org.apache.commons.lang3.tuple.Pair;


public class PriceList {

    public static Boolean itsId(PriceList List, Integer id) {
        return List.getMap().containsKey(id);
    }

    public static Boolean itsPrice(String price) {
        return price.matches("\\d+[.,]?\\d{0,2}") && !price.matches("\\d+[.,]\\d");
    }

    private final Map<Integer, Pair<String, Price>> products = new HashMap<>();


    public PriceList() {
    }

    public PriceList(Integer id, String name, String price) throws IllegalArgumentException {
        IllegalArgumentException e = new IllegalArgumentException();
        if (!itsId(this, id) && itsPrice(price)) {
            products.put(id, Pair.of(name, new Price(price)));
        } else throw e;

    }

    public PriceList(Map<Integer, Pair<String, String>> x) throws IllegalArgumentException {
        IllegalArgumentException e = new IllegalArgumentException();
        for (Integer id : x.keySet()) {
                String name = x.get(id).getKey();
                String price = x.get(id).getValue();
                if (!itsId(this, id) && itsPrice(price)) {
                    products.put(id, Pair.of(name, new Price(price)));
                } else throw e;
        }

    }

    public boolean add(Integer id, String name, String price) {
            if (!itsId(this, id) && itsPrice(price)) {
                products.put(id, Pair.of(name, new Price(price)));
                return true;
            } else return false;
    }

    public boolean delete(Integer id) {
        if (itsId(this, id)) {
            products.remove(id);
            return true;
        } else return false;
    }

    public boolean changePrice(Integer id, String price) {
            if (itsId(this, id) && itsPrice(price)) {
                products.put(id, Pair.of(products.get(id).getKey(), new Price(price)));
                return true;
            } else return false;
    }

    public boolean changeName(Integer id, String name) {
        if (itsId(this, id)) {
            products.put(id, Pair.of(name, products.get(id).getValue()));
            return true;
        } else return false;
    }

    public String getCost(Integer id, Integer cnt) {
        int cost = products.get(id).getValue().kopek * cnt;
        String str = "";
        str += cost / 100;
        str += ".";
        if (cost % 100 < 10) str += "0";
        str += cost % 100;
        return str;
    }

    @Override
    public String toString() {
        StringBuilder mapAsString = new StringBuilder();
        for (Integer id : products.keySet()) {
            mapAsString.append(id).append(" -> ").append(products.get(id).getKey()).append(" -> ")
                    .append(products.get(id).getValue().toString()).append("\n");
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
