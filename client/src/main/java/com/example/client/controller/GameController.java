package com.example.client.controller;

import com.example.client.StageInitializer;
import com.example.client.model.Player;
import com.example.client.view.GameView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static com.example.client.constant.ControllerConstants.*;
import static com.example.client.constant.GameViewConstants.FONT_PATH;

public class GameController implements Initializable {

    private static RestTemplate restTemplate;

    @FXML
    public Button backMenuButton;

    @FXML
    public Button startGame;

    @FXML
    private Label usernameLable;

    public static Player player;

    private static int score;

    public static void setScore(int value){
        score = value;
    }

    /**
     * Loads main menu if user clicks "Back to Menu" button
     * @throws IOException from FXMLloader.load
     */
    @FXML
    private void loadMainMenu() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(MAIN_MENU_FXML));
        Stage mainStage = StageInitializer.parentStage;
        mainStage.getScene().setRoot(parent);
    }


    /**
     * When user clicks "Start Game" button, creates a new game view
     */
    @FXML
    private void startGame(){
        AnchorPane anchorPane = new AnchorPane();
        Scene gameScene = new Scene(anchorPane, 800, 600);
        Stage gameStage = new Stage();
        GameView gameView = new GameView(anchorPane, gameScene, gameStage);
        gameView.gameStart();
    }

    /**
     * Since this class implements Initializable interface,
     * we override the initialize function to init restTemplate
     * and player.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        restTemplate = new RestTemplate();
        player = LoginController.getPlayer();
        usernameLable.setText("Welcome " + player.getUsername() +"!");
        setFont(usernameLable);
        setButtonFont(startGame, 13);
        setButtonFont(backMenuButton, 15);
    }

    private void setFont(Label label){
        try {
            label.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 30));
        } catch (FileNotFoundException e) {
            label.setFont(Font.font("Verdana", 30));
        }
    }

    private void setButtonFont(Button button, int size){
        try {
            button.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), size));
        } catch (FileNotFoundException e) {
            button.setFont(Font.font("Verdana", size));
        }
    }

    /**
     * Gets the score of the player and using restTemplate
     * sends the score to the database
     */
    public static void addGame(){
        String username = player.getUsername();
        LocalDateTime date = LocalDateTime.now();
        String token = player.getJwt();

        String jsonString = new JSONObject()
                .put("score", score)
                .put("finishDateTime", date).toString();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        //set jwt token as a header since this request needs authentication
        httpHeaders.set("Authorization", token);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonString, httpHeaders);
        try {
            //make a request to server with the given username and send the game.
            restTemplate.exchange(
                    API_ADDRESS +"/game/addGame/" + username,
                    HttpMethod.POST,
                    httpEntity,
                    new ParameterizedTypeReference<>() {});
        } catch (RestClientException e){
            System.out.println(e.getMessage());
        }

    }
}
