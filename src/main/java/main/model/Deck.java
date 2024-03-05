package main.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;

@Getter
public class Deck {
    ArrayList<Card> sortedDeck = new ArrayList<>();
    int it = 0;

    public Deck() {
        for(int i = 0; i < 13; i++)
            for(int j = 0; j < 4; j++) {
                Card card = new Card(Card.Rank.values()[i], Card.Suit.values()[j]);
                sortedDeck.add(card);
            }
    }

    public void shuffle() {
        Collections.shuffle(sortedDeck);
    }

    public void next() {
        it = it+1;
    }
}
