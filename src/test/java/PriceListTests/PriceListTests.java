package PriceListTests;

import PriceList.*;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class PriceListTests {

    @Test
    void constructorTests() throws IllegalArgumentException{
        PriceList emptyList = new PriceList();
        PriceList list = new PriceList(0, "Test", "55.84");
        HashMap<Integer, Pair<String, String>> x = new HashMap<>();
        x.put(0, Pair.of("Test", "55.55"));
        x.put(4, Pair.of("Test4", "54.54"));


        assertEquals(new HashMap<Integer, Pair<String, Price>>() {{
            put(0, Pair.of("Test", new Price("55.55")));
            put(4, Pair.of("Test4", new Price("54.54")));
        }}, new PriceList(x).getMap());


        x.put(5, Pair.of("Test4", "54.5455"));
        assertThrows(IllegalArgumentException.class, () -> new PriceList(x));


        assertEquals(new HashMap<Integer, Pair<String, Price>>() {{
            put(0, Pair.of("Test", new Price("55.84")));
        }}, list.getMap());

        assertEquals(new HashMap<>(), emptyList.getMap());

        assertThrows(IllegalArgumentException.class, () -> new PriceList(1, "Milk", "55.555"));
    }

    @Test
    void addTests(){


        PriceList list = new PriceList();
        assertTrue(list.add(0, "Test", "55.84"));
        assertFalse(list.add(0, "","55"));


        list.add(1, "Молоко", "15798.10");
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
    void deleteTests(){
        PriceList list = new PriceList(0, "Test", "55.84");
        list.add(1, "Молоко", "15798.10");


        assertTrue(list.delete(1));
        assertEquals(new HashMap<Integer, Pair<String, Price>>() {{
            put(0, Pair.of("Test", new Price("55.84")));
        }}, list.getMap());

        list.delete(1);
        assertFalse(list.delete(1));
        assertEquals(new HashMap<Integer, Pair<String, Price>>() {{
            put(0, Pair.of("Test", new Price("55.84")));
        }}, list.getMap());
    }

    @Test
    void changePriceTests(){
        PriceList list = new PriceList(0, "Test", "55.84");
        list.add(1, "Молоко", "15798.10");


        list.changePrice(1, "555.55");
        assertEquals(new HashMap<Integer, Pair<String, Price>>() {{
            put(0, Pair.of("Test", new Price("55.84")));
            put(1, Pair.of("Молоко", new Price("555.55")));
        }}, list.getMap());

        assertTrue(list.changePrice(1, "15798.10"));
        assertFalse(list.changePrice(2, "555.55"));
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
    void changeNameTests(){
        PriceList list = new PriceList(0, "Test", "55.84");
        list.add(1, "Мясо", "15798.10");


        assertTrue(list.changeName(1, "Мясо"));
        assertEquals(new HashMap<Integer, Pair<String, Price>>() {{
            put(0, Pair.of("Test", new Price("55.84")));
            put(1, Pair.of("Мясо", new Price("15798.10")));
        }}, list.getMap());

        assertFalse(list.changeName(2, "Молоко"));
        assertEquals(new HashMap<Integer, Pair<String, Price>>() {{
            put(0, Pair.of("Test", new Price("55.84")));
            put(1, Pair.of("Мясо", new Price("15798.10")));
        }}, list.getMap());
    }

    @Test
    void getCostTests(){
        PriceList list = new PriceList(0, "Test", "55.84");
        list.add(1, "Мясо", "0.00");
        list.add(2, "Мясо", "0.01");


        assertEquals(new Price("5584.00"), list.getCost(0, 100));
        assertEquals(new Price("0.00"), list.getCost(1, 54654));
        assertEquals(new Price("0.07"), list.getCost(2, 7));
    }

    @Test
    void toStringTests(){
        PriceList list = new PriceList(0, "Test", "55.84");
        list.add(1, "Мясо", "15798.10");
        list.add(2, "Масло", "368.00");


        assertEquals("""
                        0 -> Test -> 55.84 руб.
                        1 -> Мясо -> 15798.10 руб.
                        2 -> Масло -> 368.00 руб."""
                , list.toString());
    }

    @Test
    void HashCodeTests() {
        Price x = new Price("55.55");
        Price h = new Price("55.55");
        assertEquals(h.hashCode(), x.hashCode());
        h = new Price("55.56");
        assertNotEquals(h.hashCode(), x.hashCode());

        PriceList hash1 = new PriceList(1, "Молоко", "78.55");
        PriceList hash2 = new PriceList(1, "Молоко", "78.55");
        assertEquals(hash1.hashCode(), hash2.hashCode());
        hash2 = new PriceList(2, "Молоко", "78.55");
        assertNotEquals(hash1.hashCode(), hash2.hashCode());

    }

    @Test
    void equalsTests(){
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

    @Test
    void priceTests(){
        Price price1 = new Price("100.50");
        Price price2 = new Price("150");
        assertEquals(new Price("250.50"), price1.sum(price2));

        assertEquals("100.50", price1.asRubles());
    }


}
