package main.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameManagementBeforeGameStartsTest {

    @Test
    void doesntStartGameUntilEnoughPlayersJoin() {
        GameManagement gm = new GameManagement();

        gm.newPlayer("player0");

        gm.message("player0");
        gm.response("player0", "new"); //new game q

        gm.message("player0");
        gm.response("player0", "2"); //number of players

        gm.message("player0");
        gm.response("player0", "ready");

        assertEquals("Waiting for players to join... type \"ready\" when all players join", gm.message("player0"));

        gm.newPlayer("player1");

        gm.message("player1");
        gm.response("player1", "existing"); //new game q

        gm.message("player1");
        gm.response("player1", "Game0"); //game ID

        gm.message("player0");
        gm.response("player0", "ready");

        assertNotEquals("Waiting for players to join... type \"ready\" when all players join", gm.message("player0"));
    }
}