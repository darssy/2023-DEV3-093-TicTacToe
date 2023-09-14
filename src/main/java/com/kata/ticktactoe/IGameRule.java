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

/**
 * This rule ensures that each player can play only on empty tiles. Note that this rule is not performing any boundary
 * validations. As a result the caller must make sure that the Move x and y value are sane before this rule is called
 */
class NoOverwriteRule implements IGameRule {

    @Override
    public String check(Board board, Move move) {
        return board.getTileState(move.getX(), move.getY()) == Check.Empty ? null
                : "Please select an empty tile to play on.";
    }
}

/**
 * A rule that merges 2 mutually exclusive rules into one. For example there is no reason to test if the player is
 * attempting to play on an occupied tile if the tile's x and y are not valid. In that case the rule that checks
 * for overwrite the tile validity <i>depends on</i> the rules that checks the tile validity. 
 */
class DependentRule implements IGameRule {

    private final IGameRule primaryRule;

    private final IGameRule dependentRule;

    public DependentRule(IGameRule primaryRule, IGameRule dependentRule) {
        if (primaryRule == null) throw new IllegalArgumentException("primaryRule must not be null");
        if (dependentRule == null) throw new IllegalArgumentException("dependentRule must not be null");
        if (dependentRule == primaryRule) {
            throw new IllegalArgumentException("dependentRule and primaryRule must not be the same");
        }
        this.primaryRule = primaryRule;
        this.dependentRule = dependentRule;
    }
    
    @Override
    public String check(Board board, Move move) {
        String result = primaryRule.check(board, move);
        return result == null ? dependentRule.check(board, move) : result;
    }
}
