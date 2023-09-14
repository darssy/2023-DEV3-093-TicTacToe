package com.kata.ticktactoe.game;

/**
 * Represents a player move. Holds the position and the player type (X or O)
 */
public class Move {
    private final Player player;
    private final int x;
    private final int y;

    public Move(Player player, int x, int y) {
        this.player = player;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Applies the move to a bord. The move should be validated before applying.
     *
     * @param board the board to perform the move on
     */
    public void apply(Board board) {
        switch (player) {
            case X:
                board.setTileState(Check.X, x, y);
                break;
            case O:
                board.setTileState(Check.O, x, y);
                break;
        }
    }

    @Override
    public String toString() {
        return String.format("Putting %s at (%d,%d)", player, x, y);
    }
}
