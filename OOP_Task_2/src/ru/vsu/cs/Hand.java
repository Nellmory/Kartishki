package ru.vsu.cs;

import java.util.ArrayList;
import java.util.List;

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

    public Card getCard(int index) {
        if (index <= hand.size()) {
            return hand.get(index);
        } else System.err.println("Выход за рамки массива!");
        return null;
    }

    public boolean durakHandCheck() {
        boolean isNull = false;
        //проверка на 5 карт одной масти
        if (hand.size() == 6) {
            int[] suits = new int[4];
            for (int i = 0; i < 6; i++) {
                String suit = hand.get(i).getSuit();
                switch (suit) {
                    case "h" -> {
                        suits[0]++;
                        if (suits[0] == 5) {
                            isNull = true;
                        }
                    }
                    case "p" -> {
                        suits[1]++;
                        if (suits[1] == 5) {
                            isNull = true;
                        }
                    }
                    case "d" -> {
                        suits[2]++;
                        if (suits[2] == 5) {
                            isNull = true;
                        }
                    }
                    case "c" -> {
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
