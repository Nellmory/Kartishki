package ru.vsu.cs;

import java.util.ArrayList;
import java.util.Random;

public class Durak {

    private Pack pack = new Pack(36);
    private ArrayList<Card> gamingPack = new ArrayList<>();
    private Hand userHand = new Hand(6);
    private Hand enemyHand = new Hand(6);
    private final Random rnd = new Random();
    private String trumpSuit;

    public enum GameState {
        NOT_STARTED,
        PLAYING,
        WIN,
        FAIL
    }

    GameState state = GameState.NOT_STARTED;

    private void calcState() {};

    public void newGame() {
        //раздадим карты игрокам
        for (int i = 0; i < 6; i++) {
            Card card1 = null;
            Card card2 = null;
            boolean isNotUsed1 = false;
            boolean isNotUsed2 = false;
            //выбираем из колоды по две разные карты
            while (!isNotUsed1 || !isNotUsed2) {
                if (!isNotUsed1) {
                    card1 = pack.getCard(rnd.nextInt(36));
                    if (card1.getState() == Card.CardState.NOT_USED) {
                        isNotUsed1 = true;
                    }
                }
                if (!isNotUsed2) {
                    card2 = pack.getCard(rnd.nextInt(36));
                    if (card2.getState() == Card.CardState.NOT_USED) {
                        isNotUsed2 = true;
                    }
                }
            }
            userHand.add(card1);
            enemyHand.add(card2);
        }
        //сформируем оставшуюся колоду и определим козырную масть
        int k = 0;
        while (k < 26) {
            Card card = pack.getCard(rnd.nextInt(36));
            if (card.getState() == Card.CardState.NOT_USED) {
                gamingPack.add(card);
                k++;
            }
            if (k == 26) {
                trumpSuit = card.getSuit();
            }
        }

        // начинаем игру
        state = GameState.PLAYING;
    }

    public void attack(){};


}
