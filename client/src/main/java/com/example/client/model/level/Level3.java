package com.example.client.model.level;

import com.example.client.model.Alien;

public class Level3 extends AbstractLevel{

    public Level3(){
        //create 2 aliens type EASY
        for(int i=0; i<2 ; i++){
            Alien alienEasy = new Alien("EASY",1, 125 + i * 500 , 100, false, "/static/shipBlue_manned.png");
            addAlien(alienEasy);
        }

        //create a alien type HARD
        Alien alienHard = new Alien("HARD", 3, 375, 100, true, "/static/shipPink_manned.png");
        addAlien(alienHard);

        //create 2 aliens type EASY
        for(int i=0; i<2 ; i++){
            Alien alienMedium = new Alien("MEDIUM",2, 250 + i * 250 , 200, false, "/static/shipGreen_manned.png");
            addAlien(alienMedium);
        }
    }
}
