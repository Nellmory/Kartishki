package ru.vsu.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import ru.vsu.cs.Pasians;

public class PasiansController {
    public AnchorPane anchorPane;
    private final Pasians pasians = new Pasians();

    @FXML
    private void initialize() {
        //подготовка игры
        pasians.newGame();

        //подготовка сцены
        anchorPane.setStyle("-fx-background-image: url('images/table.jpg')");

        //подготовка к игре
    }
}
