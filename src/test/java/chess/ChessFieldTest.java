package chess;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessFieldTest {
    @Test
    void constructorTest() {
        // Проверка, что создалось без прошествий
        ChessField field = new ChessField(new ChessFigure(1, 1, FigureType.KING, ChessFigure.Color.WHITE),
                new ChessFigure(1, 3, FigureType.KING, ChessFigure.Color.BLACK));
        ChessField field2 = new ChessField(new ChessFigure(1, 1, FigureType.KING, ChessFigure.Color.BLACK),
                new ChessFigure(1, 3, FigureType.KING, ChessFigure.Color.WHITE));
        // Одинаковые координаты королей
        assertThrows(IllegalArgumentException.class, () -> {
            ChessField chessField = new ChessField(new ChessFigure(1, 1, FigureType.KING, ChessFigure.Color.WHITE),
                    new ChessFigure(1, 1, FigureType.KING, ChessFigure.Color.BLACK));
        });
        // Одинаковые цвета королей
        assertThrows(IllegalArgumentException.class, () -> {
            ChessField chessField = new ChessField(new ChessFigure(1, 1, FigureType.KING, ChessFigure.Color.WHITE),
                    new ChessFigure(1, 3, FigureType.KING, ChessFigure.Color.WHITE));
        });
        // Короли слишком близко
        assertThrows(IllegalArgumentException.class, () -> {
            ChessField chessField = new ChessField(new ChessFigure(1, 1, FigureType.KING, ChessFigure.Color.WHITE),
                    new ChessFigure(1, 2, FigureType.KING, ChessFigure.Color.BLACK));
        });
        // Фигура не является королем
        assertThrows(IllegalArgumentException.class, () -> {
            ChessField chessField = new ChessField(new ChessFigure(1, 1, FigureType.KING, ChessFigure.Color.WHITE),
                    new ChessFigure(1, 3, FigureType.BISHOP, ChessFigure.Color.BLACK));
        });
    }

    @Test
    void addAndClearTest() {
        ChessField field = new ChessField(new ChessFigure(1, 1, FigureType.KING, ChessFigure.Color.WHITE),
                new ChessFigure(1, 8, FigureType.KING, ChessFigure.Color.BLACK));
        assertFalse(field.addSquare(new ChessFigure(1, 5, FigureType.KING, ChessFigure.Color.BLACK)));
        assertFalse(field.addSquare(new ChessFigure(1, 8, FigureType.BISHOP, ChessFigure.Color.BLACK)));
        for (int i = 1; i <= 8; i++) {
            assertTrue(field.addSquare(new ChessFigure(2, i, FigureType.PAWN, ChessFigure.Color.BLACK)));
        }
        assertFalse(field.addSquare(new ChessFigure(3, 8, FigureType.PAWN, ChessFigure.Color.BLACK)));
        assertTrue(field.clearSquare(2, 1));
        assertTrue(field.addSquare(new ChessFigure(3, 8, FigureType.PAWN, ChessFigure.Color.BLACK)));
        assertFalse(field.clearSquare(1, 1));
        assertFalse(field.addSquare(null));
    }

    @Test
    void moveFigure() {
        ChessField field = new ChessField(new ChessFigure(1, 1, FigureType.KING, ChessFigure.Color.WHITE),
                new ChessFigure(1, 8, FigureType.KING, ChessFigure.Color.BLACK));
        assertTrue(field.moveFigure(1, 8, 1, 7));
        assertFalse(field.moveFigure(1, 7, 1, 2));
        assertFalse(field.moveFigure(1, 7, 1, 1));
        assertFalse(field.moveFigure(9, 9, 1, 1));
        assertTrue(field.moveFigure(1, 7, 8, 8));
        assertTrue(field.addSquare(new ChessFigure(1, 2, FigureType.PAWN, ChessFigure.Color.WHITE)));
        assertFalse(field.moveFigure(8, 8, 1, 2));
        assertTrue(field.moveFigure(1, 2, 2, 3));
        assertTrue(field.moveFigure(8, 8, 2, 3));
        // Проверка, что counter пешек уменьшился
        for (int i = 1; i <= 8; i++) {
            assertTrue(field.addSquare(new ChessFigure(3, i, FigureType.PAWN, ChessFigure.Color.WHITE)));
        }
        assertTrue(field.addSquare(new ChessFigure(1, 5, FigureType.BISHOP, ChessFigure.Color.BLACK)));
        assertTrue(field.moveFigure(3, 3, 1, 5));
        assertFalse(field.moveFigure(1, 1, 2, 2));
    }

    @Test
    void pawnAttack() {
        ChessField field = new ChessField(new ChessFigure(1, 2, FigureType.KING, ChessFigure.Color.WHITE),
                new ChessFigure(4, 4, FigureType.KING, ChessFigure.Color.BLACK));
        // Тест, что короли не угрожают друг-другу
        assertFalse(field.checkKing(ChessFigure.Color.WHITE));
        assertFalse(field.checkKing(ChessFigure.Color.BLACK));
        // 2 белые пешки сверху и снизу черного короля
        assertTrue(field.addSquare(new ChessFigure(4, 5, FigureType.PAWN, ChessFigure.Color.WHITE)));
        assertTrue(field.addSquare(new ChessFigure(4, 3, FigureType.PAWN, ChessFigure.Color.WHITE)));
        assertFalse(field.checkKing(ChessFigure.Color.WHITE));
        assertFalse(field.checkKing(ChessFigure.Color.BLACK));
        // 2 белые пешки. Не могу съесть т.к. ходят только вверх, а король снизу
        assertTrue(field.addSquare(new ChessFigure(3, 3, FigureType.PAWN, ChessFigure.Color.WHITE)));
        assertTrue(field.addSquare(new ChessFigure(5, 3, FigureType.PAWN, ChessFigure.Color.WHITE)));
        assertFalse(field.checkKing(ChessFigure.Color.WHITE));
        assertFalse(field.checkKing(ChessFigure.Color.BLACK));
        // Пешка, которая может съесть черного короля
        assertTrue(field.addSquare(new ChessFigure(3, 5, FigureType.PAWN, ChessFigure.Color.WHITE)));
        assertFalse(field.checkKing(ChessFigure.Color.WHITE));
        assertTrue(field.checkKing(ChessFigure.Color.BLACK));
        // Тест, что пешка не будет есть весь ряд
        assertTrue(field.clearSquare(3, 5));
        assertTrue(field.addSquare(new ChessFigure(8, 5, FigureType.PAWN, ChessFigure.Color.WHITE)));
        assertFalse(field.checkKing(ChessFigure.Color.WHITE));
        assertFalse(field.checkKing(ChessFigure.Color.BLACK));
        // Проверяем, что белого короля тоже можно съесть
        assertTrue(field.addSquare(new ChessFigure(2, 1, FigureType.PAWN, ChessFigure.Color.BLACK)));
        assertTrue(field.checkKing(ChessFigure.Color.WHITE));
        assertFalse(field.checkKing(ChessFigure.Color.BLACK));
    }

    @Test
    void bishopAttack() {
        ChessField field = new ChessField(new ChessFigure(1, 2, FigureType.KING, ChessFigure.Color.BLACK),
                new ChessFigure(4, 4, FigureType.KING, ChessFigure.Color.WHITE));
        // Тест, что короли не угрожают друг-другу
        assertFalse(field.checkKing(ChessFigure.Color.WHITE));
        assertFalse(field.checkKing(ChessFigure.Color.BLACK));
        // Угрожаем белому королю
        assertTrue(field.addSquare(new ChessFigure(6, 6, FigureType.BISHOP, ChessFigure.Color.BLACK)));
        assertTrue(field.checkKing(ChessFigure.Color.WHITE));
        assertFalse(field.checkKing(ChessFigure.Color.BLACK));
        // Блокируем слона
        assertTrue(field.addSquare(new ChessFigure(5, 5, FigureType.BISHOP, ChessFigure.Color.WHITE)));
        assertFalse(field.checkKing(ChessFigure.Color.WHITE));
        assertFalse(field.checkKing(ChessFigure.Color.BLACK));
        // Создадим "Препятствие" "за" слоном
        assertTrue(field.clearSquare(5, 5));
        assertTrue(field.addSquare(new ChessFigure(7, 7, FigureType.BISHOP, ChessFigure.Color.WHITE)));
        assertTrue(field.checkKing(ChessFigure.Color.WHITE));
        assertFalse(field.checkKing(ChessFigure.Color.BLACK));
        // Просто создаем слона, того же цвета, что и король, которому мы угрожаем (черному)
        assertTrue(field.addSquare(new ChessFigure(6, 7, FigureType.BISHOP, ChessFigure.Color.BLACK)));
        assertTrue(field.checkKing(ChessFigure.Color.WHITE));
        assertFalse(field.checkKing(ChessFigure.Color.BLACK));
    }

    @Test
    void rookAttack() {
        ChessField field = new ChessField(new ChessFigure(1, 1, FigureType.KING, ChessFigure.Color.BLACK),
                new ChessFigure(4, 4, FigureType.KING, ChessFigure.Color.WHITE));
        // Тест, что короли не угрожают друг-другу
        assertFalse(field.checkKing(ChessFigure.Color.WHITE));
        assertFalse(field.checkKing(ChessFigure.Color.BLACK));
        // Угрожаем белому королю (по Y)
        assertTrue(field.addSquare(new ChessFigure(4, 8, FigureType.ROOK, ChessFigure.Color.BLACK)));
        assertTrue(field.checkKing(ChessFigure.Color.WHITE));
        assertFalse(field.checkKing(ChessFigure.Color.BLACK));
        // Блокируем ладью
        assertTrue(field.addSquare(new ChessFigure(4, 5, FigureType.ROOK, ChessFigure.Color.WHITE)));
        assertFalse(field.checkKing(ChessFigure.Color.WHITE));
        assertFalse(field.checkKing(ChessFigure.Color.BLACK));
        // Угрожаем белому королю (По X)
        assertTrue(field.addSquare(new ChessFigure(8, 4, FigureType.ROOK, ChessFigure.Color.BLACK)));
        assertTrue(field.checkKing(ChessFigure.Color.WHITE));
        assertFalse(field.checkKing(ChessFigure.Color.BLACK));
        // Блокируем ладью
        assertTrue(field.addSquare(new ChessFigure(5, 4, FigureType.ROOK, ChessFigure.Color.WHITE)));
        assertFalse(field.checkKing(ChessFigure.Color.WHITE));
        assertFalse(field.checkKing(ChessFigure.Color.BLACK));
        // Создадим "Препятствие" "за" ладьей
        assertTrue(field.addSquare(new ChessFigure(4, 2, FigureType.ROOK, ChessFigure.Color.WHITE)));
        assertTrue(field.addSquare(new ChessFigure(4, 1, FigureType.ROOK, ChessFigure.Color.BLACK)));
        assertFalse(field.checkKing(ChessFigure.Color.WHITE));
        assertFalse(field.checkKing(ChessFigure.Color.BLACK));
    }

    @Test
    void knightAttack() {
        ChessField field = new ChessField(new ChessFigure(1, 1, FigureType.KING, ChessFigure.Color.BLACK),
                new ChessFigure(4, 4, FigureType.KING, ChessFigure.Color.WHITE));
        // Тест, что короли не угрожают друг-другу
        assertFalse(field.checkKing(ChessFigure.Color.WHITE));
        assertFalse(field.checkKing(ChessFigure.Color.BLACK));
        // Тест нескольких способов атаковать белового короля
        // +2 +1
        assertTrue(field.addSquare(new ChessFigure(6, 5, FigureType.KNIGHT, ChessFigure.Color.BLACK)));
        assertTrue(field.checkKing(ChessFigure.Color.WHITE));
        assertFalse(field.checkKing(ChessFigure.Color.BLACK));
        // +2 -1
        assertTrue(field.clearSquare(6, 5));
        assertTrue(field.addSquare(new ChessFigure(6, 3, FigureType.KNIGHT, ChessFigure.Color.BLACK)));
        assertTrue(field.checkKing(ChessFigure.Color.WHITE));
        assertFalse(field.checkKing(ChessFigure.Color.BLACK));
        // -2 +1
        assertTrue(field.clearSquare(6, 3));
        assertTrue(field.addSquare(new ChessFigure(2, 5, FigureType.KNIGHT, ChessFigure.Color.BLACK)));
        assertTrue(field.checkKing(ChessFigure.Color.WHITE));
        assertFalse(field.checkKing(ChessFigure.Color.BLACK));
        // -2 -1
        assertTrue(field.clearSquare(2, 5));
        assertTrue(field.addSquare(new ChessFigure(2, 3, FigureType.KNIGHT, ChessFigure.Color.BLACK)));
        assertTrue(field.checkKing(ChessFigure.Color.WHITE));
        assertFalse(field.checkKing(ChessFigure.Color.BLACK));
        // Тест, что не будет бить прямо
        assertTrue(field.clearSquare(2, 3));
        assertTrue(field.addSquare(new ChessFigure(2, 4, FigureType.KNIGHT, ChessFigure.Color.BLACK)));
        assertFalse(field.checkKing(ChessFigure.Color.WHITE));
        assertFalse(field.checkKing(ChessFigure.Color.BLACK));
    }

    @Test
    void queenAttack() {
        ChessField field = new ChessField(new ChessFigure(1, 2, FigureType.KING, ChessFigure.Color.BLACK),
                new ChessFigure(4, 4, FigureType.KING, ChessFigure.Color.WHITE));
        // Тест, что короли не угрожают друг-другу
        assertFalse(field.checkKing(ChessFigure.Color.WHITE));
        assertFalse(field.checkKing(ChessFigure.Color.BLACK));
        // Тест удара "наискосок"
        assertTrue(field.addSquare(new ChessFigure(2, 2, FigureType.QUEEN, ChessFigure.Color.BLACK)));
        assertTrue(field.checkKing(ChessFigure.Color.WHITE));
        assertFalse(field.checkKing(ChessFigure.Color.BLACK));
        // Тест удара "прямо"
        assertTrue(field.addSquare(new ChessFigure(1, 5, FigureType.QUEEN, ChessFigure.Color.WHITE)));
        assertTrue(field.checkKing(ChessFigure.Color.WHITE));
        assertTrue(field.checkKing(ChessFigure.Color.BLACK));
        // Нет смысла продолжать тесты с королевой т.к. остальное проверено в rookAttack и bishopAttack
    }
}
