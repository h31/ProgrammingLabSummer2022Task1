import java.util.ArrayList;
import java.util.Iterator;

public class TicTacToe {
    private int size;
    private Item[][] field;
    private final ArrayList<Item> items = new ArrayList<>();

    public TicTacToe(int size) {
        if (size > 0) {
            this.size = size;
            this.field = new Item[size][size];
        }
        else {
            System.out.println("The field size must be a natural number");
        }
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

    public ArrayList<Item> FindTheLongest(Boolean isX) {
        ArrayList<Item> longestList = new ArrayList<>();

        //region Вертикаль
        ArrayList<Item> checkedItems = new ArrayList<>();
        ArrayList<Item> itemsFindList = new ArrayList<>(this.items);
        itemsFindList.removeIf(e -> e.isX != isX);
        for (Item item : itemsFindList) {
            if (checkedItems.contains(item))
                continue;

            int dx = 0;
            int dy = -1;
            ArrayList<Item> cashList = new ArrayList<>();
            cashList.add(item);
            //вверх
            while (item.y + dy >= 0) {
                Item newItem = field[item.x][item.y + dy];
                if (newItem == null)
                    break;
                else {
                    cashList.add(newItem);
                    checkedItems.add(newItem);
                    dy--;
                }
            }

            dy = 1;
            //вниз
            while (item.y + dy < size) {
                Item newItem = field[item.x][item.y + dy];
                if (newItem == null)
                    break;
                else {
                    cashList.add(newItem);
                    checkedItems.add(newItem);
                    dy++;
                }
            }

            if (longestList.size() < cashList.size())
                longestList = new ArrayList<>(cashList);
        }
        //endregion

        //region Горизонталь
        checkedItems = new ArrayList<>();
        itemsFindList = new ArrayList<>(this.items);
        itemsFindList.removeIf(e -> e.isX != isX);
        for (Item item : itemsFindList) {
            if (checkedItems.contains(item))
                continue;
            int dx = -1;
            int dy = 0;
            ArrayList<Item> cashList = new ArrayList<>();
            cashList.add(item);
            //влево
            while (item.x + dx >= 0) {
                Item newItem = field[item.x + dx][item.y];
                if (newItem == null)
                    break;
                else {
                    cashList.add(newItem);
                    checkedItems.add(newItem);
                    dx--;
                }
            }

            dx = 1;
            //вправо
            while (item.x + dx < size) {
                Item newItem = field[item.x + dx][item.y];
                if (newItem == null)
                    break;
                else {
                    cashList.add(newItem);
                    checkedItems.add(newItem);
                    dx++;
                }
            }

            if (longestList.size() < cashList.size())
                longestList = new ArrayList<>(cashList);
        }
        //endregion

        //region Диагональ правая
        checkedItems = new ArrayList<>();
        itemsFindList = new ArrayList<>(this.items);
        itemsFindList.removeIf(e -> e.isX != isX);
        for (Item item : itemsFindList) {
            if (checkedItems.contains(item))
                continue;
            int dx = -1;
            int dy = -1;
            ArrayList<Item> cashList = new ArrayList<>();
            cashList.add(item);
            //вниз влево
            while (item.y + dy >= 0 && item.x + dx >= 0) {
                Item newItem = field[item.x + dx][item.y + dy];
                if (newItem == null)
                    break;
                else {
                    cashList.add(newItem);
                    checkedItems.add(newItem);
                    dx--;
                    dy--;
                }
            }

            dx = 1;
            dy = 1;
            //вверх вправо
            while (item.y + dy < size && item.x + dx < size) {
                Item newItem = field[item.x + dx][item.y + dy];
                if (newItem == null)
                    break;
                else {
                    cashList.add(newItem);
                    checkedItems.add(newItem);
                    dx++;
                    dy++;
                }
            }

            if (longestList.size() < cashList.size())
                longestList = new ArrayList<>(cashList);
        }
        //endregion

        //region Диагональ левая
        checkedItems = new ArrayList<>();
        itemsFindList = new ArrayList<>(this.items);
        itemsFindList.removeIf(e -> e.isX != isX);
        for (Item item : itemsFindList) {
            if (checkedItems.contains(item))
                continue;
            int dx = -1;
            int dy = 1;
            ArrayList<Item> cashList = new ArrayList<>();
            cashList.add(item);
            //вверх влево
            while (item.y + dy < size && item.x + dx >= 0) {
                Item newItem = field[item.x + dx][item.y + dy];
                if (newItem == null)
                    break;
                else {
                    cashList.add(newItem);
                    checkedItems.add(newItem);
                    dx--;
                    dy--;
                }
            }

            dx = 1;
            dy = -1;
            //вниз вправо
            while (item.y + dy >= 0 && item.x + dx < size) {
                Item newItem = field[item.x + dx][item.y + dy];
                if (newItem == null)
                    break;
                else {
                    cashList.add(newItem);
                    checkedItems.add(newItem);
                    dx++;
                    dy++;
                }
            }

            if (longestList.size() < cashList.size())
                longestList = new ArrayList<>(cashList);
        }
        //endregion

        return longestList;
    }
}
