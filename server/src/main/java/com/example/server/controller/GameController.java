package com.example.server.controller;

import com.example.server.model.Game;
import com.example.server.service.GameService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    //add the game from given json
    @PostMapping(value = "/addGame/{username}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addGame(@PathVariable (value = "username") String username,
                                          @Valid @RequestBody Game game){
        gameService.addGame(username,game);
        return ResponseEntity.ok("Game successfully added");
    }

    //get all games in the database
    @GetMapping("/getAllGames")
    public List<Game> getAllGames(){
        return gameService.getAllGames();
    }

    //delete all games in the database
    //written for testing purposes.
    //users will not be able to use this.
    @DeleteMapping("/delete")
    public void deleteAll(){ gameService.clearAll();}

    //sends bad request response if in addGame function the request body is not valid.
    //checks the validation according to validation tags (ex. @NotNull) in the related entity.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

