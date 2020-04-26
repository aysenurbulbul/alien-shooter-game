package com.example.client.model.level;

import com.example.client.model.Alien;

public class Level1 extends AbstractLevel{

    public Level1(){
        // create 3 aliens type EASY
        for(int i=0; i<3 ; i++){
            Alien alien = new Alien("EASY",1, 100 + i * 250 , 150, false, "/static/shipBlue_manned.png");
            addAlien(alien);
        }
    }
}
