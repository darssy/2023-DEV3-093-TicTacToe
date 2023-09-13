package com.kata.ticktactoe.scafold;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Game {

    private final Board board;
    private final ArrayList<IGameRule> rules;

    public Game(Board board, ArrayList<IGameRule> rules) {
        if (board == null) throw new IllegalArgumentException("Board must not be null");
        this.board = board;
        this.rules = rules == null ? new ArrayList<>() : rules;
    }

    public List<String> play(Move nextMove) {
        List<String> errors = rules.stream()
                .map(r -> r.check(board, nextMove))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (errors.isEmpty()) nextMove.apply(board);
        return errors;
    }
}
