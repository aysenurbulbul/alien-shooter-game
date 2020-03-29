package com.example.server.controller;

import com.example.server.model.Game;
import com.example.server.service.GameService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    //add the game from given json
    @PostMapping("/addGame")
    public Game addGame(@RequestBody final Game game){
        return gameService.addGame(game);
    }

    //get all games in the database
    @GetMapping("/getAllGames")
    public List<Game> getAllGames(){
        return gameService.getAllGames();
    }

    //delete all games in the database
    @DeleteMapping("/delete")
    public void deleteAll(){ gameService.clearAll();}
}
