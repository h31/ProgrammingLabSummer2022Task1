import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeTest {

    @Test
    void NormalTests() {
        assertThrows(IllegalArgumentException.class, () -> {
            TicTacToe game = new TicTacToe(0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            TicTacToe game = new TicTacToe(Integer.MAX_VALUE);
        });

        TicTacToe game = new TicTacToe(3);

        assertFalse(game.AddInField(true, -1, 1));
        assertTrue(game.AddInField(false, 0, 0));
        assertTrue(game.CleanInField(0, 0));
        assertFalse(game.CleanInField(-1, 1));
    }

    @Test
    void EqualsTests() {
        TicTacToe game = new TicTacToe(4);
        game.AddInField(true, 0, 0);

        TicTacToe game1 = new TicTacToe(4);
        game1.AddInField(true, 0, 0);

        TicTacToe game2 = new TicTacToe(4);
        game2.AddInField(false, 0, 0);

        TicTacToe game3 = new TicTacToe(10);
        game3.AddInField(true, 0, 0);

        assertTrue(game.equals(game1));
        assertFalse(game.equals(game2));
        assertFalse(game.equals(game3));
    }

    @Test
    void FindTheLongestTests() {
        TicTacToe game = new TicTacToe(14);

        //region Формирование поля
        for (int i = 4; i < 12; i++) {
            game.AddInField(true, i, 3);
        }

        for (int i = 3; i < 7; i++) {
            game.AddInField(true, i, 0);
        }

        for (int i = 1; i < 5; i++) {
            game.AddInField(true, 0, i);
        }

        for (int i = 12; i < 14; i++) {
            game.AddInField(false, 13, i);
        }

        game.AddInField(false, 3, 5);
        game.AddInField(false, 4, 6);
        game.AddInField(false, 5, 7);
        game.AddInField(false, 6, 8);
        game.AddInField(false, 7, 9);
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

        assertEquals(8, game.FindTheLongest(true).size());
        assertEquals(5, game.FindTheLongest(false).size());
    }
}
