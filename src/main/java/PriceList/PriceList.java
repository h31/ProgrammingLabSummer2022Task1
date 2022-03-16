// Хранит списки товаров и их цен, причём у каждого товара есть имя и целочисленный
// код, а цена представляется в рублях и копейках.
// Методы: добавление товара и его цены, изменение цены товара, изменение имени
// товара, удаление товара, определение цены покупки по коду и количеству
// экземпляров.

package PriceList;


import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


class CheckForMistakes {
    Boolean id;
    Boolean price;
    public void checkIt(PriceList List, Integer id, String price) {
        this.id = !List.getMap().containsKey(id);
        this.price = price.matches("\\d+[.,]?\\d{0,2}");
    }
}


class stringToInteger {
    public Integer turn(String str){
        ArrayList<String> array = new ArrayList<>(
                Arrays.asList(str.split("[.,]")));
        if (array.size() == 1) {
            array.add("0");
        }
        if (array.get(1).length() == 1) {
            array.set(1,  array.get(1) + "0");
        }
        return Integer.parseInt(array.get(0)) * 100 + Integer.parseInt(array.get(1));
    }
}


public class PriceList {

    private final Map<Integer, Pair<String, Integer>> products = new HashMap<>();
    private final CheckForMistakes checker = new CheckForMistakes();
    private final stringToInteger turner = new stringToInteger();

    public PriceList(){
    }

    public PriceList(Integer id, String name, String price){
        checker.checkIt(this, id, price);
        if (checker.price) {
            products.put(id, new Pair<>(name, turner.turn(price)));
        }
    }

    public void add(Integer id, String name, String price){
        checker.checkIt(this, id, price);
        if (checker.id && checker.price) {
            products.put(id, new Pair<>(name, turner.turn(price)));
        }
    }

    public void delete(Integer id){
        checker.checkIt(this, id, "0.0");
        if (!checker.id) {
            products.remove(id);
        }
    }

    public void changePrice(Integer id, String price){
        checker.checkIt(this, id, price);
        if (!checker.id && checker.price) {
            products.put(id, new Pair<>(products.get(id).getKey(), turner.turn(price)));
        }
    }

    public void changeName(Integer id, String name){
        checker.checkIt(this, id, "0");
        if (!checker.id && checker.price) {
            products.put(id, new Pair<>(name, products.get(id).getValue()));
        }
    }

    public Double getCost(Integer id, Integer cnt){
        int cost = products.get(id).getValue() * cnt;
        String i = String.valueOf(cost % 100);
        if (i.length() < 2) {i = "0" + i;}
        String str = cost / 100 + "." + i;
        return Double.valueOf(str);
    }

    public String toString() {
        StringBuilder mapAsString = new StringBuilder();
        for (Integer id : products.keySet()) {
            int price = products.get(id).getValue();
            mapAsString.append(id).append(" -> ").append(products.get(id).getKey()).append(" -> ")
                    .append(price / 100).append(".").append(price % 100).append(" руб.\n");
        }
        mapAsString.delete(mapAsString.length()-1, mapAsString.length());
        return mapAsString.toString();
    }

    public Map<Integer, Pair<String, Integer>> getMap() {
        return products;
    }
}
