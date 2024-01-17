package main.java.ru.vsu.cs.tools;

import javafx.scene.Node;
import javafx.scene.Scene;
import main.java.ru.vsu.cs.tools.Card;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Hand extends Node {
    private ArrayList<Card> hand = new ArrayList<>();

    public Hand() {
        this.hand = new ArrayList<>();
    }

    public int getSize() {
        return hand.size();
    }

    public void add(Card card) {
        hand.add(card);
    }

    public void add(List<Card> cards) {
        hand.addAll(cards);
    }

    public void remove(Card card) {
        for (int i = 0; i < this.getSize(); i++) {
            Card card1 = this.getCard(i);
            if (card1.getSuit() == card.getSuit() && card1.getValue() == card.getValue()) {
                this.remove(i);
                break;
            }
        }
    }

    public void remove(List<Card> cards) {
        for (Card card : cards) {
            this.remove(card);
        }
    }

    public void remove(int index) {
        hand.remove(index);
    }

    public void clear() {
        this.hand.clear();
    }

    //runtime exception
    public Card getCard(int index) {
        try {
            return hand.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean containsSuit(Card.Suits suit) {
        for (int i = 0; i < this.getSize(); i++) {
            Card card = this.getCard(i);
            if (Objects.equals(card.getSuit(), suit)) {
                return true;
            }
        }
        return false;
    }

    public boolean durakHandCheck() {
        boolean isNull = false;
        //проверка на 5 карт одной масти
        if (hand.size() == 6) {
            int[] suits = new int[4];
            for (int i = 0; i < 6; i++) {
                Card.Suits suit = hand.get(i).getSuit();
                switch (suit) {
                    case HEARTS -> {
                        suits[0]++;
                        if (suits[0] == 5) {
                            isNull = true;
                        }
                    }
                    case SPADES -> {
                        suits[1]++;
                        if (suits[1] == 5) {
                            isNull = true;
                        }
                    }
                    case DIAMONDS -> {
                        suits[2]++;
                        if (suits[2] == 5) {
                            isNull = true;
                        }
                    }
                    case CLUBS -> {
                        suits[3]++;
                        if (suits[3] == 5) {
                            isNull = true;
                        }
                    }
                }
                if (isNull) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }

    public boolean ifContains(Card card) {
        for (int i = 0; i < this.getSize(); i++) {
            Card handCard = this.getCard(i);
            if (handCard.getSuit() == card.getSuit() && handCard.getValue() == card.getValue()) {
                return true;
            }
        }
        return false;
    }
}
