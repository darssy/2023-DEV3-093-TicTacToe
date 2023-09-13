package com.kata.ticktactoe.scafold;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameRuleTests {
    
    @Test
    public void BoundaryCheckRule_ValidCoordinates_checkReturnsNull(){
        Board board = new Board();
        Move next = new Move(Player.X, 0, 0);
        assertNull(new BoundaryCheckRule().check(board, next));
    }

    @Test
    public void BoundaryCheckRule_CoordinatesOutsideBoardBounds_checkReturnsErrorText(){
        Board board = new Board();
        IGameRule rule = new BoundaryCheckRule();
        assertNotNull(rule.check(board, new Move(Player.X, 7, 0)));
        assertNotNull(rule.check(board, new Move(Player.X, 3, 0)));
        assertNotNull(rule.check(board, new Move(Player.X, 0, 3)));
        assertNotNull(rule.check(board, new Move(Player.X, 0, -1)));
        assertNotNull(rule.check(board, new Move(Player.X, -1, 0)));
    }
}
