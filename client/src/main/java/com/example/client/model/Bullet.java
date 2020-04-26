package com.example.client.model;

import javafx.scene.image.ImageView;

public class Bullet {
    private String type;
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

    public Bullet(String type, double positionX, double positionY, String imagePath) {
        this.type = type;
        this.positionX = positionX;
        this.positionY = positionY;
        this.imagePath = imagePath;
        imageView = new ImageView(imagePath);
        imageView.setLayoutX(positionX);
        imageView.setLayoutY(positionY);
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
        this.imageView.setTranslateX(positionX);
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
        this.imageView.setTranslateY(positionY);
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
        return imageView.getTranslateX()+0;
    }

    public double getImagePositionY(){
        return imageView.getTranslateY()+0;
    }

    public void moveUp(){
        imageView.setTranslateY(imageView.getTranslateY() - 50);
    }

    public void moveDown(){
        imageView.setTranslateY(imageView.getTranslateY() + 50);
    }
}
