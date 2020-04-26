package com.example.client.model;


import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameButton extends Button {

    private final String FONT_PATH = "/font/kenvector_future.ttf";
    private final String IMAGE_PATH = "/static/blue_button13.png";

    public GameButton(String text){
        setText(text);
        setButtonFont();
        setPrefHeight(40);
        setPrefWidth(190);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(IMAGE_PATH, 190, 40, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(backgroundImage));
    }

    private void setButtonFont(){
        try {
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 12));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 15));
        }
    }
}
