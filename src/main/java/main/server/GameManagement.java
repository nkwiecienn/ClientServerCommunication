package main.server;

import main.model.Game;

import java.util.*;

public class GameManagement {
    Map<String, Game> games = new HashMap<>();
    private static final Map<String, GameManagement.CurrentPhase> phases = new HashMap<>();
    private static final Map<String, String> errors = new HashMap<>();
    Map<String, String> playersInGames = new HashMap<>();

    public enum CurrentPhase {
        NEW_GAME_Q, NEW_GAME_ID, ENTER_GAME_ID, NUMBER_OF_PLAYERS, WAITING_FOR_PLAYERS, DEALING_CARDS, INITIAL_BETS, BET, WAITING_FOR_BETS, NEW_CARDS,
        SECOND_BETS, BEST_HAND, WAITING_FOR_BEST_HAND, PLAY_AGAIN
    }

    public String message(String clientId) {

        CurrentPhase phase = phases.get(clientId);

        String message = "";

        if(errors.containsKey(clientId)) {
            message = errors.get(clientId);
            errors.remove(clientId);
            return message;
        }

        switch(phase) {
            case NEW_GAME_Q:
                message = "Welcome to the game. Do you want to make a new game or join an existing one? Type \"new\" for a new one, \"existing\" for an existing one." +
                        "(The game ID is: Game + host's number)";
                break;

            case ENTER_GAME_ID:
                message = "Enter game ID";
                break;

            case NUMBER_OF_PLAYERS:
                message = "Enter the number of players (between 2 and 4)";
                break;

            case WAITING_FOR_PLAYERS:
                message = "Waiting for players to join... type \"ready\" when all players join";
                break;

            case DEALING_CARDS:
                this.getGame(clientId).dealCards();

                message = "The initial game pot is 100. Your cards:" + "\n" + this.getGame(clientId).getStringCardsFromPlayer(clientId);

                message += "Type \"ready\" when you're ready for betting round";
                break;

            case INITIAL_BETS:

                int turn = this.getGame(clientId).getPlayerTurn(clientId);


                message = "The initial bets round has started. You're " + turn + ". in round. Type \"ready\" when it's your turn";

                break;

            case SECOND_BETS:

                int turn2 = this.getGame(clientId).getPlayerTurn(clientId);

                message = "Your cards:" + "\n" + this.getGame(clientId).getStringCardsFromPlayer(clientId);

                message += "The second bets round has started. You're " + turn2 + ". in round. Type \"ready\" when it's your turn";

                break;


            case BET:

                message = "Before your turn: \n";
                message += this.getGame(clientId).getBeforeClientMessage();
                message += "Choose your action and amount if needed (\"fold\", ";

                if(this.getGame(clientId).getCurrentBet() == 0)
                    message += "\"bet\", ";
                else
                    message += "\"raise\", \"call\"";

                message += "or put \"all-in\")";

                break;

            case NEW_CARDS:
                message = "The first bets round has ended, in this round: \n";
                message += this.getGame(clientId).getBeforeClientMessage();
                message += "Now you'll be able to change your cards. Your current cards are" +
                        ":" + "\n" + this.getGame(clientId).getStringCardsFromPlayer(clientId);
                message += "Type numbers of cards you want to change (counting from 1 to 5 or 0 if you don't want to change any)";
                break;

            case WAITING_FOR_BETS:
                message += "You're now waiting for bets to end. Type \"end\" to see if they end";
                break;

            case BEST_HAND:
                message += this.getGame(clientId).listHands();
                List<String> winners = this.getGame(clientId).winningHand();
                message += "The winner is " + winners.get(0);

                if(winners.size() > 1)
                    message += "and " + winners.get(1) + " (draw)";

                message += "\n" +
                "The pot is " + this.getGame(clientId).getPot();

                message += "\n Type \"exit\" to exit the game";
                break;

            case WAITING_FOR_BEST_HAND:
                message = "You're now waiting for game to end. Type \"end\" to see if it ends";
                break;

            default:
                break;
        }
        return message;
    }

    public void response(String clientId, String response) {
        CurrentPhase phase = phases.get(clientId);

        switch (phase) {
            case NEW_GAME_Q:
                if(Objects.equals(response, "new")) {
                    Game game = new Game();
                    game.addNewPlayer(clientId);

                    String gameId = "Game" + clientId.substring(6);

                    games.put(gameId, game);

                    playersInGames.put(clientId, gameId);

                    phases.put(clientId, CurrentPhase.NUMBER_OF_PLAYERS);
                }
                else if(Objects.equals(response, "existing")) {
                    phases.put(clientId, CurrentPhase.ENTER_GAME_ID);
                }
                break;
            case NUMBER_OF_PLAYERS:
                String gameId = "Game" + clientId.substring(6);

                if(Integer.parseInt(response) < 2 || Integer.parseInt(response) > 4) {
                    errors.put(clientId, "Number must be between 2 and 4. Try Again");
                    break;
                }

                games.get(gameId).setExpectedNumberOfPlayers(Integer.parseInt(response));
                phases.put(clientId, CurrentPhase.WAITING_FOR_PLAYERS);
                break;
            case ENTER_GAME_ID:

                if(!games.containsKey(response)) {
                    errors.put(clientId, "This Game ID doesn't exist. Try Again");
                    break;
                }

                games.get(response).addNewPlayer(clientId);
                phases.put(clientId, CurrentPhase.WAITING_FOR_PLAYERS);
                playersInGames.put(clientId, response);
                break;
            case WAITING_FOR_PLAYERS:
                String id = playersInGames.get(clientId);

                if(games.get(id).getNumberOfPlayers() != games.get(id).getExpectedNumberOfPlayers())
                    break;

                phases.put(clientId, CurrentPhase.DEALING_CARDS);

                break;
            case DEALING_CARDS:
                phases.put(clientId, CurrentPhase.INITIAL_BETS);
                break;
            case INITIAL_BETS, SECOND_BETS:
                if(Objects.equals(this.getGame(clientId).getCurrentTurn(), clientId))
                    phases.put(clientId, CurrentPhase.BET);
                break;

            case BET:

                String[] command = response.split(" ");

                if(!this.possibleBets().contains(command[0])) {
                    errors.put(clientId, "Unknown command. Try Again");
                    break;
                }

                if(response.contains(" ")) {

                    if(this.getGame(clientId).getCurrentBet() > Integer.parseInt(command[1])) {
                        errors.put(clientId, "You must put a number higher than the previous bet. Try Again");
                        break;
                    }

                    this.getGame(clientId).bet(clientId, command[0], Integer.parseInt(command[1]));
                }
                else
                    this.getGame(clientId).bet(clientId, response, 0);

                if(response.equals("fold"))
                    phases.put(clientId, CurrentPhase.WAITING_FOR_BEST_HAND);
                else
                    phases.put(clientId, CurrentPhase.WAITING_FOR_BETS);

                break;

            case WAITING_FOR_BETS:
                if(Objects.equals(response, "winner")) {
                    phases.put(clientId, CurrentPhase.BEST_HAND);
                }

                if(this.getGame(clientId).endOfGame()) {
                    phases.put(clientId, CurrentPhase.BEST_HAND);
                    break;
                }

                if(!this.getGame(clientId).endOfTurn()) { //jeśli wszyscy jeszcze się nie zrównali
                    if (Objects.equals(this.getGame(clientId).getCurrentTurn(), clientId)) //jeśli jest kogoś kolej
                        phases.put(clientId, CurrentPhase.BET);
                    break;
                }

                if(!this.getGame(clientId).isSecondBets()) {
                    phases.put(clientId, CurrentPhase.NEW_CARDS);
                    break;
                }

                phases.put(clientId, CurrentPhase.BEST_HAND);
                break;

            case WAITING_FOR_BEST_HAND:
                if(Objects.equals(response, "winner")) {
                    phases.put(clientId, CurrentPhase.BEST_HAND);
                }

                if(!this.getGame(clientId).endOfGame())
                    break;

                if(!this.getGame(clientId).isSecondBets())
                    break;

                if(!this.getGame(clientId).endOfTurn())
                    break;

                phases.put(clientId, CurrentPhase.BEST_HAND);
                break;

            case NEW_CARDS:
                String[] newCards = response.split(" ");

                for(String c: newCards) {
                    if(Integer.parseInt(c) < 0 || Integer.parseInt(c) > 5) {
                        errors.put(clientId, "Number must be between 0 and 5. Try Again");
                        return;
                    }
                }

                this.getGame(clientId).newCards(clientId, newCards);
                phases.put(clientId, CurrentPhase.SECOND_BETS);
                this.getGame(clientId).setSecondBets(true);
                break;

            default:
                break;
        }

    }

    public Game getGame(String clientId) {
        return games.get(playersInGames.get(clientId));
    }

    public void newPlayer(String clientId) {
        phases.put(clientId, CurrentPhase.NEW_GAME_Q);
    }

    private List<String> possibleBets() {
        List<String> responses = new ArrayList<>();
        responses.add("bet");
        responses.add("raise");
        responses.add("fold");
        responses.add("all-in");
        responses.add("call");

        return responses;
    }

}

