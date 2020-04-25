package com.example.client.controller;

import com.example.client.StageInitializer;
import com.example.client.model.Game;
import com.example.client.model.Player;
import com.example.client.view.GameView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class LoginController implements Initializable {

    private RestTemplate restTemplate;

    @FXML
    public Button backMenuButton;

    @FXML
    public TextField usernameField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public Button loginButton;

    private static Player player;

    static Player getPlayer(){
        return player;
    }

    @FXML
    private void loadMainMenu() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        Stage mainStage = StageInitializer.parentStage;
        mainStage.getScene().setRoot(parent);
    }

    private void loadGame() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/Game.fxml"));
        Stage mainStage = StageInitializer.parentStage;
        mainStage.getScene().setRoot(parent);
    }

    //gets player's info with the given username and sets player jwt token.
    private void loadPlayer(String token, String username){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //set jwt token as a header since this request needs authentication
        httpHeaders.set("Authorization", token);
        HttpEntity<String> httpEntity = new HttpEntity<>("body", httpHeaders);
        //make a request to server with the given username and get player.
        ResponseEntity<Player> playerResponse = restTemplate.exchange(
                "http://localhost:8080/players/" + username,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<>() {});
        //set player's authorization token to given jwt token for making further requests.
        player = playerResponse.getBody();
        player.setJwt(token);
    }

    @FXML
    private void login()  {
        String username = usernameField.getText();
        String password = passwordField.getText();

        String jsonString = new JSONObject()
                .put("username", username)
                .put("password", password).toString();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonString, httpHeaders);

        try {
            // on successful login this request returns a jwt token.
            ResponseEntity<String> tokenResponse = restTemplate.exchange("http://localhost:8080/login",
                    HttpMethod.POST,
                    httpEntity,
                    String.class);
            String token = "Bearer " + tokenResponse.getBody();
            //load player's info and
            loadPlayer(token, username);
            //load game page
            try{
                loadGame();
            } catch (IOException e){
                messageAlert(Alert.AlertType.ERROR, "Error", "Cannot open game page at the moment");
            }
        } catch (RestClientException e){
            messageAlert(Alert.AlertType.ERROR, "Error", "Wrong credentials");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        restTemplate = new RestTemplate();
    }

    private void messageAlert(Alert.AlertType alertType, String title, String message){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
