package com.example.server.controller;

import com.example.server.model.Game;
import com.example.server.model.Player;
import com.example.server.service.GameService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    @PostMapping("/addGame")
    public Game addGame(@RequestBody final Game game){
        return gameService.addGame(game);
    }
}
