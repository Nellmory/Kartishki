package ru.vsu.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import ru.vsu.cs.Hearts;

public class HeartsController {
    public AnchorPane anchorPane;
    private final Hearts hearts = new Hearts();
    @FXML
    private void initialize() {
        //подготовка игры
        hearts.newGame();

        //подготовка сцены
        anchorPane.setStyle("-fx-background-image: url('images/table.jpg')");

        //подготовка к игре
    }
}
