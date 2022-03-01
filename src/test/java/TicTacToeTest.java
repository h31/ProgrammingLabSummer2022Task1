import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeTest {

    @Test
    void NormalTests() {
        TicTacToe game = new TicTacToe(3);

        assertFalse(game.AddInField(true, -1, 1));
        assertTrue(game.AddInField(false, 0, 0));
        assertTrue(game.CleanInField(0, 0));
    }

    @Test
    void FindTheLongestTest() {
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
        //endregion

        assertEquals(8, game.FindTheLongest(true).size());
        assertEquals(2, game.FindTheLongest(false).size());
    }
}
