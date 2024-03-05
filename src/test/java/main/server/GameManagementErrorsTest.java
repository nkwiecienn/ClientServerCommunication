package main.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameManagementErrorsTest {
    @Test
    void errorWhenIncorrectNumberOfPlayer() {
        GameManagement gm = new GameManagement();

        gm.newPlayer("player0");

        gm.message("player0");
        gm.response("player0", "new"); //new game q

        gm.message("player0");
        gm.response("player0", "5");

        assertEquals("Number must be between 2 and 4. Try Again", gm.message("player0"));
    }

    @Test
    void errorWhenWrongGameID() {
        GameManagement gm = new GameManagement();
        gm.newPlayer("player0");
        gm.newPlayer("player1");

        gm.message("player0");
        gm.response("player0", "new"); //new game q

        gm.message("player0");
        gm.response("player0", "2"); //number of players

        gm.message("player1");
        gm.response("player1", "existing"); //new game q

        gm.message("player1");
        gm.response("player1", "Game10");

        assertEquals("This Game ID doesn't exist. Try Again", gm.message("player1"));
    }

    @Test
    void errorWhenWrongBettingCommand() {
        GameManagement gm = new GameManagement();
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

        gm.message("player0");
        gm.response("player0", "ready"); //initial bets

        gm.message("player0");
        gm.response("player0", "win"); //bet

        assertEquals("Unknown command. Try Again", gm.message("player0"));
    }

    @Test
    void errorWhenTryingToBetLowerAmount() {
        GameManagement gm = new GameManagement();
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

        gm.message("player0");
        gm.response("player0", "ready"); //initial bets

        gm.message("player0");
        gm.response("player0", "bet 5"); //bet

        gm.message("player1");
        gm.response("player1", "ready"); //initial bets

        gm.message("player1");
        gm.response("player1", "raise 2"); //bet

        assertEquals("You must put a number higher than the previous bet. Try Again", gm.message("player1"));
    }

    @Test
    void errorWhenWrongNumberWhileChangingCards() {
        GameManagement gm = new GameManagement();
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

        gm.message("player0");
        gm.response("player0", "ready"); //initial bets

        gm.message("player0");
        gm.response("player0", "bet 5"); //bet

        gm.message("player1");
        gm.response("player1", "ready"); //initial bets

        gm.message("player1");
        gm.response("player1", "raise 10"); //bet

        gm.message("player0");
        gm.response("player0", "end"); //bets to end

        gm.message("player0");
        gm.response("player0", "call"); //bet

        gm.message("player0");
        gm.response("player0", "end");

        gm.message("player1");
        gm.response("player1", "end"); //bets to end

        gm.message("player0");
        gm.response("player0", "1 2 6");

        assertEquals("Number must be between 0 and 5. Try Again", gm.message("player0"));
    }

}