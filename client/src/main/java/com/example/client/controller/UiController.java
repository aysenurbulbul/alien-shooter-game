package com.example.client.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class UiController {

    @FXML
    private TextField password;

    @FXML
    private TextField email;

    @FXML
    private TextField username;

    @FXML
    private Button submit;
}
