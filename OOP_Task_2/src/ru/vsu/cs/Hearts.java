package ru.vsu.cs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static ru.vsu.cs.Card.CardState.NOT_USED;
import static ru.vsu.cs.Card.CardState.USED;

public class Hearts {
    private Pack pack = new Pack(52);
    private List<Hand> hands = new ArrayList<>(4);
    private List<UserTrick> trick = new ArrayList<>(4);
    private List<Integer> score = new ArrayList<>(4);
    private int round = 1;
    private int lastToTakeTrick = -1;
    private int userTurn = -1;
    private boolean isFirstTrick = true;
    private boolean isHeartsOpened = false;
    private boolean preparing = true;
    private final Random rnd = new Random();
    private Hearts.GameState state = Hearts.GameState.NOT_STARTED;

    public enum GameState {
        NOT_STARTED,
        PLAYING,
        FINISHED
    }

    public void calcState() {
        if (trick.size() == 4) {
            Card.Suits targetSuit = trick.get(0).getCard().getSuit();
            int max = 0;
            //найдем невезучего
            for (UserTrick uT : trick) {
                if (Objects.equals(uT.getCard().getSuit(), targetSuit) && uT.getCard().getValue() > max) {
                    max = uT.getCard().getValue();
                    lastToTakeTrick = uT.getUserHand();
                }
            }
            //считаем, сколько ему баллов начислить
            for (UserTrick uT : trick) {
                if (Objects.equals(uT.getCard().getSuit(), Card.Suits.HEARTS)) {
                    int scr = score.get(lastToTakeTrick) + 1;
                    score.set(lastToTakeTrick, scr);
                } else {
                    if (Objects.equals(uT.getCard().getSuit(), Card.Suits.SPADES) && uT.getCard().getValue() == 12) {
                        int scr = score.get(lastToTakeTrick) + 13;
                        score.set(lastToTakeTrick, scr);
                    }
                }
            }
            turn();
            trick.clear();
        }
        if (hands.get(0).getSize() == 0 &&
                hands.get(1).getSize() == 0 &&
                hands.get(2).getSize() == 0 &&
                hands.get(3).getSize() == 0) {
            newRound();
        }
        for (int i = 0; i < 4; i++) {
            if (score.get(i) >= 100) {
                int min = 200;
                for (int j = 0; j < 4; j++) {
                    if (score.get(j) < min) {
                        min = score.get(j);
                    }
                }
                state = GameState.FINISHED;
                break;
            }
        }
    }

    public void newGame() {
        //создадим руки
        for (int i = 0; i < 4; i++) {
            Hand hand = new Hand();
            hands.add(hand);
        }
        //раздадим карты
        dealCards();
        turn();
        for (int i = 0; i < 4; i++) {
            score.add(0);
        }
        state = Hearts.GameState.PLAYING;
    }

    public void newRound() {
        pack = new Pack(52);
        round += 1;
        userTurn = -1;
        lastToTakeTrick = -1;
        isFirstTrick = true;
        isHeartsOpened = false;
        preparing = true;
        //раздадим карты
        dealCards();
        turn();
    }

    private void dealCards() {
        for (Hand hand : hands) {
            for (int j = 0; j < 13; j++) {
                Card card = null;
                boolean isNotUsed = false;
                //выбираем из колоды карту
                while (!isNotUsed) {
                    card = pack.getCard(rnd.nextInt(52));
                    if (card.getState() == NOT_USED) {
                        isNotUsed = true;
                    }
                }
                //кладем в стопку
                card.setState(USED);
                hand.add(card);
            }
        }
    }

    public int turn() {
        if (userTurn == -1) {
            userTurn = 0;
        } else {
            if (!preparing) {
                if (lastToTakeTrick != -1) {
                    userTurn = lastToTakeTrick;
                } else {
                    for (int i = 0; i < 4; i++) {
                        Hand hand = hands.get(i);
                        Card card = new Card(2, Card.Suits.CLUBS);
                        if (hand.ifContains(card)) {
                            userTurn = i;
                        }
                    }
                }
            } else {
                userTurn += 1;
            }
        }
        return userTurn;
    }

    public void switchCardsBeforeRound(List<Card> first, List<Card> second, List<Card> third, List<Card> fourth) {
        Hand firstH = hands.get(0);
        Hand secondH = hands.get(1);
        Hand thirdH = hands.get(2);
        Hand fourthH = hands.get(3);
        switch (round % 4) {
            case 1 -> {
                //firstH.remove(first);
                secondH.add(first);
                //secondH.remove(second);
                thirdH.add(second);
                //thirdH.remove(third);
                fourthH.add(third);
                //fourthH.remove(fourth);
                firstH.add(fourth);
            }
            case 2 -> {
                //firstH.remove(first);
                fourthH.add(first);
                //fourthH.remove(fourth);
                thirdH.add(fourth);
                //thirdH.remove(third);
                secondH.add(third);
                //secondH.remove(second);
                firstH.add(second);
            }
            case 3 -> {
                //firstH.remove(first);
                thirdH.add(first);
                //thirdH.remove(third);
                firstH.add(third);
                //secondH.remove(second);
                fourthH.add(second);
                //fourthH.remove(fourth);
                secondH.add(fourth);
            }
        }
        preparing = false;
    }

    public boolean trickFormation(int handIndex, Card card) {
        Hand hand = hands.get(handIndex);
        Card fCard = new Card(2, Card.Suits.CLUBS);
        //проверяем, начало ли это новой взятки
        if (trick.isEmpty() && handIndex == userTurn) {
            //это первая взятка нового раунда?
            if (isFirstTrick) {
                if (card.getSuit() != fCard.getSuit() && card.getValue() != fCard.getValue()) {
                    return false;
                } else {
                    isFirstTrick = false;
                    hand.remove(card);
                    trick.add(new UserTrick(handIndex, card));
                    return true;
                }
            } else {
                //у человека в руке есть только сердца?
                if (!hand.containsSuit(Card.Suits.SPADES) && !hand.containsSuit(Card.Suits.DIAMONDS) && !hand.containsSuit(Card.Suits.CLUBS)) {
                    //тогда он выложил сердца?
                    if (Objects.equals(card.getSuit(), Card.Suits.HEARTS)) {
                        //а сердца были открыты?
                        if (isHeartsOpened) {
                            hand.remove(card);
                            trick.add(new UserTrick(handIndex, card));
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        } else {
            Card.Suits targetSuit = trick.get(0).getCard().getSuit();
            //игрок выложил карту нужной масти?
            if (Objects.equals(targetSuit, card.getSuit())) {
                hand.remove(card);
                trick.add(new UserTrick(handIndex, card));
                return true;
            } else {
                //у него в руках была нужная масть?
                if (hand.containsSuit(targetSuit)) {
                    return false;
                } else {
                    //у человека в руке есть только сердца?
                    if (!hand.containsSuit(Card.Suits.SPADES) && !hand.containsSuit(Card.Suits.DIAMONDS) && !hand.containsSuit(Card.Suits.CLUBS)) {
                        //тогда он выложил сердца?
                        if (Objects.equals(card.getSuit(), Card.Suits.HEARTS)) {
                            hand.remove(card);
                            trick.add(new UserTrick(handIndex, card));
                            //открываем сердца
                            isHeartsOpened = true;
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        //он же выложил не сердца?
        if (!Objects.equals(card.getSuit(), Card.Suits.HEARTS)) {
            hand.remove(card);
            trick.add(new UserTrick(handIndex, card));
            return true;
        } else {
            return false;
        }
    }

    public List<Hand> getHands() {
        return hands;
    }

    public GameState getState() {
        return state;
    }

    public int getUserTurn() {
        return userTurn;
    }

    public boolean isPreparing() {
        return preparing;
    }

    public List<Integer> getScore() {
        return score;
    }
}