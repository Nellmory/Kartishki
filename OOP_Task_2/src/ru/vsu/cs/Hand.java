package ru.vsu.cs;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> hand = new ArrayList<>();
    private int size = 0;

    public Hand(int size) {
        this.size = size;
        this.hand = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            hand.add(null);
        }
    }

    public int getSize() {
        return size;
    }

    public void add(Card card) {
        Card lastCard = hand.get(getSize() - 1);
        //добавление карт в руку
        if (lastCard == null) {
            hand.add(card);
        } else {
            System.err.println("Нельзя добавить больше " + getSize() + " карт!");
        }
    }

    public boolean durakHandCheck() {
        boolean isNull = false;
        Card lastCard = hand.get(getSize() - 1);
        //проверка на 5 карт одной масти
        if (lastCard != null) {
            int[] suits = new int[4];
            for (int i = 0; i < getSize(); i++) {
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
                    hand.clear();
                    for (int j = 0; j < getSize(); j++) {
                        hand.add(null);
                    }
                    return false;
                }
            }
        } else {
            System.err.println("Неполный набор карт!");
        }
        return true;
    }
}
