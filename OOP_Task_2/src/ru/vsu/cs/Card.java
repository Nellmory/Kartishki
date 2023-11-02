package ru.vsu.cs;

public class Card {
    private int value;
    private String suit;
    private CardState state;
    private GameCardState gameState;

    public enum CardState {
        USED,
        NOT_USED
    }
    public enum GameCardState {
        PLAYING,
        DISCARDED
    }


    public Card(int value, String suit) {
        this.value = value;
        this.suit = suit;
        this.state = CardState.NOT_USED;
        this.gameState = GameCardState.PLAYING;
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

    public void setState(CardState state) {
        this.state = state;
    }

    public void setGameState(GameCardState gameState) {
        this.gameState = gameState;
    }
}


