package com.example.client.model;


import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameButton extends Button {

    private final String FONT_PATH = "/font/kenvector_future.ttf";
    private final String IMAGE_PATH = "/static/buttonYellow.png";
    private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image url: ('/static/buttonYellow.png');";
    private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image url: ('/static/yellow_button03.png');";

    public GameButton(String text){
        setText(text);
        setButtonFont();
        setPrefHeight(190);
        setPrefWidth(49);
        setStyle(BUTTON_FREE_STYLE);
    }

    private void setButtonFont(){
        try {
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 12));
        } catch (FileNotFoundException e) {
            System.out.println("why");
            setFont(Font.font("Verdana", 15));
        }
    }
    private void setButtonPressedStyle(){
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(45);
        setLayoutY(getLayoutY()+4);
    }
    private void setButtonReleasedStyle(){
        setStyle(BUTTON_FREE_STYLE);
        setPrefHeight(49);
        setLayoutY(getLayoutY()-4);
    }
    private void initButtonListeners(){
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    setButtonReleasedStyle();
                }
            }
        });
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    setButtonPressedStyle();
                }
            }
        });
    }
}
