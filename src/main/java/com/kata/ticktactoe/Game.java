package com.kata.ticktactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a tick-tac-toe game
 */
public class Game {

    private final Board board;
    private final ArrayList<IGameRule> rules;
    private int remainingMoves = Board.SIZE * Board.SIZE;

    /**
     * Initializes a game with a board and a set of rules
     * @param board the board to play on
     * @param rules the game rules to apply
     */
    public Game(Board board, ArrayList<IGameRule> rules) {
        if (board == null) throw new IllegalArgumentException("Board must not be null");
        this.board = board;
        this.rules = rules == null ? new ArrayList<>() : rules;
    }

    /**
     * Plays a move and returns the result of that move. The move can either be accepted, end the game or result in a
     * rule violation.
     * @param nextMove the next move to play if it is valid move.
     * @return returns the result of this move.
     */
    public MoveResultData play(Move nextMove) {
        //Could have been placed under a rule but this will be much faster than iterating the board every time
        if (remainingMoves == 0) {
            ArrayList<String> err = new ArrayList<>(1);
            err.add("This game has finished.");
            return new MoveResultData(err);
        }
        List<String> errors = rules.stream()
                .map(r -> r.check(board, nextMove))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (errors.isEmpty()) {
            nextMove.apply(board);
            remainingMoves--;
            Check winner = board.getWinner(nextMove.getX(), nextMove.getY());
            switch (winner) {
                case Empty:
                    if (remainingMoves == 0) {
                        return new MoveResultData(GameResult.DRAW);
                    }
                    break;
                case X:
                    remainingMoves = 0;
                    return new MoveResultData(GameResult.X);
                case O:
                    remainingMoves = 0;
                    return new MoveResultData(GameResult.O);
            }
            return new MoveResultData();
        } else {
            return new MoveResultData(errors);
        }
    }

    public static Game createDefault() {
        Board board = new Board();
        ArrayList<IGameRule> rules = new ArrayList<>();
        rules.add(new DependentRule(new BoundaryCheckRule(), new NoOverwriteRule()));
        rules.add(new PlayerOrderRule());
        return new Game(board, rules);
    }
}
