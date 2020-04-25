package com.example.client.model;

import javafx.scene.image.ImageView;

public class Bullet {
    private double positionX;
    private double positionY;
    private String imagePath;
    private ImageView imageView;

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Bullet(double positionX, double positionY, String imagePath) {
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

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public String getImagePath() {
        return imagePath;
    }
}
