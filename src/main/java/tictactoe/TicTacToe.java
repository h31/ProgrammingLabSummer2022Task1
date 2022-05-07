package tictactoe;

import java.util.*;

public class TicTacToe {
    private final int size;
    private final Map<Coordinate, Item> items = new HashMap<>();

    public TicTacToe(int size) {
        if (size <= 0) throw new IllegalArgumentException();
        this.size = size;
    }

    public boolean addInField(Item item, int x, int y) {
        //проверка наличия значения в указанном поле; проверка выхода за границы
        if (this.items.containsKey(new Coordinate(x, y)) || x < 0 || y < 0 || x >= this.size || y >= this.size) {
            return false;
        }
        items.put(new Coordinate(x, y), item);

        return true;
    }

    public boolean cleanInField(int x, int y) {
        //проверка отсутствия значения в указанном поле; проверка выхода за границы
        if (!this.items.containsKey(new Coordinate(x, y)) ||
                x < 0 || y < 0 || x >= this.size || y >= this.size) {
            return false;
        }
        this.items.remove(new Coordinate(x, y));

        return true;
    }

    public List<Coordinate> findTheLongest(Item item) {
        //разные направления поиска (горизонталь, вертикаль, диагонали)
        List<Coordinate> vectors = Arrays.asList(
                new Coordinate(-1, 0),
                new Coordinate(0, 1),
                new Coordinate(-1, 1),
                new Coordinate(1, 1));

        List<Coordinate> longestList = new ArrayList<>();


        for (Coordinate vector : vectors) {
            List<Coordinate> localLongest = localFindTheLongest(vector, item);

            if (localLongest.size() > longestList.size()) {
                longestList = localLongest;
            }
        }

        return longestList;
    }

    private List<Coordinate> localFindTheLongest(Coordinate vector, Item item) {
        //checked позволяет не проходить по одним и тем же элементам множество раз
        HashSet<Coordinate> checked = new HashSet<>();
        List<Coordinate> result = new ArrayList<>();

        for (Coordinate xy : items.keySet()) {
            List<Coordinate> localResult = new ArrayList<>();

            if (items.get(xy) != item || checked.contains(xy)) continue;

            checked.add(xy);
            localResult.add(xy);

            //переменная для смены "направления" проверки
            boolean directionChanged = false;
            Coordinate newXY = new Coordinate(xy.getX() + vector.getX(), xy.getY() + vector.getY());

            //проверка условий выхода за границы
            while (newXY.getX() >= 0 && newXY.getY() >= 0 && newXY.getX() < size && newXY.getY() < size) {

                if (!items.containsKey(newXY) || items.get(newXY) != item) {
                    if (directionChanged) break;
                    directionChanged = true;
                    vector.makeNegative();
                    newXY = new Coordinate(xy.getX() + vector.getX(), xy.getY() + vector.getY());
                    continue;
                }

                checked.add(newXY);
                localResult.add(newXY);

                newXY = new Coordinate(newXY.getX() + vector.getX(), newXY.getY() + vector.getY());
            }

            if (localResult.size() > result.size()) {
                result = localResult;
            }
        }

        return result;
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
                if (items.containsKey(new Coordinate(i, j))) {
                    builder.append(items.get(new Coordinate(i, j)) == Item.CROSS ? "X" : "O");
                }
                else builder.append("-");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
