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

import static com.example.client.constant.ControllerConstants.API_ADDRESS;
import static com.example.client.constant.ControllerConstants.MAIN_MENU_FXML;

@Component
public class LeaderBoardController implements Initializable {

    private RestTemplate restTemplate;

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

    /**
     * Loads main menu when user clicks "Back to Menu" button
     * @throws IOException from FXMLloader.load
     */
    @FXML
    private void loadMainMenu() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(MAIN_MENU_FXML));
        Stage mainStage = StageInitializer.parentStage;
        mainStage.getScene().setRoot(parent);
    }

    /**
     * Initialize restTemplate that is used to get scores and
     * calls functions to gets scores based on last 7 days,
     * last 30 days and all time.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        restTemplate = new RestTemplate();
        initializeAllTimesLeaderboard();
        initializeLastSevenDaysLeaderboard();
        initializeLastThirtyDaysLeaderboard();
        loadLeaderboards();
    }

    /**
     * Creates tables
     * @param usernameColumn username of player
     * @param scoreColumn score of the player
     * @param dateColumn date of the game finished
     * @param leaderboardTable table to adjust
     */
    private void createTable(TableColumn<Player, String> usernameColumn, TableColumn<Game, Integer> scoreColumn,
                             TableColumn<Game, LocalDateTime> dateColumn,TableView<Game> leaderboardTable){
        int columnNumber = 3;
        usernameColumn.prefWidthProperty().bind(leaderboardTable.widthProperty().divide(columnNumber));
        scoreColumn.prefWidthProperty().bind(leaderboardTable.widthProperty().divide(columnNumber));
        dateColumn.prefWidthProperty().bind(leaderboardTable.widthProperty().divide(columnNumber));

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("finishDateTime"));
    }

    /**
     * Creates table for last 7 days scores
     */
    private void initializeLastSevenDaysLeaderboard(){
        createTable(usernameColumnSeven, scoreColumnSeven, dateColumnSeven, lastSevenDaysLeaderboard);
    }

    /**
     * Creates table for last 30 days scores
     */
    private void initializeLastThirtyDaysLeaderboard(){
        createTable(usernameColumnThirty, scoreColumnThirty, dateColumnThirty, lastThirtyDaysLeaderboard);
    }

    /**
     * Cretaes table for all scores
     */
    private void initializeAllTimesLeaderboard(){
        createTable(usernameColumnAll, scoreColumnAll, dateColumnAll, allTimesLeaderboard);
    }

    /**
     * Calls the function for getting data from database and loads tables
     */
    private void loadLeaderboards(){
        loadLeaderboard(lastSevenDaysLeaderboard, "/getLastSevenDays");
        loadLeaderboard(lastThirtyDaysLeaderboard, "/getLastThirtyDays");
        loadLeaderboard(allTimesLeaderboard, "/getAllTimes");
    }

    /**
     * Sends a GET request using restTemplate to get the scores.
     * @param leaderboard table to load
     * @param getLeaderboard URL to use in restTemplate
     */
    private void loadLeaderboard(TableView<Game> leaderboard, String getLeaderboard){
        String address = API_ADDRESS + "/leaderboard" + getLeaderboard;
        ResponseEntity<List<Game>> response = restTemplate.exchange(
                address,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});
        leaderboard.setItems(FXCollections.observableList(Objects.requireNonNull(response.getBody())));
    }
}
