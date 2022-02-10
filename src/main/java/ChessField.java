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
    // Белые фигуры
    private int countWhitePawn = 0;
    private final List<ChessFigure> piecesWhite = new LinkedList<>();
    // Черные фигуры
    private int countBlackPawn = 0;
    private final List<ChessFigure> piecesBlack = new LinkedList<>();
    // Занятые координаты
    private final Set<Pair<Integer, Integer>> notEmptySquares = new HashSet<>();

    public ChessField(ChessFigure king1, ChessFigure king2) {
        if (king1 == null || king2 == null) throw new IllegalArgumentException("King cannot be null");
        if (king1.equalsCoordinate(king2)) throw new IllegalArgumentException("The same coordinates");
        if (king1.getColor() == king2.getColor()) throw new IllegalArgumentException("The same color of the kings");
        if (king1.getFigureType() != FigureType.KING || king1.getFigureType() != king2.getFigureType())
            throw new IllegalArgumentException("Figure is not the KING");
        notEmptySquares.add(king1.figureToPair());
        notEmptySquares.add(king2.figureToPair());
        if (king1.colorIsWhite()) {
            piecesWhite.add(king1);
            piecesBlack.add(king2);
        } else {
            piecesWhite.add(king2);
            piecesBlack.add(king1);
        }
        if (abs(piecesWhite.get(0).getX() - piecesBlack.get(0).getX()) <= 1 &&
                abs(piecesWhite.get(0).getY() - piecesBlack.get(0).getY()) <= 1) {
            throw new IllegalArgumentException("The Kings are too close");
        }
    }

    // Отчистка клетки, кроме короля
    public boolean clearSquare(int x, int y) {
        // И без этого будет работать, но не хочу лишний раз бегать по массивам
        if (x > 8 || y > 8 || y < 1 || x < 1) return false;
        if (!notEmptySquares.contains(Pair.of(x, y))) return false;
        Iterator<ChessFigure> iterator = piecesWhite.iterator();
        if (iterator.hasNext()) iterator.next();
        while (iterator.hasNext()) {
            ChessFigure i = iterator.next();
            if (i.getX() == x && i.getY() == y) {
                if (i.getFigureType() == FigureType.PAWN) countWhitePawn--;
                notEmptySquares.remove(i.figureToPair());
                iterator.remove();
                return true;
            }
        }
        iterator = piecesBlack.iterator();
        if (iterator.hasNext()) iterator.next();
        while (iterator.hasNext()) {
            ChessFigure i = iterator.next();
            if (i.getX() == x && i.getY() == y) {
                if (i.getFigureType() == FigureType.PAWN) countBlackPawn--;
                notEmptySquares.remove(i.figureToPair());
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public boolean moveFigure(int xStart, int yStart, int xStop, int yStop) {
        if (xStart > 8 || yStart > 8 || xStart < 1 || yStart < 1 || xStop > 8 || yStop > 8 || xStop < 1 || yStop < 1)
            return false;
        if (!notEmptySquares.contains(Pair.of(xStart, yStart))) return false;
        // Фигура, которая ходит
        ChessFigure walking = null;
        // Фигура, на которую ходят
        ChessFigure target = null;
        for (ChessFigure figure : piecesWhite) {
            if (figure.getX() == xStart && figure.getY() == yStart) {
                walking = figure;
            }
            if (figure.getX() == xStop && figure.getY() == yStop) {
                target = figure;
            }
        }
        if (walking == null || target == null)
            for (ChessFigure figure : piecesBlack) {
                if (figure.getX() == xStart && figure.getY() == yStart) {
                    walking = figure;
                }
                if (figure.getX() == xStop && figure.getY() == yStop) {
                    target = figure;
                }
            }
        // Корректность перемещения, если там король
        if (Objects.requireNonNull(walking).getFigureType() == FigureType.KING) {
            if (walking.colorIsBlack() && abs(xStop - piecesWhite.get(0).getX()) <= 1 &&
                    abs(yStop - piecesWhite.get(0).getY()) <= 1) return false;
            if (walking.colorIsWhite() && abs(xStop - piecesBlack.get(0).getX()) <= 1 &&
                    abs(yStop - piecesBlack.get(0).getY()) <= 1) return false;
        }
        if (target != null) {
            // Нельзя есть фигуру, того же цвета
            if (walking.getColor() == target.getColor()) return false;
            // Всегда должен быть король (из ограничений)
            if (target.getFigureType() == FigureType.KING) return false;
            if (target.getFigureType() == FigureType.PAWN)
                if (target.colorIsBlack()) countBlackPawn--;
                else countWhitePawn--;
            if (target.colorIsBlack()) piecesBlack.remove(target);
            else piecesWhite.remove(target);
        }
        notEmptySquares.remove(walking.figureToPair());
        walking.move(xStop, yStop);
        notEmptySquares.add(walking.figureToPair());
        return true;
    }

    // Добавление новой фигуры
    public boolean addSquare(ChessFigure figure) {
        if (figure == null) return false;
        if (figure.getFigureType() == FigureType.KING) return false;
        if (notEmptySquares.contains(figure.figureToPair())) return false;
        if (figure.getFigureType() == FigureType.PAWN)
            if (figure.colorIsBlack()) {
                if (countBlackPawn == 8) return false;
                countBlackPawn++;
            } else {
                if (countWhitePawn == 8) return false;
                countWhitePawn++;
            }
        if (figure.colorIsBlack()) piecesBlack.add(figure);
        else piecesWhite.add(figure);
        notEmptySquares.add(figure.figureToPair());
        return true;
    }

    // Фигуры находятся на 1 диагонали
    private boolean onOneDiagonal(ChessFigure figure1, ChessFigure figure2) {
        return abs(figure1.getX() - figure2.getX()) == abs(figure1.getY() - figure2.getY());
    }

    // Фигура находится между двумя фигурами (по X)
    private boolean betweenFiguresX(ChessFigure figure1, ChessFigure figure2, ChessFigure across) {
        return (across.getX() > figure1.getX() && across.getX() < figure2.getX()) ||
                (across.getX() < figure1.getX() && across.getX() > figure2.getX());
    }

    // Фигура находится между двумя фигурами (по Y)
    private boolean betweenFiguresY(ChessFigure figure1, ChessFigure figure2, ChessFigure across) {
        return (across.getY() > figure1.getY() && across.getY() < figure2.getY()) ||
                (across.getY() < figure1.getY() && across.getY() > figure2.getY());
    }

    private boolean bishopAttack(ChessFigure defendersKing, List<ChessFigure> defenders, ChessFigure bishop) {
        // Проверка, можно ли убить
        if (onOneDiagonal(defendersKing, bishop)) {
            // Проверка, можно ли защитить
            for (ChessFigure defender : defenders)
                if (onOneDiagonal(defender, defendersKing) && onOneDiagonal(defender, bishop) &&
                        betweenFiguresY(defendersKing, bishop, defender)) {
                    return false;
                }
            return true;
        }
        return false;
    }

    private boolean rookAttack(ChessFigure defendersKing, List<ChessFigure> defenders, ChessFigure rook) {
        // Проверка, можно ли убить
        if (defendersKing.getX() == rook.getX()) {
            // Проверка, можно ли защитить
            for (ChessFigure defender : defenders)
                if (defender.getX() == defendersKing.getX() &&
                        betweenFiguresY(defendersKing, rook, defender))
                    return false;
            return true;
        } else if (defendersKing.getY() == rook.getY()) {
            // Проверка, можно ли защитить
            for (ChessFigure defender : defenders)
                if (defender.getY() == defendersKing.getY() &&
                        betweenFiguresX(defendersKing, rook, defender))
                    return false;
            return true;
        }
        return false;
    }

    // Проверяет, под угрозой ли король, переданного цвета
    public boolean checkKing(ChessFigure.Color colorOfKing) {
        if (colorOfKing == null) return false;
        List<ChessFigure> threats = colorOfKing == ChessFigure.Color.BLACK ? piecesWhite : piecesBlack; // Создали лист "атакующих"
        ChessFigure.Color threatsColor = ChessFigure.giveAnotherColor(colorOfKing); // Цвет атакующих
        List<ChessFigure> defenders = new ArrayList<>(piecesWhite);  // Фигуры, которые будут мешать ходить
        defenders.addAll(piecesBlack); // Ходить будут мешать оба цвета
        // Для упрощения сразу обозначим защищающегося короля
        ChessFigure defendersKing = colorOfKing == ChessFigure.Color.BLACK ? piecesBlack.get(0) : piecesWhite.get(0);
        // Проверяем, может ли убить короля любой из атакующих
        for (ChessFigure figure : threats) {
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
                    if (bishopAttack(defendersKing, defenders, figure)) return true;
                    break;
                case ROOK:
                    if (rookAttack(defendersKing, defenders, figure)) return true;
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
                    if (bishopAttack(defendersKing, defenders, figure)) return true;
                    if (rookAttack(defendersKing, defenders, figure)) return true;
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
        return countWhitePawn == that.countWhitePawn && countBlackPawn == that.countBlackPawn && Objects.equals(piecesWhite, that.piecesWhite) && Objects.equals(piecesBlack, that.piecesBlack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countWhitePawn, piecesWhite, countBlackPawn, piecesBlack);
    }
}
