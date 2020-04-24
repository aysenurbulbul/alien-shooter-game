package com.example.server.service;

import com.example.server.model.Game;
import com.example.server.repository.GameRepository;
import com.example.server.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final LeaderboardService leaderboardService;
    private final PlayerRepository playerRepository;

    public Game addGame(String username, Game game){

        //sets game's player with the given username
        game.setPlayer(playerRepository.findByUsername(username));
        //updates the leaderboard if neccesarry.
        leaderboardService.updateAllLeaderboards(game);
        return gameRepository.save(game);
    }

    public List<Game> getAllGames(){
        return gameRepository.findAll();
    }

    public void clearAll(){ gameRepository.deleteAll();}
}
