package com.example.client.view;

import com.example.client.StageInitializer;
import com.example.client.model.Alien;
import com.example.client.model.Bullet;
import com.example.client.model.InfoLabel;
import com.example.client.model.Ship;
import com.example.client.model.level.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.client.constant.ControllerConstants.GAME_FXML;
import static com.example.client.constant.GameViewConstants.*;

public class GameView {

    private AnchorPane anchorPane;
    private Scene gameScene;
    private Stage gameStage;
    private final String playerShipPath = PLAYER_SHIP;
    private final String gameBackground = GAME_BACKGROUND;
    private final String scoreString = INFO_LABEL_SCOREBOARD;
    private final String levelString = INFO_LABEL_LEVEL;
    private AnimationTimer animationTimer;
    private double t = 0;
    private List<AbstractLevel> levels;
    private int level;
    private double mousePositionX;
    private double mousePositionY;
    private Ship playerShip;
    private InfoLabel scoreboard;
    private InfoLabel levelLabel;
    private List<ImageView> shipHealthImages;
    private int score;

    public GameView(AnchorPane anchorPane, Scene gameScene, Stage gameStage){
        this.anchorPane = anchorPane;
        this.gameScene = gameScene;
        this.gameStage = gameStage;
        playerShip = new Ship(playerShipPath);
        scoreboard = new InfoLabel("SCORE: 000",120);
        scoreboard.setLayoutX(SCOREBOARD_LAYOUT_X);
        scoreboard.setLayoutY(SCOREBOARD_LAYOUT_Y);
        levelLabel = new InfoLabel("LEVEL: 1",120);
        levelLabel.setLayoutX(LEVEL_LAYOUT_X);
        levelLabel.setLayoutY(LEVEL_LAYOUT_Y);
        anchorPane.getChildren().add(scoreboard);
        anchorPane.getChildren().add(levelLabel);
        score = 0;
        shipHealthImages = new ArrayList<>();
        for(int i=0; i< playerShip.getHealth(); i++){
            ImageView imageView = new ImageView(PLAYER_LIFE);
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            imageView.setLayoutX(665 + i * 30);
            imageView.setLayoutY(55);
            shipHealthImages.add(imageView);
            anchorPane.getChildren().add(imageView);
        }
    }


    private void createBackground(String backgroundImagePath){
        Image background = new Image(backgroundImagePath, 256,256,false, true);
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        anchorPane.setBackground(new Background(backgroundImage));
    }

    private void isLevelFinished()  {
        if(levels.get(level).getAliens().size() == 0){
            ++level;
            if(level<NUMBER_OF_LEVELS){
                levels.get(level).getAliens().forEach(alien -> {anchorPane.getChildren().add(alien.getImageView());});
                playerShip.getBullets().forEach(bullet -> anchorPane.getChildren().remove(bullet.getImageView()));
                playerShip.clearBullets();
            }
            else{
                gameScene.setCursor(Cursor.DEFAULT);
                animationTimer.stop();
                GameSubView gameDoneView = new GameSubView(score, "Congratulations!");
                gameDoneView.setLayoutX(100);
                gameDoneView.setLayoutY(100);
                anchorPane.getChildren().add(gameDoneView);
            }
        }
    }

    private void cheat(){
        Iterator<Alien> alienIterator = levels.get(level).getAliens().iterator();
        while(alienIterator.hasNext()){
            Alien alien = alienIterator.next();
            addToScore(alien);
            Iterator<Bullet> bulletIterator = alien.getBullets().iterator();
            while (bulletIterator.hasNext()){
                Bullet bullet = bulletIterator.next();
                anchorPane.getChildren().remove(bullet.getImageView());
                bulletIterator.remove();
            }
            anchorPane.getChildren().remove(alien.getImageView());
            alienIterator.remove();
            isLevelFinished();
        }
    }

    private void gameLoop(){
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                t += TIMER_INCREASE;
                if(t>TIMER_SHOULD_BE_LESS){
                    addNewPlayerBullet();
                    updatePlayerBullet();
                    alienShoot();
                    isLevelFinished();
                    writeLabels();
                    t = 0;
                }
                updateShipPosition();
            }
        };
        animationTimer.start();
    }

    private boolean isOnSamePosition(Alien alien, Bullet bullet){
        return bullet.getImageView().getBoundsInParent().intersects(alien.getImageView().getBoundsInParent());
    }
    private boolean isOnSamePosition(Ship playerShip, Bullet bullet){
        return bullet.getImageView().getBoundsInParent().intersects(playerShip.getShipImage().getBoundsInParent());
    }

    private void addNewPlayerBullet(){
        Bullet newBullet = new Bullet("PLAYER", mousePositionX + 20, mousePositionY + 7, "/static/laserBlue03.png");
        playerShip.addBullet(newBullet);
        anchorPane.getChildren().add(newBullet.getImageView());
    }

    private void updatePlayerBullet(){
        Iterator<Bullet> bulletIterator = playerShip.getBullets().iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.moveUp();
            Iterator<Alien> alienIterator = levels.get(level).getAliens().iterator();
            while(alienIterator.hasNext()){
                Alien alien = alienIterator.next();
                if(isOnSamePosition(alien, bullet)){
                    anchorPane.getChildren().remove(bullet.getImageView());
                    bulletIterator.remove();
                    alien.decreaseHealth();
                    if(alien.getHealth()<= ZERO_HEALTH){
                        alien.getBullets().forEach(alienBullet -> {
                            anchorPane.getChildren().remove(alienBullet.getImageView());
                        });
                        anchorPane.getChildren().remove(alien.getImageView());
                        //score++;
                        addToScore(alien);
                        alienIterator.remove();
                    }
                    break;
                }
            }
        }
    }

    private void addToScore(Alien alien){
        switch (alien.getType()){
            case "EASY":
                score = score + EASY_SCORE_INCREASE;
                break;
            case "MEDIUM":
                score = score + MEDIUM_SCORE_INCREASE;
                break;
            case "HARD":
                score = score + HARD_SCORE_INCREASE;
                break;
        }
    }

    private void writeLabels(){
        if(score<10){
            scoreboard.setText(scoreString + "00" + score);
        } else if(score>10 && score<100){
            scoreboard.setText(scoreString + "0" + score);
        } else {
            scoreboard.setText(scoreString + score);
        }
        levelLabel.setText(levelString + (level+1));
    }

    private void alienBulletShoot(Alien alien){
        Iterator<Bullet> bulletIterator = alien.getBullets().iterator();
        while(bulletIterator.hasNext()){
            Bullet bullet = bulletIterator.next();
            if(isOnSamePosition(playerShip, bullet)){
                anchorPane.getChildren().remove(bullet.getImageView());
                bulletIterator.remove();
                playerShip.setHealth(playerShip.getHealth()-1);
                int shipHealth = playerShip.getHealth();
                if(shipHealth>ZERO_HEALTH){
                    anchorPane.getChildren().remove(shipHealthImages.get(shipHealth));
                }
                else{
                    GameSubView gameDoneView = new GameSubView(score, "LOSER... YOU SUCK!");
                    gameDoneView.setLayoutX(100);
                    gameDoneView.setLayoutY(100);
                    anchorPane.getChildren().add(gameDoneView);
                    gameScene.setCursor(Cursor.DEFAULT);
                    animationTimer.stop();
                    anchorPane.getChildren().remove(playerShip.getShipImage());
                }
            }
        }
    }

    private void updateShipPosition(){

        anchorPane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mousePositionX = mouseEvent.getSceneX();
                mousePositionY = mouseEvent.getSceneY();
                playerShip.getShipImage().setLayoutX(mouseEvent.getSceneX());
                playerShip.getShipImage().setLayoutY(mouseEvent.getSceneY());
            }
        });
    }

    private void alienShoot(){
        levels.get(level).getAliens().forEach(alien -> {
            if(alien.isCanShoot()){
                if(Math.random()<ALIEN_SHOOT_RANDOM){
                    Bullet bullet = new Bullet("ENEMY",alien.getPositionX()+21.5, alien.getPositionY()+20, "/static/laserRed03.png");
                    alien.addBullet(bullet);
                    anchorPane.getChildren().add(bullet.getImageView());
                }
                alien.getBullets().forEach(Bullet::moveDown);
                alienBulletShoot(alien);
            }
        });
    }

    private void moveCursor() {
        Platform.runLater(() -> {
            Robot robot = new Robot();
            robot.mouseMove(robot.getMouseX(), robot.getMouseY()+200);
        });
    }

    private void initLevels(){
        levels = new ArrayList<>();
        Level1 level1 = new Level1();
        levels.add(level1);
        Level2 level2 = new Level2();
        levels.add(level2);
        Level3 level3 = new Level3();
        levels.add(level3);
        Level4 level4 = new Level4();
        levels.add(level4);
        level = 0;
        level1.getAliens().forEach(alien -> {anchorPane.getChildren().add(alien.getImageView());});
    }

    public void gameStart() {
        gameStage = StageInitializer.parentStage;
        gameStage.setScene(gameScene);
        gameScene.setCursor(Cursor.NONE);
        anchorPane.getChildren().add(playerShip.getShipImage());
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.isControlDown() && keyEvent.isShiftDown() && keyEvent.getCode() == KeyCode.DIGIT9){
                    cheat();
                }
            }
        });
        moveCursor();
        createBackground(gameBackground);
        initLevels();
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
