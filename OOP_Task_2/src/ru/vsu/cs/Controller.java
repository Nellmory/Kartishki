package ru.vsu.cs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Controller {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Canvas canvas;
    @FXML
    private Button mainButton;

    @FXML
    private void buttonClicked() throws IOException {
        mainButton.setText("Click me again!");
    }

    @FXML
    private void sw() throws IOException {
        AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/new.fxml")));
        anchorPane.getChildren().setAll(pane);
    }
}
