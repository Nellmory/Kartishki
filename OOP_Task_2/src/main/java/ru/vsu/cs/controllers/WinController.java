package ru.vsu.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class WinController {

    public AnchorPane anchorPane;

    @FXML
    private void initialize() {
        //подготовка сцены
        anchorPane.setStyle("-fx-background-image: url('images/win.png')");
    }
}
