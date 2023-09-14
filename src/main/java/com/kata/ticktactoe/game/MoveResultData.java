package com.kata.ticktactoe.game;

import java.util.List;

/**
 * The result data of a move. Either the errors or the game result and in case the move result.
 */
public class MoveResultData {
    private final MoveResult moveResult;
    private final List<String> errors;
    private final GameResult gameResult;

    /**
     * Initializes a successful move result
     */
    public MoveResultData() {
        gameResult = null;
        errors = null;
        moveResult = MoveResult.SUCCESS;
    }

    /**
     * Initializes a rejected move result containing the errors
     * @param errors the errors or rule violations that caused the rejection
     */
    public MoveResultData(List<String> errors) {
        if (errors == null) throw new IllegalArgumentException("errors must not be null");
        if (errors.isEmpty()) throw new IllegalArgumentException("must contain at least one error");
        moveResult = MoveResult.ERROR;
        this.errors = errors;
        gameResult = null;
    }

    /**
     * Initializes an endgame move result.
     * @param gameResult the result of the game.
     */
    public MoveResultData(GameResult gameResult) {
        if (gameResult == null) throw new IllegalArgumentException("gameResult must not be null");
        this.gameResult = gameResult;
        moveResult = MoveResult.END;
        this.errors = null;
    }

    /**
     * Gets the game result.
     * @return the result of the game
     * @throws IllegalStateException if the game did not end (moveResult is not MoveResult.END)
     */
    public GameResult getGameResult() {
        if (moveResult != MoveResult.END) throw new IllegalStateException("Game result is not END");
        return gameResult;
    }

    /**
     * Gets the errors or rule violations of the move that caused this result.
     * @return the errors or rule violations
     * @throws IllegalStateException if the move did not cause an error (moveResult is not MoveResult.ERROR)
     */
    public List<String> getErrors() {
        if (moveResult != MoveResult.ERROR) throw new IllegalStateException("Game result is not ERROR");
        return errors;
    }

    /**
     * Returns the result status of the move. It should be checked before attempting to access the errors or the game
     * result.
     * @return the result status of the move
     */
    public MoveResult getMoveResult() {
        return moveResult;
    }
}
