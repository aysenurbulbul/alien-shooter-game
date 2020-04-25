package com.example.client.model;

import javafx.scene.image.ImageView;

public class Alien {
    private double positionX;
    private double positionY;
    private int health;
    private String imagePath;
    private ImageView imageView;

    public Alien(int health, double positionX, double positionY, String imagePath) {
        this.health = health;
        this.positionX = positionX;
        this.positionY = positionY;
        this.imagePath = imagePath;
        imageView = new ImageView(imagePath);
        imageView.setLayoutX(positionX);
        imageView.setLayoutY(positionY);
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public int getHealth() {
        return health;
    }

    public String getImagePath() {
        return imagePath;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
