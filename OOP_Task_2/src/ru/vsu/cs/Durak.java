package ru.vsu.cs;

import java.util.*;

import static ru.vsu.cs.Card.CardState.NOT_USED;
import static ru.vsu.cs.Card.CardState.USED;

public class Durak {
    private Pack pack = new Pack(36);
    private Stack<Card> gamingPack = new Stack<>();
    private Hand userHand = new Hand();
    private Hand enemyHand = new Hand();
    private final Random rnd = new Random();
    private String trumpSuit;
    private boolean isUserTurn = false;
    private boolean isEnemyTurn = false;
    private List<Card> enemyMove = new ArrayList<>();
    private List<Card> userMove = new ArrayList<>();
    private boolean firstMove = true;

    public enum GameState {
        NOT_STARTED,
        PLAYING,
        WIN,
        FAIL
    }

    GameState state = GameState.NOT_STARTED;

    private void calcState() {
    }

    public void newGame() {
        //раздадим карты игрокам
        dealCards(userHand);
        dealCards(enemyHand);
        //сформируем оставшуюся колоду и определим козырную масть
        int k = 0;
        while (k < 26) {
            Card card = pack.getCard(rnd.nextInt(36));
            if (card.getState() == NOT_USED) {
                gamingPack.push(card);
                k++;
            }
            if (k == 1) {
                trumpSuit = card.getSuit();
            }
        }
        //определим, кто первый ходит
        turn();
        // начинаем игру
        state = GameState.PLAYING;
    }

    public List<Card> enemyFirstAttack(int max) {
        Card resCard = null;
        Card resTrampCard = null;
        int minValue = 20;
        int minTrampValue = 20;
        List<Card> res = new ArrayList<>();
        // Выбираем наименьшую некозырную карту (если таковых нет, то наим. козырную)
        for (int i = 0; i < enemyHand.getSize(); i++) {
            Card card = enemyHand.getCard(i);
            if (!Objects.equals(card.getSuit(), trumpSuit) && card.getValue() < minValue) {
                minValue = card.getValue();
                resCard = card;
            }
            if (Objects.equals(card.getSuit(), trumpSuit) && card.getValue() < minTrampValue) {
                minTrampValue = card.getValue();
                resTrampCard = card;
            }
        }
        // Смотрим, можем ли мы походить несколькими картами
        if (resCard != null) {
            res.add(resCard);
            enemyHand.remove(resCard);
            for (int i = 0; i < enemyHand.getSize(); i++) {
                Card card = enemyHand.getCard(i);
                if (!Objects.equals(card.getSuit(), trumpSuit) && card.getValue() == minValue && !Objects.equals(card.getSuit(), resCard.getSuit()) && res.size() < max - 1) {
                    res.add(card);
                    enemyHand.remove(card);
                }
            }
        } else {
            res.add(resTrampCard);
            enemyHand.remove(resTrampCard);
        }
        return res;
    }

    public List<Card> enemySecondAttack(int max) {
        List<Card> res = new ArrayList<>();
        for (int i = 0; i < enemyHand.getSize(); i++) {
            Card card = enemyHand.getCard(i);
            for (int j = 0; j < userMove.size(); j++) {
                Card userMoveCard = userMove.get(j);
                Card enemyMoveCard = enemyMove.get(j);
                if ((card.getValue() == userMoveCard.getValue() || card.getValue() == enemyMoveCard.getValue()) && !Objects.equals(card.getSuit(), trumpSuit) && res.size() < max) {
                    res.add(card);
                    enemyHand.remove(card);
                }
            }
        }
        return res;
    }

    private void dealCards(Hand hand) {
        boolean ready = false;
        while (!ready) {
            for (int i = 0; i < 6; i++) {
                Card card = null;
                boolean isNotUsed = false;
                //выбираем из колоды карту
                while (!isNotUsed) {
                    card = pack.getCard(rnd.nextInt(36));
                    if (card.getState() == NOT_USED) {
                        isNotUsed = true;
                    }
                }
                hand.add(card);
                card.setState(USED);
            }
            if (!hand.durakHandCheck()) {
                for (int i = 0; i < 6; i++) {
                    Card card = hand.getCard(i);
                    card.setState(NOT_USED);
                }
                hand.clear();
            } else ready = true;
        }
    }

    private void turn() {
        Hand hand = userHand;
        int min = 20;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 6; j++) {
                Card tmp = hand.getCard(j);
                if (Objects.equals(tmp.getSuit(), trumpSuit)) {
                    if (tmp.getValue() < min) {
                        if (i == 0) {
                            min = tmp.getValue();
                        } else {
                            isEnemyTurn = true;
                            return;
                        }
                    }
                }
            }
            hand = enemyHand;
        }
        isUserTurn = true;
    }

    public void finishTurn() {

    }

    public void pickUpCards(Hand hand, List<Card> turn) {
        for (Card card : turn) {
            hand.add(card);
        }
    }

    public Card getCardFromGamingPack() {
        return gamingPack.pop();
    }

    public boolean ruleCheckForDefence(Card attackCard, Card defenceCard) {
        if (Objects.equals(attackCard.getSuit(), defenceCard.getSuit())) {
            return attackCard.getValue() > defenceCard.getValue();
        } else {
            return !Objects.equals(defenceCard.getSuit(), trumpSuit);
        }
    }

    public void addInEnemyMoveList(List<Card> turn) {
        enemyMove.addAll(turn);
    }

    public void addInUserMoveList(List<Card> turn) {
        userMove.addAll(turn);
    }

    public String getTrumpSuit() {
        return trumpSuit;
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }

    public boolean isUserTurn() {
        return isUserTurn;
    }

    public boolean isEnemyTurn() {
        return isEnemyTurn;
    }

    public void setUserTurn(boolean userTurn) {
        this.isUserTurn = userTurn;
    }

    public void setEnemyTurn(boolean enemyTurn) {
        this.isEnemyTurn = enemyTurn;
    }

    public GameState getState() {
        return state;
    }

    public Hand getUserHand() {
        return userHand;
    }
}
