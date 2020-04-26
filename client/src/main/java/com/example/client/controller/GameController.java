package com.example.client.controller;

import com.example.client.StageInitializer;
import com.example.client.model.Game;
import com.example.client.model.Player;
import com.example.client.view.GameView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.client.constant.ControllerConstants.API_ADDRESS;
import static com.example.client.constant.ControllerConstants.MAIN_MENU_FXML;

public class GameController implements Initializable {

    private RestTemplate restTemplate;

    private final String apiAddress = API_ADDRESS;

    @FXML
    public Button backMenuButton;

    @FXML
    public Button startGame;

    private Player player;

    private static Long score;

    public static void setScore(Long value){
        score = value;
    }

    @FXML
    private void loadMainMenu() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(MAIN_MENU_FXML));
        Stage mainStage = StageInitializer.parentStage;
        mainStage.getScene().setRoot(parent);
    }
    @FXML
    private void startGame(){
        AnchorPane anchorPane = new AnchorPane();
        Scene gameScene = new Scene(anchorPane, 800, 600);
        Stage gameStage = new Stage();
        GameView gameView = new GameView(anchorPane, gameScene, gameStage);
        gameView.gameStart();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        restTemplate = new RestTemplate();
        player = LoginController.getPlayer();
    }

    private void addGame(){
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
        HttpEntity<String> httpEntity = new HttpEntity<>("body", httpHeaders);
        //make a request to server with the given username and get player.
        ResponseEntity<Player> playerResponse = restTemplate.exchange(
                apiAddress +"/game/addGame/" + username,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<>() {});
    }
}
