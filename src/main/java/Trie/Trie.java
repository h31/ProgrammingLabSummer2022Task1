package Trie;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trie {

    List<Trie> children;
    char symbol;
    boolean flag;

    public Trie(char symbol) {
        this.symbol = symbol;
        children = new ArrayList<>();
        flag = false;
    }

    @Override
    public String toString() {
        return "Trie{" +
                "children=" + children +
                ", symbol=" + symbol +
                '}';
    }

    public void insert(String str) {
        recursiveInsertion(str.trim().toLowerCase());
    }

    private void recursiveInsertion(String str) {
        if (str.length() == 0) {
            flag = true;
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

    public boolean containString(String str) {
        Trie current = this;
        for (char ch : str.toCharArray()) {
            current = current.findChild(ch);
            if (current == null) return false;
        }
        return current.flag;
    }


    public List<String> findAll(String path) {
        List<String> result = new ArrayList<>();
        searchRecursion(path, result);
        return result;
    }


    private void searchRecursion(String path, List<String> result) {
        if (symbol != '~') {
            path += symbol;
        }
        if (children.size() != 0) {
            for (Trie node : children) {
                node.searchRecursion(path, result);
            }
        } else result.add(path);

    }

    public List<String> findByPrefix(String prefix) {
        List<String> res = new ArrayList<>();
        List<String> all = findAll("");
        for (String str : all) {
            if (str.startsWith(prefix)) {
                res.add(str);
            }
        }
        return res;
    }


    public void delete(String str) {
        deletionRecursion(str.trim().toLowerCase());
    }

    private void deletionRecursion(String str) {
        if (str.trim().length() == 0 || !containString(str)) return;
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
        return symbol == trie.symbol && Objects.equals(children, trie.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(children, symbol);
    }

    public static void main(String[] args) {
        Trie root = new Trie('~');
        root.insert("big boy");
        root.insert("big");

        List<String> list = new ArrayList<>();
        root.findAll("");
        System.out.println(root.findAll(""));
    }
}

