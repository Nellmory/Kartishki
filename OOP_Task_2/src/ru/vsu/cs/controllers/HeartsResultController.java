package ru.vsu.cs.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ru.vsu.cs.Hearts;

import java.util.ArrayList;
import java.util.List;

public class HeartsResultController {
    public AnchorPane anchorPane;
    public TextField resultWindow;
    public TextField user1;
    public TextField user2;
    public TextField user3;
    public TextField user4;
    public ImageView first;
    public ImageView second;
    public ImageView third;
    public ImageView fourth;
    public TextField resultText;
    private List<TextField> userListFX = new ArrayList<>();
    private List<Integer> userList;
    public TextField user1Score = new TextField();
    public TextField user2Score = new TextField();
    public TextField user3Score = new TextField();
    public TextField user4Score = new TextField();

    private final HeartsController heartsController = new HeartsController();
    private List<TextField> userScoreListFX = new ArrayList<>();
    private List<Integer> userScoreList;

    @FXML
    private void initialize() {
        anchorPane.setStyle("-fx-base: #dbf4f9;");
        prepareWindow();
        //distributePrizePlaces();
    }

    private void distributePrizePlaces() {
        makeLists();
        for (int i = 0; i < 4; i++) {
            //Берем итый элемент т.е. индекс игрока
            userListFX.get(i).setText("Игрок_" + (userList.get(i) + 1) + " :");
            //окно с баллами тоже присваиваем кол-во баллов
            userScoreListFX.get(i).setText(userScoreList.get(i) + "");
        }
    }

    private void makeLists() {
        List<Integer> score = heartsController.score;
        for (int j = 0; j < 4; j++) {
            int min = -1;
            int indexOfPlayer = -1;
            for (int i = 0; i < score.size(); i++) {
                int scoreN = score.get(i);
                if (scoreN < min) {
                    min = scoreN;
                    indexOfPlayer = i;
                }
            }
            userList.add(indexOfPlayer);
            userScoreList.add(score.get(indexOfPlayer));
            score.remove(indexOfPlayer);
        }
    }

    private void prepareWindow() {
        //окошко
        resultWindow.setAlignment(Pos.CENTER);
        resultWindow.setStyle("-fx-background-color: #abdee6; -fx-font: 30 times-new-roman; -fx-border-width: 1.5; " +
                "-fx-border-color: #03a9f4; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        resultWindow.setMinHeight(600);
        resultWindow.setMinWidth(450);
        resultWindow.setLayoutX(225);
        resultWindow.setLayoutY(50);
        resultWindow.setEditable(false);
        resultWindow.setDisable(false);
        resultWindow.setFocusTraversable(false);

        //надписи
        resultText.setText("Результаты");
        resultText.setAlignment(Pos.CENTER);
        resultText.setStyle("-fx-background-color: transparent; -fx-font: 30 times-new-roman; -fx-border-width: 1.5; " +
                "-fx-border-color: #03a9f4; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        resultText.setMinHeight(70);
        resultText.setMinWidth(450);
        resultText.setLayoutX(225);
        resultText.setLayoutY(50);
        resultText.setEditable(false);
        resultText.setDisable(false);
        resultText.setFocusTraversable(false);

        user1.setText("Игрок_1 :");
        user1.setAlignment(Pos.CENTER_LEFT);
        user1.setStyle("-fx-background-color: #dbf4f9; -fx-font: 20 times-new-roman; -fx-border-width: 1.5; " +
                "-fx-border-color: #03a9f4; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        user1.setMinHeight(50);
        user1.setMinWidth(300);
        user1.setLayoutX(349);
        user1.setLayoutY(163);
        user1.setEditable(false);
        user1.setDisable(false);
        user1.setFocusTraversable(false);

        user2.setText("Игрок_2 :");
        user2.setAlignment(Pos.CENTER_LEFT);
        user2.setStyle("-fx-background-color: #dbf4f9; -fx-font: 20 times-new-roman; -fx-border-width: 1.5; " +
                "-fx-border-color: #03a9f4; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        user2.setMinHeight(50);
        user2.setMinWidth(300);
        user2.setLayoutX(349);
        user2.setLayoutY(285);
        user2.setEditable(false);
        user2.setDisable(false);
        user2.setFocusTraversable(false);

        user3.setText("Игрок_3 :");
        user3.setAlignment(Pos.CENTER_LEFT);
        user3.setStyle("-fx-background-color: #dbf4f9; -fx-font: 20 times-new-roman; -fx-border-width: 1.5; " +
                "-fx-border-color: #03a9f4; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        user3.setMinHeight(50);
        user3.setMinWidth(300);
        user3.setLayoutX(349);
        user3.setLayoutY(407);
        user3.setEditable(false);
        user3.setDisable(false);
        user3.setFocusTraversable(false);

        user4.setText("Игрок_4 :");
        user4.setAlignment(Pos.CENTER_LEFT);
        user4.setStyle("-fx-background-color: #dbf4f9; -fx-font: 20 times-new-roman; -fx-border-width: 1.5; " +
                "-fx-border-color: #03a9f4; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        user4.setMinHeight(50);
        user4.setMinWidth(300);
        user4.setLayoutX(349);
        user4.setLayoutY(529);
        user4.setEditable(false);
        user4.setDisable(false);
        user4.setFocusTraversable(false);

        userListFX.add(user1);
        userListFX.add(user2);
        userListFX.add(user3);
        userListFX.add(user4);

        //очки
        user1Score.setText("10");
        user1Score.setAlignment(Pos.CENTER_LEFT);
        user1Score.setStyle("-fx-background-color: white; -fx-font: 20 times-new-roman; -fx-border-width: 1.5; " +
                "-fx-border-color: #03a9f4; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        anchorPane.getChildren().add(user1Score);
        user1Score.setMinHeight(50);
        user1Score.setMaxWidth(60);
        user1Score.setLayoutX(589);
        user1Score.setLayoutY(163);
        user1Score.setEditable(false);
        user1Score.setDisable(false);
        user1Score.setFocusTraversable(false);

        user2Score.setText("40");
        user2Score.setAlignment(Pos.CENTER_LEFT);
        user2Score.setStyle("-fx-background-color: white; -fx-font: 20 times-new-roman; -fx-border-width: 1.5; " +
                "-fx-border-color: #03a9f4; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        anchorPane.getChildren().add(user2Score);
        user2Score.setMinHeight(50);
        user2Score.setMaxWidth(60);
        user2Score.setLayoutX(589);
        user2Score.setLayoutY(285);
        user2Score.setEditable(false);
        user2Score.setDisable(false);
        user2Score.setFocusTraversable(false);

        user3Score.setText("99");
        user3Score.setAlignment(Pos.CENTER_LEFT);
        user3Score.setStyle("-fx-background-color: white; -fx-font: 20 times-new-roman; -fx-border-width: 1.5; " +
                "-fx-border-color: #03a9f4; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        anchorPane.getChildren().add(user3Score);
        user3Score.setMinHeight(50);
        user3Score.setMaxWidth(60);
        user3Score.setLayoutX(589);
        user3Score.setLayoutY(407);
        user3Score.setEditable(false);
        user3Score.setDisable(false);
        user3Score.setFocusTraversable(false);

        user4Score.setText("100");
        user4Score.setAlignment(Pos.CENTER_LEFT);
        user4Score.setStyle("-fx-background-color: white; -fx-font: 20 times-new-roman; -fx-border-width: 1.5; " +
                "-fx-border-color: #03a9f4; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        anchorPane.getChildren().add(user4Score);
        user4Score.setMinHeight(50);
        user4Score.setMaxWidth(60);
        user4Score.setLayoutX(589);
        user4Score.setLayoutY(529);
        user4Score.setEditable(false);
        user4Score.setDisable(false);
        user4Score.setFocusTraversable(false);

        userScoreListFX.add(user1Score);
        userScoreListFX.add(user2Score);
        userScoreListFX.add(user3Score);
        userScoreListFX.add(user4Score);

        //значки мест
        first = new ImageView("images/heartsResult/crown1.png");
        anchorPane.getChildren().add(first);
        first.setFitHeight(100);
        first.setFitWidth(100);
        first.setLayoutX(237);
        first.setLayoutY(138);

        second = new ImageView("images/heartsResult/crown2.png");
        anchorPane.getChildren().add(second);
        second.setFitHeight(100);
        second.setFitWidth(100);
        second.setLayoutX(237);
        second.setLayoutY(260);

        third = new ImageView("images/heartsResult/crown3.png");
        anchorPane.getChildren().add(third);
        third.setFitHeight(100);
        third.setFitWidth(100);
        third.setLayoutX(237);
        third.setLayoutY(382);

        third = new ImageView("images/heartsResult/dead.png");
        anchorPane.getChildren().add(third);
        third.setFitHeight(90);
        third.setFitWidth(90);
        third.setLayoutX(242);
        third.setLayoutY(509);
    }
}
