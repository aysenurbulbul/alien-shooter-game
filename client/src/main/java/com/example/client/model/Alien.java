package com.example.client.model;

import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Alien {
    private double positionX;
    private double positionY;
    private int health;
    private String imagePath;
    private ImageView imageView;
    private boolean canShoot;
    private List<Bullet> bullets;

    public Alien(int health, double positionX, double positionY, boolean canShoot, String imagePath) {
        this.health = health;
        this.positionX = positionX;
        this.positionY = positionY;
        this.imagePath = imagePath;
        this.canShoot = canShoot;
        bullets = new ArrayList<>();
        imageView = new ImageView(imagePath);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
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

    public boolean isCanShoot() {
        return canShoot;
    }

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void addBullet(Bullet bullet){
        bullets.add(bullet);
    }

}
