import java.util.Objects;

public class ChessFigure {
    private final FigureType figureType;
    private final char color;
    private int x;
    private int y;

    public ChessFigure(int x, int y, FigureType figureType, char color) {
        if (figureType == null) throw new IllegalArgumentException("FigureType cannot be null");
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

    public static char giveAnotherColor(char color) {
        if (color == 'w') return 'b';
        else if (color == 'b') return 'w';
        return color; // В случае, если color некорректный
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equalsCoordinate(ChessFigure o) {
        return x == o.x && y == o.y;
    }

    public boolean equalsCoordinate(int x, int y) {
        return x == this.x && y == this.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessFigure figure = (ChessFigure) o;
        return color == figure.color && x == figure.x && y == figure.y && figureType == figure.figureType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(figureType, color, x, y);
    }
}
