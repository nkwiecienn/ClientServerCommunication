package main.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HandBetterHandTest {

    @Test
    void betterHandWithoutDraw() {
        List<Card> cards1 = new ArrayList<>();

        Card card11 = new Card(Card.Rank.TEN, Card.Suit.HEARTS);
        Card card12 = new Card(Card.Rank.JACK, Card.Suit.HEARTS);
        Card card13 = new Card(Card.Rank.QUEEN, Card.Suit.HEARTS);
        Card card14 = new Card(Card.Rank.KING, Card.Suit.HEARTS);
        Card card15 = new Card(Card.Rank.ACE, Card.Suit.HEARTS);

        cards1.add(card11);
        cards1.add(card12);
        cards1.add(card13);
        cards1.add(card14);
        cards1.add(card15);

        List<Card> cards2 = new ArrayList<>();

        Card card21 = new Card(Card.Rank.TWO, Card.Suit.HEARTS);
        Card card22 = new Card(Card.Rank.THREE, Card.Suit.HEARTS);
        Card card23 = new Card(Card.Rank.FOUR, Card.Suit.HEARTS);
        Card card24 = new Card(Card.Rank.FIVE, Card.Suit.HEARTS);
        Card card25 = new Card(Card.Rank.SIX, Card.Suit.HEARTS);

        cards2.add(card21);
        cards2.add(card22);
        cards2.add(card23);
        cards2.add(card24);
        cards2.add(card25);

        Hand hand = new Hand();

        assertEquals(1, hand.betterHand(cards1, cards2));
    }

    @Test
    void betterHandWithHighCardDraw() {
        List<Card> cards1 = new ArrayList<>();

        Card card11 = new Card(Card.Rank.TWO, Card.Suit.HEARTS);
        Card card12 = new Card(Card.Rank.THREE, Card.Suit.HEARTS);
        Card card13 = new Card(Card.Rank.FOUR, Card.Suit.HEARTS);
        Card card14 = new Card(Card.Rank.FIVE, Card.Suit.HEARTS);
        Card card15 = new Card(Card.Rank.ACE, Card.Suit.HEARTS);

        cards1.add(card11);
        cards1.add(card12);
        cards1.add(card13);
        cards1.add(card14);
        cards1.add(card15);

        List<Card> cards2 = new ArrayList<>();

        Card card21 = new Card(Card.Rank.SEVEN, Card.Suit.DIAMONDS);
        Card card22 = new Card(Card.Rank.THREE, Card.Suit.DIAMONDS);
        Card card23 = new Card(Card.Rank.FOUR, Card.Suit.DIAMONDS);
        Card card24 = new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS);
        Card card25 = new Card(Card.Rank.SIX, Card.Suit.DIAMONDS);

        cards2.add(card21);
        cards2.add(card22);
        cards2.add(card23);
        cards2.add(card24);
        cards2.add(card25);

        Hand hand = new Hand();

        assertEquals(1, hand.betterHand(cards1, cards2));
    }

    @Test
    void betterHandWithFourOfAKind() {
        List<Card> cards1 = new ArrayList<>();

        Card card11 = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
        Card card12 = new Card(Card.Rank.ACE, Card.Suit.SPADES);
        Card card13 = new Card(Card.Rank.ACE, Card.Suit.CLUBS);
        Card card14 = new Card(Card.Rank.ACE, Card.Suit.DIAMONDS);
        Card card15 = new Card(Card.Rank.KING, Card.Suit.HEARTS);

        cards1.add(card11);
        cards1.add(card12);
        cards1.add(card13);
        cards1.add(card14);
        cards1.add(card15);

        List<Card> cards2 = new ArrayList<>();

        Card card21 = new Card(Card.Rank.TWO, Card.Suit.HEARTS);
        Card card22 = new Card(Card.Rank.TWO, Card.Suit.SPADES);
        Card card23 = new Card(Card.Rank.TWO, Card.Suit.CLUBS);
        Card card24 = new Card(Card.Rank.TWO, Card.Suit.DIAMONDS);
        Card card25 = new Card(Card.Rank.THREE, Card.Suit.HEARTS);

        cards2.add(card21);
        cards2.add(card22);
        cards2.add(card23);
        cards2.add(card24);
        cards2.add(card25);

        Hand hand = new Hand();

        assertEquals(1, hand.betterHand(cards1, cards2));
    }

    @Test
    void betterHandWithThreeOfAKind() {
        List<Card> cards1 = new ArrayList<>();

        Card card11 = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
        Card card12 = new Card(Card.Rank.ACE, Card.Suit.SPADES);
        Card card13 = new Card(Card.Rank.ACE, Card.Suit.CLUBS);
        Card card14 = new Card(Card.Rank.FOUR, Card.Suit.DIAMONDS);
        Card card15 = new Card(Card.Rank.KING, Card.Suit.HEARTS);

        cards1.add(card11);
        cards1.add(card12);
        cards1.add(card13);
        cards1.add(card14);
        cards1.add(card15);

        List<Card> cards2 = new ArrayList<>();

        Card card21 = new Card(Card.Rank.TWO, Card.Suit.HEARTS);
        Card card22 = new Card(Card.Rank.TWO, Card.Suit.SPADES);
        Card card23 = new Card(Card.Rank.TWO, Card.Suit.CLUBS);
        Card card24 = new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS);
        Card card25 = new Card(Card.Rank.THREE, Card.Suit.HEARTS);

        cards2.add(card21);
        cards2.add(card22);
        cards2.add(card23);
        cards2.add(card24);
        cards2.add(card25);

        Hand hand = new Hand();

        assertEquals(1, hand.betterHand(cards1, cards2));
    }

    @Test
    void betterHandWithTwoPair() {
        List<Card> cards1 = new ArrayList<>();

        Card card11 = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
        Card card12 = new Card(Card.Rank.ACE, Card.Suit.SPADES);
        Card card13 = new Card(Card.Rank.KING, Card.Suit.CLUBS);
        Card card14 = new Card(Card.Rank.FOUR, Card.Suit.DIAMONDS);
        Card card15 = new Card(Card.Rank.KING, Card.Suit.HEARTS);

        cards1.add(card11);
        cards1.add(card12);
        cards1.add(card13);
        cards1.add(card14);
        cards1.add(card15);

        List<Card> cards2 = new ArrayList<>();

        Card card21 = new Card(Card.Rank.TWO, Card.Suit.HEARTS);
        Card card22 = new Card(Card.Rank.TWO, Card.Suit.SPADES);
        Card card23 = new Card(Card.Rank.THREE, Card.Suit.CLUBS);
        Card card24 = new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS);
        Card card25 = new Card(Card.Rank.THREE, Card.Suit.HEARTS);

        cards2.add(card21);
        cards2.add(card22);
        cards2.add(card23);
        cards2.add(card24);
        cards2.add(card25);

        Hand hand = new Hand();

        assertEquals(1, hand.betterHand(cards1, cards2));
    }

    @Test
    void betterHandDraw() {
        List<Card> cards1 = new ArrayList<>();

        Card card11 = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
        Card card12 = new Card(Card.Rank.FIVE, Card.Suit.SPADES);
        Card card13 = new Card(Card.Rank.KING, Card.Suit.CLUBS);
        Card card14 = new Card(Card.Rank.FOUR, Card.Suit.DIAMONDS);
        Card card15 = new Card(Card.Rank.THREE, Card.Suit.HEARTS);

        cards1.add(card11);
        cards1.add(card12);
        cards1.add(card13);
        cards1.add(card14);
        cards1.add(card15);

        List<Card> cards2 = new ArrayList<>();

        Card card21 = new Card(Card.Rank.TWO, Card.Suit.HEARTS);
        Card card22 = new Card(Card.Rank.ACE, Card.Suit.SPADES);
        Card card23 = new Card(Card.Rank.THREE, Card.Suit.CLUBS);
        Card card24 = new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS);
        Card card25 = new Card(Card.Rank.JACK, Card.Suit.HEARTS);

        cards2.add(card21);
        cards2.add(card22);
        cards2.add(card23);
        cards2.add(card24);
        cards2.add(card25);

        Hand hand = new Hand();

        assertEquals(0, hand.betterHand(cards1, cards2));
    }
}