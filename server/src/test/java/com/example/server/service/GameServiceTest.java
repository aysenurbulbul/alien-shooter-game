package com.example.server.service;

import com.example.server.model.Game;
import com.example.server.model.Player;
import com.example.server.repository.GameRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class GameServiceTest {

    @Mock
    GameRepository gameRepository;

    @Mock
    LeaderboardService leaderboardService;

    @InjectMocks
    GameService gameService;

    @Mock
    Player player;

    @Test
    public void addGameTest(){

        /*
        Game game = new Game(null, player, 10L, LocalDateTime.now());
        ArgumentCaptor<Game> gameArgumentCaptor = ArgumentCaptor.forClass(Game.class);
        gameService.addGame(game);
        verify(leaderboardService, Mockito.times(1)).updateAllLeaderboards(game);
        verify(gameRepository).save(gameArgumentCaptor.capture());
        Assert.assertTrue(gameArgumentCaptor.getValue().equals(game));
        verify(gameRepository, Mockito.times(1)).save(Mockito.any(Game.class));

         */
    }

    @Test
    public void getAllGamesTest() {

        List<Game> games= new ArrayList<>(Arrays.asList(new Game(null, player, 10L,LocalDateTime.now()),
                new Game(null, player, 15L,LocalDateTime.now())));
        when(gameRepository.findAll()).thenReturn(games);
        List<Game> gamesFound = gameService.getAllGames();
        verify(gameRepository).findAll();
        Assert.assertEquals(games, gamesFound);
    }

}