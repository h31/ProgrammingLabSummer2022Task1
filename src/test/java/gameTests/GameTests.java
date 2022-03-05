package gameTests;

import gameTicTacToe.GameTicTacToe;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class GameTests {

    @Test
    void GameTicTacToeTest() {
        GameTicTacToe game1 = new GameTicTacToe(3);
        GameTicTacToe game2 = new GameTicTacToe(1000);

        assertThrows(IllegalArgumentException.class, () -> {
            GameTicTacToe game = new GameTicTacToe(Integer.MIN_VALUE);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            GameTicTacToe game = new GameTicTacToe(0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            GameTicTacToe game = new GameTicTacToe(2);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            GameTicTacToe game = new GameTicTacToe(10000);
        });
    }

    @Test
    void getBoxValue() {
        GameTicTacToe game = new GameTicTacToe(100);
        game.setCross(10,90);
        game.setZero(80, 20);

        assertEquals('X', game.getBoxValue(10, 90));
        assertEquals('O', game.getBoxValue(80, 20));
        assertEquals('\0', game.getBoxValue(50, 50));

        assertThrows(IllegalArgumentException.class, () -> {
            game.getBoxValue(Integer.MIN_VALUE, Integer.MAX_VALUE);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            game.getBoxValue(-1, -99);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            game.getBoxValue(-1, 99);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            game.getBoxValue(10, 110);
        });
    }

    @Test
    void setCross() {
        GameTicTacToe game = new GameTicTacToe(7);

        game.setCross(1,1);
        assertThrows(IllegalArgumentException.class, () -> {
            game.setCross(1,1);
        });

        game.setZero(2,2);
        assertThrows(IllegalArgumentException.class, () -> {
            game.setCross(2,2);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            game.setCross(-1,4);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            game.setCross(0,Integer.MAX_VALUE);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            game.setCross(-1,Integer.MIN_VALUE);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            game.setCross(6, -100);
        });
    }

    @Test
    void setZero() {
        GameTicTacToe game = new GameTicTacToe(10);

        game.setZero(0,0);
        assertThrows(IllegalArgumentException.class, () -> {
            game.setCross(0,0);
        });

        game.setCross(3,3);
        assertThrows(IllegalArgumentException.class, () -> {
            game.setCross(3,3);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            game.setZero(Integer.MIN_VALUE,0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            game.setZero(-10,10);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            game.setZero(0,11);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            game.setZero(-10,Integer.MAX_VALUE);
        });
    }

    @Test
    void isBoxEmpty() {
        GameTicTacToe game = new GameTicTacToe(5);
        game.setCross(1,2);
        game.setZero(4,3);

        assertFalse(game.isBoxEmpty(1, 2));
        assertFalse(game.isBoxEmpty(4,3));
        assertTrue(game.isBoxEmpty(4,4));

        assertThrows(IllegalArgumentException.class, () -> {
            game.isBoxEmpty(-1,2);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            game.isBoxEmpty(1, Integer.MIN_VALUE);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            game.isBoxEmpty(3, Integer.MAX_VALUE);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            game.isBoxEmpty(-100, 100);
        });
    }

    @Test
    void clearingBox() {
        GameTicTacToe game = new GameTicTacToe(10);
        game.setCross(3,3);
        game.setZero(6,6);

        game.clearingBox(3,3);
        game.clearingBox(6,6);
        game.clearingBox(9,9);
        assertEquals('\0', game.getBoxValue(3,3));
        assertEquals('\0', game.getBoxValue(6,6));
        assertEquals('\0', game.getBoxValue(9,9));

        assertThrows(IllegalArgumentException.class, () -> {
            game.setZero(Integer.MIN_VALUE,3);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            game.setZero(Integer.MIN_VALUE,Integer.MAX_VALUE);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            game.setZero(4,10);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            game.setZero(100,100);
        });
    }

    // Testing both getSetOfCrossesValue() and getSetOfZerosValue()
    @Test
    void getHashSetsValues() {
        GameTicTacToe game = new GameTicTacToe(50);

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            game.getSetOfCrossesValue(0);
        });
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            game.getSetOfZerosValue(0);
        });

        game.setCross(25, 2);
        game.setZero(0,49);

        assertEquals(25 * 50 + 2, game.getSetOfCrossesValue(0));
        assertEquals(49, game.getSetOfZerosValue(0));

        game.clearingBox(25,2);
        game.clearingBox(0,49);

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            game.getSetOfCrossesValue(0);
        });
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            game.getSetOfZerosValue(0);
        });
    }

    @Test
    void findLongestLineOfSigns() {
        GameTicTacToe game1 = new GameTicTacToe(10);
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j)
                game1.setZero(i, j);
        }
        assertEquals(10, game1.findLongestLineOfSigns('O'));

        GameTicTacToe game2 = new GameTicTacToe(50);
        for (int i = 20; i < 40; ++i) {
            for (int j = 40; j < 50; ++j)
                game2.setCross(i, j);
        }
        assertEquals(20, game2.findLongestLineOfSigns('X'));

        GameTicTacToe game3 = new GameTicTacToe(6);
        {
            game3.setCross(0, 0);
            game3.setCross(1, 1);
            game3.setCross(2, 2);
            game3.setCross(3, 2);
            game3.setCross(4, 2);
            game3.setCross(5, 2);
            game3.setCross(1, 4);
            game3.setCross(4, 3);
        }
        assertEquals(4, game3.findLongestLineOfSigns('X'));

        GameTicTacToe game4 = new GameTicTacToe(8);
        for (int i = 0; i < 8; i+=2) {
            for (int j = 0; j < 8; j+=2) {
                game4.setZero(i, j);
            }
        }
        assertEquals(1, game4.findLongestLineOfSigns('O'));
        game4.setZero(1,1);
        assertEquals(3, game4.findLongestLineOfSigns('O'));
        game4.setZero(3,3);
        assertEquals(5, game4.findLongestLineOfSigns('O'));


        GameTicTacToe game = new GameTicTacToe(3);
        assertEquals(0, game.findLongestLineOfSigns('O'));
        assertEquals(0, game.findLongestLineOfSigns('X'));

        assertThrows(IllegalArgumentException.class, () -> {
            game.findLongestLineOfSigns('d');
        });
        assertThrows(IllegalArgumentException.class, () -> {
            game.findLongestLineOfSigns('\0');
        });
    }
}
