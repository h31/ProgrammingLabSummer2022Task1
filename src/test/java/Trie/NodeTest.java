package Trie;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class NodeTest {
    Trie testTrie;

    @Test
    void testGetChild() {
        testTrie = new Trie();
        testTrie.add("abc");

        Node lastCharNode = testTrie.lastCharNode("abc");
        Node penultimateCharNode = testTrie.lastCharNode("ab");
        assertNull(lastCharNode.getChild('d'));
        assertEquals('c', penultimateCharNode.getChild('c').value);
        assertNull(penultimateCharNode.getChild('q'));
    }
}