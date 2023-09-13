package com.kata.ticktactoe.scafold;

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
                tile[i] = Check.Empty;
            }
        }
    }

    /**
     * Sets a value to the tile positioned at . Only the board boundaries are checked. No other logic applies.
     *
     * @param value the value to set.
     * @param x     the zero based horizontal position of the check
     * @param y     the zero based vertical position of the check
     */
    public void setPosition(Check value, int x, int y) {
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
    public Check getPosition(int x, int y) {
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
                tile[i] = Check.Empty;
            }
        }
    }

    public Player getNextMove() {
        int xs = 0;
        int os = 0;
        for (Check[] tile : tiles) {
            for (Check check : tile) {
                switch (check) {
                    case Empty:
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
     * Returns the winning check or Empty if we have no winner. In order to avoid searching for all 8 winning
     * possibilities, the method is "hinted" with the last played position. That way a maximum of 4 cases will be checked
     * and that is possible only for the central tile 1,1.
     * @param hintX the x position of the last move.
     * @param hintY the y position of the last move.
     * @return the winning check or Empty if there is no winner.
     */
    public Check getWinner(int hintX, int hintY) {
        Check horizontalCheck = checkHorizontally(hintY);
        if (horizontalCheck != Check.Empty) return horizontalCheck;
        
        Check verticalCheck = checkVertically(hintX);
        if (verticalCheck != Check.Empty) return verticalCheck;

        return checkDiagonally(hintX, hintY);
    }

    private final Check[] cache = new Check[]{Check.Empty, Check.Empty, Check.Empty};

    private Check isWinningStreak() {
        int xs = 0;
        int os = 0;
        for (Check check : cache) {
            switch (check){
                case Empty:
                    return Check.Empty;
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
        return Check.Empty;
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
        return Check.Empty;
    }
}

