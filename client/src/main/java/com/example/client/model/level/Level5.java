package com.example.client.model.level;

import com.example.client.model.Alien;

public class Level5 extends AbstractLevel{

    public Level5(){
        Alien finalAlien = new Alien("FINAL",15, 300 , 150, true, "/static/shipYellow_manned.png");
        addAlien(finalAlien);
    }
}
