import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class TicTacToe {
    private final int size;
    private final Item[][] field;
    private final ArrayList<Item> items = new ArrayList<>();

    public TicTacToe(int size) {
        if (size <= 0 || size >= Integer.MAX_VALUE) throw new IllegalArgumentException();
        this.size = size;
        this.field = new Item[size][size];
    }

    public Boolean AddInField(Boolean isX, int x, int y) {
        if (x >= 0 && y >= 0 && x < this.size && y < this.size && this.field[x][y] == null) {
            Item newItem = new Item(x, y, isX);

            this.items.add(newItem);
            this.field[x][y] = newItem;

            return true;
        }
        else return false;
    }

    public Boolean CleanInField(int x, int y) {
        if (x >= 0 && y >= 0 && x < this.size && y < this.size) {
            this.items.remove(this.field[x][y]);
            this.field[x][y] = null;

            return true;
        }
        else return false;
    }

    private ArrayList<Item> LocalFindTheLongest(ArrayList<Item> itemsFindList, int dx, int dy) {
        ArrayList<Item> checkedItems = new ArrayList<>();
        ArrayList<Item> resultList = new ArrayList<>();
        for (Item item : itemsFindList) {
            ArrayList<Item> cashList = new ArrayList<>();
            if (checkedItems.contains(item))
                continue;

            cashList.add(item);
            int i = 1;
            while (item.x + dx * i >= 0 && item.x + dx * i < size && item.y + dy * i >= 0 && item.y + dy * i < size) {
                Item newItem = field[item.x + dx * i][item.y + dy * i];
                if (newItem == null)
                    break;
                else {
                    cashList.add(newItem);
                    checkedItems.add(newItem);
                    i++;
                }
            }

            i = 1;
            while (item.x - dx * i >= 0 && item.x - dx * i < size && item.y - dy * i >= 0 && item.y - dy * i < size) {
                Item newItem = field[item.x - dx * i][item.y - dy * i];
                if (newItem == null)
                    break;
                else {
                    cashList.add(newItem);
                    checkedItems.add(newItem);
                    i++;
                }
            }

            if (cashList.size() > resultList.size())
                resultList = new ArrayList<>(cashList);
        }

        return resultList;
    }

    public ArrayList<Item> FindTheLongest(Boolean isX) {
        ArrayList<Item> itemsFindList = new ArrayList<>(this.items);
        itemsFindList.removeIf(e -> e.isX != isX);

        //вертикаль
        ArrayList<Item> longestList = LocalFindTheLongest(itemsFindList, 0, 1);

        //горизонталь
        ArrayList<Item> localLongestList = LocalFindTheLongest(itemsFindList, 1, 0);
        if (longestList.size() < localLongestList.size())
            longestList = new ArrayList<>(localLongestList);

        //левая диагональ
        localLongestList = LocalFindTheLongest(itemsFindList, 1, 1);
        if (longestList.size() < localLongestList.size())
            longestList = new ArrayList<>(localLongestList);

        //правая диагональ
        localLongestList = LocalFindTheLongest(itemsFindList, 1, -1);
        if (longestList.size() < localLongestList.size())
            longestList = new ArrayList<>(localLongestList);

        return longestList;
    }



    @Override
    public int hashCode() {
        return Objects.hash(size, Arrays.deepHashCode(field), items);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != getClass()) return false;
        TicTacToe game = (TicTacToe) obj;
        return this.size == game.size
                && Arrays.deepEquals(this.field, game.field)
                && this.items.equals(game.items);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                if (field[i][j] != null) {
                    if (field[i][j].isX) builder.append("X");
                    else builder.append("O");
                }
                else builder.append("-");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
