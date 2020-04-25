package com.example.client.model;

import javafx.scene.image.ImageView;

public class Ship {
    private ImageView shipImage;
    private int health;

    public Ship(String imagePath){
        shipImage = new ImageView(imagePath);
        shipImage.setFitHeight(50);
        shipImage.setFitWidth(50);
        health = 3;
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
}
