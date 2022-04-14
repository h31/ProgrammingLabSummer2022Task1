package Trie;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrieTest {
    Trie root = new Trie('\0');


    @Test
    public void insert() {
        root.insert("butterfly");
        root.insert("butter");
        root.insert("string");
        assertTrue(root.contains("butterfly"));
        assertTrue(root.contains("butter"));
        assertTrue(root.contains("string"));
    }

    @Test
    public void delete() {
        root.insert("key");
        root.insert("key2");
        root.insert("file");

        root.delete("key");
        root.delete("file");

        assertEquals(List.of("key2"), root.findAll(""));
    }

    @Test
    public void containString() {
        root.insert("dog");

        assertTrue(root.contains("dog"));
        assertFalse(root.contains("cat"));
        assertFalse(root.contains("do"));
    }

    @Test
    public void findAll() {
        List <String> check = Arrays.asList("chocolate", "cat", "random");
        for (String str : check) {
            root.insert(str);
        }
        assertEquals(check, root.findAll(""));
    }

    @Test
    public void findByPrefix() {
        root.insert("run");
        root.insert("rutube");
        root.insert("string");

        assertEquals(List.of("run", "rutube"), root.findByPrefix("ru"));
    }

    @Test
    public void findChild() {
        root.insert("kl");
        root.insert("iu");
        assertNull(root.findChild('l'));
        assertEquals('k', root.findChild('k').symbol);
        assertEquals('i', root.findChild('i').symbol);

    }
}