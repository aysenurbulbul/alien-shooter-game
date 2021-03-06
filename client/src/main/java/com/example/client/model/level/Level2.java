package com.example.client.model.level;

import com.example.client.model.Alien;

public class Level2 extends AbstractLevel{

    public Level2(){
        //create 3 aliens type EASY
        for(int i=0; i<3 ; i++){
            Alien alien = new Alien("EASY",1, 125 + i * 250 , 200, false, "/static/shipBlue_manned.png");
            addAlien(alien);
        }

        //create 2 aliens type MEDIUM
        for(int i=0; i<2 ; i++){
            Alien alien = new Alien("MEDIUM",2, 250 + i * 250 , 100, false, "/static/shipGreen_manned.png");
            addAlien(alien);
        }
    }
}
