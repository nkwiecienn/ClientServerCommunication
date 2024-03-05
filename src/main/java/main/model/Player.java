package main.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Player {
    @Getter
    ArrayList<Card> cards = new ArrayList<>();
    String playerId;
    @Getter
    @Setter
    boolean active = true;
    @Getter
    @Setter
    boolean allIn = false;
    int order;
    @Getter
    int money = 50;

    Player(String playerId, int order) {
        this.playerId = playerId;
        this.order = order;
    }

    void setCards(ArrayList<Card> cards) {
        this.cards.addAll(cards);
    }

    public String toString() {
        String stringCards = "";
        for(Card c: cards) {
            stringCards = stringCards + c.toString() + "\n";
        }

        return stringCards;
    }
}

