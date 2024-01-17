package ru.vsu.cs.tools;

import javafx.scene.image.Image;
import ru.vsu.cs.tools.Card;

public class ImageReturn {

    private Card card;

    public static Image getImage(Card card) {
        if (card.getFaceState() == Card.CardFaceState.FACE_DOWN) {
            return new Image("images/0.gif");
        } else {
            StringBuilder path = new StringBuilder();
            path.append("images/");
            switch (card.getSuit()) {
                case CLUBS -> {
                    path.append("clubs/0");
                    int value = card.getValue();
                    String val = String.valueOf(value);
                    path.append(val);
                    if (value < 6) {
                        path.append(".png");
                    } else {
                        path.append(".gif");
                    }
                }
                case HEARTS -> {
                    path.append("hearts/1");
                    int value = card.getValue();
                    String val = String.valueOf(value);
                    path.append(val);
                    if (value < 6) {
                        path.append(".png");
                    } else {
                        path.append(".gif");
                    }
                }
                case DIAMONDS -> {
                    path.append("diamonds/2");
                    int value = card.getValue();
                    String val = String.valueOf(value);
                    path.append(val);
                    if (value < 6) {
                        path.append(".png");
                    } else {
                        path.append(".gif");
                    }
                }
                case SPADES -> {
                    path.append("spades/3");
                    int value = card.getValue();
                    String val = String.valueOf(value);
                    path.append(val);
                    if (value < 6) {
                        path.append(".png");
                    } else {
                        path.append(".gif");
                    }
                }
            }
            return new Image(String.valueOf(path));
        }
    }
}
