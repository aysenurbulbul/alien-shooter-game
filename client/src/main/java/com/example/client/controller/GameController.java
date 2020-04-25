package com.example.client.controller;

import com.example.client.StageInitializer;
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
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    private RestTemplate restTemplate;

    @FXML
    public Button backMenuButton;

    @FXML
    public Button startGame;

    private Player player;

    @FXML
    private void loadMainMenu() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
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

    }
}
