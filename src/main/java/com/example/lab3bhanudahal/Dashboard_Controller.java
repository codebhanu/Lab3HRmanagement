package com.example.lab3bhanudahal;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Dashboard_Controller {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}