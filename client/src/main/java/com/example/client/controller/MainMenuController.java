package com.example.client.controller;

import com.example.client.StageInitializer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MainMenuController {

    @Value("${spring.application.ui.windowWidth}")
    private int windowWidth;

    @Value("${spring.application.ui.windowHeight}")
    private int windowHeight;

    @FXML
    public Button login;

    @FXML
    public Button register;

    @FXML
    public Button leaderBoard;

    @FXML
    private void loadLogin() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        Stage mainStage = StageInitializer.parentStage;
        mainStage.getScene().setRoot(parent);
    }

    @FXML
    private void loadRegister() throws  IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/Register.fxml"));
        Stage mainStage = StageInitializer.parentStage;
        mainStage.getScene().setRoot(parent);
    }

    @FXML
    private void loadLeaderBoard() throws  IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/LeaderBoard.fxml"));
        Stage mainStage = StageInitializer.parentStage;
        mainStage.getScene().setRoot(parent);
    }

}
