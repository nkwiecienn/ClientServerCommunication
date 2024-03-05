package main.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Game {
    Deck deck = new Deck();
    Map<String, Player> players = new HashMap<>();
    Map<String, Integer> bets = new HashMap<>();
    Map<String, String> betNames = new HashMap<>();
    boolean secondBets = false;
    List<String> turns = new ArrayList<>();
    int turn = 0;
    int pot = 100; //trzyma ile w sumie pieniędzy jest do zdobycia
    int expectedNumberOfPlayers;
    Boolean cardsDealt = false;
    int currentBet = 0; //trzyma stawkę, do której wszyscy zrównują
    String beforeClientMessage = "";
    ArrayList<String> draws = new ArrayList<>();

    public Game() {
        deck.shuffle();
    }

    public void bet(String clientId, String commend, int amount) { //amount to wartość do której wszyscy zrównują
        beforeClientMessage += clientId + " " + commend;

        if(amount != 0)
            beforeClientMessage += " " + amount;

        beforeClientMessage += "\n";

        switch (commend) {
            case "fold": //rzucenie kart
                players.get(clientId).setActive(false);
                break;
            case "bet", "raise": //pierwszy zakład
                bets.put(clientId, amount);
                currentBet = amount;
                break;
            case "call": //dorównanie poprzedniemu zakładowi
                bets.put(clientId, currentBet);
                break;
            case "all-in":
                bets.put(clientId, players.get(clientId).getMoney());
                currentBet = bets.get(clientId);
                players.get(clientId).setAllIn(true);
                players.get(clientId).setActive(false);
                break;
            default:
                break;
        }

        betNames.put(clientId, commend);

        if(players.size()-1 == turn)
            turn = 0;
        else
            turn++;

        while(!players.get(turns.get(turn)).isActive()) {
            turn++;

            if(turn == players.size())
                turn = 0;
        }

        if(this.endOfTurn())
            turn = 0;
    }

    public void addNewPlayer(String clientId) {
        Player player = new Player(clientId, players.size());
        players.put(clientId, player);
        bets.put(clientId, 0);
        betNames.put(clientId, "");
        turns.add(clientId);
    }

    public int getNumberOfPlayers() {
        return players.size();
    }

    public void dealCards() {
        if(Boolean.TRUE.equals(cardsDealt))
            return;


        for(Player p : players.values()) {
            ArrayList<Card> cards = new ArrayList<>();

            int startingIt = deck.getIt();

            for(int i = startingIt; i < startingIt + 5; i++) {
                cards.add(deck.getSortedDeck().get(i));

                deck.next();
            }

            p.setCards(cards);
        }

        cardsDealt = true;
    }

    public void newCards(String clientId, String[] cards) {

        if(cards[0].equals("0"))
            return;

        ArrayList<Card> hand = players.get(clientId).getCards();
        for(String c : cards) {

            hand.set(Integer.parseInt(c)-1, deck.getSortedDeck().get(deck.getIt()));
            deck.next();
        }
    }

    public List<String> winningHand() {
        Hand hand = new Hand();

        for(Integer b : bets.values())
            pot += b;

        bets.replaceAll((b, v) -> 0);

        String bestPlayer = turns.get(0);

        for(Player p : players.values()) {
            if((p.isActive() || p.isAllIn()) && hand.betterHand(p.getCards(), players.get(bestPlayer).getCards()) == 1)
                bestPlayer = p.playerId;
        }

        for(Player p : players.values()) {
            if((p.isActive() || p.isAllIn()) && hand.betterHand(p.getCards(), players.get(bestPlayer).getCards()) == 0 && (!draws.contains(p.playerId)))
                draws.add(p.playerId);
        }

        return draws;
    }

    public String listHands() {
        String message = "";
        Hand hand = new Hand();

        for(Player p : players.values()) {
            message += p.playerId + " had " + hand.whichHand(p.getCards()).toString();
            if(!p.isActive() && !p.isAllIn())
                message += " (folded)";

            message += "\n";
        }

        return message;
    }

    public boolean endOfTurn() {

        if(this.endOfGame())
            return true;

        for(Map.Entry<String, Integer> b : bets.entrySet()) {
            if(players.get(b.getKey()).isActive() && bets.get(b.getKey()) != currentBet)
                return false;
        }

        return true;
    }

    public boolean endOfGame() {
        int activePlayers = 0;

        for(Player p: players.values()) {
            if(p.isActive())
                activePlayers++;
        }

        return activePlayers < 2;
    }

    public String getStringCardsFromPlayer(String clientId) {
        return players.get(clientId).toString();
    }

    public String getCurrentTurn() {
        return turns.get(turn);
    }

    public int getPlayerTurn(String clientId) {
        return turns.indexOf(clientId) + 1;
    }


}
