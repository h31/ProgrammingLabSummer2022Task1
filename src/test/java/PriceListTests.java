import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class PriceListTests {

    @Test
    void PriceList() {
        PriceList List1 = new PriceList();
        PriceList List2 = new PriceList();


        assertEquals(new HashMap<Integer, Pair<String, Double>>() {{
            put(0, new Pair<>("testValue", 0.0));
            put(1, new Pair<>("Молоко", 133.55));
        }}, List1.add(1, "Молоко", 133.55));

        assertEquals(new HashMap<Integer, Pair<String, Double>>() {{
            put(0, new Pair<>("testValue", 0.0));
        }}, List1.delete(1));

        assertEquals(new HashMap<Integer, Pair<String, Double>>() {{
            put(0, new Pair<>("Молоко", 0.0));
        }}, List2.changeName(0, "Молоко"));

        assertEquals(new HashMap<Integer, Pair<String, Double>>() {{
            put(0, new Pair<>("Молоко", 350.10));
        }}, List2.changePrice(0, 350.10));

        assertEquals(1050.30, List2.getCost(0, 3), 1e-3);
        assertThrows(NumberFormatException.class, () -> List1.add(1, "Молоко", 133.5566));
        assertThrows(NumberFormatException.class, () -> List1.add(0, "Молоко", 133.5566));
        assertThrows(NumberFormatException.class, () -> List1.delete(5));
        assertThrows(NumberFormatException.class, () -> List1.changeName(99, "Молоко"));
        assertThrows(NumberFormatException.class, () -> List1.changePrice(99, 133.55));
        assertThrows(NumberFormatException.class, () -> List1.changePrice(0,  133.5566));
        assertThrows(NumberFormatException.class, () -> List1.getCost(55, 3));
    }
}