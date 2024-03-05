# Five-Card Draw Poker Game

This program is designed for playing five-card draw poker.

Implemented rules of the game:

- At the beginning of the game, each player is dealt 5 cards.
- Then the first betting phase begins.
- There is no fixed initial bet.
- The phase ends when all players have placed an equal amount.
- Then each player can exchange up to 4 cards.
- The second betting phase begins.
- Ties are resolved using the best card for the hand, e.g., for two pairs, the pair with the higher card wins; for a full house, the better three of a kind wins.
- If a tie still cannot be resolved, the pot is split among them.

Communication with the server:

- Before each move, the server informs about possible actions.
- The game starts with the message "new"/"existing" to create/join a game respectively.
- Then the number of players (from 2 to 4) or the game ID (where the game ID is GameN where N is the host number of the game) should be provided.
- The server continues to inform about the progress of the game and possible commands.
- It is important to remember that during the betting phase, the stake TO which we raise must be provided.

Error handling:

- In case of entering an incorrect value, the server informs about it and allows entering the value again (repeatedly until correct).

How to run the program:

- First, start the server by compiling the Server.java file. It is important to do this before running the client.
- Then players can join by compiling the Client.java file.
