package PriceListTests;

import PriceList.*;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class PriceListTests {

    @Test
    void constructorTests() {
        PriceList emptyList = new PriceList();
        PriceList list = new PriceList(0, "Test", "55.84");
        PriceList errorList = new PriceList(0, "Test", "55.845");


        assertEquals(new HashMap<Integer, Pair<String, Price>>() {{
            put(0, Pair.of("Test", new Price("55.84")));
        }}, list.getMap());

        assertEquals(new HashMap<>(), emptyList.getMap());

        assertEquals(new HashMap<>(), errorList.getMap());
    }

    @Test
    void addTests() {
        PriceList list = new PriceList(0, "Test", "55.84");


        list.add(1, "Молоко", "15798.1");
        assertEquals(new HashMap<Integer, Pair<String, Price>>() {{
            put(0, Pair.of("Test", new Price("55.84")));
            put(1, Pair.of("Молоко", new Price("15798.10")));
        }}, list.getMap());

        list.add(1, "NoValue", "0");
        assertEquals(new HashMap<Integer, Pair<String, Price>>() {{
            put(0, Pair.of("Test", new Price("55.84")));
            put(1, Pair.of("Молоко", new Price("15798.10")));
        }}, list.getMap());

        list.add(2, "NoValue", "15798.166");
        assertEquals(new HashMap<Integer, Pair<String, Price>>() {{
            put(0, Pair.of("Test", new Price("55.84")));
            put(1, Pair.of("Молоко", new Price("15798.10")));
        }}, list.getMap());

        list.add(3, "Apple", "0");
        assertEquals(new HashMap<Integer, Pair<String, Price>>() {{
            put(0, Pair.of("Test", new Price("55.84")));
            put(1, Pair.of("Молоко", new Price("15798.10")));
            put(3, Pair.of("Apple", new Price("0")));
        }}, list.getMap());
    }

    @Test
    void deleteTests() {
        PriceList list = new PriceList(0, "Test", "55.84");
        list.add(1, "Молоко", "15798.1");


        list.delete(1);
        assertEquals(new HashMap<Integer, Pair<String, Price>>() {{
            put(0, Pair.of("Test", new Price("55.84")));
        }}, list.getMap());

        list.delete(1);
        assertEquals(new HashMap<Integer, Pair<String, Price>>() {{
            put(0, Pair.of("Test", new Price("55.84")));
        }}, list.getMap());
    }

    @Test
    void changePriceTests() {
        PriceList list = new PriceList(0, "Test", "55.84");
        list.add(1, "Молоко", "15798.1");


        list.changePrice(1, "555.55");
        assertEquals(new HashMap<Integer, Pair<String, Price>>() {{
            put(0, Pair.of("Test", new Price("55.84")));
            put(1, Pair.of("Молоко", new Price("555.55")));
        }}, list.getMap());

        list.changePrice(1, "15798.1");
        list.changePrice(2, "555.55");
        assertEquals(new HashMap<Integer, Pair<String, Price>>() {{
            put(0, Pair.of("Test", new Price("55.84")));
            put(1, Pair.of("Молоко", new Price("15798.10")));
        }}, list.getMap());

        list.changePrice(1, "555.555");
        assertEquals(new HashMap<Integer, Pair<String, Price>>() {{
            put(0, Pair.of("Test", new Price("55.84")));
            put(1, Pair.of("Молоко", new Price("15798.10")));
        }}, list.getMap());

        list.changePrice(1, "15798");
        assertEquals(new HashMap<Integer, Pair<String, Price>>() {{
            put(0, Pair.of("Test", new Price("55.84")));
            put(1, Pair.of("Молоко", new Price("15798.00")));
        }}, list.getMap());

        list.changePrice(1, "0");
        assertEquals(new HashMap<Integer, Pair<String, Price>>() {{
            put(0, Pair.of("Test", new Price("55.84")));
            put(1, Pair.of("Молоко", new Price("0")));
        }}, list.getMap());
    }

    @Test
    void changeNameTests() {
        PriceList list = new PriceList(0, "Test", "55.84");
        list.add(1, "Мясо", "15798.1");


        list.changeName(1, "Мясо");
        assertEquals(new HashMap<Integer, Pair<String, Price>>() {{
            put(0, Pair.of("Test", new Price("55.84")));
            put(1, Pair.of("Мясо", new Price("15798.10")));
        }}, list.getMap());

        list.changeName(2, "Молоко");
        assertEquals(new HashMap<Integer, Pair<String, Price>>() {{
            put(0, Pair.of("Test", new Price("55.84")));
            put(1, Pair.of("Мясо", new Price("15798.10")));
        }}, list.getMap());
    }

    @Test
    void getCostTests() {
        PriceList list = new PriceList(0, "Test", "55.84");
        list.add(1, "Мясо", "0.00");


        assertEquals(5584.00, list.getCost(0, 100));
        assertEquals(0.00, list.getCost(1, 54654));
    }

    @Test
    void toStringTests() {
        PriceList list = new PriceList(0, "Test", "55.84");
        list.add(1, "Мясо", "15798.1");
        list.add(2, "Масло", "368.00");


        assertEquals("""
                        0 -> Test -> 55.84 руб.
                        1 -> Мясо -> 15798.10 руб.
                        2 -> Масло -> 368.00 руб."""
                , list.toString());
    }

    @Test
    void HashCodeTests() {
        assertEquals("13".hashCode() * 29 + "7852".hashCode(), new Price("13.7852").hashCode());
        assertEquals(29 + 1 + Pair.of("Test", new Price("55.98")).hashCode() + "Test".hashCode()
                + new Price("55.98").hashCode(), new PriceList(1, "Test", "55.98").hashCode());

        int hash = 2 * 29 + 1 + Pair.of("Test", new Price("55.98")).hashCode() + "Test".hashCode()
                + new Price("55.98").hashCode();
        hash = hash * 29 + 2 + Pair.of("Milk", new Price("11.98")).hashCode()
                + "Milk".hashCode() + new Price("11.98").hashCode();

        PriceList list = new PriceList();
        list.add(1, "Test", "55.98");
        list.add(2, "Milk", "11.98");
        assertEquals(hash, list.hashCode());
    }

    @Test
    void equalsTests() {
        Price price0 = new Price("44.44");
        Price price1 = new Price("44.44");

        assertEquals(price0, price0);
        assertNotEquals(price0, null);
        assertNotEquals(price0, new HashMap<>());
        assertEquals(price1, price0);
        assertEquals(price0, price1);


        PriceList list0 = new PriceList(0, "Test", "0");
        PriceList list1 = new PriceList(0, "Test", "0");

        assertEquals(list0, list0);
        assertNotEquals(list0, null);
        assertNotEquals(list0, new HashMap<>());
        assertEquals(list0, list1);
        assertEquals(list1, list0);
    }


}
