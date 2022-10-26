import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    // variables
    private static Deck deck = new Deck();
    private static DiscardPile discardPile = new DiscardPile();
    private static List<PlayerHand> players = new ArrayList<>();
    private static String wildCard = "W";
    private static String wildPlus4 = "W_4";
    private static String skip = "S";
    private static String reverse = "R";
    private static String draw2 = "D";
    private static boolean flip = false;

    public static void main(String[] args) {
        // set up deck of cards
        setUpDeck("B", "blue");
        setUpDeck("G", "green");
        setUpDeck("R", "red");
        setUpDeck("Y", "yellow");
        setUpDeck();

        // shuffle cards
        deck.shuffleCards(); 

        // add players
        players.add(new PlayerHand("Joshua"));
        players.add(new PlayerHand("Kraken"));
        players.add(new PlayerHand("Leonardo"));
        players.add(new PlayerHand("Michaelangelo"));

        // start game
        discardPile.addCard(deck.removeBottomCard());

        // show top of discard pile
        discardPile.showTopCard();

        // deal 7 cards to each player
        int q = 0;
        while(q < players.size()) {
            for(int i = 0; i < 7; i++) {
                players.get(q).addCard(deck.removeCard());
            }
        }

        int p = 0;
        while(true) {

            // display player cards
            players.get(p).displayCards();

            // prompt player to either discard matching card
            if(players.get(p).pickOrNo() == true) {
                Card pickedCard = players.get(p).pickCard();
            
                // variables
                String playerCardName = pickedCard.getCardName();
                String playerCardColor = pickedCard.getCardColor();
                String playerCardSymbol = pickedCard.getCardSymbol();
                String requiredColor = pickedCard.getCardColor();
                String requiredSymbol = discardPile.showTopCard().getCardSymbol();
                
                // check if player's card is a wild card
                if(playerCardName.equals(wildCard) || playerCardName.equals(wildPlus4)) {
                    discardPile.addCard(players.get(p).pickCard());

                    // set color of card
                    String color = players.get(p).enterColor();
                    pickedCard.setCardColor(color);
                    pickedCard.setCardName((Character.toString(color.charAt(0))).toUpperCase() + "_" + wildCard);

                    // if the card picked is wild
                    if(playerCardName.equals(wildPlus4)) {
                        pickedCard.setCardName((Character.toString(color.charAt(0))).toUpperCase() + "_" + wildPlus4);
                       // the next player draws four cards
                       int d = nextPlayerDraws(flip, p);
                       for(int i = 0; i < 4; i++) {
                        players.get(d).addCard(deck.removeCard());
                       }
                    }
                }
                // check if either the color or symbol of the player's card matches those of the top discard card
                else if(playerCardColor.equals(requiredColor) || playerCardSymbol.equals(requiredSymbol)) {
                    discardPile.addCard(players.get(p).pickCard());
                    // if player places a skip card
                    if(playerCardSymbol.equals(skip)) {
                        // skip the turn of the next player
                        p = skipTurn(flip, p);
                    }
                    // if player places a reverse card
                    if(playerCardSymbol.equals(reverse)) {
                        // change the direction of the game
                        if(flip == false) {
                            flip = true;
                        }
                        else {
                            flip = false;
                        }
                    }
                    // if player places a draw 2 card
                    if(playerCardSymbol.equals(draw2)) {
                        // next player draws two cards
                        int d = nextPlayerDraws(flip, p);
                        players.get(d).drawCards();
                        players.get(d).addCard(deck.removeCard());
                        players.get(d).addCard(deck.removeCard());
                        
                        // skip the turn of the next player
                        p = skipTurn(flip, p);
                    }
                }
                else {
                    System.out.println("Invalid placement");
                }
            }
            // force player to draw two cards
            else {
                players.get(p).drawCards();
                players.get(p).addCard(deck.removeCard());
                players.get(p).addCard(deck.removeCard());
            }

            // display 
            discardPile.showTopCard();
            players.get(p).displayCards();

            // end the game if a player has no cards left 
            if(players.get(p).returnCards().size() == 0) {
                break;
            }

            // direction of game
            if(flip == false) {
                // default direction
                if(p < (players.size() - 1)) {
                    p++;
                }
                else {
                    p = 0;
                }
            }
            else {
                if(p > 0) {
                    p--;
                }
                else {
                    p = (players.size() - 1);
                }
            }
        }

        displayPoints();
        rankPlayers(); 
    }

    // set up the deck of non-wild cards
    private static void setUpDeck(String letter, String color) {
        // card 0
        deck.addCard(new Card(letter + "0", color, "0"));
        
        int n = 0;
        while(n < 2) {
            // numbered cards
            for(Integer i = 1; i < 10; i++) {
                deck.addCard(new Card(letter + i, color, i.toString()));
            }
            // special cards
            deck.addCard(new Card(letter + "_Skip", color, skip));
            deck.addCard(new Card(letter + "_Reverse", color, reverse));
            deck.addCard(new Card(letter + "_Draw-2", color, draw2));
            // increment
            n++;
        }
    }

    // set of deck of wild cards
    private static void setUpDeck() {
        for(int i = 0; i < 4; i++) {
            deck.addCard(new Card(wildCard, ""));
            deck.addCard(new Card(wildPlus4, ""));
        }
    }

    // skip the turn of the next player 
    private static int skipTurn(boolean flip, int p) {
        if(flip == false) {
            if((p + 2) <= (players.size() - 1)) {
                p+=2;
            }
            else {
                p = ((p + 2) - players.size());
            }
        }
        else {
            if((p - 2) >= 0) {
                p-=2;
            }
            else {
                p = ((p - 2) + players.size());
            }
        }
        return p;
    }

    // determine the next player to draw the cards
    private static int nextPlayerDraws(boolean flip, int p) {
        if(flip == false) {
            if((p + 1) <= (players.size() - 1)) {
                p+=1;
            }
            else {
                p = ((p + 1) - players.size());
            }
        }
        else {
            if((p - 1) >= 0) {
                p-=1;
            }
            else {
                p = ((p - 1) + players.size());
            }
        }
        return p;
    }

    // display the amount of points for each player 
    private static void displayPoints() { 
        int i = 1;
        System.out.println("The player with the least amount of points wins the game");
        for(PlayerHand player : players) {
            System.out.println("Player " + i + " had " + player.tallyPoints() + " remaining");
            i++;
        }
    }

    // rank players
    private static void rankPlayers() {
        // use collections
        Collections.sort(players);

        // print out ranks
        System.out.println("\nRANKS");
        System.out.println("*****");
        int i = 1;
        for(PlayerHand player : players) {
            System.out.println(i + ". " + player.getPlayerName());
            i++;
        }
    }
}