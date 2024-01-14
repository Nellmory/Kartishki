package ru.vsu.cs.controllers;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import ru.vsu.cs.Card;
import ru.vsu.cs.ImageReturn;

public class FocusedCard {
    private Card card;
    private int indexInStackPane;
    private int indexOfStackPane;

    public FocusedCard(Card card, int indexInStackPane, int indexOfStackPane) {
        this.card = card;
        this.indexInStackPane = indexInStackPane;
        this.indexOfStackPane = indexOfStackPane;
    }

    public Card getCard() {
        return card;
    }

    public int getIndexInStackPane() {
        return indexInStackPane;
    }

    public int getIndexOfStackPane() {
        return indexOfStackPane;
    }
}
