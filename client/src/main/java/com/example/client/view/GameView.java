package com.example.client.view;

import com.example.client.StageInitializer;
import com.example.client.clientSocket.Client;
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

import javax.print.attribute.standard.MediaSize;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.client.constant.GameViewConstants.*;

public class GameView {

    private final AnchorPane anchorPane;
    private final Scene gameScene;
    private Stage gameStage;
    private AnimationTimer animationTimer;
    private AnimationTimer multiplayerAnimationTimer;
    private double t = 0;
    private List<AbstractLevel> levels;
    private int level;
    private double mousePositionX;
    private double mousePositionY;
    private Ship playerShip;
    private Ship enemyShip;
    private InfoLabel scoreboard;
    private InfoLabel levelLabel;
    private List<ImageView> shipHealthImages = new ArrayList<>();
    private List<ImageView> enemyShipHealthImages = new ArrayList<>();
    private int score;
    private Client client;
    private String username;

    /**
     * game view constructor
     * @param anchorPane pane that will have object nodes
     * @param gameScene scene that contains pane
     * @param gameStage stage to show
     */
    public GameView(AnchorPane anchorPane, Scene gameScene, Stage gameStage){
        this.username = GameController.player.getUsername();
        this.anchorPane = anchorPane;
        this.gameScene = gameScene;
        this.gameStage = gameStage;
        this.gameStage = StageInitializer.parentStage;
        this.gameStage.setScene(gameScene);
        this.gameScene.setCursor(Cursor.NONE);
        score = 0;
        level = 0;
        cheatHandle();
        createPlayerShip();
        moveCursor();
        createBackground();
        createLevelInfo();
        createScoreboard();
        createPlayerShipHealthInfo(675, PLAYER_LIFE, playerShip.getHealth(), shipHealthImages);
        initLevels();
    }

    /**
     * creates player ship and place it where mouse is
     */
    private void createPlayerShip(){
        playerShip = new Ship(PLAYER_SHIP);
        this.anchorPane.getChildren().add(playerShip.getShipImage());
        playerShip.getShipImage().setLayoutX(mousePositionX);
        playerShip.getShipImage().setLayoutY(mousePositionY);
    }

    private void createEnemyShip(){
        enemyShip = new Ship(ENEMY_SHIP);
        this.anchorPane.getChildren().add(enemyShip.getShipImage());
    }

    /**
     * label which shows player ship's health with small ship images
     */
    private void createPlayerShipHealthInfo(int xLayout, String who, int health, List<ImageView> shipHealthImages){
        shipHealthImages = new ArrayList<>();
        for(int i=0; i< health; i++){
            ImageView imageView = new ImageView(who);
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            imageView.setLayoutX(xLayout + i * 30);
            imageView.setLayoutY(55);
            shipHealthImages.add(imageView);
            anchorPane.getChildren().add(imageView);
        }
    }

    /**
     * label that shows which level is player at
     */
    private void createLevelInfo(){
        levelLabel = new InfoLabel("LEVEL: 1",120);
        levelLabel.setLayoutX(LEVEL_LAYOUT_X);
        levelLabel.setLayoutY(LEVEL_LAYOUT_Y);
        anchorPane.getChildren().add(levelLabel);
    }

    private void createUsernameInfo(String playerUsername, String enemyUsername){
        InfoLabel playerUsernameInfo = new InfoLabel(playerUsername,120);
        playerUsernameInfo.setLayoutX(LEVEL_LAYOUT_X);
        playerUsernameInfo.setLayoutY(LEVEL_LAYOUT_Y);
        anchorPane.getChildren().add(playerUsernameInfo);
        InfoLabel enemyUsernameInfo = new InfoLabel(enemyUsername,120);
        enemyUsernameInfo.setLayoutX(SCOREBOARD_LAYOUT_X);
        enemyUsernameInfo.setLayoutY(SCOREBOARD_LAYOUT_Y);
        anchorPane.getChildren().add(enemyUsernameInfo);

    }

    /**
     * label that shows score of the player
     */
    private void createScoreboard(){
        scoreboard = new InfoLabel("SCORE: 000",120);
        scoreboard.setLayoutX(SCOREBOARD_LAYOUT_X);
        scoreboard.setLayoutY(SCOREBOARD_LAYOUT_Y);
        anchorPane.getChildren().add(scoreboard);
    }

    /**
     * creates background of the game
     */
    private void createBackground(){
        Image background = new Image(GAME_BACKGROUND, 256,256,false, true);
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        anchorPane.setBackground(new Background(backgroundImage));
    }

    /**
     * if game ends, shows game ends sub view
     * either player is dead or passes all levels
     * @param text text to show on screen
     */
    private void gameFinished(String text){
        gameScene.setCursor(Cursor.DEFAULT);
        anchorPane.getChildren().remove(playerShip.getShipImage());
        animationTimer.stop();
        GameController.setScore(score);
        //GameController.addGame();
        GameSubView gameDoneView = new GameSubView(score, text, true);
        gameDoneView.setLayoutX(100);
        gameDoneView.setLayoutY(100);
        anchorPane.getChildren().add(gameDoneView);
    }


    private void waitingEnemy(String text){
        gameScene.setCursor(Cursor.DEFAULT);
        anchorPane.getChildren().remove(playerShip.getShipImage());
        animationTimer.stop();
        GameSubView gameWaitView = new GameSubView(score, text, false);
        gameWaitView.setLayoutX(100);
        gameWaitView.setLayoutY(100);
        gameWaitView.setId("gameWaitView");
        anchorPane.getChildren().add(gameWaitView);
    }
    private void createFinalAlien(){
        levels.get(level).getAliens().forEach(alien -> anchorPane.getChildren().add(alien.getImageView()));
        levels.get(level).getAliens().get(0).getImageView().setFitHeight(100);
        levels.get(level).getAliens().get(0).getImageView().setFitWidth(100);
    }

    /**
     * checks if levels are finished or
     * player's health is 0 then ends the game
     * calls the game finished screen
     */
    private void areSingleLevelsFinished() {
        if(level == NUMBER_OF_LEVELS ){
            waitingEnemy("WAITING FOR ENEMY");
            Thread thread = new Thread(new Runnable(){
                @Override
                public void run() {
                    client = new Client();
                    try {
                        client.connectToServer();
                        client.sendUsername(username);
                        String enemyName = client.getUsername();
                        client.sendHealth(playerShip.getHealth());
                        int enemyHealth = client.getHealth();
                        client.setController();
                        Platform.runLater(()->{
                            anchorPane.getChildren().clear();
                            gameScene.setCursor(Cursor.NONE);
                            createUsernameInfo(username, enemyName);
                            createPlayerShipHealthInfo(10, PLAYER_LIFE, playerShip.getHealth(), shipHealthImages);
                            createPlayerShipHealthInfo(675, ENEMY_LIFE, enemyHealth, enemyShipHealthImages);
                            createBackground();
                            createPlayerShip();
                            createEnemyShip();
                            createFinalAlien();
                            multiplayerGameLoop();
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        }
        if(playerShip.getHealth() == ZERO_HEALTH){
            gameFinished("LOSER...GAME OVER");
        }
    }

    /**
     * check if all aliens in the level are destroyed
     * if so increments level and displays other level
     */
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
    private void isGameFinished(){
        if(playerShip.getHealth() <= 0){
            gameFinished("LOSER... GAME OVER");
        }
        if(enemyShip.getHealth() <=0 ){
            gameFinished("ENEMY DIED...GAME OVER!");
        }
        if(levels.get(4).getAliens().size() == 0){
            gameFinished("CONGRATS...");
        }
    }

    /**
     * cheat which enables passing levels and getting all points
     * by pressing CTRL + SHIFT + 9, bound to key pressing event
     */
    private void cheat() {
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
            writeLabels();
            isLevelFinished();
            areSingleLevelsFinished();
        }
    }

    /**
     * animation timer which enables game to go on
     */
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
                    areSingleLevelsFinished();
                    t = 0;
                }
                updateShipPosition();
            }
        };
        animationTimer.start();
    }

    private void multiplayerGameLoop(){
        multiplayerAnimationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                t += TIMER_INCREASE;
                if(t>TIMER_SHOULD_BE_LESS){
                    t = 0;
                }
                updateShipPosition();
                addNewPlayerBullet();
                try {
                    isGameFinished();
                    updateFinalLevelPlayerBullet();
                    isGameFinished();
                    didFinalAlienDied();
                    isGameFinished();
                    moveAlien();
                    finalAlienShoot();
                    moveEnemyShipAndBullets();
                    updateEnemyPlayerHealth();
                    isGameFinished();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        multiplayerAnimationTimer.start();
    }

    private void updateEnemyPlayerHealth() throws IOException{
        client.sendHealth(playerShip.getHealth());
        int enemyHealth = client.getHealth();
        enemyShip.setHealth(enemyHealth);
        int currentEnemyHealth = this.enemyShipHealthImages.size();
        if(enemyHealth<currentEnemyHealth && enemyHealth>=0 ){
            System.out.println("**********removing enemy health**********");
            anchorPane.getChildren().remove(enemyShipHealthImages.get(currentEnemyHealth-1));
            enemyShipHealthImages.remove(currentEnemyHealth-1);
        }
    }

    private void updateFinalLevelPlayerBullet() throws IOException{
        Iterator<Bullet> bulletIterator = playerShip.getBullets().iterator();
        int didPlayerShout = 0;
        if(levels.get(level).getAliens().size() == 0) return;
        Alien alien = levels.get(level).getAliens().get(0);
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.moveUp();
            if(isOnSamePosition(alien, bullet)){
                anchorPane.getChildren().remove(bullet.getImageView());
                bulletIterator.remove();
                alien.decreaseHealth();
                addToScore(alien);
                didPlayerShout = 1;
                break;
            }
        }
        client.sendHealth(didPlayerShout);
        int didEnemyShout = client.getHealth();
        if(didEnemyShout == 1){
            alien.decreaseHealth();
        }
    }

    private void didFinalAlienDied(){
        if(levels.get(level).getAliens().size() == 0) return;
        Alien alien = levels.get(level).getAliens().get(0);
        if(alien.getHealth()<= ZERO_HEALTH){
            alien.getBullets().forEach(alienBullet -> anchorPane.getChildren().remove(alienBullet.getImageView()));
            anchorPane.getChildren().remove(alien.getImageView());
            levels.get(level).getAliens().remove(0);
        }
    }


    private void moveAlien() throws IOException{
        if(levels.get(level).getAliens().size() == 0) return;
        if(client.isController() == 1){
            Double[] alienCoords = updateAlienPosition();
            client.sendShipCoords(alienCoords[0], alienCoords[1]);
        }
        else{
            Double[] coords = client.getShipCoords();
            setAlienPosition(coords);
        }
    }
    private void finalAlienShoot() throws IOException{
        if(levels.get(level).getAliens().size() == 0) return;
        Double alienShootRandom = Math.random();
        if(client.isController() == 1){
            client.sendAlienShootRandom(alienShootRandom);
        }
        else{
            alienShootRandom = client.getAlienShootRandom();
        }
        Alien alien = levels.get(level).getAliens().get(0);
        if(alienShootRandom<ALIEN_SHOOT_RANDOM){
            Bullet bullet = new Bullet("ENEMY",alien.getPositionX()+21.5, alien.getPositionY()+20, "/static/laserRed03.png");
            levels.get(level).getAliens().get(0).addBullet(bullet);
            anchorPane.getChildren().add(bullet.getImageView());
        }
        alien.getBullets().forEach(Bullet::moveDown);
        alienBulletShoot(alien);
    }
    private void moveEnemyShipAndBullets() throws IOException{
        client.sendShipCoords(mousePositionX, mousePositionY);
        Double[] coords = client.getShipCoords();
        updateEnemyPosition(coords);
        addNewEnemyPlayerBullet(coords);
        updateEnemyPlayerBullets();
    }

    /**
     * checks if player bullet hits to alien
     * @param alien alien that is targetted
     * @param bullet player bullet
     * @return true if hits
     */
    private boolean isOnSamePosition(Alien alien, Bullet bullet){
        return bullet.getImageView().getBoundsInParent().intersects(alien.getImageView().getBoundsInParent());
    }

    /**
     * checks if player gets hit by alien bullet
     * @param playerShip player's ship that is targetted
     * @param bullet alien's bullet
     * @return true if hits
     */
    private boolean isOnSamePosition(Ship playerShip, Bullet bullet){
        return bullet.getImageView().getBoundsInParent().intersects(playerShip.getShipImage().getBoundsInParent());
    }

    private void addNewEnemyPlayerBullet(Double[] coords){
        Bullet newBullet = new Bullet("PLAYER", coords[0] + 20, coords[1] + 7, "/static/laserBlue03.png");
        enemyShip.addBullet(newBullet);
        anchorPane.getChildren().add(newBullet.getImageView());
    }

    private void updateEnemyPlayerBullets(){
        enemyShip.getBullets().forEach(Bullet::moveUp);
    }

    /**
     * create player bullet
     * enables auto-firing by calling in game loop
     */
    private void addNewPlayerBullet(){
        Bullet newBullet = new Bullet("PLAYER", mousePositionX + 20, mousePositionY + 7, "/static/laserBlue03.png");
        playerShip.addBullet(newBullet);
        anchorPane.getChildren().add(newBullet.getImageView());
    }

    /**
     * updates player's bullets position by time
     * if player's bullet hit alien, removes bullet, decreases alien's health
     * if alien's health is 0 removes alien
     */
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

    /**
     * checks alien's type, increments score based on this value
     * @param alien alien that is destroyed
     */
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
            case "FINAL":
                score = score + 20;
                break;
        }
    }

    /**
     * writes score label and level label
     */
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

    /**
     * if alien's bullet hit player, removes bullet, decreases player's health
     * removes the little ship image which represents health bar
     * @param alien alien that shoots
     */
    private void alienBulletShoot(Alien alien){
        Iterator<Bullet> bulletIterator = alien.getBullets().iterator();
        while(bulletIterator.hasNext()){
            Bullet bullet = bulletIterator.next();
            if(isOnSamePosition(playerShip, bullet)){
                anchorPane.getChildren().remove(bullet.getImageView());
                bulletIterator.remove();
                playerShip.setHealth(playerShip.getHealth()-1);
                int shipHealth = playerShip.getHealth();
                if(shipHealth>=ZERO_HEALTH){
                    System.out.println("******** removing health *******");
                    anchorPane.getChildren().remove(shipHealthImages.get(shipHealth));
                }
            }
        }
    }

    /**
     * set mouse move event to replace ship based on mouse movement
     */
    private void updateShipPosition(){
        anchorPane.setOnMouseMoved(mouseEvent -> {
            mousePositionX = mouseEvent.getSceneX();
            mousePositionY = mouseEvent.getSceneY();
            playerShip.getShipImage().setLayoutX(mouseEvent.getSceneX());
            playerShip.getShipImage().setLayoutY(mouseEvent.getSceneY());
        });
    }

    private Double[] updateAlienPosition(){
        Double alienPosX = this.levels.get(level).getAliens().get(0).getPositionX();
        Double alienPosY = this.levels.get(level).getAliens().get(0).getPositionY();
        Double randomX = -50 + (Math.random() * ((50 - (-50)) + 1));
        Double randomY = -50 + (Math.random() * ((50 - (-50)) + 1));
        if(50<alienPosX + randomX && alienPosX + randomX < 650)
            alienPosX += randomX;
        if(50<alienPosY + randomY && alienPosY + randomY < 300)
            alienPosY += randomY;
        this.levels.get(level).getAliens().get(0).setPositionX(alienPosX);
        this.levels.get(level).getAliens().get(0).setPositionY(alienPosY);
        return new Double[]{alienPosX, alienPosY};
    }

    private void setAlienPosition(Double[] coords){
        this.levels.get(level).getAliens().get(0).setPositionX(coords[0]);
        this.levels.get(level).getAliens().get(0).setPositionY(coords[1]);
    }

    private void updateEnemyPosition(Double[] coords){
        enemyShip.getShipImage().setLayoutX(coords[0]);
        enemyShip.getShipImage().setLayoutY(coords[1]);
    }

    /**
     * creates alien bullets, works on ALIEN_SHOOT_RANDOM probability
     * moves alien bullets
     */
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

    /**
     * when player starts the game replaces the mosue position
     * which also means replaces the player ship's position
     */
    private void moveCursor() {
        Platform.runLater(() -> {
            Robot robot = new Robot();
            robot.mouseMove(robot.getMouseX(), robot.getMouseY()+200);
        });
    }

    /**
     * initialize game levels, starts level1
     */
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
        Level5 finalLevel = new Level5();
        levels.add(finalLevel);
        level1.getAliens().forEach(alien -> anchorPane.getChildren().add(alien.getImageView()));

    }

    /**
     * when user presses ctrl+shift+9 combination cheat() function gets calles
     * which passes levels
     */
    private void cheatHandle(){
        gameScene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.isControlDown() && keyEvent.isShiftDown() && keyEvent.getCode() == KeyCode.DIGIT9) {
                cheat();
            }
        });
    }

    /**
     * starts the game by calling gameLoop()
     */
    public void gameStart() {
        gameLoop();
    }
}
