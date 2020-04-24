package com.example.client.model;

import java.time.LocalDateTime;

public class Game {
    private Long id;
    private Player player;
    private Long score;
    private LocalDateTime finishDateTime;

    public Long getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public Long getScore() {
        return score;
    }

    public LocalDateTime getFinishDateTime() {
        return finishDateTime;
    }

    public String getUsername(){return player.getUsername();}

}