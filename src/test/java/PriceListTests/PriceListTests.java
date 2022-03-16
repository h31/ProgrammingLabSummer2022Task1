package PriceListTests;

import PriceList.PriceList;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

public class PriceListTests {

    @Test
    void PriceList() {
        PriceList emptyList = new PriceList();
        PriceList List = new PriceList(0, "Test", "55.84");
        PriceList errorList = new PriceList(0, "Test", "55.845");

        //Проверка работоспособности конструктора класса
        assertEquals(new HashMap<Integer, Pair<String, Integer>>() {{
            put(0, new Pair<>("Test", 5584));
        }}, List.getMap());

        assertEquals(new HashMap<Integer, Pair<String, Integer>>(), emptyList.getMap());

        assertEquals(new HashMap<Integer, Pair<String, Integer>>(), errorList.getMap());


        //Проверка метода add и обработки исключений в нём
        List.add(1, "Молоко", "15798.1");
        assertEquals(new HashMap<Integer, Pair<String, Integer>>() {{
            put(0, new Pair<>("Test", 5584));
            put(1, new Pair<>("Молоко", 1579810));
        }}, List.getMap());

        List.add(1, "NoValue", "0");
        assertEquals(new HashMap<Integer, Pair<String, Integer>>() {{
            put(0, new Pair<>("Test", 5584));
            put(1, new Pair<>("Молоко", 1579810));
        }}, List.getMap());

        List.add(2, "NoValue", "15798.166");
        assertEquals(new HashMap<Integer, Pair<String, Integer>>() {{
            put(0, new Pair<>("Test", 5584));
            put(1, new Pair<>("Молоко", 1579810));
        }}, List.getMap());


        //Проверка метода delete и обработки исключений в нём
        List.delete(1);
        assertEquals(new HashMap<Integer, Pair<String, Integer>>() {{
            put(0, new Pair<>("Test", 5584));
        }}, List.getMap());

        List.delete(1);
        assertEquals(new HashMap<Integer, Pair<String, Integer>>() {{
            put(0, new Pair<>("Test", 5584));
        }}, List.getMap());


        //Проверка метода changePrice и обработки исключений в нём
        List.add(1, "Молоко", "15798.1");
        List.changePrice(1, "555.55");
        assertEquals(new HashMap<Integer, Pair<String, Integer>>() {{
            put(0, new Pair<>("Test", 5584));
            put(1, new Pair<>("Молоко", 55555));
        }}, List.getMap());

        List.changePrice(1, "15798.1");
        List.changePrice(2, "555.55");
        assertEquals(new HashMap<Integer, Pair<String, Integer>>() {{
            put(0, new Pair<>("Test", 5584));
            put(1, new Pair<>("Молоко", 1579810));
        }}, List.getMap());

        List.changePrice(1, "555.555");
        assertEquals(new HashMap<Integer, Pair<String, Integer>>() {{
            put(0, new Pair<>("Test", 5584));
            put(1, new Pair<>("Молоко", 1579810));
        }}, List.getMap());


        //Проверка метода changeName и обработки исключений в нём
        List.changeName(1, "Мясо");
        assertEquals(new HashMap<Integer, Pair<String, Integer>>() {{
            put(0, new Pair<>("Test", 5584));
            put(1, new Pair<>("Мясо", 1579810));
        }}, List.getMap());

        List.changeName(2, "Молоко");
        assertEquals(new HashMap<Integer, Pair<String, Integer>>() {{
            put(0, new Pair<>("Test", 5584));
            put(1, new Pair<>("Мясо", 1579810));
        }}, List.getMap());


        //Проверка метода getCost
        assertEquals(5584.00, List.getCost(0, 100));


        //Завершение покрытия кода
        List.add(2, "Масло", "368");
        assertEquals(new HashMap<Integer, Pair<String, Integer>>() {{
            put(0, new Pair<>("Test", 5584));
            put(1, new Pair<>("Мясо", 1579810));
            put(2, new Pair<>("Масло", 36800));
        }}, List.getMap());

        assertEquals("""
                        0 -> Test -> 55.84 руб.
                        1 -> Мясо -> 15798.10 руб.
                        2 -> Масло -> 368.0 руб."""
        , List.toString());


    }

}
