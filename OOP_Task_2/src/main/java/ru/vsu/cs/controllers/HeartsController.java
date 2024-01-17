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
import ru.vsu.cs.controllers.durakConstans.DurakConstans;
import ru.vsu.cs.tools.Card;
import ru.vsu.cs.tools.Hand;
import ru.vsu.cs.tools.ImageReturn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HeartsController {
    public AnchorPane anchorPane;
    public StackPane user1;
    public StackPane user2;
    public StackPane user3;
    public StackPane user4;
    public StackPane user1Select;
    public StackPane user2Select;
    public StackPane user3Select;
    public StackPane user4Select;
    public Button ready;
    public TextField clue;
    public TextField name1;
    public TextField name2;
    public TextField name3;
    public TextField name4;
    private int tmpTurn;
    private int turn;
    private boolean prepare = true;
    private int paddingSS = 0;
    private final ru.vsu.cs.Hearts hearts = new ru.vsu.cs.Hearts();
    private List<Hand> hands = new ArrayList<>(4);
    private final List<StackPane> handsFX = new ArrayList<>(4);
    private final List<StackPane> selectSpaces = new ArrayList<>(4);
    private final List<List<Card>> selectedCardsBeforeRound = new ArrayList<>(4);
    private final List<Card> selectedCardsTmp = new ArrayList<>();
    public List<Integer> score = new ArrayList<>(4);

    @FXML
    private void initialize() {
        //подготовка игры
        hearts.newGame();
        turn = hearts.getUserTurn();
        prepare = hearts.isPreparing();

        //подготовка сцены
        anchorPane.setStyle("-fx-background-image: url('images/heartsTable.jpg')");

        //подготовка к игре
        prepareForGame();
    }

    private void prepareForGame() {
        //поле
        handsFX.add(user1);
        handsFX.add(user2);
        handsFX.add(user3);
        handsFX.add(user4);
        user1.setLayoutX(239);
        user1.setLayoutY(527);
        user2.setLayoutX(10);
        user2.setLayoutY(107);
        user3.setLayoutX(239);
        user3.setLayoutY(10);
        user4.setLayoutX(780);
        user4.setLayoutY(107);
        updatePlayingField();

        //select-зоны
        selectSpaces.add(user1Select);
        selectSpaces.add(user2Select);
        selectSpaces.add(user3Select);
        selectSpaces.add(user4Select);
        user1Select.setLayoutX(343);
        user1Select.setLayoutY(354);
        user2Select.setLayoutX(130);
        user2Select.setLayoutY(242);
        user3Select.setLayoutX(343);
        user3Select.setLayoutY(183);
        user4Select.setLayoutX(600);
        user4Select.setLayoutY(242);

        //поле-подсказка
        clue = new TextField("Выберите 3 карты для обмена с соседом слева.");
        clue.setStyle("-fx-background-color: #01579b; -fx-font: 16 times-new-roman; -fx-text-fill: white;" +
                "-fx-border-color: white; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        anchorPane.getChildren().add(clue);
        clue.setMinHeight(60);
        clue.setMinWidth(375);
        clue.setLayoutX(DurakConstans.LayoutX);
        clue.setLayoutY(320);
        clue.setEditable(false);
        clue.setDisable(false);
        clue.setFocusTraversable(false);

        //Обработка кнопок
        ready.setText("Готово");
        ready.setStyle("-fx-background-color: #01579b; -fx-font: 16 times-new-roman; -fx-text-fill: white;" +
                "-fx-border-color: white; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        ready.setMaxHeight(50);
        ready.setMaxWidth(150);
        ready.setLayoutX(543);
        ready.setLayoutY(410);
        ready.setDisable(false);
        ready.setFocusTraversable(false);
        ready.setVisible(false);
        ready.setOnMouseClicked(this::readyButEvent);

        //надписи
        name1 = new TextField("Игрок_1");
        name1.setStyle("-fx-background-color: #01579b; -fx-font: 14 times-new-roman; -fx-text-fill: white;" +
                "-fx-border-color: white; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        anchorPane.getChildren().add(name1);
        name1.setMaxHeight(40);
        name1.setMaxWidth(85);
        name1.setLayoutX(244);
        name1.setLayoutY(650);
        name1.setEditable(false);
        name1.setDisable(false);
        name1.setFocusTraversable(false);

        name2 = new TextField("Игрок_2");
        name2.setStyle("-fx-background-color: #01579b; -fx-font: 14 times-new-roman; -fx-text-fill: white;" +
                "-fx-border-color: white; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        anchorPane.getChildren().add(name2);
        name2.setMaxHeight(40);
        name2.setMaxWidth(85);
        name2.setLayoutX(22);
        name2.setLayoutY(52);
        name2.setEditable(false);
        name2.setDisable(false);
        name2.setFocusTraversable(false);

        name3 = new TextField("Игрок_3");
        name3.setStyle("-fx-background-color: #01579b; -fx-font: 14 times-new-roman; -fx-text-fill: white;" +
                "-fx-border-color: white; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        anchorPane.getChildren().add(name3);
        name3.setMaxHeight(40);
        name3.setMaxWidth(85);
        name3.setLayoutX(244);
        name3.setLayoutY(133);
        name3.setEditable(false);
        name3.setDisable(false);
        name3.setFocusTraversable(false);

        name4 = new TextField("Игрок_4");
        name4.setStyle("-fx-background-color: #01579b; -fx-font: 14 times-new-roman; -fx-text-fill: white;" +
                "-fx-border-color: white; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        anchorPane.getChildren().add(name4);
        name4.setMaxHeight(40);
        name4.setMaxWidth(85);
        name4.setLayoutX(793);
        name4.setLayoutY(52);
        name4.setEditable(false);
        name4.setDisable(false);
        name4.setFocusTraversable(false);
    }

    private void updatePlayingField() {
        for (int i = 0; i < 4; i++) {
            hands = hearts.getHands();
            Hand content = hearts.getHands().get(i);
            StackPane stackPane = handsFX.get(i);
            stackPane.getChildren().clear();
            int pad = 0;
            for (int j = 0; j < content.getSize(); j++) {
                Card card = content.getCard(j);
                if (turn == i && prepare) {
                    card.setFaceState(Card.CardFaceState.FACE_UP);
                } else {
                    if (turn != i && prepare) {
                        card.setFaceState(Card.CardFaceState.FACE_DOWN);
                    } else {
                        if (tmpTurn == i && !prepare) {
                            card.setFaceState(Card.CardFaceState.FACE_UP);
                        } else {
                            if (tmpTurn != i && !prepare) {
                                card.setFaceState(Card.CardFaceState.FACE_DOWN);
                            }
                        }
                    }
                }
                ImageView image = new ImageView(ImageReturn.getImage(card));
                image.setFitHeight(163);
                image.setFitWidth(110);
                image.setOnMouseClicked(this::mouseEvent);
                if (i % 2 == 1) {
                    StackPane.setAlignment(image, Pos.TOP_CENTER);
                    StackPane.setMargin(image, new Insets(pad, 0, 0, 0));
                    stackPane.getChildren().add(image);
                    pad += 27;
                } else {
                    StackPane.setAlignment(image, Pos.CENTER_LEFT);
                    StackPane.setMargin(image, new Insets(0, 0, 0, pad));
                    stackPane.getChildren().add(image);
                    pad += 26;
                }
            }
        }
    }

    private void addToSelectSpace(int indexOfImageInUserHand, ImageView image) {
        if (prepare) {
            selectSpaces.get(turn).getChildren().add(image);
            StackPane.setAlignment(image, Pos.CENTER_LEFT);
            StackPane.setMargin(image, new Insets(0, 0, 0, paddingSS));
            paddingSS += 30;
            handsFX.get(turn).getChildren().remove(indexOfImageInUserHand, indexOfImageInUserHand);
            selectedCardsTmp.add(hearts.getHands().get(turn).getCard(indexOfImageInUserHand));
            hearts.getHands().get(turn).remove(indexOfImageInUserHand);
        } else {
            selectSpaces.get(tmpTurn).getChildren().add(image);
            StackPane.setAlignment(image, Pos.CENTER_LEFT);
            StackPane.setMargin(image, new Insets(0, 0, 0, paddingSS));
            handsFX.get(tmpTurn).getChildren().remove(indexOfImageInUserHand, indexOfImageInUserHand);
        }
    }

    private void mouseEvent(MouseEvent mouseEvent) {
        ImageView image = (ImageView) mouseEvent.getSource();
        int index;
        if (prepare) {
            if (selectSpaces.get(turn).getChildren().size() < 3) {
                clue.setVisible(false);
                StackPane stackPane = handsFX.get(turn);
                index = stackPane.getChildren().indexOf(image);
                if (index >= 0) {
                    addToSelectSpace(index, image);
                    if (selectSpaces.get(turn).getChildren().size() == 3) {
                        if (turn == 0) {
                            ready.setLayoutX(543);
                            ready.setLayoutY(410);
                        }
                        if (turn == 1) {
                            ready.setLayoutX(175);
                            ready.setLayoutY(425);
                        }
                        if (turn == 2) {
                            ready.setLayoutX(543);
                            ready.setLayoutY(239);
                        }
                        if (turn == 3) {
                            ready.setLayoutX(645);
                            ready.setLayoutY(425);
                        }
                        ready.setVisible(true);
                    }
                }
            }
        } else {
            if (selectSpaces.get(tmpTurn).getChildren().size() < 1) {
                clue.setVisible(false);
                StackPane stackPane = handsFX.get(tmpTurn);
                index = stackPane.getChildren().indexOf(image);
                if (index >= 0) {
                    Card card = hands.get(tmpTurn).getCard(index);
                    if (hearts.trickFormation(tmpTurn, card)) {
                        addToSelectSpace(index, image);
                        if (selectSpaces.get(tmpTurn).getChildren().size() == 1) {
                            if (tmpTurn == 0) {
                                ready.setLayoutX(463);
                                ready.setLayoutY(410);
                            }
                            if (tmpTurn == 1) {
                                ready.setLayoutX(175);
                                ready.setLayoutY(425);
                            }
                            if (tmpTurn == 2) {
                                ready.setLayoutX(463);
                                ready.setLayoutY(239);
                            }
                            if (tmpTurn == 3) {
                                ready.setLayoutX(645);
                                ready.setLayoutY(425);
                            }
                            ready.setVisible(true);
                        }
                    } else {
                        editClue("Нарушены правила игры!", 220);
                    }
                }
            }
        }
    }

    private void readyButEvent(MouseEvent mouseEvent) {
        paddingSS = 0;
        ready.setVisible(false);
        if (prepare) {
            selectSpaces.get(turn).getChildren().clear();
            selectedCardsBeforeRound.add(List.copyOf(selectedCardsTmp));
            selectedCardsTmp.clear();
            if (selectedCardsBeforeRound.size() == 4) {
                hearts.switchCardsBeforeRound(selectedCardsBeforeRound.get(0), selectedCardsBeforeRound.get(1), selectedCardsBeforeRound.get(2), selectedCardsBeforeRound.get(3));
                selectedCardsBeforeRound.clear();
                prepare = false;
                turn = hearts.turn();
                tmpTurn = turn;
                editClue("Игрок_" + (turn + 1) + " начинает выкладывать взятку", 320);
            }
            turn = hearts.turn();
            clue.setVisible(true);
        } else {
            selectedCardsTmp.clear();
            tmpTurn = getTmpTurn();
            if (tmpTurn == turn) {
                hearts.calcState();
                prepare = hearts.isPreparing();
                for (int i = 0; i < 4; i++) {
                    selectSpaces.get(i).getChildren().clear();
                }
                result();
                turn = hearts.getUserTurn();
                if (!prepare) {
                    editClue("Игрок_" + (turn + 1) + " забирает взятку! Продолжайте игру", 350);
                    tmpTurn = turn;
                } else {
                    editClue("Новый раунд! Выберите карты для обмена.", 250);
                    turn = hearts.getUserTurn();
                }
            }
        }
        updatePlayingField();
    }

    private int getTmpTurn() {
        switch (tmpTurn) {
            case 0 -> {
                return 1;
            }
            case 1 -> {
                return 2;
            }
            case 2 -> {
                return 3;
            }
            case 3 -> {
                return 0;
            }
        }
        return -1;
    }

    private void editClue(String str, int size) {
        clue.setVisible(true);
        clue.setText(str);
        clue.setMinWidth(size);
    }

    private void result() {
        if (hearts.getState() == ru.vsu.cs.Hearts.GameState.FINISHED) {
            score = hearts.getScore();
            Stage stage = (Stage) handsFX.get(0).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/heartsResult.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 900, 700);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setTitle("Results");
            stage.setScene(scene);
            stage.show();
        }
    }
}
