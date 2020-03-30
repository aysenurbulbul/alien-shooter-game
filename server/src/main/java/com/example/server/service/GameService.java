package com.example.server.service;

import com.example.server.model.Game;
import com.example.server.repository.GameRepository;
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


    public Game addGame(Game game){

        //updates the leaderboard if neccesarry.
        leaderboardService.updateAllLeaderboards(game);
        return gameRepository.save(game);
    }

    public List<Game> getAllGames(){
        return gameRepository.findAll();
    }

    public void clearAll(){ gameRepository.deleteAll();}
}
