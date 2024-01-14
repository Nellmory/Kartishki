package ru.vsu.cs.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ru.vsu.cs.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PasiansController {
    public AnchorPane anchorPane;
    public StackPane playingField1;
    public StackPane playingField2;
    public StackPane playingField3;
    public StackPane playingField4;
    public StackPane playingField5;
    public StackPane playingField6;
    public StackPane playingField7;
    public Button endGame;
    public StackPane clubsKS;
    public StackPane heartsKS;
    public StackPane spadesKS;
    public StackPane diamondsKS;
    public StackPane cardsFromGP = new StackPane();
    private List<StackPane> playingFieldFX = new ArrayList<>(7);
    private List<StackPane> keepingSpaceFX = new ArrayList<>(4);
    private List<List<Card>> playingField;
    private List<List<Card>> spaceForKeeping;
    private ImageView gamingPack;
    private TextField clue;
    private int indexForGamingPack = 0;
    private final Pasians pasians = new Pasians();
    private FocusedCard focusedCard = null;

    @FXML
    private void initialize() {
        //подготовка игры
        pasians.newGame();
        playingField = pasians.getPlayingField();
        spaceForKeeping = pasians.getSpaceForKeeping();

        //подготовка сцены
        anchorPane.setStyle("-fx-background-image: url('images/pasiansTable.jpg')");

        //подготовка к игре
        prepareForGame();

        //игра
        pasians();
    }

    private void prepareForGame() {
        //поле
        playingFieldFX.add(playingField1);
        playingFieldFX.add(playingField2);
        playingFieldFX.add(playingField3);
        playingFieldFX.add(playingField4);
        playingFieldFX.add(playingField5);
        playingFieldFX.add(playingField6);
        playingFieldFX.add(playingField7);
        int lX = 55;
        for (StackPane stackPane : playingFieldFX) {
            stackPane.setLayoutX(lX);
            stackPane.setLayoutY(210);
            lX += 115;
        }
        updatePlayingField();

        //место хранения карт
        keepingSpaceFX.add(clubsKS);
        keepingSpaceFX.add(heartsKS);
        keepingSpaceFX.add(spadesKS);
        keepingSpaceFX.add(diamondsKS);
        lX = 400;
        for (StackPane stackPane : keepingSpaceFX) {
            stackPane.setLayoutX(lX);
            stackPane.setLayoutY(35);
            lX += 115;
        }
        updateKeepingSpace();


        //колода
        gamingPack = new ImageView("images/0.gif");
        gamingPack.setFitHeight(148);
        gamingPack.setFitWidth(100);
        anchorPane.getChildren().add(gamingPack);
        gamingPack.setLayoutX(55);
        gamingPack.setLayoutY(35);
        gamingPack.setOnMouseClicked(this::takeCardFromGP);

        //Обработка кнопок
        endGame.setText("Закончить игру");
        endGame.setStyle("-fx-background-color: #7cb342; -fx-font: 16 times-new-roman; -fx-text-fill: white;" +
                "-fx-border-color: white; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        endGame.setMaxHeight(50);
        endGame.setMaxWidth(150);
        endGame.setLayoutX(735);
        endGame.setLayoutY(630);
        endGame.setDisable(false);
        endGame.setFocusTraversable(false);
        endGame.setVisible(true);
        endGame.setOnMouseClicked(this::endGameButEvent);

        /*
        takeCards.setText("Взять карты");
        takeCards.setStyle("-fx-background-color: #800000; -fx-font: 16 times-new-roman; -fx-text-fill: white;" +
                "-fx-border-color: white; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        takeCards.setMaxHeight(50);
        takeCards.setMaxWidth(150);
        takeCards.setLayoutX(695);
        takeCards.setLayoutY(622);
        takeCards.setDisable(false);
        takeCards.setFocusTraversable(false);
        takeCards.setVisible(false);
        takeCards.setOnMouseClicked(this::takeCardsButMouseEvent);

        attack.setText("Атака!");
        attack.setStyle("-fx-background-color: #800000; -fx-font: 18 times-new-roman; -fx-text-fill: white;" +
                "-fx-border-color: white; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        attack.setMaxHeight(50);
        attack.setMaxWidth(100);
        attack.setLayoutX(710);
        attack.setLayoutY(525);
        attack.setDisable(false);
        attack.setFocusTraversable(false);
        attack.setVisible(false);
        attack.setOnMouseClicked(this::attackButMouseEvent);

        stop.setText("Закончить ход");
        stop.setStyle("-fx-background-color: #800000; -fx-font: 16 times-new-roman; -fx-text-fill: white;" +
                "-fx-border-color: white; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        stop.setMaxHeight(50);
        stop.setMaxWidth(150);
        stop.setLayoutX(670);
        stop.setLayoutY(525);
        stop.setDisable(false);
        stop.setFocusTraversable(false);
        stop.setVisible(false);
        stop.setOnMouseClicked(this::attackButMouseEvent);

        play.setText("Ок!");
        play.setStyle("-fx-background-color: #800000; -fx-font: 18 times-new-roman; -fx-text-fill: white;" +
                "-fx-border-color: white; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        play.setMaxHeight(50);
        play.setMaxWidth(100);
        play.setLayoutX(710);
        play.setLayoutY(525);
        play.setDisable(false);
        play.setVisible(false);
        play.setFocusTraversable(false);
        play.setOnMouseClicked(this::playButMouseEvent);*/

        //поле-подсказка
        clue = new TextField(" ");
        anchorPane.getChildren().add(clue);
        clue.setStyle("-fx-background-color: #7cb342; -fx-font: 16 times-new-roman; -fx-text-fill: white;" +
                "-fx-border-color: white; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        clue.setMinHeight(50);
        clue.setMinWidth(260);
        clue.setLayoutX(45);
        clue.setLayoutY(620);
        clue.setEditable(false);
        clue.setDisable(false);
        clue.setFocusTraversable(false);
        clue.setVisible(false);
    }

    private void updatePlayingField() {
        for (int i = 0; i < 7; i++) {
            List<Card> content = playingField.get(i);
            StackPane stackPane = playingFieldFX.get(i);
            stackPane.getChildren().clear();
            int pad = 0;
            for (Card card : content) {
                ImageView image = new ImageView(ImageReturn.getImage(card));
                image.setFitHeight(148);
                image.setFitWidth(100);
                image.setOnMouseClicked(this::mouseEvent);
                StackPane.setAlignment(image, Pos.TOP_CENTER);
                StackPane.setMargin(image, new Insets(pad, 0, 0, 0));
                stackPane.getChildren().add(image);
                pad += 20;
            }
        }
    }

    private void updateKeepingSpace() {
        for (int i = 0; i < 4; i++) {
            //добавим дефолтную карту
            StackPane stackPane = keepingSpaceFX.get(i);
            stackPane.getChildren().clear();
            ImageView base = new ImageView("images/keepingSpace.png");
            base.setFitHeight(148);
            base.setFitWidth(100);
            base.setOnMouseClicked(this::KSMouseEvent);
            stackPane.getChildren().add(base);
            //добавим хранимые карты
            if (spaceForKeeping.size() != 0) {
                List<Card> content = spaceForKeeping.get(i);
                for (Card card : content) {
                    ImageView image = new ImageView(ImageReturn.getImage(card));
                    image.setFitHeight(148);
                    image.setFitWidth(100);
                    image.setOnMouseClicked(this::KSMouseEvent);
                    stackPane.getChildren().add(image);
                }
            }
        }
    }

    private void KSMouseEvent(MouseEvent mouseEvent) {
        if (focusedCard != null) {
            ImageView image = (ImageView) mouseEvent.getSource();
            int index;
            for (int i = 0; i < 4; i++) {
                StackPane stackPane = keepingSpaceFX.get(i);
                index = stackPane.getChildren().indexOf(image);
                if (index >= 0) {
                    if (pasians.putInKeepingSpace(i, focusedCard.getCard())) {
                        updateKeepingSpace();
                        pasians.removeFromPlayingField(focusedCard.getIndexOfStackPane(), focusedCard.getCard());
                        updatePlayingField();
                        focusedCard = null;
                    } else {
                        focusedCard = null;
                        editClue("Нарушены правила игры!", 220);
                    }
                    return;
                }
            }
        }
    }

    private void useCardFromGP(MouseEvent mouseEvent) {
        //доделать
    }

    private void takeCardFromGP(MouseEvent mouseEvent) {
        //доделать
        Card card = pasians.getCardFromGamingPack(indexForGamingPack);
        indexForGamingPack++;
        ImageView image = new ImageView(ImageReturn.getImage(card));
        image.setFitHeight(148);
        image.setFitWidth(100);
        image.setOnMouseClicked(this::useCardFromGP);
        cardsFromGP.getChildren().add(image);
        cardsFromGP.setLayoutX(170);
        cardsFromGP.setLayoutY(35);
    }

    private void endGameButEvent(MouseEvent mouseEvent) {
        pasians.setState(Pasians.GameState.FAIL);
        result();
    }

    private void editClue(String str, int size) {
        clue.setVisible(true);
        clue.setText(str);
        clue.setMinWidth(size);
    }

    private void pasians() {

    }

    public void mouseEvent(MouseEvent mouseEvent) {
        ImageView image = (ImageView) mouseEvent.getSource();
        int index;
        for (int i = 0; i < 7; i++) {
            StackPane stackPane = playingFieldFX.get(i);
            index = stackPane.getChildren().indexOf(image);
            if (index >= 0 && playingField.get(i).get(index).getFaceState() == Card.CardFaceState.FACE_UP) {
                focusedCard = new FocusedCard(playingField.get(i).get(index), index, i);
                return;
            }
        }
    }

    private void result() {
        if (pasians.getState() == Pasians.GameState.WIN) {
            Stage stage = (Stage) endGame.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/win.fxml"));
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
        if (pasians.getState() == Pasians.GameState.FAIL) {
            Stage stage = (Stage) endGame.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fail.fxml"));
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
    }
}
