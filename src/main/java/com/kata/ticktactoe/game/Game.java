package com.kata.ticktactoe.game;

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
                case EMPTY:
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

    /**
     * How many tiles are free. It can be zero if the game has ended with a winner. In that case there might be free
     * tiles but there are no moves left.
     * @return the number of free tiles (possible moves) or zero if the game ended.
     */
    public int getRemainingMoves() {
        return remainingMoves;
    }

    /**
     * Prints the game board in the standard output. Uses default OS line separator and underscores for empty tiles.
     */
    public void printBoardStatus() {
        System.out.println(board.getBoardStatus(System.lineSeparator(), '_'));
    }

    /**
     * Constructs and returns a string that represents the status of the board for this game. X is printed where X has
     * played, Y is printed where Y has played and emptyTileChar tile is used as spaces might not always be convenient.
     * @param rowSeparator The string to use in order to separate the rows
     * @param emptyTileChar The character that will represent empty tiles
     * @return a string representation of the board status showing who has played and where
     */
    public String getBoardStatus(String rowSeparator, char emptyTileChar) {
        return board.getBoardStatus(rowSeparator, emptyTileChar);
    }

    /**
     * Resets the game. After this method is called, moves can be played again if the previous game ended and all progress
     * is lost.
     */
    public void reset() {
        board.reset();
        remainingMoves = Board.SIZE * Board.SIZE;
    }

    /**
     * Creates a default game
     * @return a game with the default tic-tac-toe rules applying
     */
    public static Game createDefault() {
        Board board = new Board();
        ArrayList<IGameRule> rules = new ArrayList<>();
        rules.add(new DependentRule(new BoundaryCheckRule(), new NoOverwriteRule()));
        rules.add(new PlayerOrderRule());
        return new Game(board, rules);
    }
}
