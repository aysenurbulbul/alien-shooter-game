package com.example.server.controller;

import com.example.server.model.Player;
import com.example.server.repository.PlayerRepository;
import com.example.server.service.PlayerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.Silent.class)
@AutoConfigureMockMvc
public class PlayerControllerTest {

    @Mock
    PlayerRepository playerRepository;

    @Mock
    PlayerService playerService;

    @InjectMocks
    PlayerController playerController;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
    }

    @Test
    public void getAllPlayers() throws Exception{
        mockMvc.perform(get("/players")).andExpect(status().isOk());
        then(playerService).should().getAllPlayers();
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getPlayer() throws Exception{
        Player player = new Player(1L,"username1", "email@mail.com", "password");

        when(playerService.getPlayer("username1")).thenReturn(player);

        mockMvc.perform(get("/players/{username}", "username1")).andExpect(status().isOk());
        then(playerService).should().getPlayer("username1");
    }

    @Test
    public void registerPlayer() throws Exception{
        Player player = new Player(1L,"username", "email@mail.com", "password");
        when(playerService.addPlayer(any(Player.class))).thenReturn(player);

        mockMvc.perform(post("/registration").content(asJsonString(player))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        then(playerService).should().addPlayer(any(Player.class));
    }

    @Test
    public void deletePlayer() throws Exception{
        Player player = new Player(1L,"username1", "email@mail.com", "password");
        when(playerRepository.findById(anyLong())).thenReturn(Optional.of(player));

        mockMvc.perform(delete("/delete/{playerId}",1L)).andExpect(status().isOk());
        then(playerService).should().deletePlayer(anyLong());
    }

}
