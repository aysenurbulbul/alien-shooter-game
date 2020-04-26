package com.example.client.controller;

import com.example.client.StageInitializer;
import com.example.client.model.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.client.constant.ControllerConstants.*;
import static com.example.client.constant.GameViewConstants.FONT_PATH;

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

    @FXML
    public Label passwordLabel;

    @FXML
    public Label usernameLabel;

    @FXML
    public Label loginLabel;

    private static Player player;

    static Player getPlayer(){
        return player;
    }

    /**
     * loads main menu when user clicks "Back" button
     * @throws IOException from FXMLloader.load
     */
    @FXML
    private void loadMainMenu() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(MAIN_MENU_FXML));
        Stage mainStage = StageInitializer.parentStage;
        mainStage.getScene().setRoot(parent);
    }

    /**
     * loads Game.fxml which allows play the game or go back to menu
     * @throws IOException from FXMLloader.load
     */
    private void loadGame() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(GAME_FXML));
        Stage mainStage = StageInitializer.parentStage;
        mainStage.getScene().setRoot(parent);
    }

    /**
     * gets player's info with the given username and sets player jwt token.
     * @param token player's token
     * @param username player's username
     */
    private void loadPlayer(String token, String username){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //set jwt token as a header since this request needs authentication
        httpHeaders.set("Authorization", token);
        HttpEntity<String> httpEntity = new HttpEntity<>("body", httpHeaders);
        //make a request to server with the given username and get player.
        ResponseEntity<Player> playerResponse = restTemplate.exchange(
                API_ADDRESS + "/players/" + username,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<>() {});
        //set player's authorization token to given jwt token for making further requests.
        player = playerResponse.getBody();
        assert player != null;
        player.setJwt(token);
    }

    /**
     * using restTemplate authenticate the user with username and password
     */
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
            ResponseEntity<String> tokenResponse = restTemplate.exchange(API_ADDRESS + "/login",
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
            messageAlert(Alert.AlertType.ERROR, "Error", ERROR_CREDENTIALS);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        restTemplate = new RestTemplate();
        setFont(loginLabel, 30);
        setFont(usernameLabel, 15);
        setFont(passwordLabel, 15);
        setButtonFont(loginButton, 15);
        setButtonFont(backMenuButton, 15);
    }

    /**
     * create alert
     * @param alertType alert's type ike Error, Confirmation
     * @param title title of the alert window
     * @param message context of the alert window
     */
    private void messageAlert(Alert.AlertType alertType, String title, String message){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    private void setFont(Label label, int size){
        try {
            label.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), size));
        } catch (FileNotFoundException e) {
            label.setFont(Font.font("Verdana", size));
        }
    }

    private void setButtonFont(Button button, int size){
        try {
            button.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), size));
        } catch (FileNotFoundException e) {
            button.setFont(Font.font("Verdana", size));
        }
    }
}
