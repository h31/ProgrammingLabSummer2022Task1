public class ChessFigure {
    private final FigureType figureType;
    private final char color;
    private int x;
    private int y;

    public ChessFigure(int x, int y, FigureType figureType, char color) {
        this.figureType = figureType;
        if (color != 'b' && color != 'w') throw new IllegalArgumentException("Incorrect color");
        this.color = color;
        if (x > 8 || y > 8 || y < 1 || x < 1) throw new IllegalArgumentException("Incorrect coordinates");
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public FigureType getFigureType() {
        return figureType;
    }

    public char getColor() {
        return color;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessFigure that = (ChessFigure) o;
        return x == that.x && y == that.y;
    }
}