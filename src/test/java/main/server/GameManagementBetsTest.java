package main.server;

import main.model.Card;
import main.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GameManagementBetsTest {
    GameManagement gm = new GameManagement();
    Game game = new Game();
    List<Card> cards0 = new ArrayList<>();
    List<Card> cards1 = new ArrayList<>();

    @BeforeEach
    void beforeEach() {

        gm.newPlayer("player0");
        gm.newPlayer("player1");

        gm.message("player0");
        gm.response("player0", "new"); //new game q

        gm.message("player0");
        gm.response("player0", "2"); //number of players

        gm.message("player1");
        gm.response("player1", "existing"); //new game q

        gm.message("player1");
        gm.response("player1", "Game0"); //game id

        gm.message("player0");
        gm.response("player0", "ready"); //waiting for players

        gm.message("player1");
        gm.response("player1", "ready"); //waiting for players

        gm.message("player0");
        gm.response("player0", "ready"); //dealing cards

        gm.message("player1");
        gm.response("player1", "ready"); //dealing cards

        game = gm.getGame("player0");
        cards0 = game.getPlayers().get("player0").getCards();
        cards1 = game.getPlayers().get("player1").getCards();
    }

    @Test
    void betsEndWhenAllPlayersBetEqualAmount() {
        gm.message("player0");
        gm.response("player0", "ready"); //initial bets

        gm.message("player0");
        gm.response("player0", "bet 5"); //bet

        gm.message("player1");
        gm.response("player1", "ready"); //initial bets

        gm.message("player1");
        gm.response("player1", "call"); //bet

        assertTrue(game.endOfTurn());
    }

    @Test
    void betsAndGameEndWhenOnlyOneActivePlayer() {
        gm.message("player0");
        gm.response("player0", "ready"); //initial bets

        gm.message("player0");
        gm.response("player0", "bet 5"); //bet

        gm.message("player1");
        gm.response("player1", "ready"); //initial bets

        gm.message("player1");
        gm.response("player1", "fold"); //bet

        assertTrue(game.endOfTurn());
        assertTrue(game.endOfGame());
    }

    @Test
    void betsAndGameEndWhenOnlyOneActivePlayerWithAllIn() {
        gm.message("player0");
        gm.response("player0", "ready"); //initial bets

        gm.message("player0");
        gm.response("player0", "bet 5"); //bet

        gm.message("player1");
        gm.response("player1", "ready"); //initial bets

        gm.message("player1");
        gm.response("player1", "all-in"); //bet

        assertTrue(game.endOfTurn());
        assertTrue(game.endOfGame());
    }

}