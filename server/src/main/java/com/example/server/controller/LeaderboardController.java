package com.example.server.controller;


import com.example.server.model.Game;
import com.example.server.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leaderboard")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    //gets last seven days highest scores.
    @GetMapping("/getLastSevenDays")
    public List<Game> getLastSevenDays(){
        return leaderboardService.getLastSevenDays();
    }

    //gets last thirty days highest scores.
    @GetMapping("/getLastThirtyDays")
    public List<Game> getLastThirtyDays(){
        return leaderboardService.getLastThirtyDays();
    }

    //gets all times highest scores.
    @GetMapping("/getAllTimes")
    public List<Game> getAllTimes(){
        return leaderboardService.getAllTimes();
    }

    //deletes all the leadboards.
    //written for testing purposes.
    //users will not be able to use this.
    @DeleteMapping("/delete")
    public void deleteAll(){ leaderboardService.clearAll();}
}
