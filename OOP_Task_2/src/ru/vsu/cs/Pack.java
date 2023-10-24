package ru.vsu.cs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pack {
    private List<Card> pack;
    private final List<String> suit = Arrays.asList("h", "p", "d", "c");

    public Pack(int numberOfCards) {
        this.pack = getPack(numberOfCards);
    }

    public List<Card> getPack(int n) {
        boolean jokers = false;
        int numberOfCards = n;

        //проверка на джокеров
        if (numberOfCards == 54) {
            numberOfCards -= 2;
            jokers = true;
        }

        //формирование колоды
        for (int i = 0; i < numberOfCards; i++) {
            for (String curSuit : suit) {
                for (int j = 0; j < numberOfCards / 4; j++) {
                    Card card = new Card(j + (2 + (52 - numberOfCards) / 4), curSuit);
                    pack.add(card);
                }
            }
        }
        if (jokers) {
            Card redJoker = new Card(15, "rJ");
            pack.add(redJoker);
            Card blackJoker = new Card(15, "bJ");
            pack.add(blackJoker);
        }

        return pack;
    }
}