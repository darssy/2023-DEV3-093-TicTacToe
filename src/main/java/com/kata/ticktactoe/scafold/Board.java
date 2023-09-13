package com.kata.ticktactoe.scafold;

/**
 * Represents a 3x3 tick tac toe board
 */
public class Board {
    private static final int SIZE = 3;
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
}

