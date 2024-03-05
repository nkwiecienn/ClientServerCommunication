package main.model;

import lombok.Value;

import java.util.List;

@Value
public class Hand {
    public enum HandKinds {
        ROYAL_FLUSH, // dziesiątka, walet, dama, król, as w tym samym kolorze
        STRAIGHT_FLUSH, // pięć kolejnych kart w tym samym kolorze
        FOUR_OF_A_KIND, // cztery te same figury
        FULL_HOUSE, // para i trójka
        FLUSH, // pięć kart w tym samym kolorze
        STRAIGHT, // pięć kolejnych kart w różnych kolorach
        THREE_OF_A_KIND, // trzy karty z tą samą figurą
        TWO_PAIR, // dwie pary
        ONE_PAIR, //dwie karty z tą samą figurą
        HIGH_CARD // nic z powyższych, w przypadku remisu najwyższa karta
    }

    //zwraca 1 jeśli pierwsza karta jest lepsza, 2 jeśli druga, 0 jeśli jest remis
    public int betterHand(List<Card> cardsA, List<Card> cardsB) {
        HandKinds handKindA = this.whichHand(cardsA);
        HandKinds handKindB = this.whichHand(cardsB);

        if(handKindA.ordinal() < handKindB.ordinal())
            return 1;

        if(handKindA.ordinal() > handKindB.ordinal())
            return 2;

        return draw(handKindA, cardsA, cardsB);
    }

    private int draw(HandKinds handKindA, List<Card> cardsA, List<Card> cardsB) {
        int[] ranksA = new int[13];
        int[] ranksB = new int[13];

        for(Card c : cardsA) {
            ranksA[c.getRank().ordinal()]++;
        }

        for(Card c : cardsB) {
            ranksB[c.getRank().ordinal()]++;
        }


        switch (handKindA) {
            case STRAIGHT_FLUSH, FLUSH, STRAIGHT, HIGH_CARD:
                return this.hightCard(ranksA, ranksB);
            case FOUR_OF_A_KIND:
                if(this.highestCardNumber(ranksA, 4) > this.highestCardNumber(ranksB, 4))
                    return 1;
                if(this.highestCardNumber(ranksA, 4) < this.highestCardNumber(ranksB, 4))
                    return 2;
                return this.hightCard(ranksA, ranksB);
            case FULL_HOUSE, THREE_OF_A_KIND:
                if(this.highestCardNumber(ranksA, 3) > this.highestCardNumber(ranksB, 3))
                    return 1;
                if(this.highestCardNumber(ranksA, 3) < this.highestCardNumber(ranksB, 3))
                    return 2;
                return this.hightCard(ranksA, ranksB);
            case TWO_PAIR, ONE_PAIR:
                if(this.highestCardNumber(ranksA, 2) > this.highestCardNumber(ranksB, 2))
                    return 1;
                if(this.highestCardNumber(ranksA, 2) < this.highestCardNumber(ranksB, 2))
                    return 2;
                return this.hightCard(ranksA, ranksB);
            default:
                return 0;
        }
    }

    public HandKinds whichHand(List<Card> cards) {
        int[] ranks = new int[13];
        int[] suits = new int[4];

        for(Card c : cards) {
            ranks[c.getRank().ordinal()]++;
            suits[c.getSuit().ordinal()]++;
        }

        if(this.flush(suits) && this.straight(ranks) && ranks[12] == 1 && ranks[11] == 1)
            return HandKinds.ROYAL_FLUSH;

        if(this.flush(suits) && this.straight(ranks))
            return HandKinds.STRAIGHT_FLUSH;

        if(Boolean.TRUE.equals(this.fourRanks(ranks)))
            return HandKinds.FOUR_OF_A_KIND;

        if(this.numberOfPairs(ranks) == 1 && Boolean.TRUE.equals(this.threeRanks(ranks)))
            return HandKinds.FULL_HOUSE;

        if(Boolean.TRUE.equals(this.flush(suits)))
            return HandKinds.FLUSH;

        if(Boolean.TRUE.equals(this.straight(ranks)))
            return HandKinds.STRAIGHT;

        if(Boolean.TRUE.equals(this.threeRanks(ranks)))
            return HandKinds.THREE_OF_A_KIND;

        if(this.numberOfPairs(ranks) == 2)
            return HandKinds.TWO_PAIR;

        if(this.numberOfPairs(ranks) == 1)
            return HandKinds.ONE_PAIR;

        return HandKinds.HIGH_CARD;
    }

    // zwraca wyższą rękę gdy o zwycięstwie decyduje wyższa ręka
    private int hightCard(int[] ranksA, int[] ranksB) {
        if(this.highestCard(ranksA) > this.highestCard(ranksB))
            return 1;
        if(this.highestCard(ranksA) < this.highestCard(ranksB))
            return 2;
        return 0;
    }

    // zwraca najwyższą kartę
    private int highestCard(int[] ranks) {
        for(int i = 12; i >= 0; i--)
            if(ranks[i] != 0)
                return i;
        return 0;
    }

    // używane np. gdy patrze jaka jest największa wartość dla dwóch par - dla pary number = 2
    private int highestCardNumber(int[] ranks, int number) {
        for(int i = 12; i >= 0; i--)
            if(ranks[i] == number)
                return i;
        return 0;
    }

    // zwraca true jeśli na ręce jest 5 kolejnych kart
    private Boolean straight(int[] ranks) {

        int i = 0;

        while(ranks[i] == 0)
            i++;

        for(int j = i; j < i + 5; j++) {
            if(ranks[j] == 0) {
                return j == 4 && ranks[12] == 1 && ranks[0] == 1;
            }
        }

        return true;
    }

    // zwraca true jeśli na ręce jest 5 kart w tym samym kolorze
    private Boolean flush(int[] suits) {
        if(suits[0] == 5)
            return true;
        if(suits[1] == 5)
            return true;
        if(suits[2] == 5)
            return true;
        return suits[3] == 5;
    }

    private int numberOfPairs(int[] ranks) {
        int pairs = 0;

        for(int r : ranks) {
            if(r == 2)
                pairs++;
        }

        return pairs;
    }

    private Boolean threeRanks(int[] ranks) {
        for(int r : ranks) {
            if(r == 3)
                return true;
        }
        return false;
    }

    private Boolean fourRanks(int[] ranks) {
        for(int r : ranks) {
            if(r == 4)
                return true;
        }
        return false;
    }
}

