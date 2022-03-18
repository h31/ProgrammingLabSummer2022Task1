package Trie;

import java.util.ArrayList;
import java.util.List;

class Node {
    char value;
    boolean isLastSymbol;
    List<Node> children;

    Node(char value) {
        this.value = value;
        this.isLastSymbol = false;
        this.children = new ArrayList<>();
    }

    Node getChild(char symbol) {
        for (Node current : children) {
            if (current.value == symbol) {
                return current;
            }
        }
        return null;
    }
}