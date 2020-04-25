package com.example.client.view;

import com.example.client.StageInitializer;
import com.example.client.model.Bullet;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameView {

    private AnchorPane anchorPane;
    private Scene gameScene;
    private Stage gameStage;
    private final String playerShipPath = "/static/playerShip1_red.png";
    private final String startButtonBackground = "/static/darkPurple.png";
    private final String gameBackground = "/static/purple.png";
    private AnimationTimer animationTimer;
    private List<Bullet> bullets;
    private double t = 0;

    public GameView(){
        anchorPane = new AnchorPane();
        gameScene = new Scene(anchorPane, 800, 600);
        gameStage = new Stage();
        bullets = new ArrayList<>();
    }


    private void createBackground(String backgroundImagePath){
        Image background = new Image(backgroundImagePath, 256,256,false, true);
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        anchorPane.setBackground(new Background(backgroundImage));
    }

    private void gameLoop(){
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };

        animationTimer.start();
    }

    private void update(){
        t += 0.016;
        if(t>2){
            fireClicked();
            bullets.forEach(Bullet::moveUp);
            t = 0;
        }
        anchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Bullet bullet = new Bullet(mouseEvent.getSceneX() + 11.5, mouseEvent.getSceneY() - 40, "/static/laserBlue03.png");
                bullets.add(bullet);
                anchorPane.getChildren().add(bullet.getImageView());
            }
        });
        // mouse moved dediğinde sadece mouse movelarsa atıyor durursa atmıyor

        /*anchorPane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(t>2){
                    Bullet bullet = new Bullet(mouseEvent.getSceneX() + 11.5, mouseEvent.getSceneY() - 40, "/static/laserBlue03.png");
                    bullets.add(bullet);
                    anchorPane.getChildren().add(bullet.getImageView());
                    t = 0;
                }


            }
        });*/



    }

    private void fireClicked(){
        Event.fireEvent(anchorPane, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
                0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                true, true, true, true, true, true, null));
    }

    public void gameStart() {
        gameStage = StageInitializer.parentStage;
        gameStage.setScene(gameScene);
        Image cursorImage = new Image(playerShipPath);
        gameScene.setCursor(new ImageCursor(cursorImage));
        createBackground(gameBackground);
        gameLoop();
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
