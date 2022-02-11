package chess;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Objects;

public class ChessFigure {
    private final FigureType figureType;
    private final Color color;
    private int x;
    private int y;

    public enum Color {
        BLACK,
        WHITE
    }

    public ChessFigure(int x, int y, FigureType figureType, Color color) {
        if (figureType == null) throw new IllegalArgumentException("chess.FigureType cannot be null");
        this.figureType = figureType;
        if (color == null) throw new IllegalArgumentException("Color cannot be null");
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

    public Color getColor() {
        return color;
    }

    public boolean colorIsBlack() {
        return color == Color.BLACK;
    }

    public boolean colorIsWhite() {
        return color == Color.WHITE;
    }

    public static Color giveAnotherColor(Color color) {
        if (color == Color.WHITE) return Color.BLACK;
        return Color.WHITE;
    }

    public Pair<Integer, Integer> figureToPair() {
        return Pair.of(x, y);
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
