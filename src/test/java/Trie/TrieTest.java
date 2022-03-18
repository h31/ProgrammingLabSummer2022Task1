package Trie;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrieTest {

    Trie testTrie;
    Trie testTrieExtra;
    List<String> expectedStrings;


    @BeforeEach
    void setUp() {
        testTrie = new Trie();
        expectedStrings = new ArrayList<>();
    }
    @Test
    void testAdd() {
        expectedStrings.add("dragonfly");
        expectedStrings.add("dragon");
        testTrie.add("Dragon");
        testTrie.add("Dragonfly");
        testTrie.add("DRAGON");
        testTrie.add("DRAGONFLY");
        testTrie.add(null);
        testTrie.add("");

        assertEquals(expectedStrings, testTrie.getAll());
    }

    @Test
    void testRemove() {
        expectedStrings.add("hedgehog");
        expectedStrings.add("squirrel");
        testTrie.add("Deer");
        testTrie.add("Hedgehog");
        testTrie.add("Squirrel");

        testTrie.remove("Deer");
        testTrie.remove(null);
        testTrie.remove("");
        assertEquals(testTrie.getAll(),expectedStrings);
    }

    @Test
    void testContainsString() {
        testTrie.add("Crow");
        testTrie.add("Sparrow");

        assertTrue(testTrie.containsString("Crow"));
        assertFalse(testTrie.containsString("Eagle"));
        assertFalse(testTrie.containsString(null));
        assertFalse(testTrie.containsString(""));
    }

    @Test
    void testGetAllByPrefix() {
        ArrayList<String> expectedStringsExtra = new ArrayList<>();
        expectedStringsExtra.add("wild cat");
        expectedStrings.add("wild cat");
        expectedStrings.add("wild boar");
        testTrie.add("Wild boar");
        testTrie.add("Wild cat");
        testTrie.add("Hamster");

        assertEquals(expectedStrings, testTrie.getAllByPrefix("wild"));
        assertTrue(testTrie.getAllByPrefix(null).isEmpty());
        assertTrue(testTrie.getAllByPrefix("").isEmpty());
        assertEquals(testTrie.getAllByPrefix("Wild cat"), expectedStringsExtra);
    }

    @Test
    void testGetAll() {
        expectedStrings.add("kangaroo");
        expectedStrings.add("koala");
        expectedStrings.add("tasmanian devil");
        for (String str : expectedStrings) testTrie.add(str);
        testTrieExtra = new Trie();
        testTrieExtra.add(null);
        testTrieExtra.add("");

        assertEquals(expectedStrings, testTrie.getAll());
        assertTrue(testTrieExtra.getAll().isEmpty());
    }

    @Test
    void testLastCharNode() {
        testTrie.add("Gorilla");
        assertNull(testTrie.lastCharNode("Chimpanzee"));
        assertEquals('a', testTrie.lastCharNode("Gorilla").value);
        assertNull(testTrie.lastCharNode(""));
        assertNull(testTrie.lastCharNode(null));
    }
}
