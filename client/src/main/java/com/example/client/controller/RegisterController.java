package com.example.client.controller;

import com.example.client.StageInitializer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RegisterController {

    @FXML
    public Button backMenu;

    @FXML
    public PasswordField password;

    @FXML
    public TextField email;

    @FXML
    public TextField username;

    @FXML
    public Button submit;

    @FXML
    private void loadMainMenu() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        Stage mainStage = StageInitializer.parentStage;
        mainStage.getScene().setRoot(parent);
    }
}
