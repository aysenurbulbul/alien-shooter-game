package com.example.client.controller;

import com.example.client.StageInitializer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.client.constant.ControllerConstants.*;
import static com.example.client.constant.GameViewConstants.*;

@Component
public class MainMenuController implements Initializable {

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
    public Label label;

    /**
     * loads login page when user clicks "LOGIN" button
     * @throws IOException from FXMLloader.laod
     */
    @FXML
    private void loadLogin() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(LOGIN_FXML));
        Stage mainStage = StageInitializer.parentStage;
        mainStage.getScene().setRoot(parent);
    }

    /**
     * loads register page when user clicks "REGISTER" button
     * @throws IOException from FXMLloader
     */
    @FXML
    private void loadRegister() throws  IOException{
        Parent parent = FXMLLoader.load(getClass().getResource(REGISTER_FXML));
        Stage mainStage = StageInitializer.parentStage;
        mainStage.getScene().setRoot(parent);
    }

    /**
     * loads leader boards when user click "LEADERBOARD" button
     * @throws IOException from FXMLloader.laod
     */
    @FXML
    private void loadLeaderBoard() throws  IOException{
        Parent parent = FXMLLoader.load(getClass().getResource(LEADERBOARD_FXML));
        Stage mainStage = StageInitializer.parentStage;
        mainStage.getScene().setRoot(parent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            label.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 30));
        } catch (FileNotFoundException e) {
            label.setFont(Font.font("Verdana", 30));
        }
        setButtonFont(login, 15);
        setButtonFont(register, 15);
        leaderBoard.setText("LEADERBOARD");
        setButtonFont(leaderBoard, 12);
    }

    private void setButtonFont(Button button, int size){
        try {
            button.setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), size));
        } catch (FileNotFoundException e) {
            button.setFont(Font.font("Verdana", size));
        }
    }
}
