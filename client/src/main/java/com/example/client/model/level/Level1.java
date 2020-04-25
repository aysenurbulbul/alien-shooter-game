package com.example.client.model.level;

import com.example.client.model.Alien;

public class Level1 extends AbstractLevel{

    public Level1(){
        for(int i=0; i<3 ; i++){
            Alien alien = new Alien(1, 100 + i * 250 , 150, true, "/static/shipBlue_manned.png");
            addAlien(alien);
        }
    }
}
