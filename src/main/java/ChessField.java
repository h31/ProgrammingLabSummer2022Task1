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

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class ChessField {
    // Белые фигуры
    private int countWhitePawn = 0;
    private final List<ChessFigure> piecesWhite;
    // Черные фигуры
    private int countBlackPawn = 0;
    private final List<ChessFigure> piecesBlack;

    public ChessField(ChessFigure king1, ChessFigure king2) {
        if (king1 == null || king2 == null) throw new IllegalArgumentException("King cannot be null");
        if (king1.equals(king2)) throw new IllegalArgumentException("The same coordinates");
        if (king1.getColor() == king2.getColor()) throw new IllegalArgumentException("The same color of the kings");
        if (king1.getFigureType() != FigureType.KING || king1.getFigureType() != king2.getFigureType())
            throw new IllegalArgumentException("Figure is not the KING");
        piecesWhite = new ArrayList<>();
        piecesBlack = new ArrayList<>();
        if (king1.getColor() == 'w') {
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
    public boolean clearCage(int x, int y) {
        // И без этого будет работать, но не хочу лишний раз бегать по массивам
        if (x > 8 || y > 8 || y < 1 || x < 1) return false;
        for (int i = 1; i < piecesWhite.size(); i++)
            if (piecesWhite.get(i).getX() == x && piecesWhite.get(i).getY() == y) {
                if (piecesWhite.get(i).getFigureType() == FigureType.PAWN) countWhitePawn--;
                piecesWhite.remove(i);
                return true;
            }
        for (int i = 1; i < piecesBlack.size(); i++)
            if (piecesBlack.get(i).getX() == x && piecesBlack.get(i).getY() == y) {
                if (piecesBlack.get(i).getFigureType() == FigureType.PAWN) countBlackPawn--;
                piecesBlack.remove(i);
                return true;
            }
        return false;
    }

    // Вернет true, если клетка занята
    private boolean checkCage(int x, int y) {
        // И без этого будет работать, но не хочу лишний раз бегать по массивам
        if (x > 8 || y > 8 || y < 1 || x < 1) return false;
        for (ChessFigure chessFigure : piecesWhite)
            if (chessFigure.getX() == x && chessFigure.getY() == y) {
                return true;
            }
        for (ChessFigure chessFigure : piecesBlack)
            if (chessFigure.getX() == x && chessFigure.getY() == y) {
                return true;
            }
        return false;
    }

    public boolean moveFigure(int xStart, int yStart, int xStop, int yStop) {
        if (xStart > 8 || yStart > 8 || xStart < 1 || yStart < 1 || xStop > 8 || yStop > 8 || xStop < 1 || yStop < 1)
            return false;
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
        if (walking == null) return false;
        // Корректность перемещения, если там король
        if (walking.getFigureType() == FigureType.KING) {
            if (walking.getColor() == 'b' && abs(xStop - piecesWhite.get(0).getX()) <= 1 &&
                    abs(yStop - piecesWhite.get(0).getY()) <= 1) return false;
            if (walking.getColor() == 'w' && abs(xStop - piecesBlack.get(0).getX()) <= 1 &&
                    abs(yStop - piecesBlack.get(0).getY()) <= 1) return false;
        }
        if (target != null) {
            // Нельзя есть фигуру, того же цвета
            if (walking.getColor() == target.getColor()) return false;
            // Всегда должен быть король (из ограничений)
            if (target.getFigureType() == FigureType.KING) return false;
            if (target.getFigureType() == FigureType.PAWN)
                if (target.getColor() == 'b') countBlackPawn--;
                else countWhitePawn--;
            if (target.getColor() == 'b') piecesBlack.remove(target);
            else piecesWhite.remove(target);
        }
        walking.move(xStop, yStop);
        return true;
    }

    // Добавление новой фигуры
    public boolean addCage(ChessFigure figure) {
        if (figure == null) return false;
        if (figure.getFigureType() == FigureType.KING) return false;
        if (checkCage(figure.getX(), figure.getY())) return false;
        if (figure.getFigureType() == FigureType.PAWN)
            if (figure.getColor() == 'b') {
                if (countBlackPawn == 8) return false;
                countBlackPawn++;
            } else {
                if (countWhitePawn == 8) return false;
                countWhitePawn++;
            }
        if (figure.getColor() == 'b') piecesBlack.add(figure);
        else piecesWhite.add(figure);
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

    // Проверяет, под угрозой ли король, переданного цвета
    public boolean checkKing(char colorOfKing) {
        if (colorOfKing != 'b' && colorOfKing != 'w') return false;
        List<ChessFigure> threats = colorOfKing == 'b' ? piecesWhite : piecesBlack; // Создали лист "атакующих"
        char threatsColor = ChessFigure.giveAnotherColor(colorOfKing); // Цвет атакующих
        List<ChessFigure> defenders = new ArrayList<>(piecesWhite);  // Фигуры, которые будут мешать ходить
        defenders.addAll(piecesBlack); // Ходить будут мешать оба цвета
        // Для упрощения сразу обозначим защищающегося короля
        ChessFigure defendersKing = colorOfKing == 'b' ? piecesBlack.get(0) : piecesWhite.get(0);
        // Проверяем, может ли убить короля любой из атакующих
        for (ChessFigure figure : threats) {
            switch (figure.getFigureType()) {
                case PAWN:
                    // УСЛОВНОСТЬ: Я считаю, черные пешки едят вниз, а белые вверх
                    if (abs(defendersKing.getX() - figure.getX()) == 1 &&
                            ((threatsColor == 'b' && defendersKing.getY() - figure.getY() == 1) ||
                                    (threatsColor == 'w' && figure.getY() - defendersKing.getY() == 1)))
                        return true;
                    break;
                case BISHOP:
                    // Проверка, что может убить
                    if (onOneDiagonal(defendersKing, figure)) {
                        boolean canKill = true;
                        // Проверка, можно ли защитить
                        for (ChessFigure defender : defenders)
                            if (onOneDiagonal(defender, defendersKing) && onOneDiagonal(defender, figure) &&
                                    betweenFiguresY(defendersKing, figure, defender)) {
                                canKill = false;
                                break;
                            }
                        if (canKill) return true;
                    }
                    break;
                case ROOK:
                    // Проверка, можно ли убить
                    if (defendersKing.getX() == figure.getX()) {
                        boolean canKill = true;
                        // Проверка, можно ли защитить
                        for (ChessFigure defender : defenders)
                            if (defender.getX() == defendersKing.getX() &&
                                    betweenFiguresY(defendersKing, figure, defender)) {
                                canKill = false;
                                break;
                            }
                        if (canKill) return true;
                    } else if (defendersKing.getY() == figure.getY()) {
                        boolean canKill = true;
                        // Проверка, можно ли защитить
                        for (ChessFigure defender : defenders)
                            if (defender.getY() == defendersKing.getY() &&
                                    betweenFiguresX(defendersKing, figure, defender)) {
                                canKill = false;
                                break;
                            }
                        if (canKill) return true;
                    }
                    break;
                case KNIGHT:
                    int x = figure.getX(), y = figure.getY();
                    if (defendersKing.equals(x + 2, y + 1) || defendersKing.equals(x + 2, y - 1) ||
                            defendersKing.equals(x - 2, y + 1) || defendersKing.equals(x - 2, y - 1) ||
                            defendersKing.equals(x + 1, y + 2) || defendersKing.equals(x + 1, y - 2) ||
                            defendersKing.equals(x - 1, y + 2) || defendersKing.equals(x - 1, y - 2))
                        return true;
                    break;
                case QUEEN:
                    if (defendersKing.getX() == figure.getX()) {
                        boolean canKill = true;
                        // Проверка, можно ли защитить
                        for (ChessFigure defender : defenders)
                            if (defender.getX() == defendersKing.getX() &&
                                    betweenFiguresY(defendersKing, figure, defender)) {
                                canKill = false;
                                break;
                            }
                        if (canKill) return true;
                    } else if (defendersKing.getY() == figure.getY()) {
                        boolean canKill = true;
                        // Проверка, можно ли защитить
                        for (ChessFigure defender : defenders)
                            if (defender.getY() == defendersKing.getY() &&
                                    betweenFiguresX(defendersKing, figure, defender)) {
                                canKill = false;
                                break;
                            }
                        if (canKill) return true;
                    } else if (onOneDiagonal(defendersKing, figure)) {
                        boolean canKill = true;
                        // Проверка, можно ли защитить
                        for (ChessFigure defender : defenders)
                            if (onOneDiagonal(defender, defendersKing) && onOneDiagonal(defender, figure) &&
                                    betweenFiguresY(defendersKing, figure, defender)) {
                                canKill = false;
                                break;
                            }
                        if (canKill) return true;
                    }
                    break;
            }
        }
        return false;
    }
}
