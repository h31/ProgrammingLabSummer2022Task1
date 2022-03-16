package Trie;

import java.util.ArrayList;
import java.util.List;

/*package-private*/ class Node {
    char value;
    boolean isLastSymbol;
    List<Node> children;

    Node(char value) {
        this.value = value;
        this.isLastSymbol = false;
        this.children = new ArrayList<>();
    }

    /*package-private*/ Node getChild(char symbol) {
        for (Node current : children) {
            if (current.value == symbol) {
                return current;
            }
        }
        return null;
    }
}
