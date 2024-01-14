package ru.vsu.cs;

import java.util.*;

import static ru.vsu.cs.Card.CardState.NOT_USED;
import static ru.vsu.cs.Card.CardState.USED;

public class Pasians {
    private final Pack pack = new Pack(52);
    private final List<Card> gamingPack = new ArrayList<>();
    private final Random rnd = new Random();
    private final List<List<Card>> playingField = new ArrayList<>(7);
    private final List<List<Card>> spaceForKeeping = new ArrayList<>(4);
    private Pasians.GameState state = Pasians.GameState.NOT_STARTED;

    public enum GameState {
        NOT_STARTED,
        PLAYING,
        WIN,
        FAIL
    }

    private void calcState() {
        for (List<Card> target : playingField) {
            Card lastCard = target.get(target.size() - 1);
            if (lastCard.getFaceState() != Card.CardFaceState.FACE_UP) {
                lastCard.setFaceState(Card.CardFaceState.FACE_UP);
            }
        }
        boolean pF = true;
        boolean gP = true;
        for (List<Card> field : playingField) {
            for (Card card : field) {
                if (card.getFaceState() == Card.CardFaceState.FACE_DOWN) {
                    pF = false;
                    break;
                }
            }
        }
        if (!gamingPack.isEmpty()) {
            gP = false;
        }
        if (pF == gP && pF) {
            state = GameState.WIN;
        }
    }

    public void newGame() {
        //разложим карты на 7 стопок
        dealCards();
        //сформируем колоду оставшихся карт
        List<Card> tmpList = new ArrayList<>();
        for (int i = 0; i < 52; i++) {
            Card card = pack.getCard(i);
            if (card.getState() == NOT_USED) {
                tmpList.add(card);
                card.setState(USED);
            }
        }
        Collections.shuffle(tmpList, rnd);
        for (int i = 0; i < 24; i++) {
            Card card = tmpList.get(i);
            gamingPack.add(card);
        }
        //создадим место хранения карт
        for (int i = 0; i < 4; i++) {
            List<Card> list = new ArrayList<>();
            spaceForKeeping.add(list);
        }
        //начнем игру
        state = Pasians.GameState.PLAYING;
    }

    private void dealCards() {
        for (int i = 0; i < 7; i++) {
            List<Card> content = new ArrayList<>();
            for (int j = 0; j < i + 1; j++) {
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
                if (j == i) {
                    card.setFaceState(Card.CardFaceState.FACE_UP);
                }
                card.setState(USED);
                content.add(card);
            }
            playingField.add(i, content);
        }
    }

    public void putOnTop(int indexOfColumn, Card card) {
        List<Card> target = playingField.get(indexOfColumn);
        if (target.isEmpty() && card.getValue() == 13) {
            target.add(card);
        } else {
            Card lastCard = target.get(target.size() - 1);
            if (lastCard.getValue() != 14 && lastCard.getValue() == card.getValue() + 1 && card.isSuitRed() != lastCard.isSuitRed() ||
                    lastCard.getValue() == 2 && card.getValue() == 14 && card.isSuitRed() != lastCard.isSuitRed()) {
                target.add(card);
            }
        }
    }

    public boolean putInKeepingSpace(int indexOfColumn, Card card) {
        List<Card> target = spaceForKeeping.get(indexOfColumn);
        if (target.isEmpty() && card.getValue() == 14) {
            target.add(card);
            calcState();
            return true;
        } else {
            if (!target.isEmpty()) {
                Card lastCard = target.get(target.size() - 1);
                if (lastCard.getValue() != 13 && lastCard.getValue() == card.getValue() - 1 && Objects.equals(card.getSuit(), lastCard.getSuit()) ||
                        lastCard.getValue() == 14 && card.getValue() == 2 && Objects.equals(card.getSuit(), lastCard.getSuit())) {
                    target.add(card);
                    calcState();
                    return true;
                }
            }
        }
        calcState();
        return false;
    }

    public Card getCardFromGamingPack(int index) {
        if (!gamingPack.isEmpty() && index < gamingPack.size()) {
            Card card = gamingPack.get(index);
            card.setFaceState(Card.CardFaceState.FACE_UP);
            return card;
        } else {
            return null;
        }
    }

    public void removeFromPlayingField(int indexOfColumn, Card card) {
        playingField.get(indexOfColumn).remove(card);
        calcState();
    }

    public List<List<Card>> getPlayingField() {
        return playingField;
    }

    public List<List<Card>> getSpaceForKeeping() {
        return spaceForKeeping;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }
}
