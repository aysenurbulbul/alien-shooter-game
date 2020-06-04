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
    // list of bullets that alien shoots
    private List<Bullet> bullets;
    private String type;

    /**
     * create alien
     * @param type EASY, MEDIUM, HARD
     * @param health how many bullets to kill the alien
     * @param positionX coordinate X on the pane
     * @param positionY coordinate Y on the pane
     * @param canShoot if true alien can shoot
     * @param imagePath path for alien's image view
     */
    public Alien(String type, int health, double positionX, double positionY, boolean canShoot, String imagePath) {
        this.type = type;
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
        this.imageView.setLayoutX(positionX);
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
        this.imageView.setLayoutY(positionY);
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

    public String getType() {
        return type;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    /**
     * when alien shoots add the bullet to the list
     * in our game if you kill the alien its bullets also disappears
     * @param bullet bullet that alien shoots
     */
    public void addBullet(Bullet bullet){
        bullets.add(bullet);
    }

    /**
     * is called when alien gets shot
     */
    public void decreaseHealth(){
        health = health -1;
    }



}
