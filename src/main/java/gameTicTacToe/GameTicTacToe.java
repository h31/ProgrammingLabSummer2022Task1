package gameTicTacToe;

import java.util.HashSet;
import java.util.Objects;



public class GameTicTacToe {
    // Special vector for finding the longest lines of crosses & zeros
    // by 8 directions
    private static final int[][] vec = new int[][] {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1},
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
    };

    private final int sizeField;
    private char[][] gameField;

    // There are two HashSets that will store info of 'O' & 'X' location
    private HashSet<Integer> setOfCrosses;
    private HashSet<Integer> setOfZeros;




    // Constructor that builds gameField and creates two HashSets
    public GameTicTacToe(int sizeField) {
        if (sizeField < 3  || sizeField > 1000) throw new IllegalArgumentException();
        this.sizeField = sizeField;
        gameField = new char[sizeField][sizeField];
        setOfZeros = new HashSet<>(sizeField * sizeField);
        setOfCrosses = new HashSet<>(sizeField * sizeField);
    }


    // Getter for size of field
    public int getSizeField() { return sizeField; }


    // Getter for value of box: if it's empty - \0, else - O or X
    public char getBoxValue(int line, int column) {
        if (line < 0 || line >= sizeField || column < 0 || column >= sizeField)
            throw new IllegalArgumentException();
        else return gameField[line][column];
    }


    // THESE TWO GETTERS JUST FOR TESTS
    // Getter for setOfZeros
    public int getSetOfZerosValue(int num) {
        return (int) setOfZeros.toArray()[num];
    }


    // Getter for setOfCrosses
    public int getSetOfCrossesValue(int num) {
        return (int) setOfCrosses.toArray()[num];
    }


    // This checks emptiness of box in field
    public boolean isBoxEmpty(int line, int column) {
        if (line < 0 || line >= sizeField || column < 0 || column >= sizeField)
            throw new IllegalArgumentException();
        else return gameField[line][column] == '\0';
    }


    // Parameter zero have to include uppercase letter 'O', not digit '0'
    // Set 'O' into box with checking out size of field & empty space in field
    public void setZero(int line, int column) {
        if (setOfCrosses.contains(line * sizeField + column)
                || !setOfZeros.add(line * sizeField + column) || line < 0
                || line >= sizeField || column < 0 || column >= sizeField)
            throw new IllegalArgumentException();
        gameField[line][column] = 'O';
    }


    // Set 'X' into box with checking out size of field & empty space in field
    public void setCross(int line, int column) {
        if (setOfZeros.contains(line * sizeField + column)
                || !setOfCrosses.add(line * sizeField + column) || line < 0
                || line >= sizeField || column < 0 || column >= sizeField)
            throw new IllegalArgumentException();
        gameField[line][column] = 'X';
    }


    // Clearing the box
    public void clearingBox(int line, int column) {
        if (!isBoxEmpty(line, column)
                && (setOfZeros.remove(line * sizeField + column)
                || setOfCrosses.remove(line * sizeField + column)))
            gameField[line][column] = '\0';
    }


    // Finding the longest line of crosses and the line of zeros
    public int findLongestLineOfSigns(char sign) {
        if (sign != 'O' && sign != 'X') throw new IllegalArgumentException();

        int maximumLength = 0;
        HashSet<Integer> setOfSigns;
        if (sign == 'O') setOfSigns = setOfZeros;
        else setOfSigns = setOfCrosses;

        for (int position : setOfSigns) {

            // Coordinates of current position in set of crosses
            int signLine = position / sizeField;
            int signColumn = position % sizeField;

            // Variables for moving on field while looking for length
            int curLine, curColumn;

            // This one for counting signs from current position
            int countSigns;

            // Main part - Finding with using vector of directions
            for (int[] pair : vec) {
                int index = 0;
                countSigns = 1;
                while (true) {
                    ++index;
                    curLine = signLine + pair[0] * index;
                    if (curLine < 0 || curLine > sizeField - 1) {
                        if (maximumLength < countSigns)
                            maximumLength = countSigns;
                        break;
                    }
                    curColumn = signColumn + pair[1] * index;
                    if (curColumn < 0 || curColumn > sizeField - 1) {
                        if (maximumLength < countSigns)
                            maximumLength = countSigns;
                        break;
                    }
                    if (gameField[curLine][curColumn] == sign) ++countSigns;
                    else {
                        if (maximumLength < countSigns)
                            maximumLength = countSigns;
                        break;
                    }
                }
            }
        }

        return maximumLength;
    }


    // HashCode() Overriding
    @Override
    public int hashCode() {
        return Objects.hash(sizeField, gameField, setOfCrosses, setOfZeros);
    }


    // Equals() Overriding
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        GameTicTacToe game = (GameTicTacToe) o;
        return this.sizeField == game.sizeField
                && this.gameField == game.gameField
                && this.setOfZeros == game.setOfZeros
                && this.setOfCrosses == game.setOfCrosses;
    }
}