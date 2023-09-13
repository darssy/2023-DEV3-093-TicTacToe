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
        List<String> errors = game.play(new Move(Player.X, 0, 0));
        assertTrue(errors.isEmpty());
        assertEquals(Check.X, board.getPosition(0, 0));
    }

    @Test
    public void play_OAttemptsTheFirstMove_MoveIsRejected() {
        Board board = new Board();
        ArrayList<IGameRule> rules = new ArrayList<>();
        rules.add(new BoundaryCheckRule());
        rules.add(new PlayerOrderRule());
        Game game = new Game(board, rules);
        List<String> errors = game.play(new Move(Player.O, 0, 0));
        assertEquals(1, errors.size());
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
        List<String> errors = game.play(new Move(Player.X, -1, -1));
        assertEquals(2, errors.size());
        assertEquals(Check.X, board.getPosition(0, 0));
    }

}
