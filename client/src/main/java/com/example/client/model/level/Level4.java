package com.example.client.model.level;

import com.example.client.model.Alien;

public class Level4 extends AbstractLevel{

    public Level4(){
        //create 3 aliens type HARD
        for(int i=0; i<3 ; i++){
            Alien alienHard = new Alien("HARD",3, 125 + i * 250 , 100, true, "/static/shipPink_manned.png");
            addAlien(alienHard);
        }

        // create 2 aliens type MEDIUM
        for(int i=0; i<2 ; i++){
            Alien alienMedium = new Alien("MEDIUM",2, 250 + i * 250 , 200, false, "/static/shipGreen_manned.png");
            addAlien(alienMedium);
        }
    }
}
