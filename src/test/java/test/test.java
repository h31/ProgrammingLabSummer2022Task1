package test;

import Main.tsena;
import Main.spisok;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test {

    private spisok list(){
       spisok tmp = new spisok();
       tmp.add("dsfg", new tsena(1231));
       tmp.add("23qwas", new tsena(355));
       tmp.add("asd33", new tsena(223));
       tmp.add("sdff", new tsena(790));
       return tmp;
    }

    @Test
    void add(){
        spisok test = list();
        assertEquals("dsfg", test.getName(0));

    }

    @Test
    void priceCount(){
        spisok test = list();
        assertEquals(7110, test.priceCount(3, 9));
    }

    @Test
    void editName(){
        spisok test = list();
        assertEquals("dsfg", test.getName(0));
        test.editName(0, "abcd");
        assertEquals("abcd", test.getName(0));
    }

    @Test
    void editPrice(){
        spisok test = list();

        assertEquals(1231, test.getPrice(0).get());
        test.editPrice(0, new tsena(86775));
        assertEquals(86775, test.getPrice(0).get());
    }

    @Test
    void remove(){
        spisok test = list();

        assertEquals(4, test.count());
        test.remove(0);
        assertEquals(3, test.count());
    }

}
