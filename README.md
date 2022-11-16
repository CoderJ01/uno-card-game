# Uno

## Purpose
[Uno](https://www.unorules.org/) is a classic card game. The object of the game is to get rid of all the cards in your hand. The game begins with 108 cards, most of which have a number and/or a specific color. The cards are shuffled. Afterwards, the bottom card is placed onto the discard pile. The first player to play must put a card on the discard pile. The card must have the same symbol or color as the card on top of the discard pile. Then, the second player must do the same for the new card on top of the discard pile. If a player places a '0' card, then all players must pass their hands to the next player.

There are five types of special cards - 'reverse', 'skip', 'draw 2', 'wild', and 'wild +4'. The former three are colored. The latter two are not. If a reverse card is placed, then the direction of the game, with regards to whose turn it is, is flipped. If a skip card is played, then the next player will lose his turn (i.e. player A plays skip, player B loses turn, player C consequently plays). If a draw 2 card is played, then the next player must draw 2 cards. Wild cards have no color. The player sets the color after placing a wild card. If a 'wild + 4' is placed, the next player must draw 4 cards.

Each card is worth a certain amount of points. The code in this respository is designed to reflect the following variation of the game: A zero card is worth 10 points. The other numbered cards are worth the number of the card (e.g. a five card is worth 5 points). Skip, reverse, and draw 2 are each worth 20 points. Each wild card is worth 50 points. Again, the object of the game is to play all of your cards. The player that discards all of his cards will have 0 points remaining. The goal is to lose points: the less points, the better.

When the game ends, players will be raked based on the amounts of points they have; not the amount of cards left in their hand.

Download the .jar file [here](https://github.com/CoderJ01/uno-card-game/blob/main/assets/jar/uno-card-game.jar) to run the application. For further assistance, read [this](https://github.com/CoderJ01/how-to-run-jar-files) reference repository.

### Table
| Symbol | Card    |
| ------ | ------- |
| B      | blue    |
| G      | green   |
| R      | red     |
| Y      | yellow  |
| W      | wild    |  
| W+4    | wild +4 |

## Built With
* Java

## Screenshot of Terminal
![Alt text](./assets/images/image-01_terminal.JPG?raw=true "Uno")

## Contribution
Made by CoderJ01