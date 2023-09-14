package com.kata.ticktactoe.rest;

import com.kata.ticktactoe.game.GameResult;
import com.kata.ticktactoe.game.MoveResult;

import java.util.List;

/**
 * Adapters for MoveResultData which isn't serialization friendly
 */
public class PlayResponseBase {
    private final MoveResult moveResult;

    protected PlayResponseBase(MoveResult moveResult) {
        this.moveResult = moveResult;
    }

    public MoveResult getMoveResult() {
        return moveResult;
    }
}

class SuccessResponse extends PlayResponseBase {

    public SuccessResponse() {
        super(MoveResult.SUCCESS);
    }
}

class ErrorResponse extends PlayResponseBase {

    private final List<String> errors;

    public ErrorResponse(List<String> errors) {
        super(MoveResult.ERROR);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}

class EndGameResponse extends PlayResponseBase {

    private final GameResult gameResult;

    public EndGameResponse(GameResult gameResult) {
        super(MoveResult.END);
        this.gameResult = gameResult;
    }

    public GameResult getGameResult() {
        return gameResult;
    }
}
