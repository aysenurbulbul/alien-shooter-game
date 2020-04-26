package com.example.client.view;

import com.example.client.StageInitializer;
import com.example.client.model.GameButton;
import com.example.client.model.InfoLabel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.example.client.constant.ControllerConstants.GAME_FXML;
import static com.example.client.constant.GameViewConstants.BACKGROUND_IMAGE;
import static com.example.client.constant.GameViewConstants.FONT_PATH;

/**
 * is showed after player loses or finishes all levels
 */
class GameSubView extends SubScene {

    GameSubView(int score, String text) {
        super(new AnchorPane(), 600, 400);
        prefHeight(600);
        prefWidth(400);
        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 200,200, false, true),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                null);
        AnchorPane root = (AnchorPane) this.getRoot();
        root.setBackground(new Background(image));
        setHeadLine(text, 130,100);
        createButton("Back to Start", 205, 250);
        createInfoLabel("YOUR FINAL SCORE: " + score, 192,175);
    }

    /**
     * button that enables player to get back to menu
     * @param text text on button
     * @param x coordinate x
     * @param y coordinate y
     */
    private void createButton(String text, double x, double y){
        GameButton button = new GameButton (text);
        button.setLayoutX(x);
        button.setLayoutY(y);
        try {
            button.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 15)); // set to Label
        } catch (FileNotFoundException e) {
            button.setFont(Font.font("Verdana", 15));
        }
        AnchorPane root = (AnchorPane) this.getRoot();
        root.getChildren().add(button);
        button.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                backToGameController();
            }
        });
    }

    /**
     * creates label that shows the final score of player
     * @param text label' text
     * @param x coordinate x
     * @param y coordinate y
     */
    private void createInfoLabel(String text, double x, double y){
        InfoLabel infoLabel = new InfoLabel(text,220);
        infoLabel.setLayoutX(x);
        infoLabel.setLayoutY(y);
        AnchorPane root = (AnchorPane) this.getRoot();
        root.getChildren().add(infoLabel);
    }

    /**
     * set headline
     * @param text headline text
     * @param x coordinate x
     * @param y coordinate y
     */
    private void setHeadLine(String text, double x, double y){
        Label label = new Label();
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setText(text);
        try {
            label.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 30)); // set to Label
        } catch (FileNotFoundException e) {
            label.setFont(Font.font("Verdana", 30));
        }
        label.setTextFill(Color.WHITE);
        AnchorPane root = (AnchorPane) this.getRoot();
        root.getChildren().add(label);
    }

    /**
     * loads game page when user clicks "Back to start" button
     */
    private void backToGameController(){
        try{
            Parent parent = FXMLLoader.load(getClass().getResource(GAME_FXML));
            Stage mainStage = StageInitializer.parentStage;
            mainStage.getScene().setRoot(parent);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
