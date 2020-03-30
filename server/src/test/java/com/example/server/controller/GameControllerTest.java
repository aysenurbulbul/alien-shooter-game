package com.example.server.controller;


import com.example.server.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;




import static org.mockito.BDDMockito.then;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.Silent.class)
@AutoConfigureMockMvc
public class GameControllerTest {

    @Mock
    GameService gameService;

    @InjectMocks
    GameController gameController;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void addGame() throws Exception {

        /*
        Game game = new Game(null, new Player(1L,"zeyneperd", "aa", "bb"), 10L, LocalDateTime.now());
        when(gameService.addGame(any(Game.class))).thenReturn(game);

        mockMvc.perform(post("/game/addGame").content(asJsonString(game))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        then(gameService).should().addGame(any(Game.class));

         */
    }

    @Test
    public void getAllGames() throws Exception{

        mockMvc.perform(get("/game/getAllGames")).andExpect(status().isOk());
        then(gameService).should().getAllGames();
    }

    @Test
    public void deleteAll() throws Exception {

        mockMvc.perform(delete("/game/delete")).andExpect(status().isOk());
        then(gameService).should().clearAll();
    }
}

