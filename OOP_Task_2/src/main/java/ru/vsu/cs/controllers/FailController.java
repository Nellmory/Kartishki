package main.java.ru.vsu.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class FailController {
    public AnchorPane anchorPane;

    @FXML
    private void initialize() {
        //подготовка сцены
        anchorPane.setStyle("-fx-background-image: url('/main/java/images/fail.jpg')");
    }
}
