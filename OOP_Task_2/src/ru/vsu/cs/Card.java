package ru.vsu.cs;

public class Card {
    private int value;
    private String suit;
    private CardState state;

    public enum CardState {
        USED,
        NOT_USED
    }


    public Card(int value, String suit) {
        this.value = value;
        this.suit = suit;
        this.state = CardState.NOT_USED;
    }

    public int getValue() {
        return value;
    }

    public String getSuit() {
        return suit;
    }

    public CardState getState() {
        return state;
    }
}


