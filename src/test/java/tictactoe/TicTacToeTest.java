package tictactoe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeTest {

    @Test
    void normalTests() {
        assertThrows(IllegalArgumentException.class, () -> new TicTacToe(0));

        TicTacToe game = new TicTacToe(3);

        assertFalse(game.addInField(Item.CROSS, -1, 1));
        assertTrue(game.addInField(Item.ZERO, 0, 0));
        assertTrue(game.cleanInField(0, 0));
        assertFalse(game.cleanInField(-1, 1));
    }

    @Test
    void hashAndEqualsTests() {
        TicTacToe game = new TicTacToe(4);
        game.addInField(Item.CROSS, 0, 0);

        TicTacToe game1 = new TicTacToe(4);
        game1.addInField(Item.CROSS, 0, 0);

        TicTacToe game2 = new TicTacToe(4);
        game2.addInField(Item.ZERO, 0, 0);

        TicTacToe game3 = new TicTacToe(10);
        game3.addInField(Item.CROSS, 0, 0);

        assertEquals(game, game1);
        assertNotEquals(game, game2);
        assertNotEquals(game, game3);

        assertEquals(game1.hashCode(), game.hashCode());
        //Тесты HashCode
    }

    @Test
    void findTheLongestTests() {
        TicTacToe game = new TicTacToe(14);

        //region Формирование поля
        for (int i = 4; i < 12; i++) {
            game.addInField(Item.CROSS, i, 3);
        }

        for (int i = 3; i < 7; i++) {
            game.addInField(Item.CROSS, i, 0);
        }

        for (int i = 1; i < 5; i++) {
            game.addInField(Item.CROSS, 0, i);
        }

        for (int i = 12; i < 14; i++) {
            game.addInField(Item.ZERO, 13, i);
        }

        game.addInField(Item.ZERO, 3, 5);
        game.addInField(Item.ZERO, 4, 6);
        game.addInField(Item.ZERO, 5, 7);
        game.addInField(Item.ZERO, 6, 8);
        game.addInField(Item.ZERO, 7, 9);
        //endregion

        assertEquals("---XXXX-------\n" +
                "X-------------\n" +
                "X-------------\n" +
                "X---XXXXXXXX--\n" +
                "X-------------\n" +
                "---O----------\n" +
                "----O---------\n" +
                "-----O--------\n" +
                "------O-------\n" +
                "-------O------\n" +
                "--------------\n" +
                "--------------\n" +
                "-------------O\n" +
                "-------------O\n", game.toString());

        assertEquals(8, game.findTheLongest(Item.CROSS).size());
        assertEquals(5, game.findTheLongest(Item.ZERO).size());
    }
}
