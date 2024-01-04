package ru.vsu.cs.HeartsGame;

import ru.vsu.cs.GamesTools.Card;

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
