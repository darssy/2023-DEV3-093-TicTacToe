package com.kata.ticktactoe;

import com.kata.ticktactoe.rest.TicTacToeGameRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class TickTacToeApplicationTests {

    @Autowired
    private TicTacToeGameRestController controller;

    @Autowired
    private MockMvc mvc;
    
    @Test
    void smokeTest() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void play_MoveSucceeds() throws Exception {
        mvc.perform(post("/play")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"player\": \"X\", \"x\": \"2\", \"y\": \"0\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{ \"moveResult\": \"SUCCESS\" }"));
    }

    /* **************************************
     * Likewise with the rest of the cases  *
     ****************************************/
}
