package ru.vsu.cs.tools;


public class Card {
    private final int value;
    private CardState state;
    private GameCardState gameState;
    private CardFaceState faceState;
    private final Suits suit;

    public enum CardState {
        USED,
        NOT_USED
    }

    public enum GameCardState {
        PLAYING,
        DISCARDED
    }

    public enum CardFaceState {
        FACE_DOWN,
        FACE_UP
    }

    public enum Suits {
        HEARTS,
        SPADES,
        DIAMONDS,
        CLUBS,
        RED_JOKER,
        BLACK_JOKER
    }


    public Card(int value, Suits suit) {
        this.value = value;
        this.suit = suit;
        this.state = CardState.NOT_USED;
        this.gameState = GameCardState.PLAYING;
        this.faceState = CardFaceState.FACE_DOWN;
    }

    public boolean isSuitRed() {
        return this.getSuit() == Suits.DIAMONDS || this.getSuit() == Suits.HEARTS;
    }

    public int getValue() {
        return value;
    }

    public Suits getSuit() {
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

    public void setFaceState(CardFaceState faceState) {
        this.faceState = faceState;
    }

    public CardFaceState getFaceState() {
        return faceState;
    }
}


