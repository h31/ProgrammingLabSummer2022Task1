package tictactoe;

import java.util.*;

public class TicTacToe {
    private final int size;
    private final Map<Pair<Integer, Integer>, Boolean> items = new HashMap<>();

    public TicTacToe(int size) {
        if (size <= 0) throw new IllegalArgumentException();
        this.size = size;
    }

    public boolean addInField(boolean isX, int x, int y) {
        //проверка наличия значения в указанном поле; проверка выхода за границы
        if (this.items.containsKey(new Pair<>(x, y)) || x < 0 || y < 0 || x >= this.size || y >= this.size) {
            return false;
        }
        items.put(new Pair<>(x, y), isX);

        return true;
    }

    public boolean cleanInField(int x, int y) {
        //проверка отсутствия значения в указанном поле; проверка выхода за границы
        if (!this.items.containsKey(new Pair<>(x, y)) ||
                x < 0 || y < 0 || x >= this.size || y >= this.size) {
            return false;
        }
        this.items.remove(new Pair<>(x, y));

        return true;
    }

    private List<Pair<Integer, Integer>> localFindTheLongest(Pair<Integer, Integer> vector, boolean isX) {
        //checked позволяет не проходить по одним и тем же элементам множество раз
        HashSet<Pair<Integer, Integer>> checked = new HashSet<>();
        List<Pair<Integer, Integer>> localResult = new ArrayList<>();
        List<Pair<Integer, Integer>> result = new ArrayList<>();

        for (Pair<Integer, Integer> xy : items.keySet()) {
            if (items.get(xy) != isX || checked.contains(xy)) continue;

            checked.add(xy);
            localResult.add(xy);

            //переменная для смены "направления" проверки
            int i = 1;
            int newX = xy.getX() + vector.getX();
            int newY = xy.getY() + vector.getY();
            Pair<Integer, Integer> newXY = new Pair<>(newX, newY);
            //проверка условий выхода за границы
            while (newX >= 0 && newY >= 0 && newX < size && newY < size) {

                if (!items.containsKey(newXY) || items.get(newXY) != isX) {
                    if (i == -1) break;
                    i = -1;
                    newX = xy.getX() + vector.getX() * i;
                    newY = xy.getY() + vector.getY() * i;
                    newXY = new Pair<>(newX, newY);
                    continue;
                }

                checked.add(newXY);
                localResult.add(newXY);

                newX = newX + vector.getX() * i;
                newY = newY + vector.getY() * i;
                newXY = new Pair<>(newX, newY);
            }

            if (localResult.size() > result.size()) {
                result = new ArrayList<>(localResult);
            }

            localResult = new ArrayList<>();
        }

        return result;
    }

    public List<Pair<Integer, Integer>> findTheLongest(boolean isX) {
        //разные направления поиска (горизонталь, вертикаль, диагонали)
        List<Pair<Integer, Integer>> vectors = Arrays.asList(
                new Pair<>(-1, 0),
                new Pair<>(0, 1),
                new Pair<>(-1, 1),
                new Pair<>(1, 1));

        List<Pair<Integer, Integer>> longestList = new ArrayList<>();


        for (Pair<Integer, Integer> vector : vectors) {
            List<Pair<Integer, Integer>> localLongest = localFindTheLongest(vector, isX);

            if (localLongest.size() > longestList.size()) {
                longestList = localLongest;
            }
        }

        return longestList;
    }



    @Override
    public int hashCode() {
        return Objects.hash(size, items);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != getClass()) return false;
        TicTacToe game = (TicTacToe) obj;
        return this.size == game.size
                && this.items.equals(game.items);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                if (items.containsKey(new Pair<>(i, j))) {
                    if (items.get(new Pair<>(i, j))) builder.append("X");
                    else builder.append("O");
                }
                else builder.append("-");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
