package main.model;

import lombok.Value;

@Value
public class Card {

    public enum Rank {
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
    }

    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

    Rank rank;
    Suit suit;

    public String toString() {
        return rank.name() + " OF " + suit.name();
    }
}
