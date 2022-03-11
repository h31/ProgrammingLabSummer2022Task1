package chess;
// Поле 8х8 клеток (1;1 - левый верхний угол)
// Фигуры: Пешка, конь, слон, ладья, ферзь, король
// Требования:
// 1. Ровно один белый король, ровно один чёрный король
// 2. Не более восьми белых пешек, не более восьми чёрных пешек
// 3. Короли не могут находиться на соседних клетках
// Операции:
// 1. Конструктор (сразу же указывает положение белого и чёрного короля)    v
// 2. Очистить клетку   v
// 3. Поставить новую фигуру (кроме короля)   v
// 4. Переместить существующую фигуру на другую клетку (соблюдать правила ходов не надо)  v
// Бонус:
// 1. Проверить, находится ли определённый король под шахом   v

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static java.lang.Math.abs;

public class ChessField {
    // Белые пешки
    private int countWhitePawn = 0;
    // Черные пешки
    private int countBlackPawn = 0;
    // Пара координаты фигуры и фигура
    private final Map<Pair<Integer, Integer>, ChessFigure> figures = new HashMap<>();
    // Короли
    private final ChessFigure blackKing;
    private final ChessFigure whiteKing;

    public ChessField(ChessFigure king1, ChessFigure king2) {
        if (king1 == null || king2 == null) throw new IllegalArgumentException("King cannot be null");
        if (king1.equalsCoordinate(king2)) throw new IllegalArgumentException("The same coordinates");
        if (king1.getColor() == king2.getColor()) throw new IllegalArgumentException("The same color of the kings");
        if (king1.getFigureType() != FigureType.KING || king1.getFigureType() != king2.getFigureType())
            throw new IllegalArgumentException("Figure is not the KING");
        if (abs(king1.getX() - king2.getX()) <= 1 &&
                abs(king1.getY() - king2.getY()) <= 1)
            throw new IllegalArgumentException("The Kings are too close");
        if (king1.colorIsBlack()) {
            blackKing = king1;
            whiteKing = king2;
        } else {
            blackKing = king2;
            whiteKing = king1;
        }
        figures.put(king1.figureToPair(), king1);
        figures.put(king2.figureToPair(), king2);
    }

    // Отчистка клетки, кроме короля
    public boolean clearSquare(int x, int y) {
        ChessFigure figureOnSquare = figures.get(Pair.of(x, y));
        if (figureOnSquare == null || figureOnSquare.getFigureType() == FigureType.KING) return false;
        if (figureOnSquare.getFigureType() == FigureType.PAWN)
            if (figureOnSquare.getColor() == ChessFigure.Color.BLACK)
                countBlackPawn--;
            else
                countWhitePawn--;
        figures.remove(figureOnSquare.figureToPair());
        return true;
    }

    // Добавление новой фигуры
    public boolean addSquare(ChessFigure figure) {
        if (figure == null) return false;
        if (figure.getFigureType() == FigureType.KING) return false;
        if (figures.get(figure.figureToPair()) != null) return false;
        if (figure.getFigureType() == FigureType.PAWN)
            if (figure.colorIsBlack()) {
                if (countBlackPawn == 8) return false;
                countBlackPawn++;
            } else {
                if (countWhitePawn == 8) return false;
                countWhitePawn++;
            }
        figures.put(figure.figureToPair(), figure);
        return true;
    }

    public boolean moveFigure(int xStart, int yStart, int xStop, int yStop) {
        ChessFigure target = figures.get(Pair.of(xStop, yStop));
        ChessFigure walking = figures.get(Pair.of(xStart, yStart));
        if (walking == null) return false;
        if (walking.getFigureType() == FigureType.KING) {
            if (walking.colorIsBlack() && abs(xStop - whiteKing.getX()) <= 1 &&
                    abs(yStop - whiteKing.getY()) <= 1) return false;
            if (walking.colorIsWhite() && abs(xStop - blackKing.getX()) <= 1 &&
                    abs(yStop - blackKing.getY()) <= 1) return false;
        }
        if (target != null) {
            // Нельзя есть фигуру, того же цвета
            if (walking.getColor() == target.getColor()) return false;
            // Всегда должен быть король (из ограничений)
            if (target.getFigureType() == FigureType.KING) return false;
            if (target.getFigureType() == FigureType.PAWN)
                if (target.colorIsBlack()) countBlackPawn--;
                else countWhitePawn--;
        }
        walking.move(xStop, yStop);
        figures.put(Pair.of(xStop, yStop), walking);
        return true;
    }


    // Фигуры находятся на 1 диагонали
    private boolean onOneDiagonal(ChessFigure figure1, ChessFigure figure2) {
        return abs(figure1.getX() - figure2.getX()) == abs(figure1.getY() - figure2.getY());
    }

    private boolean bishopAttack(ChessFigure defendersKing, ChessFigure bishop) {
        // Проверка, можно ли убить
        if (!onOneDiagonal(defendersKing, bishop)) return false;
        int plusToX = (defendersKing.getX() - bishop.getX()) / abs(defendersKing.getX() - bishop.getX());
        int plusToY = (defendersKing.getY() - bishop.getY()) / abs(defendersKing.getY() - bishop.getY());
        // Проверка, можно ли защитить
        for (int x = bishop.getX() + plusToX, y = bishop.getY() + plusToY; x != defendersKing.getX(); x += plusToX, y += plusToY)
            if (figures.get(Pair.of(x, y)) != null)
                return false;
        return true;
    }

    private boolean rookAttack(ChessFigure defendersKing, ChessFigure rook) {
        // Проверка, можно ли убить
        if (defendersKing.getY() == rook.getY()) {
            // Проверка, можно ли защитить
            int plusToX = (defendersKing.getX() - rook.getX()) / abs(defendersKing.getX() - rook.getX());
            for (int x = rook.getX() + plusToX; x != defendersKing.getX(); x += plusToX)
                if (figures.get(Pair.of(x, defendersKing.getY())) != null)
                    return false;
            return true;
        } else if (defendersKing.getX() == rook.getX()) {
            // Проверка, можно ли защитить
            int plusToY = (defendersKing.getY() - rook.getY()) / abs(defendersKing.getY() - rook.getY());
            for (int y = rook.getY() + plusToY; y != defendersKing.getY(); y += plusToY)
                if (figures.get(Pair.of(defendersKing.getX(), y)) != null)
                    return false;
            return true;
        }
        return false;
    }

    // Проверяет, под угрозой ли король, переданного цвета
    public boolean checkKing(ChessFigure.Color colorOfKing) {
        if (colorOfKing == null) return false;
        ChessFigure.Color threatsColor = ChessFigure.Color.giveAnotherColor(colorOfKing); // Цвет атакующих
        // Для упрощения сразу обозначим защищающегося короля
        ChessFigure defendersKing = colorOfKing == ChessFigure.Color.BLACK ? blackKing : whiteKing;
        // Проверяем, может ли убить короля любой из атакующих
        for (ChessFigure figure : figures.values()) {
            if (figure.getColor() == colorOfKing) continue;
            switch (figure.getFigureType()) {
                case PAWN:
                    // УСЛОВНОСТЬ: Я считаю, черные пешки едят вниз, а белые вверх
                    if (abs(defendersKing.getX() - figure.getX()) == 1 &&
                            ((threatsColor == ChessFigure.Color.BLACK && defendersKing.getY() - figure.getY() == 1) ||
                                    (threatsColor == ChessFigure.Color.WHITE && figure.getY() - defendersKing.getY() == 1)))
                        return true;
                    break;
                case BISHOP:
                    // Проверка, что может убить
                    if (bishopAttack(defendersKing, figure)) return true;
                    break;
                case ROOK:
                    if (rookAttack(defendersKing, figure)) return true;
                    break;
                case KNIGHT:
                    int x = figure.getX(), y = figure.getY();
                    if (defendersKing.equalsCoordinate(x + 2, y + 1) || defendersKing.equalsCoordinate(x + 2, y - 1) ||
                            defendersKing.equalsCoordinate(x - 2, y + 1) || defendersKing.equalsCoordinate(x - 2, y - 1) ||
                            defendersKing.equalsCoordinate(x + 1, y + 2) || defendersKing.equalsCoordinate(x + 1, y - 2) ||
                            defendersKing.equalsCoordinate(x - 1, y + 2) || defendersKing.equalsCoordinate(x - 1, y - 2))
                        return true;
                    break;
                case QUEEN:
                    if (bishopAttack(defendersKing, figure)) return true;
                    if (rookAttack(defendersKing, figure)) return true;
                    break;
                case KING:
                    break;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessField that = (ChessField) o;
        return countWhitePawn == that.countWhitePawn && countBlackPawn == that.countBlackPawn && Objects.equals(figures,
                that.figures) && Objects.equals(blackKing, that.blackKing) && Objects.equals(whiteKing, that.whiteKing);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countWhitePawn, countBlackPawn, figures, blackKing, whiteKing);
    }

    @Override
    public String toString() {
        StringBuilder a = new StringBuilder();
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessFigure figure = figures.get(Pair.of(i, j));
                if (figure == null) {
                    a.append(".");
                    continue;
                }
                // KNIGHT == HORSE == H
                if (figure.getFigureType() == FigureType.KNIGHT) {
                    a.append(figure.colorIsWhite() ? "H" : "h");
                }
                char symbol = figure.getFigureType().name().charAt(0);
                a.append(figure.colorIsWhite() ? symbol : Character.toLowerCase(symbol));
            }
            a.append("\n");
        }
        return a.toString();
    }
}
