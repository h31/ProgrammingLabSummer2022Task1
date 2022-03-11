package test;

import Main.Price;
import Main.itemList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test {

    private itemList testList(){
       itemList test = new itemList();
       test.add("1", 100);
       test.add("2", 200);
       test.add("3", 300);
       test.add("4", 400);
       return test;
    }

    @Test
    void add(){
        itemList test = testList();

        assertEquals("1", test.getName(0));
        assertEquals("2", test.getName(1));
    }

    @Test
    void priceCount(){
        itemList test = testList();

        assertEquals(1000, test.priceCount(1, 5));
        assertEquals(900, test.priceCount(2, 3));
    }

    @Test
    void editName(){
        itemList test = testList();

        assertEquals("1", test.getName(0));
        test.editName(0, "first");
        assertEquals("first", test.getName(0));
    }

    @Test
    void editPrice(){
        itemList test = testList();

        assertEquals(100, test.getPrice(0).get());
        test.editPrice(0, new Price(587));
        assertEquals(587, test.getPrice(0).get());
    }

    @Test
    void delete(){
        itemList test = testList();

        assertEquals(4, test.count());
        test.delete(0);
        assertEquals(3, test.count());
    }

}
