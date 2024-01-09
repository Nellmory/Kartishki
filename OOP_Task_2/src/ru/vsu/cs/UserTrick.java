package ru.vsu.cs;

import ru.vsu.cs.Card;

public class UserTrick {
    private int userHand;
    private Card card;

    public UserTrick(int userHand, Card card) {
        this.userHand = userHand;
        this.card = card;
    }

    public int getUserHand() {
        return userHand;
    }

    public Card getCard() {
        return card;
    }
}
