package main.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class GameTest {

    @Test
    void newCardsChangesCards() {
        Game game = new Game();
        game.addNewPlayer("player0");
        game.dealCards();

        String[] newCards = new String[1];
        newCards[0] = "1";

        Card cardBefore = game.players.get("player0").getCards().get(0);

        game.newCards("player0", newCards);

        Card cardAfter = game.players.get("player0").getCards().get(0);

        assertNotEquals(cardBefore, cardAfter);
    }

}