package com.kata.ticktactoe;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void constructor_CreatesAnEmptyBoard() {
        Board board = new Board();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Check position = board.getTileState(i, j);
                if (position != Check.EMPTY) {
                    fail(String.format("%d,%d is expected to be Empty; found '%s'", i, j, position));
                }
            }
        }
    }

    @Test
    public void successiveCallToSetAndGet_coordinatesAreWithinRange_ValueIsProperlyStoredAndRetrieved() {
        Board board = new Board();
        board.setTileState(Check.X, 1, 0);
        assertEquals(Check.X, board.getTileState(1, 0));

        //let's also see that the rest of the values are unaffected, otherwise there is no much meaning in the test :)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 1 && j == 0) continue;
                Check position = board.getTileState(i, j);
                if (position != Check.EMPTY) {
                    fail(String.format("%d,%d is expected to be Empty; found '%s'", i, j, position));
                }
            }
        }
    }

    @Test
    public void coordinatesAreOutOfRange_setterThrowsException() {
        Board board = new Board();
        assertThrows(IllegalArgumentException.class, () -> board.setTileState(Check.X, 3, 0));
        assertThrows(IllegalArgumentException.class, () -> board.setTileState(Check.X, 0, 3));
        assertThrows(IllegalArgumentException.class, () -> board.setTileState(Check.X, -1, 2));
    }

    @Test
    public void coordinatesAreOutOfRange_accessorThrowsException() {
        Board board = new Board();
        assertThrows(IllegalArgumentException.class, () -> board.getTileState(3, 0));
        assertThrows(IllegalArgumentException.class, () -> board.getTileState(0, 3));
        assertThrows(IllegalArgumentException.class, () -> board.getTileState(-1, 2));
    }

    @Test
    public void reset_FillsTheBoardWithEmpty() {
        Board board = new Board();
        board.setTileState(Check.O, 2, 2);
        board.setTileState(Check.X, 1, 1);
        board.reset();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Check position = board.getTileState(i, j);
                if (position != Check.EMPTY) {
                    fail(String.format("%d,%d is expected to be Empty; found '%s'", i, j, position));
                }
            }
        }
    }

    @Test
    public void getNextMove_EmptyBoard_ReturnsX() {
        Board board = new Board();
        assertEquals(Player.X, board.getNextMove());
    }

    @Test
    public void getNextMove_XAlreadyPlayed_ReturnsO() {
        Board board = new Board();
        board.setTileState(Check.X, 0, 0);
        assertEquals(Player.O, board.getNextMove());
    }

    @Test
    public void getWinner_EmptyBoard_HasNoWinner() {
        assertEquals(Check.EMPTY, new Board().getWinner(0, 0));
    }

    @Test
    public void getWinner_OIsHorizontal_OIsTheWinner() {

        Board board = new Board();
        board.setTileState(Check.O, 0, 0);
        board.setTileState(Check.O, 1, 0);
        board.setTileState(Check.O, 2, 0);
        assertEquals(Check.O, board.getWinner(2, 0));
    }

    @Test
    public void getWinner_XIsVertical_XIsTheWinner() {

        Board board = new Board();
        board.setTileState(Check.X, 1, 0);
        board.setTileState(Check.X, 1, 1);
        board.setTileState(Check.X, 1, 2);
        assertEquals(Check.X, board.getWinner(1, 2));
    }

    @Test
    public void getWinner_OIsOnMainDiagonal_OIsTheWinner() {

        Board board = new Board();
        board.setTileState(Check.O, 0, 0);
        board.setTileState(Check.O, 1, 1);
        board.setTileState(Check.O, 2, 2);
        assertEquals(Check.O, board.getWinner(2, 2));
    }

    @Test
    public void getWinner_OIsOnAntiDiagonal_OIsTheWinner() {

        Board board = new Board();
        board.setTileState(Check.O, 2, 0);
        board.setTileState(Check.O, 1, 1);
        board.setTileState(Check.O, 0, 2);
        assertEquals(Check.O, board.getWinner(0, 2));
    }
    
    @Test
    public void getBoardStatus_EnsureThatTheTableIsNotRotated() {

        Board board = new Board();
        board.setTileState(Check.X, 0, 0);
        board.setTileState(Check.O, 2, 0);
        board.setTileState(Check.X, 1, 2);
        String boardStatus = board.getBoardStatus("|", '.');
        assertEquals("X.O|...|.X.|", boardStatus);
    }
}
