package com.kata.ticktactoe.scafold;

import java.util.List;

public class MoveResultData {
    private final MoveResult moveResult;
    private final List<String> errors;
    private final GameResult gameResult;

    public MoveResultData() {
        gameResult = null;
        errors = null;
        moveResult = MoveResult.SUCCESS;
    }

    public MoveResultData(List<String> errors) {
        if (errors == null) throw new IllegalArgumentException("errors must not be null");
        if (errors.isEmpty()) throw new IllegalArgumentException("must contain at least one error");
        moveResult = MoveResult.ERROR;
        this.errors = errors;
        gameResult = null;
    }

    public MoveResultData(GameResult gameResult) {
        if (gameResult == null) throw new IllegalArgumentException("gameResult must not be null");
        this.gameResult = gameResult;
        moveResult = MoveResult.END;
        this.errors = null;
    }

    public GameResult getGameResult() {
        if (moveResult != MoveResult.END) throw new IllegalStateException("Game result is not END");
        return gameResult;
    }

    public List<String> getErrors() {
        if (moveResult != MoveResult.ERROR) throw new IllegalStateException("Game result is not ERROR");
        return errors;
    }

    public MoveResult getMoveResult() {
        return moveResult;
    }
}
