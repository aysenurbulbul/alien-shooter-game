package com.example.client.view;

import com.example.client.StageInitializer;
import com.example.client.model.Alien;
import com.example.client.model.Bullet;
import com.example.client.model.level.AbstractLevel;
import com.example.client.model.level.Level1;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameView {

    private AnchorPane anchorPane;
    private Scene gameScene;
    private Stage gameStage;
    private final String playerShipPath = "/static/playerShip1_red.png";
    private final String gameBackground = "/static/purple.png";
    private AnimationTimer animationTimer;
    private List<Bullet> bullets;
    private double t = 0;
    private AbstractLevel level;
    private double mousePositionX;
    private double mousePositionY;

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

    private boolean isOnSamePosition(Alien alien, Bullet bullet){

        if(bullet.getImageView().getBoundsInParent().intersects(alien.getImageView().getBoundsInParent())){
            return true;
        }
        return false;
    }

    private void addNewPlayerBullet(){
        Bullet newBullet = new Bullet("PLAYER", mousePositionX, mousePositionY -20, "/static/laserBlue03.png");
        bullets.add(newBullet);
        anchorPane.getChildren().add(newBullet.getImageView());
    }

    private void updatePlayerBullet(){
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.moveUp();
            Iterator<Alien> alienIterator = level.getAliens().iterator();
            while(alienIterator.hasNext()){
                Alien alien = alienIterator.next();
                if(isOnSamePosition(alien, bullet)){
                    System.out.println("SAME");
                    anchorPane.getChildren().remove(bullet.getImageView());
                    bulletIterator.remove();
                    alien.decreaseHealth();
                    if(alien.getHealth()<= 0){
                        anchorPane.getChildren().remove(alien.getImageView());
                        alienIterator.remove();
                    }
                    break;
                }
            }
        }
    }


    private void update(){
        t += 0.05;
        if(t>2){
            addNewPlayerBullet();
            updatePlayerBullet();
            alienShoot();
            t = 0;
        }
        anchorPane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mousePositionX = mouseEvent.getSceneX();
                mousePositionY = mouseEvent.getSceneY();
            }
        });
    }

    private void alienShoot(){
        level.getAliens().forEach(alien -> {
            if(alien.isCanShoot()){
                if(Math.random()<0.3){
                    Bullet bullet = new Bullet("ENEMY",alien.getPositionX()+21.5, alien.getPositionY()+20, "/static/laserRed03.png");
                    alien.addBullet(bullet);
                    anchorPane.getChildren().add(bullet.getImageView());
                }
                alien.getBullets().forEach(Bullet::moveDown);
            }
        });
    }

    private void moveCursor() {
        Platform.runLater(() -> {
            Robot robot = new Robot();
            robot.mouseMove(robot.getMouseX(), robot.getMouseY()+200);
        });
    }

    public void gameStart() {
        gameStage = StageInitializer.parentStage;
        gameStage.setScene(gameScene);
        Image cursorImage = new Image(playerShipPath);
        moveCursor();
        gameScene.setCursor(new ImageCursor(cursorImage));
        createBackground(gameBackground);
        Level1 level1 = new Level1();
        level1.getAliens().forEach(alien -> {anchorPane.getChildren().add(alien.getImageView());});
        level = level1;
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
