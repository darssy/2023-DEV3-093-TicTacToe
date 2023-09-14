package com.kata.ticktactoe.game;

/**
 * Represents a 3x3 tick-tac-toe board
 */
public class Board {
    public static final int SIZE = 3;
    private final Check[][] tiles = new Check[SIZE][SIZE];

    /**
     * Initializes a board with all tiles to empty
     */
    public Board() {
        for (Check[] tile : tiles) {
            for (int i = 0; i < tile.length; i++) {
                tile[i] = Check.EMPTY;
            }
        }
    }

    /**
     * Sets a value to the tile positioned at x, y. Only the board boundaries are checked. No other logic applies.
     *
     * @param value the value to set.
     * @param x     the zero based horizontal position of the check
     * @param y     the zero based vertical position of the check
     */
    public void setTileState(Check value, int x, int y) {
        if (x >= SIZE || x < 0) throw new IllegalArgumentException("x must be between 0 and 2");
        if (y >= SIZE || y < 0) throw new IllegalArgumentException("y must be between 0 and 2");
        tiles[x][y] = value;
    }

    /**
     * Gets the value of the respective tile
     *
     * @param x the zero based horizontal position of the check
     * @param y the zero based vertical position of the check
     * @return The value of the tile (x,y)
     */
    public Check getTileState(int x, int y) {
        if (x >= SIZE || x < 0) throw new IllegalArgumentException("x must be between 0 and 2");
        if (y >= SIZE || y < 0) throw new IllegalArgumentException("y must be between 0 and 2");
        return tiles[x][y];
    }

    /**
     * Sets all tiles to Empty
     */
    public void reset() {
        for (Check[] tile : tiles) {
            for (int i = 0; i < tile.length; i++) {
                tile[i] = Check.EMPTY;
            }
        }
    }

    public Player getNextMove() {
        int xs = 0;
        int os = 0;
        for (Check[] tile : tiles) {
            for (Check check : tile) {
                switch (check) {
                    case EMPTY:
                        break;
                    case X:
                        xs++;
                        break;
                    case O:
                        os++;
                        break;
                }
            }
        }
        //Since X always plays first, if X has played more times then it's O's turn to play
        return xs > os ? Player.O : Player.X;
    }

    /**
     * Constructs and returns a string that represents the status of the board. X is printed where X has played, Y is
     * printed where Y has played and emptyTileChar tile is used as spaces might not always be convenient.
     * @param rowSeparator The string to use in order to separate the rows.
     * @param emptyTileChar The character that will represent empty tiles.
     * @return a string representation of the board status showing who has played and where.
     */
    public String getBoardStatus(String rowSeparator, char emptyTileChar) {
        if (emptyTileChar == 'O' || emptyTileChar == 'X') {
            throw new IllegalArgumentException(emptyTileChar + ": This character is already reserved.");
        }
        StringBuilder builder = new StringBuilder();
        //please note that this is expected to be super inefficient as the fast counter scans the "outer" array and
        //the cache misses are expected to go bananas. Clearly, if efficiency is paramount that's not the way to go.
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                switch (tiles[x][y]) {
                    case EMPTY:
                        builder.append(emptyTileChar);
                        break;
                    case X:
                        builder.append('X');
                        break;
                    case O:
                        builder.append('O');
                        break;
                }
            }
            builder.append(rowSeparator);
        }
        return builder.toString();
    }

    /**
     * Returns the winning check or Empty if we have no winner. In order to avoid searching for all 8 winning
     * possibilities, the method is "hinted" with the last played position. That way a maximum of 4 cases will be checked
     * and that is possible only for the central tile 1,1.
     * @param hintX the x position of the last move.
     * @param hintY the y position of the last move.
     * @return the winning check or Empty if there is no winner.
     */
    public Check getWinner(int hintX, int hintY) {
        Check horizontalCheck = checkHorizontally(hintY);
        if (horizontalCheck != Check.EMPTY) return horizontalCheck;
        
        Check verticalCheck = checkVertically(hintX);
        if (verticalCheck != Check.EMPTY) return verticalCheck;

        return checkDiagonally(hintX, hintY);
    }

    private final Check[] cache = new Check[]{Check.EMPTY, Check.EMPTY, Check.EMPTY};

    private Check isWinningStreak() {
        int xs = 0;
        int os = 0;
        for (Check check : cache) {
            switch (check){
                case EMPTY:
                    return Check.EMPTY;
                case X:
                    xs++;
                    break;
                case O:
                    os++;
                    break;
            }
        }
        if (xs == 3) return Check.X;
        else if (os == 3)  return Check.O;
        return Check.EMPTY;
    }

    private Check checkHorizontally(int rowIndex) {
        for (int i = 0; i < tiles.length; i++) {
            cache[i] = tiles[i][rowIndex];
        }
        return isWinningStreak();
    }

    private Check checkVertically(int columnIndex) {
        //small optimization as arraycopy is native can be much faster than the manual copy
        System.arraycopy(tiles[columnIndex], 0, cache, 0, tiles[columnIndex].length);
        return isWinningStreak();
    }

    private Check checkDiagonally(int columnIndex, int rowIndex) {
        //On a mathematical 1-based matrix the anti-diagonal is i + j = size + 1
        //Since we are zero based though, i + j = size - 1
        int antiDiagonalSum = SIZE - 1;
        if (columnIndex == rowIndex) { //main diagonal
            for (int i = 0; i < tiles.length; i++) {
                cache[i] = tiles[i][i];
            }
            return isWinningStreak();
        }
        else if (columnIndex + rowIndex == antiDiagonalSum) {
            for (int i = 0; i < tiles.length; i++) {
                cache[i] = tiles[i][antiDiagonalSum - i];
            }
            return isWinningStreak();
        }
        return Check.EMPTY;
    }
}

