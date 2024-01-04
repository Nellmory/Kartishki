package ru.vsu.cs.DurakGame;

import ru.vsu.cs.GamesTools.Card;
import ru.vsu.cs.GamesTools.Hand;
import ru.vsu.cs.GamesTools.Pack;

import java.util.*;

import static ru.vsu.cs.GamesTools.Card.CardState.NOT_USED;
import static ru.vsu.cs.GamesTools.Card.CardState.USED;

public class Durak {
    private Pack pack = new Pack(36);
    private Stack<Card> gamingPack = new Stack<>();
    private Hand userHand = new Hand();
    private Hand enemyHand = new Hand();
    private final Random rnd = new Random();
    private Card.Suits trumpSuit;
    private boolean isUserTurn = false;
    private boolean isEnemyTurn = false;
    private List<Card> enemyMove = new ArrayList<>();
    private List<Card> userMove = new ArrayList<>();
    private boolean firstMove = true;

    public enum GameState {
        NOT_STARTED,
        PLAYING,
        WIN,
        FAIL,
        DRAW
    }

    private GameState state = GameState.NOT_STARTED;

    private void calcState() {
        if (userHand.getSize() == enemyHand.getSize() && userHand.getSize() == 0 && gamingPack.empty()) {
            state = GameState.DRAW;
        } else {
            if (userHand.getSize() != enemyHand.getSize() && userHand.getSize() == 0 && gamingPack.empty()) {
                state = GameState.WIN;
            } else {
                if (userHand.getSize() != enemyHand.getSize() && enemyHand.getSize() == 0 && gamingPack.empty()) {
                    state = GameState.FAIL;
                }
            }
        }
    }

    public void newGame() {
        //раздадим карты игрокам
        dealCards(userHand);
        dealCards(enemyHand);
        //сформируем оставшуюся колоду и определим козырную масть
        List<Card> tmpList = new ArrayList<>();
        for (int i = 0; i < 36; i++) {
            Card card = pack.getCard(i);
            if (card.getState() == NOT_USED) {
                tmpList.add(card);
                card.setState(USED);
            }
        }
        Collections.shuffle(tmpList, rnd);
        for (int i = 0; i < 24; i++) {
            Card card = tmpList.get(i);
            gamingPack.push(card);
            if (i == 0) {
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
                    break;
                }
            }
        }
        enemyHand.remove(res);
        return res;
    }

    public List<Card> enemyDefence(List<Card> attack) {
        List<Card> res = new ArrayList<>();
        for (Card attackCard : attack) {
            int minValue = 20;
            int minTrampValue = 20;
            Card resCard = null;
            Card resTrampCard = null;
            for (int j = 0; j < enemyHand.getSize() - res.size(); j++) {
                Card card = enemyHand.getCard(j);
                if (Objects.equals(attackCard.getSuit(), trumpSuit)) {
                    if (Objects.equals(card.getSuit(), trumpSuit) && card.getValue() < minTrampValue && card.getValue() > attackCard.getValue()) {
                        minTrampValue = card.getValue();
                        resTrampCard = card;
                    }

                } else {
                    if (Objects.equals(card.getSuit(), trumpSuit)) {
                        if (card.getValue() < minTrampValue) {
                            minTrampValue = card.getValue();
                            resTrampCard = card;
                        }
                    } else {
                        if (Objects.equals(card.getSuit(), attackCard.getSuit()) && card.getValue() < minValue && card.getValue() > attackCard.getValue()) {
                            minValue = card.getValue();
                            resCard = card;
                        }
                    }
                }
            }
            if (resCard != null) {
                res.add(resCard);
                enemyHand.remove(resCard);
            } else {
                if (resTrampCard != null) {
                    res.add(resTrampCard);
                    enemyHand.remove(resTrampCard);
                } else {
                    //случай, когда нечем отбить (противник тянет карты)
                    enemyHand.add(res);
                    res.clear();
                    pickUpCards(enemyHand, attack);
                    return res;
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
                card.setState(USED);
                hand.add(card);
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

    public void finishTurn(boolean finishOfUserTurn) {
        if (enemyMove.size() == userMove.size()) {
            for (int i = 0; i < enemyMove.size(); i++) {
                Card enemyCard = enemyMove.get(i);
                Card userCard = userMove.get(i);
                enemyCard.setGameState(Card.GameCardState.DISCARDED);
                userCard.setGameState(Card.GameCardState.DISCARDED);
            }
            if (finishOfUserTurn) {
                takeCards(userHand);
                takeCards(enemyHand);
            } else {
                takeCards(enemyHand);
                takeCards(userHand);
            }
        } else {
            if (userMove.size() == 0) {
                takeCards(enemyHand);
            }
            if (enemyMove.size() == 0) {
                takeCards(userHand);
            }
        }
        enemyMove.clear();
        userMove.clear();
        calcState();
    }

    public void pickUpCards(Hand hand, List<Card> turn) {
        for (Card card : turn) {
            hand.add(card);
        }
        if (enemyMove.size() != 0 || userMove.size() != 0) {
            for (Card card : enemyMove) {
                hand.add(card);
            }
            for (Card card : userMove) {
                hand.add(card);
            }
            if (hand == userHand) {
                userMove.clear();
            }
            if (hand == enemyHand) {
                enemyMove.clear();
            }
        }
    }

    public boolean ruleCheckForDefence(Card attackCard, Card defenceCard) {
        if (Objects.equals(attackCard.getSuit(), defenceCard.getSuit())) {
            return attackCard.getValue() > defenceCard.getValue();
        } else {
            return !Objects.equals(defenceCard.getSuit(), trumpSuit);
        }
    }

    public boolean ruleCheckForThrowIn(Card attackCard, List<Card> attack) {
        for (int i = 0; i < userMove.size(); i++) {
            Card userPrevCard = userMove.get(i);
            Card enemyPrevCard = enemyMove.get(i);
            if (attackCard.getValue() == userPrevCard.getValue() || attackCard.getValue() == enemyPrevCard.getValue()) {
                return false;
            }
        }
        if (attack.size() != 0 && userMove.size() == 0) {
            for (Card userPrevCard : attack) {
                if (attackCard.getValue() == userPrevCard.getValue()) {
                    return false;
                }
            }
        } else {
            return userMove.size() != 0;
        }
        return true;
    }

    private void takeCards(Hand hand) {
        if (hand == userHand) {
            for (int i = 0; i < userMove.size(); i++) {
                Card newCard = getCardFromGamingPack();
                if (newCard != null && userHand.getSize() < 6) {
                    userHand.add(newCard);
                } else break;
            }
        } else {
            for (int i = 0; i < enemyMove.size(); i++) {
                Card newCard = getCardFromGamingPack();
                if (newCard != null && enemyHand.getSize() < 6) {
                    enemyHand.add(newCard);
                } else break;
            }
        }
    }

    public Card getCardFromGamingPack() {
        if (!gamingPack.empty()) {
            return gamingPack.pop();
        } else return null;
    }

    public void addInEnemyMoveList(List<Card> turn) {
        enemyMove.addAll(turn);
    }

    public void addInUserMoveList(List<Card> turn) {
        userMove.addAll(turn);
    }

    public Card.Suits getTrumpSuit() {
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

    public int getEnemyHandSize() {
        return enemyHand.getSize();
    }
}
