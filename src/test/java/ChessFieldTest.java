import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessFieldTest {
    @Test
    void constructorTest() {
        // Проверка, что создалось без проишествий
        ChessField field = new ChessField(new ChessFigure(1, 1, FigureType.KING, 'w'),
                new ChessFigure(1, 3, FigureType.KING, 'b'));
        // Одинаковые координаты королей
        assertThrows(IllegalArgumentException.class, () -> {
            ChessField chessField = new ChessField(new ChessFigure(1, 1, FigureType.KING, 'w'),
                    new ChessFigure(1, 1, FigureType.KING, 'b'));
        });
        // Одинаковые цвета королей
        assertThrows(IllegalArgumentException.class, () -> {
            ChessField chessField = new ChessField(new ChessFigure(1, 1, FigureType.KING, 'w'),
                    new ChessFigure(1, 3, FigureType.KING, 'w'));
        });
        // Короли слишком близко
        assertThrows(IllegalArgumentException.class, () -> {
            ChessField chessField = new ChessField(new ChessFigure(1, 1, FigureType.KING, 'w'),
                    new ChessFigure(1, 2, FigureType.KING, 'b'));
        });
        // Фигура не является королем
        assertThrows(IllegalArgumentException.class, () -> {
            ChessField chessField = new ChessField(new ChessFigure(1, 1, FigureType.KING, 'w'),
                    new ChessFigure(1, 3, FigureType.BISHOP, 'b'));
        });
    }

    @Test
    void addAndClearTest() {
        ChessField field = new ChessField(new ChessFigure(1, 1, FigureType.KING, 'w'),
                new ChessFigure(1, 8, FigureType.KING, 'b'));
        assertFalse(field.addCage(new ChessFigure(1, 5, FigureType.KING, 'b')));
        assertFalse(field.addCage(new ChessFigure(1, 8, FigureType.BISHOP, 'b')));
        for (int i = 1; i <= 8; i++) {
            assertTrue(field.addCage(new ChessFigure(2, i, FigureType.PAWN, 'b')));
        }
        assertFalse(field.addCage(new ChessFigure(3, 8, FigureType.PAWN, 'b')));
        assertTrue(field.clearCage(2, 1));
        assertTrue(field.addCage(new ChessFigure(3, 8, FigureType.PAWN, 'b')));
        assertFalse(field.clearCage(1, 1));
        assertFalse(field.addCage(null));
    }

    @Test
    void moveFigure() {
        ChessField field = new ChessField(new ChessFigure(1, 1, FigureType.KING, 'w'),
                new ChessFigure(1, 8, FigureType.KING, 'b'));
        assertTrue(field.moveFigure(1, 8, 1, 7));
        assertFalse(field.moveFigure(1, 7, 1, 2));
        assertFalse(field.moveFigure(1, 7, 1, 1));
        assertFalse(field.moveFigure(9, 9, 1, 1));
        assertTrue(field.moveFigure(1, 7, 8, 8));
        assertTrue(field.addCage(new ChessFigure(1, 2, FigureType.PAWN, 'w')));
        assertFalse(field.moveFigure(8, 8, 1, 2));
        assertTrue(field.moveFigure(1, 2, 2, 3));
        assertTrue(field.moveFigure(8, 8, 2, 3));
        // Проверка, что counter пешек уменьшился
        for (int i = 1; i <= 8; i++) {
            assertTrue(field.addCage(new ChessFigure(3, i, FigureType.PAWN, 'w')));
        }
    }
}