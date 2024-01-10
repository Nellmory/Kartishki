package ru.vsu.cs;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;


public class DurakController {
    public AnchorPane anchorPane;
    public Canvas canvas;
    public StackPane user1;
    public StackPane user2;
    public StackPane battleField;
    public ImageView gamingPack;
    public ImageView discardedCards;
    public ImageView trumpCardImage;
    private final Durak durak = new Durak();

    @FXML
    private void initialize() {
        //подготовка игры
        Hand userHand;
        Hand enemyHand;
        durak.newGame();
        userHand = durak.getUserHand();
        enemyHand = durak.getEnemyHand();
        Card trumpCard = durak.getTrampCard();
        boolean discardedCards = false;

        //подготовка сцены
        anchorPane.setStyle("-fx-background-image: url('images/table.jpg')");

        //подготовка к игре
        prepareForGame(userHand, enemyHand, trumpCard);
        //игра
        /*
        while (durak.getState() == Durak.GameState.PLAYING) {
            int max = 6;
            int count = 0;
            if (durak.isFirstMove()) {
                max = 5;
                durak.setFirstMove(false);
            }
            if (durak.isEnemyTurn()) {
                boolean end = false;
                while (!end) {
                    //нападение
                    List<Card> attack;
                    if (count == 0) {
                        attack = durak.enemyFirstAttack(max);
                        count = 1;
                    } else {
                        attack = durak.enemySecondAttack(max);
                    }
                    if (attack.size() == 0) {
                        break;
                    }
                    System.out.println("Ход противника:");
                    max -= attack.size();
                    for (int i = 0; i < attack.size(); i++) {
                        Card card = attack.get(i);
                        System.out.println(i + 1 + ")" + " " + cardValue(card.getValue()) + " " + cardSuit(card.getSuit().toString()));
                    }
                    //защита
                    printUserHand();
                    System.out.println("Ваш ход: \n 1)Забрать \n 2)Отбить");
                    int tmp = 0;
                    while (tmp == 0) {
                        tmp = console.nextInt();
                        switch (tmp) {
                            case 1 -> {
                                durak.pickUpCards(durak.getUserHand(), attack);
                                durak.addInEnemyMoveList(attack);
                                takeCards = true;
                                end = true;
                            }
                            case 2 -> {
                                int index;
                                List<Card> defence = new ArrayList<>();
                                for (int i = 0; i < attack.size(); i++) {
                                    System.out.print(i + 1 + ")" + " ");
                                    index = console.nextInt();
                                    // проверка корректности индекса
                                    if (index < 1 || index > durak.getUserHand().getSize()) {
                                        System.out.println("Введено некорректное значение!");
                                        i--;
                                        continue;
                                    }
                                    // проверка корректности карты
                                    if (durak.ruleCheckForDefence(attack.get(i), durak.getUserHand().getCard(index - 1))) {
                                        System.out.println("Нарушены правила игры!");
                                        i--;
                                        continue;
                                    }
                                    // проверка на повтор карты
                                    int j;
                                    for (j = 0; j < defence.size(); j++) {
                                        if (defence.get(j) == durak.getUserHand().getCard(index - 1)) {
                                            System.out.println("Вы уже использовали эту карту!");
                                            break;
                                        }
                                    }
                                    if (j < defence.size()) {
                                        i--;
                                        continue;
                                    }
                                    // если все в порядке
                                    defence.add(durak.getUserHand().getCard(index - 1));
                                }
                                durak.getUserHand().remove(defence);
                                durak.addInEnemyMoveList(attack);
                                durak.addInUserMoveList(defence);
                            }
                            default -> {
                                System.out.println("Введено неверное значение!");
                                tmp = 0;
                            }
                        } //выбор и произведение действия юзера
                    } //конец хода юзера
                } //конец хода
                System.out.println("Конец хода!");
                durak.finishTurn(false);
                if (!takeCards) {
                    durak.setEnemyTurn(false);
                    durak.setUserTurn(true);
                } else takeCards = false;
            } else {
                if (durak.isUserTurn()) {
                    boolean end = false;
                    if (max > durak.getUserHand().getSize()) {
                        max = durak.getUserHand().getSize();
                    }
                    if (max > durak.getEnemyHandSize()) {
                        max = durak.getEnemyHandSize();
                    }
                    while (!end) {
                        if (count < max) {
                            //нападение
                            List<Card> attack = new ArrayList<>();
                            System.out.println("Ваш ход:");
                            printUserHand();
                            System.out.println("Вы можете напасть еще " + (max - count) + " картами!" + "\nУ противника " + durak.getEnemyHandSize() + " карт" + "\nСколько карт хотите выложить?");
                            int tmp = 0;
                            while (tmp == 0) {
                                int num = console.nextInt();
                                if ((num < 1 || num > max - count) && count == 0) {
                                    System.out.println("Введено некорректное значение!");
                                } else {
                                    if (num == 0 && count != 0) {
                                        end = true;
                                    } else {
                                        System.out.println("Введите номера карт: ");
                                        for (int i = 0; i < num && count < max; i++) {
                                            System.out.print(i + 1 + ")" + " ");
                                            int index = console.nextInt();
                                            // проверка корректности индекса
                                            if (index < 1 || index > durak.getUserHand().getSize()) {
                                                System.out.println("Введено некорректное значение!");
                                                i--;
                                                continue;
                                            }
                                            //проверка корректности карты
                                            if (durak.ruleCheckForThrowIn(durak.getUserHand().getCard(index - 1), attack)) {
                                                System.out.println("Нарушены правила игры!");
                                                i--;
                                                continue;
                                            }
                                            // проверка на повтор карты
                                            int j;
                                            for (j = 0; j < attack.size(); j++) {
                                                if (attack.get(j) == durak.getUserHand().getCard(index - 1)) {
                                                    System.out.println("Вы уже использовали эту карту!");
                                                    break;
                                                }
                                            }
                                            if (j < attack.size()) {
                                                i--;
                                                continue;
                                            }
                                            // если все в порядке
                                            count++;
                                            attack.add(durak.getUserHand().getCard(index - 1));
                                        }
                                        durak.getUserHand().remove(attack);
                                    }
                                    tmp = 1;
                                }
                            }
                            if (!end) {
                                //защита
                                List<Card> defence = durak.enemyDefence(attack);
                                if (defence.size() == 0) {
                                    System.out.println("Противник забрал карты");
                                    takeCards = true;
                                    end = true;
                                } else {
                                    System.out.println("Противник отбился:");
                                    for (int i = 0; i < defence.size(); i++) {
                                        Card card = defence.get(i);
                                        System.out.println(i + 1 + ")" + " " + cardValue(card.getValue()) + " " + cardSuit(card.getSuit().toString()));
                                    }
                                }
                                durak.addInEnemyMoveList(defence);
                                durak.addInUserMoveList(attack);
                            }
                        } else end = true;
                    } // конец хода
                    System.out.println("Конец хода!");
                    durak.finishTurn(true);
                    if (!takeCards) {
                        durak.setEnemyTurn(true);
                        durak.setUserTurn(false);
                    } else takeCards = false;
                }
            }
        }
        printResult();

         */
    }

    private void prepareForGame(Hand userHand, Hand enemyHand, Card trumpCard) {
        //индикатор хода
        TextField turn;
        if (durak.isEnemyTurn()) {
            turn = new TextField("Ход Бота");
        } else {
            turn = new TextField("Ход Игрока");
        }
        anchorPane.getChildren().add(turn);
        turn.setStyle(" -fx-background-color: #800000; -fx-font: 15 times-new-roman; -fx-text-fill: white;"  +
                "-fx-border-color: white; -fx-border-radius: 5px; -fx-background-radius: 5 5 5 5;");
        turn.setMaxHeight(50);
        turn.setMaxWidth(100);
        turn.setLayoutX(30);
        turn.setLayoutY(630);
        turn.setEditable(false);
        turn.setDisable(false);
        turn.setFocusTraversable(false);

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
        trumpCardImage.setFitHeight(193);
        trumpCardImage.setFitWidth(130);
        trumpCardImage.setRotate(trumpCardImage.getRotate() + 90);
        anchorPane.getChildren().add(trumpCardImage);
        trumpCardImage.setLayoutX(41);
        trumpCardImage.setLayoutY(250);

        gamingPack = new ImageView("images/0.gif");
        gamingPack.setFitHeight(193);
        gamingPack.setFitWidth(130);
        anchorPane.getChildren().add(gamingPack);
        gamingPack.setLayoutX(10);
        gamingPack.setLayoutY(250);
    }

    private void updateHand(Hand hand, StackPane stackPane) {
        int j = 0;
        for (int i = 0; i < hand.getSize(); i++) {
            Card card = hand.getCard(i);
            ImageView image = new ImageView(ImageReturn.getImage(card));
            image.setFitHeight(193);
            image.setFitWidth(130);
            image.setOnMouseClicked(this::mouseEvent);
            stackPane.getChildren().add(image);
            StackPane.setAlignment(image, Pos.CENTER_LEFT);
            StackPane.setMargin(image, new Insets(0, 0, 0, j));
            if (hand.getSize() < 10) {
                j += 30;
            } else j += 20;
        }
    }

    public void mouseEvent(MouseEvent mouseEvent) {
        ImageView image = (ImageView) mouseEvent.getSource();
        if (durak.isUserTurn()) {
            int index = user1.getChildren().indexOf(image);
            if (index >= 0) {
                System.out.println("Index of the element under the mouse: " + index);
            }
        } else {
            int index = user2.getChildren().indexOf(image);
            if (index >= 0) {
                System.out.println("Index of the element under the mouse: " + index);
            }
        }
    }
}