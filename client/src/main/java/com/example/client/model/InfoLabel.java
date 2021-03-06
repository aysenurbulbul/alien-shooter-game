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

import static com.example.client.constant.GameViewConstants.FONT_PATH;
import static com.example.client.constant.GameViewConstants.LABEL_PATH;

/**
 * this class creates a label for showing level number, score and health of the ship
 */
public class InfoLabel extends Label {

    /**
     * creates label with given context, with background and font
     * @param context label's context
     */
    public InfoLabel(String context, double w){
        setPrefHeight(40);
        setPrefWidth(w);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(LABEL_PATH, w, 40, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(backgroundImage));
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10, 10, 10, 10));
        setLabelFont();
        setText(context);
    }

    /**
     * try to load font otherwise load default font
     */
    private void setLabelFont(){
        try {
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 12));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 15));
        }

    }
}
