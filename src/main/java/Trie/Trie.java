
//Префиксное дерево (Trie)
//Хранит строки в виде префиксного дерева. Корневой узел такого дерева не хранит
//ничего, узлы 1-го уровня хранят первый символ строки, 2-го уровня -- второй символ и
//так далее.
//Методы: добавление строки в дерево, удаление строки из дерева, поиск строки в
//дереве, поиск всех строк в дереве с заданным префиксом.

package Trie;

import java.util.*;

public class Trie {
    final Node root = new Node(' ');

    private void RecursiveAdd(String input, Node node) {
        if (input == null || node == null) return;
        if (input.isEmpty()) {
            node.isLastSymbol = true;
            return;
        }
        char symbol = input.charAt(0);
        Node child = node.getChild(symbol);
        if (child == null) {
            child = new Node(symbol);
            node.children.add(child);
        }
        RecursiveAdd(input.substring(1), child);
    }

    public void add(String str) {
        if (str == null || containsString(str) || str.isEmpty()) return;
        RecursiveAdd(str.toLowerCase(), root);
    }

    private void RecursiveRemove(String input, Node node) {
        if (input == null || node == null) return;
        if (input.isEmpty()) {
            node.isLastSymbol = false;
            return;
        }
        char symbol = input.charAt(0);
        Node child = node.getChild(symbol);
        RecursiveRemove(input.substring(1), child);
        if (child.children.isEmpty() && !child.isLastSymbol) {
            node.children.remove(child);
        }
    }

    public void remove(String str) {
        if (containsString(str)) {
            RecursiveRemove(str.toLowerCase(), root);
        }
    }

    public boolean containsString(String str) {
        Node current = lastCharNode(str);
        if (current == null) return false;
        return current.isLastSymbol;
    }

    private ArrayList<String> RecursiveGetAllByPrefix(String prefix, Node node) {
        ArrayList<String> storage = new ArrayList<>();
        if (prefix == null || node == null) return storage;
        ArrayList<String> tempResult = new ArrayList<>();
        prefix += node.value;
        if (!node.children.isEmpty()) {
            for (Node child : node.children) {
                tempResult.addAll(RecursiveGetAllByPrefix(prefix, child));
            }
            if (node.isLastSymbol) tempResult.add(prefix.trim());
            return tempResult;
        }
        tempResult.add(prefix.trim());
        return tempResult;
    }

    public ArrayList<String> getAllByPrefix(String prefix) {
        Node current = lastCharNode(prefix);
        if (current == null) {
            return new ArrayList<>();
        }
        prefix = prefix.toLowerCase();
        if (current.children.isEmpty()) {
            ArrayList<String> storage = new ArrayList<>();
            storage.add(prefix);
            return storage;
        }
        prefix = prefix.substring(0, prefix.length()-1);
        ArrayList<String> result = RecursiveGetAllByPrefix(prefix, current);
        result.sort(Comparator.comparingInt(String::length));
        return result;
    }

    public ArrayList<String> getAll() {
        ArrayList<String> result = RecursiveGetAllByPrefix("", root);
        result.remove("");
        return result;
    }

    Node lastCharNode(String str) {
        if (str == null || str.isEmpty()) return null;
        Node current = root;
        for (char c : str.toLowerCase().toCharArray()) {
            current = current.getChild(c);
            if (current == null) return null;
        }
        return current;
    }
}