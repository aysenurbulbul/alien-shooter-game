package com.example.server.service;

import com.example.server.model.Game;
import com.example.server.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public Game addGame(Game game){
        return gameRepository.save(game);
    }

}
