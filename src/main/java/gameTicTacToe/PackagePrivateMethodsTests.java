package gameTicTacToe;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class PackagePrivateMethodsTests {
    // Testing both getSetOfCrossesValue() and getSetOfZerosValue()
    @Test
    void getHashSetsValues() {
        GameTicTacToe game = new GameTicTacToe(50);

        assertTrue(game.getCrosses().isEmpty());
        assertTrue(game.getZeros().isEmpty());

        game.setCross(25, 2);
        game.setZero(0, 49);

        assertTrue(game.getCrosses().contains(25 * 50 + 2));
        assertTrue(game.getZeros().contains(49));

        game.clearBox(25, 2);
        game.clearBox(0, 49);

        assertTrue(game.getCrosses().isEmpty());
        assertTrue(game.getZeros().isEmpty());
    }
}
