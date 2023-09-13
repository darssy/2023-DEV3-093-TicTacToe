package com.kata.ticktactoe.scafold;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameTests {


    @Test
    public void play_XPlaysOnZeroZero_MoveIsAccepted() {
        Board board = new Board();
        ArrayList<IGameRule> rules = new ArrayList<>();
        rules.add(new BoundaryCheckRule());
        rules.add(new PlayerOrderRule());
        Game game = new Game(board, rules);
        MoveResultData result = game.play(new Move(Player.X, 0, 0));
        assertEquals(MoveResult.SUCCESS, result.getMoveResult());
        assertEquals(Check.X, board.getPosition(0, 0));
    }

    @Test
    public void play_OAttemptsTheFirstMove_MoveIsRejected() {
        Board board = new Board();
        ArrayList<IGameRule> rules = new ArrayList<>();
        rules.add(new BoundaryCheckRule());
        rules.add(new PlayerOrderRule());
        Game game = new Game(board, rules);
        MoveResultData result = game.play(new Move(Player.O, 0, 0));
        assertEquals(MoveResult.ERROR, result.getMoveResult());
        assertEquals(1, result.getErrors().size());
        assertEquals(Check.Empty, board.getPosition(0, 0));
    }

    @Test
    public void play_XPlaysTwiceSecondTimeOutsideBounds_BothErrorsAreReturned() {
        Board board = new Board();
        ArrayList<IGameRule> rules = new ArrayList<>();
        rules.add(new BoundaryCheckRule());
        rules.add(new PlayerOrderRule());

        Game game = new Game(board, rules);
        game.play(new Move(Player.X, 0, 0));
        MoveResultData result = game.play(new Move(Player.X, -1, -1));
        assertEquals(MoveResult.ERROR, result.getMoveResult());
        List<String> errors = result.getErrors();
        assertEquals(2, errors.size());
        assertEquals("You can't play outside the board bounds", errors.get(0));
        assertEquals("It's O's turn to play", errors.get(1));
    }

    @Test
    public void play_AllMovesArePlayed_GameEndsInDraw() {
        Board board = new Board();
        ArrayList<IGameRule> rules = new ArrayList<>();
        rules.add(new BoundaryCheckRule());
        rules.add(new PlayerOrderRule());

        Move[] moves = new Move[]{
                new Move(Player.X, 0, 0),
                new Move(Player.O, 1, 0),
                new Move(Player.X, 2, 0),
                new Move(Player.O, 0, 1),
                new Move(Player.X, 1, 1),
                new Move(Player.O, 0, 2),
                new Move(Player.X, 1, 2),
                new Move(Player.O, 2, 2),
        };

        Game game = new Game(board, rules);
        for (Move move : moves) {
            MoveResultData result = game.play(move);
            MoveResult moveResult = result.getMoveResult();
            assertEquals(MoveResult.SUCCESS, moveResult);
        }
        MoveResultData result = game.play(new Move(Player.X, 2, 1));
        assertEquals(MoveResult.END, result.getMoveResult());
        assertEquals(GameResult.DRAW, result.getGameResult());
    }

    @Test
    public void play_AfterAllMovesArePlayed_ReturnsError() {
        Board board = new Board();
        ArrayList<IGameRule> rules = new ArrayList<>();
        rules.add(new BoundaryCheckRule());
        rules.add(new PlayerOrderRule());

        Move[] moves = new Move[]{
                new Move(Player.X, 0, 0),
                new Move(Player.O, 1, 0),
                new Move(Player.X, 2, 0),
                new Move(Player.O, 0, 1),
                new Move(Player.X, 1, 1),
                new Move(Player.O, 0, 2),
                new Move(Player.X, 1, 2),
                new Move(Player.O, 2, 2),
        };

        Game game = new Game(board, rules);
        for (Move move : moves) {
            MoveResultData result = game.play(move);
            MoveResult moveResult = result.getMoveResult();
            assertEquals(MoveResult.SUCCESS, moveResult);
        }
        assertEquals(MoveResult.END, game.play(new Move(Player.X, 2, 1)).getMoveResult());
        MoveResultData result = game.play(new Move(Player.O, 0, 0));
        assertEquals(MoveResult.ERROR, result.getMoveResult());
        assertEquals(1, result.getErrors().size());
        assertEquals("This game has finished.", result.getErrors().get(0));
    }

    @Test
    public void play_XWinsHorizontally() {
        Board board = new Board();
        ArrayList<IGameRule> rules = new ArrayList<>();
        rules.add(new BoundaryCheckRule());
        rules.add(new PlayerOrderRule());

        Move[] moves = new Move[]{
                new Move(Player.X, 0, 0),
                new Move(Player.O, 0, 1),
                new Move(Player.X, 1, 0),
                new Move(Player.O, 1, 1)
        };

        Game game = new Game(board, rules);
        for (Move move : moves) {
            MoveResultData result = game.play(move);
            MoveResult moveResult = result.getMoveResult();
            assertEquals(MoveResult.SUCCESS, moveResult);
        }
        MoveResultData result = game.play(new Move(Player.X, 2, 0));
        assertEquals(MoveResult.END, result.getMoveResult());
        assertEquals(GameResult.X, result.getGameResult());
    }

    @Test
    public void play_OWinsVertically() {
        Board board = new Board();
        ArrayList<IGameRule> rules = new ArrayList<>();
        rules.add(new BoundaryCheckRule());
        rules.add(new PlayerOrderRule());

        Move[] moves = new Move[]{
                new Move(Player.X, 0, 0),
                new Move(Player.O, 1, 0),
                new Move(Player.X, 0, 1),
                new Move(Player.O, 1, 2),
                new Move(Player.X, 2, 2)
        };

        Game game = new Game(board, rules);
        for (Move move : moves) {
            MoveResultData result = game.play(move);
            MoveResult moveResult = result.getMoveResult();
            assertEquals(MoveResult.SUCCESS, moveResult);
        }
        MoveResultData result = game.play(new Move(Player.O, 1, 1));
        assertEquals(MoveResult.END, result.getMoveResult());
        assertEquals(GameResult.O, result.getGameResult());
    }
}
