package com.kata.ticktactoe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameRuleTests {

    @Test
    public void BoundaryCheckRule_ValidCoordinates_checkReturnsNull() {
        Board board = new Board();
        Move next = new Move(Player.X, 0, 0);
        assertNull(new BoundaryCheckRule().check(board, next));
    }

    @Test
    public void BoundaryCheckRule_CoordinatesOutsideBoardBounds_checkReturnsErrorText() {
        Board board = new Board();
        IGameRule rule = new BoundaryCheckRule();
        assertNotNull(rule.check(board, new Move(Player.X, 7, 0)));
        assertNotNull(rule.check(board, new Move(Player.X, 3, 0)));
        assertNotNull(rule.check(board, new Move(Player.X, 0, 3)));
        assertNotNull(rule.check(board, new Move(Player.X, 0, -1)));
        assertNotNull(rule.check(board, new Move(Player.X, -1, 0)));
    }

    @Test
    public void PlayerOrderRule_FirstMoveByX_checkReturnsNull() {
        Board board = new Board();
        Move next = new Move(Player.X, 0, 0);
        assertNull(new PlayerOrderRule().check(board, next));
    }

    @Test
    public void PlayerOrderRule_XAttemptsToPlayTwice_checkReturnsError() {
        Board board = new Board();
        Move next = new Move(Player.X, 0, 0);
        assertNull(new PlayerOrderRule().check(board, next));
        next.apply(board);
        next = new Move(Player.X, 1, 1);
        assertEquals("It's O's turn to play", new PlayerOrderRule().check(board, next));
    }

    @Test
    public void OccupiedTileRule_PlayerAttemptsToPlayOnOccupiedTile_checkReturnsError() {
        Board board = new Board();
        board.setTileState(Check.O, 0, 1);
        Move next = new Move(Player.X, 0, 1);
        String checkResult = new NoOverwriteRule().check(board, next);
        assertEquals("Please select an empty tile to play on.", checkResult);
    }
}
