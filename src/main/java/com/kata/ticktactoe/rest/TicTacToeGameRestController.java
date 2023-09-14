package com.kata.ticktactoe.rest;

import com.kata.ticktactoe.*;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;


@RestController
public class TicTacToeGameRestController {
    private final Game game;

    public TicTacToeGameRestController(Game game) {
        this.game = game;
    }

    @PostMapping("/play")
    @ResponseBody
    public PlayResponseBase play(@RequestBody Move move) {

        MoveResultData data = game.play(move);
        switch (data.getMoveResult()) {
            case SUCCESS:
                return new SuccessResponse();
            case ERROR:
                return new ErrorResponse(data.getErrors());
            case END:
                return new EndGameResponse(data.getGameResult());
            default:
                throw new IllegalStateException("Unexpected value: " + data.getMoveResult());
        }
    }
    
    @GetMapping("/status")
    public String getBoardStatus(@RequestParam(value = "separator") String separator) {
        switch (separator) {
            case "newline":
                return game.getBoardStatus(System.lineSeparator(), '.');
            case "pipe":
                return game.getBoardStatus("|", '.');
            default:
                return game.getBoardStatus(separator, '.');
        }
    }
    
    @PutMapping("/reset")
    public void resetGame() {
        game.reset();
    }

    @GetMapping()
    public String greeting() {
        return "Controller up and running";
    }
    
    @Bean
    public static Game createDefaultGame() {
        return Game.createDefault();
    }
}
