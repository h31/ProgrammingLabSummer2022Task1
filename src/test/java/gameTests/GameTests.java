package gameTests;

import gameTicTacToe.Cell;
import gameTicTacToe.GameTicTacToe;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class GameTests {

    // Tests for constructor
    @Test
    void GameTicTacToeTest() {
        new GameTicTacToe(3);
        new GameTicTacToe(1000);

        assertThrows(IllegalArgumentException.class, () -> {
            GameTicTacToe game = new GameTicTacToe(Integer.MIN_VALUE);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            GameTicTacToe game = new GameTicTacToe(0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            GameTicTacToe game = new GameTicTacToe(2);
        });
    }

    // There is test for getSizeField() too at the fifth line of test method
    @Test
    void getBoxValue() {
        GameTicTacToe game = new GameTicTacToe(100);
        game.setCross(10, 90);
        game.setZero(80, 20);

        assertEquals(100, game.getSizeField());

        assertEquals(Cell.CROSS, game.getBoxValue(10, 90));
        assertEquals(Cell.ZERO, game.getBoxValue(80, 20));
        assertNull(game.getBoxValue(50, 50));

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

        game.setCross(1, 1);
        assertThrows(IllegalArgumentException.class, () -> {
            game.setCross(1, 1);
        });
        game.setZero(2, 2);
        assertThrows(IllegalArgumentException.class, () -> {
            game.setCross(2, 2);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            game.setCross(-1, 4);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            game.setCross(0, Integer.MAX_VALUE);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            game.setCross(-1, Integer.MIN_VALUE);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            game.setCross(6, -100);
        });
    }

    @Test
    void setZero() {
        GameTicTacToe game = new GameTicTacToe(10);

        game.setZero(0, 0);
        assertThrows(IllegalArgumentException.class, () -> {
            game.setCross(0, 0);
        });

        game.setCross(3, 3);
        assertThrows(IllegalArgumentException.class, () -> {
            game.setCross(3, 3);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            game.setZero(Integer.MIN_VALUE, 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            game.setZero(-10, 10);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            game.setZero(0, 11);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            game.setZero(-10, Integer.MAX_VALUE);
        });
    }

    @Test
    void isBoxEmpty() {
        GameTicTacToe game = new GameTicTacToe(5);
        game.setCross(1, 2);
        game.setZero(4, 3);

        assertFalse(game.isBoxEmpty(1, 2));
        assertFalse(game.isBoxEmpty(4, 3));
        assertTrue(game.isBoxEmpty(4, 4));

        assertThrows(IllegalArgumentException.class, () -> {
            game.isBoxEmpty(-1, 2);
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
    void clearBox() {
        GameTicTacToe game = new GameTicTacToe(10);
        game.setCross(3, 3);
        game.setZero(6, 6);

        game.clearBox(3, 3);
        game.clearBox(6, 6);
        game.clearBox(9, 9);
        assertNull(game.getBoxValue(3, 3));
        assertNull(game.getBoxValue(6, 6));
        assertNull(game.getBoxValue(9, 9));

        assertThrows(IllegalArgumentException.class, () -> {
            game.setZero(Integer.MIN_VALUE, 3);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            game.setZero(Integer.MIN_VALUE, Integer.MAX_VALUE);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            game.setZero(4, 10);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            game.setZero(100, 100);
        });
    }

    // There is toString(), hashCode() and equals() testing
    @Test
    void findLongestLineOfSigns() {

        GameTicTacToe game1 = new GameTicTacToe(10);
        for (int i = 0; i < 10; ++i) {
            game1.setZero(i, i);
        }
        assertEquals(10, game1.findLongestLineOfSigns(Cell.ZERO));

        GameTicTacToe game2 = new GameTicTacToe(50);
        for (int i = 20; i < 40; ++i) {
            for (int j = 40; j < 50; ++j)
                game2.setCross(i, j);
        }
        assertEquals(20, game2.findLongestLineOfSigns(Cell.CROSS));


        // toString() tests
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
            game3.setZero(5,5);
        }
        assertEquals(4, game3.findLongestLineOfSigns(Cell.CROSS));

        GameTicTacToe game4 = new GameTicTacToe(8);
        for (int i = 0; i < 8; i += 2) {
            for (int j = 0; j < 8; j += 2) {
                game4.setZero(i, j);
            }
        }
        assertEquals(1, game4.findLongestLineOfSigns(Cell.ZERO));
        game4.setZero(1, 1);
        assertEquals(3, game4.findLongestLineOfSigns(Cell.ZERO));
        game4.setZero(3, 3);
        assertEquals(5, game4.findLongestLineOfSigns(Cell.ZERO));


        GameTicTacToe game = new GameTicTacToe(3);
        assertEquals(0, game.findLongestLineOfSigns(Cell.ZERO));
        assertEquals(0, game.findLongestLineOfSigns(Cell.CROSS));



        // equals() tests
        GameTicTacToe game5 = new GameTicTacToe(10);
        for (int i = 0; i < 10; ++i) {
            game5.setZero(i, i);
        }
        assertFalse(game5.equals(game1));
        game5.setZero(3,5);
        game5.setCross(9,5);
        game5.setZero(1,5);
        assertFalse(game5.equals(game1));
        assertFalse(game5.equals(new Object()));
        GameTicTacToe game6 = null;
        assertFalse(game5.equals(game6));
        assertFalse(game3.equals(game4));
        assertFalse(game4.equals(game1));

        GameTicTacToe game7 = game1;
        assertTrue(game7.equals(game1));



        // hashCode() tests
        assertNotEquals(game1.hashCode(), game2.hashCode());
        assertNotEquals(game2.hashCode(), game3.hashCode());
        assertNotEquals(game3.hashCode(), game4.hashCode());
        assertNotEquals(game4.hashCode(), game1.hashCode());



        // toString() tests
        assertEquals(
                "X.....\n"
                        + ".X..X.\n"
                        + "..X...\n"
                        + "..X...\n"
                        + "..XX..\n"
                        + "..X..O\n", game3.toString()
        );
        assertEquals(
                "O.........\n"
                        + ".O........\n"
                        + "..O.......\n"
                        + "...O......\n"
                        + "....O.....\n"
                        + ".....O....\n"
                        + "......O...\n"
                        + ".......O..\n"
                        + "........O.\n"
                        + ".........O\n", game1.toString()
        );
        assertEquals(
                "O.........\n"
                        + ".O...O....\n"
                        + "..O.......\n"
                        + "...O.O....\n"
                        + "....O.....\n"
                        + ".....O....\n"
                        + "......O...\n"
                        + ".......O..\n"
                        + "........O.\n"
                        + ".....X...O\n", game5.toString()
        );
    }

}
