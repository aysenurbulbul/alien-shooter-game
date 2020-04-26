package com.example.client.controller;

import com.example.client.StageInitializer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
public class RegisterController implements Initializable {

    private RestTemplate restTemplate;

    @FXML
    public Button backMenuButton;

    @FXML
    public PasswordField passwordField;

    @FXML
    public TextField emailField;

    @FXML
    public TextField usernameField;

    @FXML
    public Button registerButton;

    @FXML
    public Label passwordLabel;

    @FXML
    public Label emailLabel;

    @FXML
    public Label usernameLabel;

    @FXML
    public Label registerLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        restTemplate = new RestTemplate();
        setFont(registerLabel, 30);
        setFont(usernameLabel, 15);
        setFont(emailLabel, 15);
        setFont(passwordLabel, 15);
        setButtonFont(registerButton, 15);
        setButtonFont(backMenuButton, 15);
    }

    /**
     * loads main menu when user clicks "Back" button
     * @throws IOException from FXMLloader.laod
     */
    @FXML
    private void loadMainMenu() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(MAIN_MENU_FXML));
        Stage mainStage = StageInitializer.parentStage;
        mainStage.getScene().setRoot(parent);
    }

    /**
     * register player,  add it to database
     * sends POST request to the server using restTemplate
     */
    @FXML
    private void register(){
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        String jsonString = new JSONObject()
                .put("username", username)
                .put("email", email)
                .put("password", password).toString();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonString, httpHeaders);
        try {
            restTemplate.exchange(API_ADDRESS + "/registration",
                    HttpMethod.POST,
                    httpEntity,
                    String.class);
            messageAlert(Alert.AlertType.CONFIRMATION, "Successfully registered", "Login and play " + username +"!");
            loadMainMenu();
        } catch (RestClientException | IOException e){
            messageAlert(Alert.AlertType.ERROR, "Failed to register", ERROR_REGISTER);
        }
    }

    /**
     * Create alert
     * @param alertType alert type like error, confirmation
     * @param title title of the alert title
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
