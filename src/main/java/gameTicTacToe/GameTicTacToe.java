package gameTicTacToe;

import java.util.*;


public class GameTicTacToe {

    // Special vector for finding the longest lines of crosses & zeros
    // by 8 directions
    private static final int[][] vec = new int[][]{
            {-1, 0}, {1, 0}, {0, -1}, {0, 1},
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
    };

    private final int sizeField;
    private Marks[][] gameField;

    // There are two HashSets that will store info of 'O' & 'X' location
    private HashSet<Integer> crosses;
    private HashSet<Integer> zeros;


    // Constructor that builds gameField and creates two HashSets
    public GameTicTacToe(int sizeField) {
        if (sizeField < 3)
            throw new IllegalArgumentException();
        this.sizeField = sizeField;
        gameField = new Marks[sizeField][sizeField];
        zeros = new HashSet<>(sizeField * sizeField);
        crosses = new HashSet<>(sizeField * sizeField);
    }


    // Getter for size of field
    public int getSizeField() {
        return sizeField;
    }


    // Getter for value of box: if it's empty - \0, else - O or X
    public Marks getBoxValue(int line, int column) {
        if (line < 0 || line >= sizeField || column < 0 || column >= sizeField) {
            throw new IllegalArgumentException();
        }
        return gameField[line][column];
    }


    // THESE TWO GETTERS JUST FOR TESTS
    // Getter for setOfZeros
    public HashSet<Integer> getZeros() {
        return zeros;
    }


    // Getter for setOfCrosses
    public HashSet<Integer> getCrosses() {
        return crosses;
    }


    // This checks emptiness of box in field
    public boolean isBoxEmpty(int line, int column) {
        if (line < 0 || line >= sizeField || column < 0 || column >= sizeField) {
            throw new IllegalArgumentException();
        } else return gameField[line][column] == null;
    }


    // There is using of set.add
    // Set 'O' into box with checking out size of field & empty space in field
    public void setZero(int line, int column) {
        if (crosses.contains(line * sizeField + column)
                || !zeros.add(line * sizeField + column) || line < 0
                || line >= sizeField || column < 0 || column >= sizeField) {
            throw new IllegalArgumentException();
        }
        gameField[line][column] = Marks.ZERO;
    }


    // Set 'X' into box with checking out size of field & empty space in field
    public void setCross(int line, int column) {
        if (zeros.contains(line * sizeField + column)
                || !crosses.add(line * sizeField + column) || line < 0
                || line >= sizeField || column < 0 || column >= sizeField) {
            throw new IllegalArgumentException();
        }
        gameField[line][column] = Marks.CROSS;
    }


    // Clearing the box
    public void clearBox(int line, int column) {
        if (!isBoxEmpty(line, column)
                && (zeros.remove(line * sizeField + column)
                || crosses.remove(line * sizeField + column))) {
            gameField[line][column] = null;
        }
    }


    // Finding the longest line of crosses and the line of zeros
    public int findLongestLineOfSigns(Marks sign) {

        int maximumLength = 0;
        HashSet<Integer> setOfSigns = (sign == Marks.ZERO) ? zeros : crosses;

        for (int position : setOfSigns) {

            // Coordinates of current position in set of crosses
            int signLine = position / sizeField;
            int signColumn = position % sizeField;

            // This one for counting signs from current position
            int countSigns;

            // Main part - Finding with using vector of directions
            // Variables 'curLine' and 'curColumn' are
            // for moving on field while looking for length
            for (int[] pair : vec) {
                int index = 0;
                countSigns = 1;
                while (true) {
                    ++index;
                    int curLine = signLine + pair[0] * index;
                    if (curLine < 0 || curLine > sizeField - 1) {
                        if (maximumLength < countSigns) {
                            maximumLength = countSigns;
                        }
                        break;
                    }
                    int curColumn = signColumn + pair[1] * index;
                    if (curColumn < 0 || curColumn > sizeField - 1) {
                        if (maximumLength < countSigns) {
                            maximumLength = countSigns;
                        }
                        break;
                    }
                    if (gameField[curLine][curColumn] == sign) ++countSigns;
                    else {
                        if (maximumLength < countSigns) {
                            maximumLength = countSigns;
                        }
                        break;
                    }
                }
            }
        }

        return maximumLength;
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(sizeField * sizeField);

        for (int i = 0; i < sizeField; ++i) {
            for (int j = 0; j < sizeField; ++j) {
                if (gameField[i][j] == Marks.ZERO) {
                    str.append('O');
                } else if (gameField[i][j] == Marks.CROSS) {
                    str.append('X');
                } else {
                    str.append('.');
                }
            }
            str.append('*');
        }

        return str.toString();
    }


    // HashCode() Overriding
    @Override
    public int hashCode() {
        return Objects.hash(sizeField, Arrays.deepHashCode(gameField), crosses, zeros);
    }


    // Equals() Overriding
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        GameTicTacToe game = (GameTicTacToe) o;
        return this.sizeField == game.sizeField
                && Arrays.deepEquals(this.gameField, game.gameField)
                && this.zeros == game.zeros
                && this.crosses == game.crosses;
    }
}