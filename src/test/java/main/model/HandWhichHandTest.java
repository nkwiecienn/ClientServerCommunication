package main.model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class HandWhichHandTest {

    @Test
    void whichHandReturnsRoyalFlush() {
        List<Card> cards = new ArrayList<>();

        Card card1 = new Card(Card.Rank.TEN, Card.Suit.HEARTS);
        Card card2 = new Card(Card.Rank.JACK, Card.Suit.HEARTS);
        Card card3 = new Card(Card.Rank.QUEEN, Card.Suit.HEARTS);
        Card card4 = new Card(Card.Rank.KING, Card.Suit.HEARTS);
        Card card5 = new Card(Card.Rank.ACE, Card.Suit.HEARTS);

        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);

        Hand hand = new Hand();

        Assert.assertEquals(Hand.HandKinds.ROYAL_FLUSH, hand.whichHand(cards));
    }

    @Test
    void whichHandReturnsStraightFlush() {
        List<Card> cards = new ArrayList<>();

        Card card1 = new Card(Card.Rank.TWO, Card.Suit.HEARTS);
        Card card2 = new Card(Card.Rank.THREE, Card.Suit.HEARTS);
        Card card3 = new Card(Card.Rank.FOUR, Card.Suit.HEARTS);
        Card card4 = new Card(Card.Rank.FIVE, Card.Suit.HEARTS);
        Card card5 = new Card(Card.Rank.SIX, Card.Suit.HEARTS);

        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);

        Hand hand = new Hand();

        Assert.assertEquals(Hand.HandKinds.STRAIGHT_FLUSH, hand.whichHand(cards));
    }

    @Test
    void whichHandReturnsStraightFlushWithFirstAce() {
        List<Card> cards = new ArrayList<>();

        Card card1 = new Card(Card.Rank.TWO, Card.Suit.HEARTS);
        Card card2 = new Card(Card.Rank.THREE, Card.Suit.HEARTS);
        Card card3 = new Card(Card.Rank.FOUR, Card.Suit.HEARTS);
        Card card4 = new Card(Card.Rank.FIVE, Card.Suit.HEARTS);
        Card card5 = new Card(Card.Rank.ACE, Card.Suit.HEARTS);

        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);

        Hand hand = new Hand();

        Assert.assertEquals(Hand.HandKinds.STRAIGHT_FLUSH, hand.whichHand(cards));
    }

    @Test
    void whichHandReturnsFourOfAKind() {
        List<Card> cards = new ArrayList<>();

        Card card1 = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
        Card card2 = new Card(Card.Rank.ACE, Card.Suit.SPADES);
        Card card3 = new Card(Card.Rank.ACE, Card.Suit.CLUBS);
        Card card4 = new Card(Card.Rank.ACE, Card.Suit.DIAMONDS);
        Card card5 = new Card(Card.Rank.KING, Card.Suit.HEARTS);

        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);

        Hand hand = new Hand();

        Assert.assertEquals(Hand.HandKinds.FOUR_OF_A_KIND, hand.whichHand(cards));
    }

    @Test
    void whichHandReturnsFullHouse() {
        List<Card> cards = new ArrayList<>();

        Card card1 = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
        Card card2 = new Card(Card.Rank.ACE, Card.Suit.SPADES);
        Card card3 = new Card(Card.Rank.ACE, Card.Suit.CLUBS);
        Card card4 = new Card(Card.Rank.KING, Card.Suit.DIAMONDS);
        Card card5 = new Card(Card.Rank.KING, Card.Suit.HEARTS);

        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);

        Hand hand = new Hand();

        Assert.assertEquals(Hand.HandKinds.FULL_HOUSE, hand.whichHand(cards));
    }

    @Test
    void whichHandReturnsFlush() {
        List<Card> cards = new ArrayList<>();

        Card card1 = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
        Card card2 = new Card(Card.Rank.TWO, Card.Suit.HEARTS);
        Card card3 = new Card(Card.Rank.THREE, Card.Suit.HEARTS);
        Card card4 = new Card(Card.Rank.KING, Card.Suit.HEARTS);
        Card card5 = new Card(Card.Rank.QUEEN, Card.Suit.HEARTS);

        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);

        Hand hand = new Hand();

        Assert.assertEquals(Hand.HandKinds.FLUSH, hand.whichHand(cards));
    }

    @Test
    void whichHandReturnsStraight() {
        List<Card> cards = new ArrayList<>();

        Card card1 = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
        Card card2 = new Card(Card.Rank.TWO, Card.Suit.SPADES);
        Card card3 = new Card(Card.Rank.THREE, Card.Suit.CLUBS);
        Card card4 = new Card(Card.Rank.FOUR, Card.Suit.DIAMONDS);
        Card card5 = new Card(Card.Rank.FIVE, Card.Suit.HEARTS);

        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);

        Hand hand = new Hand();

        Assert.assertEquals(Hand.HandKinds.STRAIGHT, hand.whichHand(cards));
    }

    @Test
    void whichHandReturnsThreeOfAKind() {
        List<Card> cards = new ArrayList<>();

        Card card1 = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
        Card card2 = new Card(Card.Rank.ACE, Card.Suit.SPADES);
        Card card3 = new Card(Card.Rank.ACE, Card.Suit.CLUBS);
        Card card4 = new Card(Card.Rank.TWO, Card.Suit.DIAMONDS);
        Card card5 = new Card(Card.Rank.THREE, Card.Suit.HEARTS);

        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);

        Hand hand = new Hand();

        Assert.assertEquals(Hand.HandKinds.THREE_OF_A_KIND, hand.whichHand(cards));
    }

    @Test
    void whichHandReturnsTwoPair() {
        List<Card> cards = new ArrayList<>();

        Card card1 = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
        Card card2 = new Card(Card.Rank.ACE, Card.Suit.SPADES);
        Card card3 = new Card(Card.Rank.TWO, Card.Suit.CLUBS);
        Card card4 = new Card(Card.Rank.TWO, Card.Suit.DIAMONDS);
        Card card5 = new Card(Card.Rank.THREE, Card.Suit.HEARTS);

        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);

        Hand hand = new Hand();

        Assert.assertEquals(Hand.HandKinds.TWO_PAIR, hand.whichHand(cards));
    }

    @Test
    void whichHandReturnsOnePair() {
        List<Card> cards = new ArrayList<>();

        Card card1 = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
        Card card2 = new Card(Card.Rank.ACE, Card.Suit.SPADES);
        Card card3 = new Card(Card.Rank.TWO, Card.Suit.CLUBS);
        Card card4 = new Card(Card.Rank.FOUR, Card.Suit.DIAMONDS);
        Card card5 = new Card(Card.Rank.THREE, Card.Suit.HEARTS);

        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);

        Hand hand = new Hand();

        Assert.assertEquals(Hand.HandKinds.ONE_PAIR, hand.whichHand(cards));
    }

    @Test
    void whichHandReturnsHighCard() {
        List<Card> cards = new ArrayList<>();

        Card card1 = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
        Card card2 = new Card(Card.Rank.KING, Card.Suit.SPADES);
        Card card3 = new Card(Card.Rank.TWO, Card.Suit.CLUBS);
        Card card4 = new Card(Card.Rank.FOUR, Card.Suit.DIAMONDS);
        Card card5 = new Card(Card.Rank.THREE, Card.Suit.HEARTS);

        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);

        Hand hand = new Hand();

        Assert.assertEquals(Hand.HandKinds.HIGH_CARD, hand.whichHand(cards));
    }
}