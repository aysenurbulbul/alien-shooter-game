package com.example.client.controller;

import com.example.client.StageInitializer;
import com.example.client.model.Game;
import com.example.client.model.Player;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
public class LeaderBoardController implements Initializable {

    private RestTemplate restTemplate;
    private final String apiAddress = "http://localhost:8080";

    @FXML
    public Button backMenu;

    @FXML public TableView<Game> lastSevenDaysLeaderboard;
    @FXML public TableColumn<Player, String> usernameColumnSeven;
    @FXML public TableColumn<Game, Integer> scoreColumnSeven;
    @FXML public TableColumn<Game, LocalDateTime> dateColumnSeven;
    @FXML public TableView<Game> lastThirtyDaysLeaderboard;
    @FXML public TableColumn<Player, String> usernameColumnThirty;
    @FXML public TableColumn<Game, Integer> scoreColumnThirty;
    @FXML public TableColumn<Game, LocalDateTime> dateColumnThirty;
    @FXML public TableView<Game> allTimesLeaderboard;
    @FXML public TableColumn<Player, String> usernameColumnAll;
    @FXML public TableColumn<Game, Integer> scoreColumnAll;
    @FXML public TableColumn<Game, LocalDateTime> dateColumnAll;

    @FXML
    private void loadMainMenu() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        Stage mainStage = StageInitializer.parentStage;
        mainStage.getScene().setRoot(parent);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        restTemplate = new RestTemplate();
        initializeAllTimesLeaderboard();
        initializeLastSevenDaysLeaderboard();
        initializeLastThirtyDaysLeaderboard();
        loadLeaderboards();
    }

    private void initializeLastSevenDaysLeaderboard(){

        int columnNumber = 3;
        usernameColumnSeven.prefWidthProperty().bind(lastSevenDaysLeaderboard.widthProperty().divide(columnNumber));
        scoreColumnSeven.prefWidthProperty().bind(lastSevenDaysLeaderboard.widthProperty().divide(columnNumber));
        dateColumnSeven.prefWidthProperty().bind(lastSevenDaysLeaderboard.widthProperty().divide(columnNumber));

        usernameColumnSeven.setCellValueFactory(new PropertyValueFactory<>("username"));
        scoreColumnSeven.setCellValueFactory(new PropertyValueFactory<>("score"));
        dateColumnSeven.setCellValueFactory(new PropertyValueFactory<>("finishDateTime"));
    }
    private void initializeLastThirtyDaysLeaderboard(){

        int columnNumber = 3;
        usernameColumnThirty.prefWidthProperty().bind(lastThirtyDaysLeaderboard.widthProperty().divide(columnNumber));
        scoreColumnThirty.prefWidthProperty().bind(lastThirtyDaysLeaderboard.widthProperty().divide(columnNumber));
        dateColumnThirty.prefWidthProperty().bind(lastThirtyDaysLeaderboard.widthProperty().divide(columnNumber));

        usernameColumnThirty.setCellValueFactory(new PropertyValueFactory<>("username"));
        scoreColumnThirty.setCellValueFactory(new PropertyValueFactory<>("score"));
        dateColumnThirty.setCellValueFactory(new PropertyValueFactory<>("finishDateTime"));
    }
    private void initializeAllTimesLeaderboard(){

        int columnNumber = 3;
        usernameColumnAll.prefWidthProperty().bind(allTimesLeaderboard.widthProperty().divide(columnNumber));
        scoreColumnAll.prefWidthProperty().bind(allTimesLeaderboard.widthProperty().divide(columnNumber));
        dateColumnAll.prefWidthProperty().bind(allTimesLeaderboard.widthProperty().divide(columnNumber));

        usernameColumnAll.setCellValueFactory(new PropertyValueFactory<>("username"));
        scoreColumnAll.setCellValueFactory(new PropertyValueFactory<>("score"));
        dateColumnAll.setCellValueFactory(new PropertyValueFactory<>("finishDateTime"));
    }

    private void loadLeaderboards(){
        loadLeaderboard(lastSevenDaysLeaderboard, "/getLastSevenDays");
        loadLeaderboard(lastThirtyDaysLeaderboard, "/getLastThirtyDays");
        loadLeaderboard(allTimesLeaderboard, "/getAllTimes");
    }
    private void loadLeaderboard(TableView<Game> leaderboard, String getLeaderboard){
        String address = apiAddress + "/leaderboard" + getLeaderboard;
        ResponseEntity<List<Game>> response = restTemplate.exchange(
                address,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});
        leaderboard.setItems(FXCollections.observableList(Objects.requireNonNull(response.getBody())));
    }
}
