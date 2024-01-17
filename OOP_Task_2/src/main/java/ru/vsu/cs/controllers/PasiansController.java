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
import ru.vsu.cs.Main;
import ru.vsu.cs.tools.Card;
import ru.vsu.cs.tools.ImageReturn;

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
    private final List<StackPane> playingFieldFX = new ArrayList<>(7);
    private final List<StackPane> keepingSpaceFX = new ArrayList<>(4);
    private List<List<Card>> playingField;
    private List<List<Card>> spaceForKeeping;
    private ImageView gamingPack;
    private ImageView repeatGP;
    private TextField clue;
    private int indexForGamingPack = -1;
    private int sizeOfGP;
    private final ru.vsu.cs.Pasians pasians = new ru.vsu.cs.Pasians();
    private FocusedCard focusedCard = null;
    private List<FocusedCard> focusedCards = null;

    @FXML
    private void initialize() {
        //подготовка игры
        pasians.newGame();
        sizeOfGP = pasians.getSizeOfGamingPack();
        playingField = pasians.getPlayingField();
        spaceForKeeping = pasians.getSpaceForKeeping();

        //подготовка сцены
        anchorPane.setStyle("-fx-background-image: url('images/pasiansTable.jpg')");

        //подготовка к игре
        prepareForGame();
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
        endGame.setStyle("-fx-background-color: #7cb342; -fx-font: 16 times-new-roman; -fx-text-fill: white;" + "-fx-border-color: white; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        endGame.setMaxHeight(50);
        endGame.setMaxWidth(150);
        endGame.setLayoutX(735);
        endGame.setLayoutY(630);
        endGame.setDisable(false);
        endGame.setFocusTraversable(false);
        endGame.setVisible(true);
        endGame.setOnMouseClicked(this::endGameButEvent);

        repeatGP = new ImageView("images/rep.png");
        repeatGP.setFitHeight(65);
        repeatGP.setFitWidth(65);
        anchorPane.getChildren().add(repeatGP);
        repeatGP.setLayoutX(72);
        repeatGP.setLayoutY(77);
        repeatGP.setVisible(false);
        repeatGP.setOnMouseClicked(this::repeatGP);

        //поле-подсказка
        clue = new TextField(" ");
        anchorPane.getChildren().add(clue);
        clue.setStyle("-fx-background-color: #7cb342; -fx-font: 16 times-new-roman; -fx-text-fill: white;" + "-fx-border-color: white; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
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
                pad += 27;
            }
            if (content.isEmpty()) {
                ImageView image = new ImageView("images/keepingSpace.png");
                image.setFitHeight(148);
                image.setFitWidth(100);
                image.setOnMouseClicked(this::mouseEvent);
                StackPane.setAlignment(image, Pos.TOP_CENTER);
                StackPane.setMargin(image, new Insets(pad, 0, 0, 0));
                stackPane.getChildren().add(image);
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

    private void updateGamingPack() {
        sizeOfGP = pasians.getSizeOfGamingPack();
        indexForGamingPack -= 1;
        cardsFromGP.getChildren().remove(cardsFromGP.getChildren().size() - 1);
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
                        if (focusedCard.getIndexOfStackPane() != -1) {
                            pasians.removeFromPlayingField(focusedCard.getIndexOfStackPane(), focusedCard.getCard());
                            updatePlayingField();
                        } else {
                            pasians.removeCardFromGamingPack(indexForGamingPack);
                            updateGamingPack();
                        }
                        focusedCard = null;
                        clue.setVisible(false);
                        result();
                    } else {
                        focusedCard = null;
                        clue.setVisible(false);
                    }
                    return;
                }
            }
        } else {
            ImageView image = (ImageView) mouseEvent.getSource();
            int index;
            for (int i = 0; i < 4; i++) {
                StackPane stackPane = keepingSpaceFX.get(i);
                index = stackPane.getChildren().indexOf(image);
                if (index > 0) {
                    if (focusedCard == null && focusedCards == null) {
                        focusedCard = new FocusedCard(pasians.getCardFromKeepingSpace(i, index - 1), index, i - 5);
                        editClue("Вы выбрали карту!", 200);
                    }
                    return;
                }
            }
        }
    }

    private boolean putOnTopOfField(int indexOfPF, FocusedCard fC, boolean upd) {
        if (pasians.putOnTop(indexOfPF, fC.getCard())) {
            if (fC.getIndexOfStackPane() >= 0) {
                pasians.removeFromPlayingField(fC.getIndexOfStackPane(), fC.getCard());
            } else {
                if (fC.getIndexOfStackPane() == -1) {
                    pasians.removeCardFromGamingPack(indexForGamingPack);
                    updateGamingPack();
                } else {
                    pasians.removeFromKeepingSpace(fC.getIndexOfStackPane() + 5, fC.getCard());
                    updateKeepingSpace();
                }
            }
            if (upd) {
                updatePlayingField();
                clue.setVisible(false);
            }
            result();
            return true;
        } else {
            clue.setVisible(false);
            return false;
        }
    }

    private void repeatGP(MouseEvent mouseEvent) {
        gamingPack.setVisible(true);
        cardsFromGP.getChildren().clear();
        repeatGP.setVisible(false);
    }

    private void takeCardFromGP(MouseEvent mouseEvent) {
        if (indexForGamingPack == sizeOfGP - 1) {
            indexForGamingPack = -1;
        }
        indexForGamingPack++;
        Card card = pasians.getCardFromGamingPack(indexForGamingPack);
        ImageView image = new ImageView(ImageReturn.getImage(card));
        image.setFitHeight(148);
        image.setFitWidth(100);
        image.setOnMouseClicked(this::mouseEvent);
        cardsFromGP.getChildren().add(image);
        cardsFromGP.setLayoutX(170);
        cardsFromGP.setLayoutY(35);
        if (indexForGamingPack == sizeOfGP - 1) {
            gamingPack.setVisible(false);
            repeatGP.setVisible(true);
        }
    }

    private void endGameButEvent(MouseEvent mouseEvent) {
        pasians.setState(ru.vsu.cs.Pasians.GameState.FAIL);
        result();
    }

    public void mouseEvent(MouseEvent mouseEvent) {
        ImageView image = (ImageView) mouseEvent.getSource();
        int index;
        StackPane stackPane = cardsFromGP;
        index = stackPane.getChildren().indexOf(image);
        if (index >= 0) {
            if (focusedCard == null && focusedCards == null) {
                focusedCard = new FocusedCard(pasians.getCardFromGamingPack(indexForGamingPack), index, -1);
                editClue("Вы выбрали карту!", 200);
            }
            return;
        }
        for (int i = 0; i < 7; i++) {
            stackPane = playingFieldFX.get(i);
            index = stackPane.getChildren().indexOf(image);
            List<Card> content = playingField.get(i);
            if (index >= 0 && content.isEmpty() || index >= 0 && playingField.get(i).get(index).getFaceState() == Card.CardFaceState.FACE_UP) {
                if (focusedCard == null && focusedCards == null && !content.isEmpty()) {
                    int sizeOfField = playingField.get(i).size();
                    if (index == sizeOfField - 1) {
                        focusedCard = new FocusedCard(playingField.get(i).get(index), index, i);
                        editClue("Вы выбрали карту!", 200);
                    } else {
                        focusedCards = new ArrayList<>();
                        for (int l = index; l < sizeOfField; l++) {
                            FocusedCard fC = new FocusedCard(playingField.get(i).get(l), l, i);
                            focusedCards.add(fC);
                        }
                        editClue("Вы выбрали несколько карт!", 230);
                    }
                    return;
                } else {
                    if (focusedCard != null) {
                        putOnTopOfField(i, focusedCard, true);
                        focusedCard = null;
                        result();
                    } else {
                        if (focusedCards != null) {
                            boolean upd = false;
                            for (int l = 0; l < focusedCards.size(); l++) {
                                FocusedCard fC = focusedCards.get(l);
                                if (l == focusedCards.size() - 1) upd = true;
                                boolean flag = putOnTopOfField(i, fC, upd);
                                if (!flag) {
                                    break;
                                }
                            }
                            focusedCards = null;
                            result();
                        }
                    }
                }
            }
        }
    }

    private void editClue(String str, int size) {
        clue.setVisible(true);
        clue.setText(str);
        clue.setMinWidth(size);
    }

    private void result() {
        pasians.calcState();
        if (pasians.getState() == ru.vsu.cs.Pasians.GameState.WIN) {
            Stage stage = (Stage) endGame.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/win.fxml"));
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
        if (pasians.getState() == ru.vsu.cs.Pasians.GameState.FAIL) {
            Stage stage = (Stage) endGame.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fail.fxml"));
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
    }
}
