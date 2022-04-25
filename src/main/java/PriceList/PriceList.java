// Хранит списки товаров и их цен, причём у каждого товара есть имя и целочисленный
// код, а цена представляется в рублях и копейках.
// Методы: добавление товара и его цены, изменение цены товара, изменение имени
// товара, удаление товара, определение цены покупки по коду и количеству
// экземпляров.

package PriceList;

import java.util.*;

import org.apache.commons.lang3.tuple.Pair;

class Checker {
    public static Boolean itsId(PriceList List, Integer id) {
        return List.getMap().containsKey(id);
    }

    public static Boolean itsPrice(String price) {
        return price.matches("\\d+[.,]?\\d{0,2}");
    }
}

public class PriceList {

    private final Map<Integer, Pair<String, Price>> products = new HashMap<>();


    public PriceList() {
    }

    public PriceList(Integer id, String name, String price) {
        if (!Checker.itsId(this, id) && Checker.itsPrice(price)) {
            products.put(id, Pair.of(name, new Price(price)));
        }
    }

    public void add(Integer id, String name, String price) {
        if (!Checker.itsId(this, id) && Checker.itsPrice(price)) {
            products.put(id, Pair.of(name, new Price(price)));
        }
    }

    public void delete(Integer id) {
        if (Checker.itsId(this, id)) {
            products.remove(id);
        }
    }

    public void changePrice(Integer id, String price) {
        if (Checker.itsId(this, id) && Checker.itsPrice(price)) {
            products.put(id, Pair.of(products.get(id).getKey(), products.get(id).getValue().change(price)));
        }
    }

    public void changeName(Integer id, String name) {
        if (Checker.itsId(this, id)) {
            products.put(id, Pair.of(name, products.get(id).getValue()));
        }
    }

    public Double getCost(Integer id, Integer cnt) {
        int cost = Integer.parseInt(products.get(id).getValue().asPennies()) * cnt;
        Price result = new Price("0.0");
        if (cost > 10) {
            result.pennies = String.valueOf(cost % 100);
            result.rubles = String.valueOf(cost / 100);
        } else {result.pennies = "0" + cost;}
        return Double.parseDouble(result.asRubles());
    }

    @Override
    public String toString() {
        StringBuilder mapAsString = new StringBuilder();
        for (Integer id : products.keySet()) {
            mapAsString.append(id).append(" -> ").append(products.get(id).getKey()).append(" -> ")
                    .append(products.get(id).getValue().toString()).append(" руб.\n");
        }
        mapAsString.delete(mapAsString.length() - 1, mapAsString.length());
        return mapAsString.toString();
    }

    public Map<Integer, Pair<String, Price>> getMap() {
        return products;
    }


    @Override
    public int hashCode() {
        int result = products.size();
        for (int key : products.keySet()) {
            result = result * 29;
            result = result + key;
            result = result + products.get(key).hashCode();
            result = result + products.get(key).getKey().hashCode();
            result = result + products.get(key).getValue().hashCode();
        }
        return result;
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
