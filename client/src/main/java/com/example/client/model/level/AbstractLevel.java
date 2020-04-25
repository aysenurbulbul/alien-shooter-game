package com.example.client.model.level;

import com.example.client.model.Alien;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLevel {
    private List<Alien> aliens = new ArrayList<>();
    private boolean levelPassed;

    public List<Alien> getAliens() {
        return aliens;
    }

    public boolean isLevelPassed() {
        return levelPassed;
    }

    public void setAliens(List<Alien> aliens) {
        this.aliens = aliens;
    }

    public void setLevelPassed(boolean levelPassed) {
        this.levelPassed = levelPassed;
    }

    void addAlien(Alien alien){
        aliens.add(alien);
    }
}
