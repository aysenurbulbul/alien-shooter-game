package com.example.client.model;

import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * player's ship class
 */
public class Ship {
    private ImageView shipImage;
    private int health;
    // list of bullets that player fires
    private List<Bullet> bullets;

    public Ship(String imagePath){
        shipImage = new ImageView(imagePath);
        shipImage.setFitHeight(50);
        shipImage.setFitWidth(50);
        health = 3;
        bullets = new ArrayList<>();
    }

    public ImageView getShipImage() {
        return shipImage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    /**
     * adds the bullets that is fired from player
     * @param bullet that player fired
     */
    public void addBullet(Bullet bullet){
        bullets.add(bullet);
    }

    public void clearBullets(){
        bullets.clear();
    }
}
