package com.kata.ticktactoe.scafold;

public interface IGameRule {
    /**
     * Checks if a move can be played on the board without violating the rule
     * @param board the board to apply the validation to
     * @param move the move that the player is attempting to make
     * @return the text describing the rule violation or null if the rule is not violated 
     */
    String check(Board board, Move move);
}

/**
 * A simple rule to check if a player's move is within the board's boundaries
 */
class BoundaryCheckRule implements IGameRule {

    @Override
    public String check(Board board, Move move) {
        if (move.getX() > 2 || move.getX() < 0 || move.getY() > 2 || move.getY() < 0){
            return "You can't play outside the board bounds";
        }
        return null;
    }
}

enum Player {
    X,
    O
}

/**
 * Represents a player move. Holds the position and the player type (X or O)
 */
class Move {
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

    /**
     * Applies the move to a bord. The move should be validated before applying.
     * @param board the board to perform the move on
     */
    public void apply(Board board) {
        switch (player){
            case X:
                board.setPosition(Check.X, x, y);
                break;
            case O:
                board.setPosition(Check.O, x, y);
                break;
        }
    }

    @Override
    public String toString() {
        return String.format("Putting %s at (%d,%d)", player, x, y);
    }
}
