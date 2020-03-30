package com.example.server.service;

import com.example.server.model.Game;
import com.example.server.model.Leaderboard;
import com.example.server.model.Player;
import com.example.server.repository.LeaderboardRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.Silent.class)
public class LeaderboardServiceTest {


    @Mock
    LeaderboardRepository leaderboardRepository;

    @InjectMocks
    @Spy
    LeaderboardService leaderboardService;

    @Mock
    Player player;

    @Test
    public void updateEmptyLeaderboardTest(){

        Game game = new Game(1L, player, 20L,LocalDateTime.now());
        leaderboardService.updateEmptyLeaderboard(game);
        verify(leaderboardRepository, Mockito.times(1)).save(Mockito.any(Leaderboard.class));
    }


    @Test
    public void updateAllLeaderboardsTestIfRepositoryIsEmpty() {

        List<Leaderboard> leaderboards = new ArrayList<>();
        Game game = new Game(null, player, 20L,LocalDateTime.now());

        when(leaderboardRepository.findAll()).thenReturn(leaderboards);

        leaderboardService.updateAllLeaderboards(game);

        verify(leaderboardRepository, Mockito.times(1)).findAll();
        verify(leaderboardService, Mockito.times(1)).updateEmptyLeaderboard(game);

    }

    @Test
    public void updateAllLeaderboardsTestIfRepositoryIsNOTEmpty() {

        List<Leaderboard> leaderboards = new ArrayList<>();
        List<Game> games= new ArrayList<>(Arrays.asList(new Game(null, player, 10L,LocalDateTime.now()),
                new Game(null, player, 15L,LocalDateTime.now())));
        Game game = new Game(null, player, 20L,LocalDateTime.now());
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setAllTimeHighestScores(games);
        leaderboard.setLastSevenDaysHighestScores(games);
        leaderboard.setLastThirtyDaysHighestScores(games);
        leaderboards.add(leaderboard);

        when(leaderboardRepository.findAll()).thenReturn(leaderboards);

        leaderboardService.updateAllLeaderboards(game);

        verify(leaderboardRepository, Mockito.times(2)).findAll();
        verify(leaderboardService, Mockito.times(3)).updateLeaderboard(games, game);
    }



    @Test
    public void getAllTimes() {
        List<Leaderboard> leaderboards = new ArrayList<>();
        List<Game> games= new ArrayList<>(Arrays.asList(new Game(null, player, 10L,LocalDateTime.now()),
                new Game(null, player, 15L,LocalDateTime.now())));
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setAllTimeHighestScores(games);
        leaderboard.setLastSevenDaysHighestScores(games);
        leaderboard.setLastThirtyDaysHighestScores(games);
        leaderboards.add(leaderboard);
        when(leaderboardRepository.findAll()).thenReturn(leaderboards);
        List<Game> gamesFound = leaderboardService.getAllTimes();
        verify(leaderboardRepository).findAll();
        Assert.assertEquals(games, gamesFound);
    }

    @Test
    public void getLastThirtyDays() {
        List<Leaderboard> leaderboards = new ArrayList<>();
        List<Game> games= new ArrayList<>(Arrays.asList(new Game(null, player, 10L,LocalDateTime.now()),
                new Game(null, player, 15L,LocalDateTime.now())));
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setAllTimeHighestScores(games);
        leaderboard.setLastSevenDaysHighestScores(games);
        leaderboard.setLastThirtyDaysHighestScores(games);
        leaderboards.add(leaderboard);
        when(leaderboardRepository.findAll()).thenReturn(leaderboards);
        List<Game> gamesFound = leaderboardService.getLastThirtyDays();
        verify(leaderboardRepository).findAll();
        Assert.assertEquals(games, gamesFound);
    }

    @Test
    public void getLastSevenDays() {

        List<Leaderboard> leaderboards = new ArrayList<>();
        List<Game> games= new ArrayList<>(Arrays.asList(new Game(null, player, 10L,LocalDateTime.now()),
                new Game(null, player, 15L,LocalDateTime.now())));
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setAllTimeHighestScores(games);
        leaderboard.setLastSevenDaysHighestScores(games);
        leaderboard.setLastThirtyDaysHighestScores(games);
        leaderboards.add(leaderboard);
        when(leaderboardRepository.findAll()).thenReturn(leaderboards);
        List<Game> gamesFound = leaderboardService.getLastSevenDays();
        verify(leaderboardRepository).findAll();
        Assert.assertEquals(games, gamesFound);
    }

}