public class Item {
    public final int x;
    public final int y;
    public final Boolean isX;

    public Item(int x, int y, Boolean isX) {
        this.x = x;
        this.y = y;
        this.isX = isX;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != getClass()) return false;
        Item item = (Item) obj;
        return this.isX == item.isX
                && this.x == item.x
                && this.y == item.y;
    }
}