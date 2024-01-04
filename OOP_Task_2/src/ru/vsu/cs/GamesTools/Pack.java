package ru.vsu.cs.GamesTools;

import java.util.ArrayList;
import java.util.List;

public class Pack {
    private List<Card> pack = new ArrayList<>(54);

    public Pack(int numberOfCards) {
        this.pack = getPack(numberOfCards);
    }

    public Card getCard(int index) {
        return pack.get(index);
    }

    private List<Card> getPack(int n) {
        boolean jokers = false;
        int num = n;

        //проверка на джокеров
        if (num == 54) {
            num -= 2;
            jokers = true;
        }

        //формирование колоды
        for (Card.Suits curSuit : Card.Suits.values()) {
            for (int j = 0; j < num / 4; j++) {
                Card card = new Card(j + (2 + (52 - num) / 4), curSuit);
                pack.add(card);
            }
        }

        if (jokers) {
            Card redJoker = new Card(15, Card.Suits.RED_JOKER);
            pack.add(redJoker);
            Card blackJoker = new Card(15, Card.Suits.BLACK_JOKER);
            pack.add(blackJoker);
        }

        return pack;
    }


}