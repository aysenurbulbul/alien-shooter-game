package com.example.client.view;

import com.example.client.StageInitializer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GameView {

    private AnchorPane anchorPane;
    private Scene gameScene;
    private Stage gameStage;

    public GameView(){
        anchorPane = new AnchorPane();
        gameScene = new Scene(anchorPane, 800, 600);
        gameStage = new Stage();
    }

    public void gameStart(){
        gameStage = StageInitializer.parentStage;
        gameStage.setScene(gameScene);
        Button button = new Button();
        anchorPane.getChildren().add(button);
    }

    public Stage getGameStage() {
        return gameStage;
    }

    public void setGameStage(Stage gameStage) {
        this.gameStage = gameStage;
    }

    public Scene getGameScene() {
        return gameScene;
    }

    public void setGameScene(Scene gameScene) {
        this.gameScene = gameScene;
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }
}
