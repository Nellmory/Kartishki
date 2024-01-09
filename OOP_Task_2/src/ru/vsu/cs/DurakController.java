package ru.vsu.cs;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;

public class DurakController {
    public Canvas canvas;
    public AnchorPane anchorPane;

    @FXML
    private void initialize() {

        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));
        anchorPane.setStyle("-fx-base: #7e191b;");

    }
}
