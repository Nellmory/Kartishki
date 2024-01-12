package ru.vsu.cs.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
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


public class DurakBotController {
    public AnchorPane anchorPane;
    public StackPane battleField2;
    public Button play;
    public Canvas canvas;
    public StackPane user1;
    public StackPane user2;
    public Button defend;
    public Button takeCards;
    public Button attack;
    public Button stop;
    public StackPane battleField;
    public TextField turn;
    public TextField clue;
    public ImageView gamingPack;
    public ImageView discardedCards;
    public ImageView trumpCardImage;
    private boolean defButClicked = false;
    private boolean isAttack = false;
    private List<Card> tmpAttack = new ArrayList<>();
    private List<Card> tmpDefence = new ArrayList<>();
    private int countForDefence = 0;
    private int count = 0;
    private int max = 6;
    private int paddingBF = 0;
    private int paddingBF2 = 0;
    private boolean isTakeCards = false;
    private boolean end = false;
    private boolean newTurn = true;
    private final Durak durak = new Durak();
    private Hand userHand;
    private Hand enemyHand;

    @FXML
    private void initialize() {
        //подготовка игры
        durak.newGame();
        userHand = durak.getUserHand();
        enemyHand = durak.getEnemyHand();
        Card trumpCard = durak.getTrampCard();

        //подготовка сцены
        anchorPane.setStyle("-fx-background-image: url('images/table.jpg')");

        //подготовка к игре
        prepareForGame(userHand, enemyHand, trumpCard);

        //игра
        durak();
    }

    private void prepareForGame(Hand userHand, Hand enemyHand, Card trumpCard) {
        //индикатор хода
        if (durak.isEnemyTurn()) {
            turn = new TextField("Ход Бота");
        } else {
            turn = new TextField("Ход Игрока");
        }
        anchorPane.getChildren().add(turn);
        turn.setStyle(" -fx-background-color: #800000; -fx-font: 15 times-new-roman; -fx-text-fill: white;" +
                "-fx-border-color: white; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        turn.setMaxHeight(50);
        turn.setMaxWidth(100);
        turn.setLayoutX(35);
        turn.setLayoutY(632);
        turn.setEditable(false);
        turn.setDisable(false);
        turn.setFocusTraversable(false);

        //поле-подсказка
        clue = new TextField(" ");
        anchorPane.getChildren().add(clue);
        clue.setStyle(" -fx-background-color: #800000; -fx-font: 15 times-new-roman; -fx-text-fill: white;" +
                "-fx-border-color: white; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        clue.setMinHeight(50);
        clue.setMinWidth(260);
        clue.setLayoutX(615);
        clue.setLayoutY(585);
        clue.setEditable(false);
        clue.setDisable(false);
        clue.setFocusTraversable(false);
        clue.setVisible(false);

        //игрок
        for (int i = 0; i < userHand.getSize(); i++) {
            Card card = userHand.getCard(i);
            card.setFaceState(Card.CardFaceState.FACE_UP);
        }
        updateHand(userHand, user1);
        user1.setLayoutX(300);
        user1.setLayoutY(500);

        //бот
        updateHand(enemyHand, user2);
        user2.setLayoutX(300);
        user2.setLayoutY(10);

        //колода
        trumpCardImage = new ImageView(ImageReturn.getImage(trumpCard));
        trumpCardImage.setFitHeight(185);
        trumpCardImage.setFitWidth(125);
        trumpCardImage.setRotate(trumpCardImage.getRotate() + 90);
        anchorPane.getChildren().add(trumpCardImage);
        trumpCardImage.setLayoutX(41);
        trumpCardImage.setLayoutY(250);

        gamingPack = new ImageView("images/0.gif");
        gamingPack.setFitHeight(185);
        gamingPack.setFitWidth(125);
        anchorPane.getChildren().add(gamingPack);
        gamingPack.setLayoutX(10);
        gamingPack.setLayoutY(250);

        //Бита
        discardedCards = new ImageView("images/0.gif");
        discardedCards.setFitHeight(185);
        discardedCards.setFitWidth(125);
        anchorPane.getChildren().add(discardedCards);
        discardedCards.setLayoutX(720);
        discardedCards.setLayoutY(250);
        discardedCards.setVisible(false);

        //Обработка кнопок
        defend.setText("Отбить");
        defend.setStyle("-fx-background-color: #800000; -fx-font: 16 times-new-roman; -fx-text-fill: white;" +
                "-fx-border-color: white; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        defend.setMaxHeight(50);
        defend.setMaxWidth(100);
        defend.setLayoutX(710);
        defend.setLayoutY(571);
        defend.setDisable(false);
        defend.setFocusTraversable(false);
        defend.setVisible(false);
        defend.setOnMouseClicked(this::defButMouseEvent);

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
        play.setOnMouseClicked(this::playButMouseEvent);

        //поле боя
        battleField.setLayoutX(300);
        battleField.setLayoutY(240);
        battleField2.setLayoutX(310);
        battleField2.setLayoutY(280);
    }

    private void playButMouseEvent(MouseEvent mouseEvent) {
        play.setVisible(false);
        if (end) {
            durak();
            updateHand(userHand, user1);
            updateHand(enemyHand, user2);
            if (durak.getGamingPackSize() == 1) {
                gamingPack.setVisible(false);
            }
            if (durak.getGamingPackSize() == 0) {
                gamingPack.setVisible(false);
                trumpCardImage.setVisible(false);
            }
            durak();
        } else {
            updateHand(userHand, user1);
            updateHand(enemyHand, user2);
            durak();
        }
    }

    private void defendButtons(boolean sw) {
        defend.setVisible(sw);
        takeCards.setVisible(sw);
    }

    private void editClue(String str, int size) {
        clue.setVisible(true);
        clue.setText(str);
        clue.setMinWidth(size);
    }

    private void updateHand(Hand hand, StackPane stackPane) {
        stackPane.getChildren().clear();
        int j = 0;
        for (int i = 0; i < hand.getSize(); i++) {
            Card card = hand.getCard(i);
            if (hand == enemyHand) {
                card.setFaceState(Card.CardFaceState.FACE_DOWN);
            } else card.setFaceState(Card.CardFaceState.FACE_UP);
            ImageView image = new ImageView(ImageReturn.getImage(card));
            image.setFitHeight(185);
            image.setFitWidth(125);
            image.setOnMouseClicked(this::mouseEvent);
            stackPane.getChildren().add(image);
            StackPane.setAlignment(image, Pos.CENTER_LEFT);
            StackPane.setMargin(image, new Insets(0, 0, 0, j));
            if (hand.getSize() < 8) {
                j += 30;
                stackPane.setLayoutX(300);
            } else {
                stackPane.setLayoutX(240);
                j += 26;
            }
        }
    }

    private void defButMouseEvent(MouseEvent mouseEvent) {
        defButClicked = true;
        defendButtons(false);
        durak.addInEnemyMoveList(tmpAttack);
        editClue("Выберите карты для своего хода", 250);
    }

    private void takeCardsButMouseEvent(MouseEvent mouseEvent) {
        defendButtons(false);
        durak.pickUpCards(durak.getUserHand(), tmpAttack);
        durak.addInEnemyMoveList(tmpAttack);
        editClue("Конец хода!", 80);
        isTakeCards = true;
        end = true;
        tmpAttack.clear();
        play.setVisible(true);
    }

    private void attackButMouseEvent(MouseEvent mouseEvent) {
        attack.setVisible(false);
        stop.setVisible(false);
        afterAttack();
    }

    private void durak() {
        if (newTurn) {
            isTakeCards = false;
        }
        if (durak.getState() == Durak.GameState.PLAYING) {
            if (newTurn) {
                count = 0;
                max = 6;
                if (durak.isFirstMove()) {
                    max = 5;
                }
                newTurn = false;
            }
            if (durak.isEnemyTurn()) {
                if (!end) {
                    durakEnemyTurn();
                } else {
                    //конец хода
                    end = false;
                    newTurn = true;
                    editClue("Конец хода!", 50);
                    battleField.getChildren().clear();
                    battleField2.getChildren().clear();
                    paddingBF = 0;
                    paddingBF2 = 0;
                    durak.finishTurn(false);
                    if (!isTakeCards) {
                        discardedCards.setVisible(true);
                        durak.setEnemyTurn(false);
                        durak.setUserTurn(true);
                    } else {
                        isTakeCards = false;
                    }
                    if (durak.isFirstMove()) {
                        durak.setFirstMove(false);
                    }
                }
            } else {
                if (durak.isUserTurn()) {
                    if (newTurn) {
                        if (max > durak.getUserHand().getSize()) {
                            max = durak.getUserHand().getSize();
                        }
                        if (max > durak.getEnemyHandSize()) {
                            max = durak.getEnemyHandSize();
                        }
                    }
                    if (!end) {
                        durakUserTurn();
                    } else {
                        // конец хода
                        end = false;
                        newTurn = true;
                        editClue("Конец хода!", 50);
                        battleField.getChildren().clear();
                        battleField2.getChildren().clear();
                        paddingBF = 0;
                        paddingBF2 = 0;
                        durak.finishTurn(true);
                        if (!isTakeCards) {
                            discardedCards.setVisible(true);
                            durak.setEnemyTurn(true);
                            durak.setUserTurn(false);
                        } else isTakeCards = false;
                        if (durak.isFirstMove()) {
                            durak.setFirstMove(false);
                        }
                    }
                }
            }
        } else result();
    }

    private void durakEnemyTurn() {
        //нападение
        List<Card> attack;
        if (count == 0) {
            attack = durak.enemyFirstAttack(max);
            count = 1;
        } else {
            attack = durak.enemySecondAttack(max);
        }
        if (attack.size() == 0) {
            end = true;
            editClue("Противник закончил ход", 200);
            play.setVisible(true);
            tmpAttack.clear();
            return;
        }
        turn.setText("Ход Бота");
        max -= attack.size();
        for (Card card : attack) {
            card.setFaceState(Card.CardFaceState.FACE_UP);
            ImageView image = new ImageView(ImageReturn.getImage(card));
            image.setFitHeight(185);
            image.setFitWidth(125);
            StackPane.setAlignment(image, Pos.CENTER_LEFT);
            StackPane.setMargin(image, new Insets(0, 0, 0, paddingBF));
            battleField.getChildren().add(image);
            paddingBF += 40;
        }
        int s = user2.getChildren().size();
        user2.getChildren().remove(s - attack.size(), s);
        //защита
        clue.setVisible(false);
        defendButtons(true);
        tmpAttack = attack;
        tmpDefence = new ArrayList<>();
        countForDefence = 0;
        //выбор и произведение действия юзера
        //конец хода юзера
    }

    private void durakUserTurn() {
        if (count < max) {
            //нападение
            turn.setText("Ход Игрока");
            editClue("Вы можете напасть еще " + (max - count) + " картами!", 265);
            isAttack = true;
            if (durak.getUserMove().size() != 0) stop.setVisible(true);
        } else end = true;
    }

    private void afterAttack() {
        if (tmpAttack.size() == 0 && durak.getUserMove().size() != 0) {
            editClue("Вы закончили ход", 100);
            end = true;
        }
        isAttack = false;
        //защита
        if (!end) {
            List<Card> defence = durak.enemyDefence(tmpAttack);
            if (defence.size() == 0) {
                editClue("Противник забрал карты", 200);
                battleField.getChildren().clear();
                battleField2.getChildren().clear();
                paddingBF = 0;
                paddingBF2 = 0;
                isTakeCards = true;
                end = true;
            } else {
                editClue("Противник отбился", 90);
                for (Card card : defence) {
                    card.setFaceState(Card.CardFaceState.FACE_UP);
                    ImageView image = new ImageView(ImageReturn.getImage(card));
                    image.setFitHeight(185);
                    image.setFitWidth(125);
                    battleField2.getChildren().add(image);
                    StackPane.setAlignment(image, Pos.CENTER_LEFT);
                    StackPane.setMargin(image, new Insets(0, 0, 0, paddingBF2));
                    paddingBF2 += 40;
                }
                int s = user2.getChildren().size();
                user2.getChildren().remove(s - defence.size(), s);
            }
            durak.addInEnemyMoveList(defence);
            durak.addInUserMoveList(tmpAttack);
        }
        play.setVisible(true);
        tmpAttack.clear();
    }

    public void mouseEvent(MouseEvent mouseEvent) {
        ImageView image = (ImageView) mouseEvent.getSource();
        if (defButClicked) {
            int index = user1.getChildren().indexOf(image);
            if (index >= 0 && countForDefence < tmpAttack.size()) {
                // проверка корректности индекса
                if (index >= durak.getUserHand().getSize()) {
                    editClue("Ошибка!", 50);
                    return;
                }
                // проверка корректности карты
                if (durak.ruleCheckForDefence(tmpAttack.get(countForDefence), durak.getUserHand().getCard(index))) {
                    editClue("Нарушены правила игры!", 200);
                    return;
                }
                // проверка на повтор карты
                int j;
                for (j = 0; j < tmpDefence.size(); j++) {
                    if (tmpDefence.get(j) == durak.getUserHand().getCard(index)) {
                        editClue("Вы уже использовали эту карту!", 200);
                        break;
                    }
                }
                if (j < tmpDefence.size()) {
                    return;
                }
                // если все в порядке
                tmpDefence.add(durak.getUserHand().getCard(index));
                editClue((countForDefence + 1) + " карта бита!", 60);
                countForDefence++;
                battleField2.getChildren().add(image);
                StackPane.setAlignment(image, Pos.CENTER_LEFT);
                StackPane.setMargin(image, new Insets(0, 0, 0, paddingBF2));
                paddingBF2 += 40;
                user1.getChildren().remove(index, index);
                durak.getUserHand().remove(durak.getUserHand().getCard(index));
                if (countForDefence == tmpAttack.size()) {
                    durak.addInUserMoveList(tmpDefence);
                    defButClicked = false;
                    tmpAttack.clear();
                    play.setVisible(true);
                }
            }
        }
        if (isAttack) {
            int index = user1.getChildren().indexOf(image);
            if (index >= 0 && max - count > 0) {
                // проверка корректности индекса
                if (index >= durak.getUserHand().getSize() + count) {
                    editClue("Ошибка!", 50);
                    System.out.println(index);
                    return;
                }
                //проверка корректности карты
                if (durak.ruleCheckForThrowIn(durak.getUserHand().getCard(index), tmpAttack)) {
                    editClue("Нарушены правила игры!", 200);
                    return;
                }
                // проверка на повтор карты
                int j;
                for (j = 0; j < tmpAttack.size(); j++) {
                    if (tmpAttack.get(j) == durak.getUserHand().getCard(index)) {
                        editClue("Вы уже использовали эту карту!", 200);
                        break;
                    }
                }
                if (j < tmpAttack.size()) {
                    return;
                }
                // если все в порядке
                count++;
                editClue("Вы можете напасть еще " + (max - count) + " картами!", 265);
                tmpAttack.add(durak.getUserHand().getCard(index));
                battleField.getChildren().add(image);
                StackPane.setAlignment(image, Pos.CENTER_LEFT);
                StackPane.setMargin(image, new Insets(0, 0, 0, paddingBF));
                paddingBF += 40;
                user1.getChildren().remove(index, index);
                durak.getUserHand().remove(durak.getUserHand().getCard(index));
                stop.setVisible(false);
                attack.setVisible(true);
            }
        }
    }

    private void result() {
        if (durak.getState() == Durak.GameState.WIN) {
            Stage stage = (Stage) attack.getScene().getWindow();
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
        if (durak.getState() == Durak.GameState.DRAW) {
            Stage stage = (Stage) attack.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/draw.fxml"));
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
        if (durak.getState() == Durak.GameState.FAIL) {
            Stage stage = (Stage) attack.getScene().getWindow();
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
