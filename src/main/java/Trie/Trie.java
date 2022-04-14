package Trie;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trie {

    List<Trie> children = new ArrayList<>();
    char symbol;
    boolean endOfWord;

    public Trie(char symbol) {
        this.symbol = symbol;
        endOfWord = false;
    }

    @Override
    public String toString() {
        return "Trie{" +
                "children=" + children +
                ", symbol=" + symbol +
                ", endOfWord=" + endOfWord +
                '}';
    }

    public void insert(String str) {
        recursiveInsertion(str.trim().toLowerCase());
    }

    private void recursiveInsertion(String str) {
        if (str.length() == 0) {
            endOfWord = true;
            return;
        }
        char ch = str.charAt(0);
        Trie child = findChild(ch);
        if (child == null) {
            child = new Trie(ch);
            children.add(child);
        }
        child.recursiveInsertion(str.substring(1));
    }

    public Trie findChild(char c) {
        for (Trie node : children) {
            if (node.symbol == c) {
                return node;
            }
        }
        return null;
    }

    public boolean contains(String str) {
        Trie current = this;
        for (char ch : str.toCharArray()) {
            current = current.findChild(ch);
            if (current == null) return false;
        }
        return current.endOfWord;
    }


    public List<String> findAll(String path) {
        List<String> result = new ArrayList<>();
        searchRecursion(path, result);
        return result;
    }


    private void searchRecursion(String path, List<String> result) {
        if (symbol != '\0') {
            path += symbol;
        }
        if (children.size() != 0) {
            for (Trie node : children) {
                node.searchRecursion(path, result);
            }
        } else result.add(path);
    }

    public static String removeLastChar(String s) {
        return (s == null || s.length() == 0) ? null : (s.substring(0, s.length() - 1));
    }

    public List<String> findByPrefix(String prefix) {
        List<String> result = new ArrayList<>();
        String path = removeLastChar(prefix);
        prefixSearchRecursion(prefix, path, result);
        return result;
    }

    public void prefixSearchRecursion(String prefix, String path, List<String> result) {
        if (prefix.trim().length() == 0) return;
        char ch = prefix.charAt(0);
        Trie child = findChild(ch);
        if (child != null) {
            child.prefixSearchRecursion(prefix.substring(1), path, result);
            if (prefix.length() == 1) {
                result.addAll(child.findAll(path));
            }
        }
    }

    public void delete(String str) {
        deletionRecursion(str.trim().toLowerCase());
    }

    private void deletionRecursion(String str) {
        if (str.trim().length() == 0 || !contains(str)) return;
        char ch = str.charAt(0);
        Trie child = findChild(ch);
        if (child != null) {
            child.deletionRecursion(str.substring(1));
            if (child.children.size() == 0) children.remove(child);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trie trie = (Trie) o;
        return symbol == trie.symbol && endOfWord == trie.endOfWord && Objects.equals(children, trie.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(children, symbol, endOfWord);
    }
}

