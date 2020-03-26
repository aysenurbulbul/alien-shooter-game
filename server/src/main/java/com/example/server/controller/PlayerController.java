package com.example.server.controller;

import com.example.server.model.Player;
import com.example.server.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/registration")
    public String registrationThing(){
        return "register";
    }

    @PostMapping("/registration")
    public Player registerPlayer(@RequestBody final Player player){
        return playerService.addPlayer(player);
    }

    /*
    @GetMapping("/login")
    public String loginPlayer(Model model, String error, String logout){
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }*/

    @GetMapping("/players/{username}")
    public Player getPlayer(@PathVariable String username){
        return playerService.getPlayer(username);
    }

    @GetMapping("/players")
    public List<Player> getAllPlayers(){
        return playerService.getAllPlayers();
    }
}
