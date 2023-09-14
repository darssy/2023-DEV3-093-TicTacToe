package com.kata.ticktactoe;

/**
 * The basis for all game rules.
 */
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
        if (move.getX() >= Board.SIZE || move.getX() < 0 || move.getY() >= Board.SIZE || move.getY() < 0){
            return "You can't play outside the board bounds";
        }
        return null;
    }
}

/**
 * This rule makes sure that one player can't play twice
 */
class PlayerOrderRule implements IGameRule {
    @Override
    public String check(Board board, Move move) {
        Player nextMove = board.getNextMove();
        return move.getPlayer() != nextMove ? "It's " + nextMove + "'s turn to play" : null;
    }
}
