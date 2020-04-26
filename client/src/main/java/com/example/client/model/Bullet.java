package com.example.client.model;

import javafx.scene.image.ImageView;

public class Bullet {
    private String type;
    private double positionX;
    private double positionY;
    private String imagePath;
    private ImageView imageView;


    public ImageView getImageView() {
        return imageView;
    }

    public Bullet(String type, double positionX, double positionY, String imagePath) {
        this.type = type;
        this.positionX = positionX;
        this.positionY = positionY;
        this.imagePath = imagePath;
        imageView = new ImageView(imagePath);
        imageView.setLayoutX(positionX);
        imageView.setLayoutY(positionY);
    }

    public double getPositionX() {
        return imageView.getTranslateX();

    }

    public double getPositionY() {
        return imageView.getTranslateY();
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getImagePositionX(){
        return imageView.getLayoutX();
    }

    public double getImagePositionY(){
        return imageView.getLayoutY();
    }

    public void moveUp(){
        imageView.setTranslateY(imageView.getTranslateY() - 50);
    }

    public void moveDown(){
        imageView.setTranslateY(imageView.getTranslateY() + 50);
    }
}
