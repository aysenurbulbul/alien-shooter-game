package com.example.client.view;

import com.example.client.StageInitializer;
import com.example.client.model.GameButton;
import com.example.client.model.InfoLabel;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.client.constant.ControllerConstants.GAME_FXML;


public class GameSubView extends SubScene {

    private final static String BACKGROUND_IMAGE = "/static/grey_panel.png";
    public GameSubView(int score, String text) {
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

        setHeadLine(text, 200,100);
        createButton("Back to Start", 200, 200);
        createInfoLabel("YOUR FINAL SCORE: " + score, 200,150);
    }
    private void createButton(String text, double x, double y){
        GameButton button = new GameButton (text);
        button.setLayoutX(x);
        button.setLayoutY(y);
        AnchorPane root = (AnchorPane) this.getRoot();
        root.getChildren().add(button);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    backToGameController();
                }
            }
        });
    }
    private void createInfoLabel(String text, double x, double y){
        InfoLabel infoLabel = new InfoLabel(text,200);
        infoLabel.setLayoutX(x);
        infoLabel.setLayoutY(y);
        AnchorPane root = (AnchorPane) this.getRoot();
        root.getChildren().add(infoLabel);
    }
    private void setHeadLine(String text, double x, double y){
        final double MAX_FONT_SIZE = 30.0; // define max font size you need
        Label label = new Label();
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setText(text);
        label.setFont(new Font(MAX_FONT_SIZE)); // set to Label
        AnchorPane root = (AnchorPane) this.getRoot();
        root.getChildren().add(label);
    }
    private void backToGameController(){
        try{
            Parent parent = FXMLLoader.load(getClass().getResource(GAME_FXML));
            Stage mainStage = StageInitializer.parentStage;
            mainStage.getScene().setRoot(parent);
        } catch (IOException e){

        }
    }
}
