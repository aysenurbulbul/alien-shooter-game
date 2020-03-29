package com.example.server.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.example.server.model.Player;
import com.example.server.repository.PlayerRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PlayerServiceTest {

    @Mock
    PlayerRepository playerRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    PlayerService playerService;

    @Test
    public void getAllPlayers(){

        //given 2 test players added to a list
        Player player1 = new Player(1L,"username1", "email@mail.com", "password");
        Player player2 = new Player(2L,"username2", "email@mail.com", "password");
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        //when asked from repo return them
        when(playerRepository.findAll()).thenReturn(players);

        List<Player> allPlayers = playerService.getAllPlayers();
        assertEquals(players, allPlayers);
    }

    @Test
    public void getPlayer(){
        //given
        Player player1 = new Player(1L,"username1", "email@mail.com", "password");

        //when asked to return player with id 1 return player1
        when(playerRepository.findByUsername(player1.getUsername())).thenReturn(player1);

        Player playerTest = playerService.getPlayer("username1");
        assertEquals(player1, playerTest);
    }

    @Test
    public void addPlayer(){
        //given
        Player player1 = new Player(1L,"username1", "email@mail.com", "password");
        player1.setPassword(bCryptPasswordEncoder.encode(player1.getPassword()));
        Optional<Player> playerOptional = Optional.of(player1);

        // return these when asked operations on playerRepository
        when(playerRepository.findById(anyLong())).thenReturn(playerOptional);
        when(playerRepository.save(any(Player.class))).thenReturn(player1);

        //when
        Player resultAdd = playerService.addPlayer(player1);

        //then
        assertNotNull("Player is not null", resultAdd);
        //wanted # of invocations : 1
        verify(playerRepository, times(1)).save(any());

    }

    @Test
    public void deletePlayer(){
        // assume given id exists
        Long id = 1L;
        when(playerRepository.existsById(id)).thenReturn(true);

        //when asked to delete
        playerService.deletePlayer(id);

        //then verify
        verify(playerRepository,times(1)).deleteById(anyLong());
    }


}
