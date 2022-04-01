package Trie;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trie {

    List<Trie> children; //список дочерних узлов
    char symbol;

    Trie(char symbol) {
        this.symbol = symbol;
        children = new ArrayList<>();
    }

    public void insert(String str) { //Добавление строки в дерево
        if (str.length() == 0) return;
        char ch = str.trim().toLowerCase().charAt(0); //первый символ строки
        Trie child = findChild(ch);
        if (child == null) {
            child = new Trie(ch);
            children.add(child); //Добавляется новый узел
        }
        child.insert(str.substring(1)); //рекурсия без первого символа
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
        return true;
    }

    public void findAll(String path, List<String> result) { //текущий пройденный путь, список найденных строк
        if (symbol != ' ') { //если не корневой узел
            path += symbol; //текущий пройденный путь + текущий символ данного узла
        }
        if (children.size() != 0) {
            for (Trie node : children) {
                node.findAll(path, result); //Вызываем рекурсию для остальных символов
            }
        } else result.add(path); //Собранная строка добавляется в список

    }

    public List<String> findPrefix(String prefix) {
        List<String> res = new ArrayList<>();
        List<String> all = new ArrayList<>(); //список всех строк
        findAll("", all);
        for (String str : all) {
            if (str.startsWith(prefix)) { //добавляем только те слова, которые начинаются с заданного префикса
                res.add(str);
            }
        }
        return res;
    }

    public void delete(String str) {
        if (str.trim().length() == 0 || !containString(str)) return;
        char ch = str.trim().toLowerCase().charAt(0);
        Trie child = findChild(ch);
        if (child != null) {
            if (str.length() != 0) {
                child.delete(str.substring(1));
                if (child.children.size() == 0) children.remove(child);
            }
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
}

