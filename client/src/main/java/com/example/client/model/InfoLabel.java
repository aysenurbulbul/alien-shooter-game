package com.example.client.model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class InfoLabel extends Label {
    private final String FONT_PATH = "src/main/resources/font/kenvector_future.ttf";

    public InfoLabel(String context){
        setPrefHeight(40);
        setPrefWidth(120);
        BackgroundImage backgroundImage = new BackgroundImage(new Image("/static/blue_button13.png", 120, 40, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(backgroundImage));
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(10, 10, 10, 10));
        setFont();
        setText(context);
    }

    private void setFont(){
        try {
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 12));
        } catch (FileNotFoundException e) {
            System.out.println("why");
            setFont(Font.font("Verdana", 15));
        }

    }

}
