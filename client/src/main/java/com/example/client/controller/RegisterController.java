package com.example.client.controller;

import com.example.client.StageInitializer;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.client.constant.ControllerConstants.*;

@Component
public class RegisterController implements Initializable {

    private RestTemplate restTemplate;

    // bu değeri null alıyor neden anlamadım, application.properties te yazmama rağmen
    @Value("${spring.application.apiAddress}")
    private String apiAddress;

    private String url = API_ADDRESS;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        restTemplate = new RestTemplate();
    }

    @FXML
    private void loadMainMenu() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(MAIN_MENU_FXML));
        Stage mainStage = StageInitializer.parentStage;
        mainStage.getScene().setRoot(parent);
    }

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
        System.out.println(apiAddress+"/registration");
        try {
            restTemplate.exchange(url + "/registration",
                    HttpMethod.POST,
                    httpEntity,
                    String.class);
            messageAlert(Alert.AlertType.CONFIRMATION, "Successfully registered", "Login and play " + username +"!");
            loadMainMenu();
        } catch (RestClientException | IOException e){
            messageAlert(Alert.AlertType.ERROR, "Failed to register", ERROR_REGISTER);
        }
    }

    private void messageAlert(Alert.AlertType alertType, String title, String message){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
