package ru.vsu.cs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.vsu.cs.Main;

import java.io.IOException;

public class StartController {
    public Button HeartsButton;
    public Button DurakButton;
    public Button PasiansButton;
    public Button ExitButton;
    public TextField textField1;
    public TextField textField2;
    @FXML
    private AnchorPane anchorPaneStart;

    @FXML
    private void initialize() {
        anchorPaneStart.setStyle("-fx-base: #dbf4f9;");
        buttonControl();

        DurakButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) DurakButton.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/durak.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 900, 700);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.setTitle("Card Games");
                stage.setScene(scene);
                stage.show();
            }
        });

        ExitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) ExitButton.getScene().getWindow();
                stage.close();
            }
        });
    }

    private void buttonControl(){
        DurakButton.setText("Дурак");
        DurakButton.setStyle("-fx-font: 29 times-new-roman; -fx-base: #abdee6;");
        DurakButton.setMinWidth(35);
        DurakButton.setMinHeight(20);
        DurakButton.setLayoutX(40);
        DurakButton.setLayoutY(230);
        DurakButton.setDisable(false);

        HeartsButton.setText("Червы");
        HeartsButton.setStyle("-fx-font: 29 times-new-roman; -fx-base: #abdee6;");
        HeartsButton.setMinWidth(35);
        HeartsButton.setMinHeight(20);
        HeartsButton.setLayoutX(40);
        HeartsButton.setLayoutY(320);
        HeartsButton.setDisable(false);

        PasiansButton.setText("Пасьянс");
        PasiansButton.setStyle("-fx-font: 29 times-new-roman; -fx-base: #abdee6;");
        PasiansButton.setMinWidth(35);
        PasiansButton.setMinHeight(20);
        PasiansButton.setLayoutX(40);
        PasiansButton.setLayoutY(410);
        PasiansButton.setDisable(false);

        ExitButton.setText("Выход");
        ExitButton.setStyle("-fx-font: 29 times-new-roman; -fx-base: #abdee6;");
        ExitButton.setMinWidth(35);
        ExitButton.setMinHeight(20);
        ExitButton.setLayoutX(650);
        ExitButton.setLayoutY(506);
        ExitButton.setDisable(false);

        textField1.setText("Добро пожаловать!");
        textField1.setAlignment(Pos.CENTER);
        textField1.setStyle("-fx-background-color: #abdee6; -fx-font: 30 times-new-roman; -fx-border-width: 1.5; " +
                "-fx-border-color: #03a9f4; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        textField1.setMinHeight(40);
        textField1.setMinWidth(200);
        textField1.setLayoutX(220);
        textField1.setLayoutY(30);
        textField1.setEditable(false);
        textField1.setDisable(false);
        textField1.setFocusTraversable(false);

        textField2.setText("В какую игру хотите сыграть сегодня?");
        textField2.setAlignment(Pos.CENTER_LEFT);
        textField2.setStyle("-fx-background-color: #abdee6; -fx-font: 25 times-new-roman; -fx-border-width: 0.5; " +
                "-fx-border-color: gray; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        textField2.setMinHeight(50);
        textField2.setMinWidth(470);
        textField2.setLayoutX(40);
        textField2.setLayoutY(150);
        textField2.setEditable(false);
        textField2.setDisable(false);
        textField2.setFocusTraversable(false);
    }
}
