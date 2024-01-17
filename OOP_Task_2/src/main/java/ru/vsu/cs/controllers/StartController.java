package ru.vsu.cs.controllers;

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

import java.io.IOException;

public class StartController {
    public Button heartsButton;
    public Button durakButton;
    public Button pasiansButton;
    public Button exitButton;
    public TextField textField1;
    public TextField textField2;
    @FXML
    private AnchorPane anchorPaneStart;

    @FXML
    private void initialize() {
        anchorPaneStart.setStyle("-fx-base: #dbf4f9;");
        buttonControl();

        durakButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) durakButton.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/durak.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 900, 700);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.setTitle("Дурак");
                stage.setScene(scene);
                stage.show();
            }
        });

        pasiansButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) pasiansButton.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pasians.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 900, 700);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.setTitle("Пасьянс");
                stage.setScene(scene);
                stage.show();
            }
        });

        heartsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) heartsButton.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hearts.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 900, 700);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.setTitle("Червы");
                stage.setScene(scene);
                stage.show();
            }
        });

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) exitButton.getScene().getWindow();
                stage.close();
            }
        });
    }

    private void buttonControl(){
        durakButton.setText("Дурак");
        durakButton.setStyle("-fx-font: 29 times-new-roman; -fx-base: #abdee6;");
        durakButton.setMinWidth(35);
        durakButton.setMinHeight(20);
        durakButton.setLayoutX(40);
        durakButton.setLayoutY(230);
        durakButton.setDisable(false);

        heartsButton.setText("Червы");
        heartsButton.setStyle("-fx-font: 29 times-new-roman; -fx-base: #abdee6;");
        heartsButton.setMinWidth(35);
        heartsButton.setMinHeight(20);
        heartsButton.setLayoutX(40);
        heartsButton.setLayoutY(320);
        heartsButton.setDisable(false);

        pasiansButton.setText("Пасьянс");
        pasiansButton.setStyle("-fx-font: 29 times-new-roman; -fx-base: #abdee6;");
        pasiansButton.setMinWidth(35);
        pasiansButton.setMinHeight(20);
        pasiansButton.setLayoutX(40);
        pasiansButton.setLayoutY(410);
        pasiansButton.setDisable(false);

        exitButton.setText("Выход");
        exitButton.setStyle("-fx-font: 29 times-new-roman; -fx-base: #abdee6;");
        exitButton.setMinWidth(35);
        exitButton.setMinHeight(20);
        exitButton.setLayoutX(650);
        exitButton.setLayoutY(506);
        exitButton.setDisable(false);

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
