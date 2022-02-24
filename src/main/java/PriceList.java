// Хранит списки товаров и их цен, причём у каждого товара есть имя и целочисленный
// код, а цена представляется в рублях и копейках.
// Методы: добавление товара и его цены, изменение цены товара, изменение имени
// товара, удаление товара, определение цены покупки по коду и количеству
// экземпляров.

import javafx.util.Pair;
import java.util.HashMap;
import java.util.Map;

public class PriceList {
    private final Map<Integer, Pair<String, Double>> products = new HashMap<>(){{
        put(0, new Pair<>("testValue", 0.0));
    }};

    public Map<Integer, Pair<String, Double>> add(Integer id, String name, Double price) throws NumberFormatException {
        if (products.containsKey(id)) {
            throw new NumberFormatException("Такой id уже занят");
        }
        if (price.toString().split("\\.")[1].length() > 2) {
            throw new NumberFormatException("Неверный формат денег");
        }
        products.put(id, new Pair<>(name, price));
        return products;
    }

    public Map<Integer, Pair<String, Double>> delete(Integer id) throws NumberFormatException{
        if (!products.containsKey(id)) {
            throw new NumberFormatException("Неверный id");
        }
        products.remove(id);
        return products;
    }

    public Map<Integer, Pair<String, Double>> changePrice(Integer id, Double price) throws NumberFormatException{
        if (!products.containsKey(id)) {
            throw new NumberFormatException("Неверный id");
        }
        if (price.toString().split("\\.")[1].length() > 2) {
            throw new NumberFormatException("Неверный формат денег");
        }
        products.put(id, new Pair<>(products.get(id).getKey(), price));
        return products;
    }

    public Map<Integer, Pair<String, Double>> changeName(Integer id, String name) throws NumberFormatException{
        if (!products.containsKey(id)) {
            throw new NumberFormatException("Неверный id");
        }
        products.put(id, new Pair<>(name, products.get(id).getValue()));
        return products;
    }

    public Double getCost(Integer id, Integer cnt) throws NumberFormatException{
        if (!products.containsKey(id)) {
            throw new NumberFormatException("Неверный id");
        }
        return products.get(id).getValue() * cnt;
    }
}