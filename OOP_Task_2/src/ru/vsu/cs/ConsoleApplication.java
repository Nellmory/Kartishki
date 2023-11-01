package ru.vsu.cs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleApplication {
    private int game = 0;
    private boolean throwInDurak = false;
    private boolean passingDurak = false;
    private Scanner console = new Scanner(System.in);
    Durak durak = new Durak();

    public void start() {
        menuPrint();
        switch (game) {
            case 1:
                durak.newGame();
                durak();
                break;
            case 2:
            case 3:
            case 4:
            case 5:
        }
    }

    private void menuPrint() {
        int tmp = 0;
        System.out.println("Выберите игру: \n 1) Дурак \n 2) Покер \n 3) Пасьянс \n 4) Преферанс \n 5) Червы");
        while (game == 0) {
            tmp = console.nextInt();
            if (tmp < 1 || tmp > 5) {
                System.out.println("Введено неверное значение");
            } else game = tmp;
        }
        tmp = 0;
        if (game == 1) {
            System.out.println("1) Подкидной \n2) Переводной");
            while (!throwInDurak && !passingDurak) {
                tmp = console.nextInt();
                if (tmp == 1) {
                    throwInDurak = true;
                } else {
                    if (tmp == 2) {
                        passingDurak = true;
                    } else System.out.println("Введено неверное значение");
                }
            }
        }
    }

    //---------ДУРАК---------//
    private void durak() {
        System.out.println("----------- \nИгра началась!");
        System.out.println("Козырь:" + cardSuit(durak.getTrumpSuit()) + "\n-----------");
        while (durak.getState() == Durak.GameState.PLAYING) {
            int max = 6;
            int count = 1;
            if (durak.isFirstMove()) {
                max = 5;
                durak.setFirstMove(false);
            }
            if (durak.isEnemyTurn()) {
                boolean end = false;
                while (!end) {
                    //нападение
                    List<Card> attack;
                    if (count == 1) {
                        attack = durak.enemyFirstAttack(max);
                        count = 0;
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
                        System.out.println(i + 1 + ")" + " " + cardValue(card.getValue()) + " " + cardSuit(card.getSuit()));
                    }
                    //защита
                    printUserHand();
                    System.out.println("Ваш ход: \n 1)Потянуть \n 2)Отбить");
                    int tmp = 0;
                    while (tmp == 0) {
                        tmp = console.nextInt();
                        switch (tmp) {
                            case 1 -> {
                                durak.pickUpCards(durak.getUserHand(), attack);
                                durak.addInEnemyMoveList(attack);
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
                durak.finishTurn();
                durak.setEnemyTurn(false);
                durak.setUserTurn(true);
            } else {
                if (durak.isUserTurn()) {
                    printUserHand();
                    int tmp = 0;
                    List<Card> attack = new ArrayList<>();
                    boolean end = false;
                    //while (!end) {
                    //нападение
                    System.out.println("Ваш ход: \n 1)Одна карта \n 2)Несколько карт");
                    while (tmp == 0) {
                        tmp = console.nextInt();
                        switch (tmp) {
                            case 1 -> {
                                for (int i = 0; i < 1; i++) {
                                    System.out.print("Введите номер карты: ");
                                    int index = console.nextInt();
                                    // проверка корректности индекса
                                    if (index < 1 || index > durak.getUserHand().getSize()) {
                                        System.out.println("Введено некорректное значение!");
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
                                    attack.add(durak.getUserHand().getCard(index - 1));
                                    durak.getUserHand().remove(attack);
                                }
                            }
                            case 2 -> {
                                System.out.println("Сколько карт ходите выложить?");
                                int num = console.nextInt();
                                System.out.println("Введите номера карт: ");
                                for (int i = 0; i < num; i++) {
                                    System.out.print(i + 1 + ")" + " ");
                                    int index = console.nextInt();
                                    // проверка корректности индекса
                                    if (index < 1 || index > durak.getUserHand().getSize()) {
                                        System.out.println("Введено некорректное значение!");
                                        i--;
                                        continue;
                                    }
                                    //проверка корректности карты
                                    if (i > 0) {
                                        Card selectedCard = durak.getUserHand().getCard(index - 1);
                                        if (selectedCard.getValue() != attack.get(attack.size() - 1).getValue()) {
                                            i--;
                                            continue;
                                        }
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
                                    attack.add(durak.getUserHand().getCard(index - 1));
                                }
                                durak.getUserHand().remove(attack);
                            }
                            default -> {
                                System.out.println("Введено неверное значение!");
                                tmp = 0;
                            }
                        }
                    }
                    //защита
                    System.out.println("Тут должна быть защита");
                    //}
                    System.out.println("Конец хода!");
                    durak.setEnemyTurn(true);
                    durak.setUserTurn(false);
                }
            }
        }
    }

    private String cardValue(int cardValue) {
        if (cardValue < 11) {
            return "" + cardValue;
        }
        return switch (cardValue) {
            case 11 -> "Валет";
            case 12 -> "Дама";
            case 13 -> "Король";
            case 14 -> "Туз";
            default -> null;
        };
    }

    private String cardSuit(String cardSuit) {
        return switch (cardSuit) {
            case "h" -> "Червы";
            case "p" -> "Пики";
            case "d" -> "Буби";
            case "c" -> "Крести";
            default -> null;
        };
    }

    private void printUserHand() {
        System.out.println("----------- \nКозырь:" + cardSuit(durak.getTrumpSuit()) + "\n----------- \nВаши карты:");
        for (int i = 0; i < durak.getUserHand().getSize(); i++) {
            Card card = durak.getUserHand().getCard(i);
            System.out.println(i + 1 + ")" + " " + cardValue(card.getValue()) + " " + cardSuit(card.getSuit()));
        }
    }
}
