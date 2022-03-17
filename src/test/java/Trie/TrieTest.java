package Trie;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrieTest {
    Trie root = new Trie(' ');
    List<String> check = new ArrayList<>();


    @Test
    public void insert() {
        root.insert("butterfly");
        root.insert("butter");
        root.insert("string");

        assertTrue(root.containString("butterfly"));
        assertTrue(root.containString("butter"));
        assertTrue(root.containString("string"));
    }

    @Test
    public void delete() {
        root.insert("key");
        root.insert("key2");
        root.insert("file");
        check.add("key2");

        root.delete("key");
        root.delete("file");

        List<String> extractedFromTree = new ArrayList<>();
        root.findAll("", extractedFromTree);
        assertEquals(check, extractedFromTree);
    }

    @Test
    public void containString() {
        root.insert("dog");

        assertTrue(root.containString("dog"));
        assertFalse(root.containString("cat"));
    }

    @Test
    public void findAll() {
        check = Arrays.asList("chocolate", "cat", "random");
        for (String str : check) {
            root.insert(str);
        }

        List<String> extractedFromTree = new ArrayList<>();
        root.findAll("", extractedFromTree);
        assertEquals(check, extractedFromTree);
    }

    @Test
    public void findPrefix() {
        root.insert("run");
        root.insert("rutube");
        root.insert("string");
        check = Arrays.asList("run", "rutube");

        assertEquals(check, root.findPrefix("ru"));
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