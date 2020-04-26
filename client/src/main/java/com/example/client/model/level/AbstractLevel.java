package com.example.client.model.level;

import com.example.client.model.Alien;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLevel {
    /**
     * list of aliens that will be rendered in that level
     */
    private List<Alien> aliens = new ArrayList<>();

    public List<Alien> getAliens() {
        return aliens;
    }

    void addAlien(Alien alien){
        aliens.add(alien);
    }
}
