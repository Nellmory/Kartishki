package ru.vsu.cs.GamesTools;

import ru.vsu.cs.GamesTools.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Hand {
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
        hand.remove(card);
    }

    public void remove(List<Card> cards) {
        for (Card card : cards) {
            hand.remove(card);
        }
    }

    public void clear() {
        this.hand.clear();
    }

    //runtime exception
    public Card getCard(int index) {
        if (index <= hand.size()) {
            return hand.get(index);
        } else System.err.println("Выход за рамки массива!");
        return null;
    }

    public boolean contains(Card card) {
        return this.contains(card);
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
            System.err.println("Неполный набор карт!");
        }
        return true;
    }
}
