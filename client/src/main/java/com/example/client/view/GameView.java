package com.example.client.view;

import com.example.client.StageInitializer;
import com.example.client.controller.GameController;
import com.example.client.model.Alien;
import com.example.client.model.Bullet;
import com.example.client.model.InfoLabel;
import com.example.client.model.Ship;
import com.example.client.model.level.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.client.constant.GameViewConstants.*;

public class GameView {

    private final AnchorPane anchorPane;
    private final Scene gameScene;
    private Stage gameStage;
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
        this.gameStage = StageInitializer.parentStage;
        this.gameStage.setScene(gameScene);
        this.gameScene.setCursor(Cursor.NONE);
        score = 0;
        level = 0;
        cheatHandle();
        moveCursor();
        createBackground();
        createPlayerShip();
        createLevelInfo();
        createScoreboard();
        createPlayerShipHealthInfo();
        initLevels();
    }
    private void createPlayerShip(){
        playerShip = new Ship(PLAYER_SHIP);
        this.anchorPane.getChildren().add(playerShip.getShipImage());
    }
    private void createPlayerShipHealthInfo(){
        shipHealthImages = new ArrayList<>();
        for(int i=0; i< playerShip.getHealth(); i++){
            ImageView imageView = new ImageView(PLAYER_LIFE);
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            imageView.setLayoutX(675 + i * 30);
            imageView.setLayoutY(55);
            shipHealthImages.add(imageView);
            anchorPane.getChildren().add(imageView);
        }
    }
    private void createLevelInfo(){
        levelLabel = new InfoLabel("LEVEL: 1",120);
        levelLabel.setLayoutX(LEVEL_LAYOUT_X);
        levelLabel.setLayoutY(LEVEL_LAYOUT_Y);
        anchorPane.getChildren().add(levelLabel);
    }

    private void createScoreboard(){
        scoreboard = new InfoLabel("SCORE: 000",120);
        scoreboard.setLayoutX(SCOREBOARD_LAYOUT_X);
        scoreboard.setLayoutY(SCOREBOARD_LAYOUT_Y);
        anchorPane.getChildren().add(scoreboard);
    }

    private void createBackground(){
        Image background = new Image(GAME_BACKGROUND, 256,256,false, true);
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        anchorPane.setBackground(new Background(backgroundImage));
    }

    private void gameFinished(String text){
        gameScene.setCursor(Cursor.DEFAULT);
        animationTimer.stop();
        GameController.setScore(score);
        GameController.addGame();
        GameSubView gameDoneView = new GameSubView(score, text);
        gameDoneView.setLayoutX(100);
        gameDoneView.setLayoutY(100);
        anchorPane.getChildren().add(gameDoneView);
    }

    private void isGameFinished(){
        if(level == NUMBER_OF_LEVELS ){
            gameFinished("CONGRATULATIONS!");
        }
        if(playerShip.getHealth() == ZERO_HEALTH){
            gameFinished("LOSER... YOU SUCK");
        }

    }

    private void isLevelFinished()  {
        if(levels.get(level).getAliens().size() == 0){
            ++level;
            if(level<NUMBER_OF_LEVELS){
                levels.get(level).getAliens().forEach(alien -> anchorPane.getChildren().add(alien.getImageView()));
                playerShip.getBullets().forEach(bullet -> anchorPane.getChildren().remove(bullet.getImageView()));
                playerShip.clearBullets();
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
            isGameFinished();
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
                    writeLabels();
                    isLevelFinished();
                    isGameFinished();
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
                        alien.getBullets().forEach(alienBullet -> anchorPane.getChildren().remove(alienBullet.getImageView()));
                        anchorPane.getChildren().remove(alien.getImageView());
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
            scoreboard.setText(INFO_LABEL_SCOREBOARD + "00" + score);
        } else if(score>10 && score<100){
            scoreboard.setText(INFO_LABEL_SCOREBOARD + "0" + score);
        } else {
            scoreboard.setText(INFO_LABEL_SCOREBOARD + score);
        }
        levelLabel.setText(INFO_LABEL_LEVEL + (level+1));
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
            }
        }
    }

    private void updateShipPosition(){

        anchorPane.setOnMouseMoved(mouseEvent -> {
            mousePositionX = mouseEvent.getSceneX();
            mousePositionY = mouseEvent.getSceneY();
            playerShip.getShipImage().setLayoutX(mouseEvent.getSceneX());
            playerShip.getShipImage().setLayoutY(mouseEvent.getSceneY());
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
        level1.getAliens().forEach(alien -> anchorPane.getChildren().add(alien.getImageView()));
    }

    public void cheatHandle(){
        gameScene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.isControlDown() && keyEvent.isShiftDown() && keyEvent.getCode() == KeyCode.DIGIT9) {
                cheat();
            }
        });
    }

    public void gameStart() {
        gameLoop();
    }
}
