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

    public static Player getPlayer(){
        return player;
    }

    @FXML
    private void loadMainMenu() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        Stage mainStage = StageInitializer.parentStage;
        mainStage.getScene().setRoot(parent);
    }

    @FXML
    private void login() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        String jsonString = new JSONObject()
                .put("username", username)
                .put("password", password).toString();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonString, httpHeaders);

        try {
            ResponseEntity<String> tokenResponse = restTemplate.exchange("http://localhost:8080/login",
                    HttpMethod.POST,
                    httpEntity,
                    String.class);
            String token = "Bearer " + tokenResponse.getBody();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.set("Authorization", token);
            httpEntity = new HttpEntity<>("body", httpHeaders);
            ResponseEntity<Player> playerResponse = restTemplate.exchange(
                    "http://localhost:8080/players/" + username,
                    HttpMethod.GET,
                    httpEntity,
                    new ParameterizedTypeReference<>() {});
            player = playerResponse.getBody();
            player.setJwt(token);
            Parent parent = FXMLLoader.load(getClass().getResource("/fxml/Game.fxml"));
            Stage mainStage = StageInitializer.parentStage;
            mainStage.getScene().setRoot(parent);
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
