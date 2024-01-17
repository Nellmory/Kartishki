package main.java.ru.vsu.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class DrawController {
    public AnchorPane anchorPane;

    @FXML
    private void initialize() {
        //подготовка сцены
        anchorPane.setStyle("-fx-background-image: url('/main/java/images/draw.jpg')");
    }
}
