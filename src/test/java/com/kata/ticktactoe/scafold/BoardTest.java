package com.kata.ticktactoe.scafold;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void constructor_CreatesAnEmptyBoard() {
        Board board = new Board();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Check position = board.getPosition(i, j);
                if (position != Check.Empty) {
                    fail(String.format("%d,%d is expected to be Empty; found '%s'", i, j, position));
                }
            }
        }
    }

    @Test
    public void successiveCallToSetAndGet_coordinatesAreWithinRange_ValueIsProperlyStoredAndRetrieved() {
        Board board = new Board();
        board.setPosition(Check.X, 1, 0);
        assertEquals(Check.X, board.getPosition(1, 0));

        //let's also see that the rest of the values are unaffected, otherwise there is no much meaning in the test :)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 1 && j == 0) continue;
                Check position = board.getPosition(i, j);
                if (position != Check.Empty) {
                    fail(String.format("%d,%d is expected to be Empty; found '%s'", i, j, position));
                }
            }
        }
    }

    @Test
    public void coordinatesAreOutOfRange_setterThrowsException() {
        Board board = new Board();
        assertThrows(IllegalArgumentException.class, ()-> board.setPosition(Check.X, 3, 0));
        assertThrows(IllegalArgumentException.class, ()-> board.setPosition(Check.X, 0, 3));
        assertThrows(IllegalArgumentException.class, ()-> board.setPosition(Check.X, -1, 2));
    }

    @Test
    public void coordinatesAreOutOfRange_accessorThrowsException() {
        Board board = new Board();
        assertThrows(IllegalArgumentException.class, ()-> board.getPosition(3, 0));
        assertThrows(IllegalArgumentException.class, ()-> board.getPosition(0, 3));
        assertThrows(IllegalArgumentException.class, ()-> board.getPosition(-1, 2));
    }

    @Test
    public void reset_FillsTheBoardWithEmpty() {
        Board board = new Board();
        board.setPosition(Check.O, 2, 2);
        board.setPosition(Check.X, 1, 1);
        board.reset();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Check position = board.getPosition(i, j);
                if (position != Check.Empty) {
                    fail(String.format("%d,%d is expected to be Empty; found '%s'", i, j, position));
                }
            }
        }
    }
}
