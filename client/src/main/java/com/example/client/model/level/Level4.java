package com.example.client.model.level;

import com.example.client.model.Alien;

public class Level4 extends AbstractLevel{

    public Level4(){
        //easy
        for(int i=0; i<2 ; i++){
            Alien alienHard = new Alien("HARD",1, 125 + i * 500 , 100, true, "/static/shipPink_manned.png");
            addAlien(alienHard);
        }

        //hard
        Alien alienHard = new Alien("MEDIUM", 2, 375, 100, false, "/static/shipGreen_manned.png");
        addAlien(alienHard);

        //medium
        for(int i=0; i<2 ; i++){
            Alien alienMedium = new Alien("MEDIUM",2, 250 + i * 250 , 200, false, "/static/shipGreen_manned.png");
            addAlien(alienMedium);
        }
    }
}
