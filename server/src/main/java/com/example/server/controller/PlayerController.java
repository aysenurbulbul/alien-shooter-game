package com.example.server.controller;

import com.example.server.model.Player;
import com.example.server.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerPlayer(@Valid @RequestBody final Player player){
        if(playerService.getPlayer(player.getUsername()) == null){
            playerService.addPlayer(player);
            return ResponseEntity.ok("Account successfully created");
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username must be unique");
        }
    }

    @GetMapping("/players/{username}")
    public Player getPlayer(@PathVariable String username){
        return playerService.getPlayer(username);
    }

    @GetMapping("/players")
    public List<Player> getAllPlayers(){
        return playerService.getAllPlayers();
    }

    @DeleteMapping("/deletePlayers")
    public void deleteAllPlayers(){
        playerService.deleteAllPlayers();
    }

    @DeleteMapping("/delete/{playerId}")
    public void deletePlayer(@PathVariable Long playerId){
        playerService.deletePlayer(playerId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> validationExceptionHandler(MethodArgumentNotValidException e){
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((err) -> {
            String fieldName = ((FieldError) err).getField();
            String errorMessage = err.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
