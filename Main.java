import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    // fields
    private static Deck deck = new Deck();
    private static DiscardPile discardPile = new DiscardPile();
    private static List<PlayerHand> players = new ArrayList<>();
    private static String wildCard = "W";
    private static String wildPlus4 = "W+4";
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

        deck.shuffleCards(); // shuffle cards

        // add players
        players.add(new PlayerHand("Joshua"));
        players.add(new ComputerHand("CPU_1"));
        players.add(new ComputerHand("CPU_2"));
        players.add(new ComputerHand("CPU_3"));

        discardPile.addCard(deck.removeBottomCard()); // place first card onto discard pile

        // deal 7 cards to each player
        int q = 0;
        while(q < players.size()) {
            for(int i = 0; i < 7; i++) {
                players.get(q).addCard(deck.removeCard());
            }
            q++;
        }

        if(discardPile.showTopCard().getCardName().equals(wildCard) || discardPile.showTopCard().getCardName().equals(wildPlus4)) {
            Card firstCard = discardPile.showTopCard();
            System.out.println("\nDiscard Pile");
            System.out.println("************");
            System.out.println(firstCard.getCardName());
            System.out.println();
            players.get(0).displayCards();
            System.out.println("\n");
            setWildCard(firstCard, 0); // allow the first player to set the color of the wild card
        }

        // start game
        int p = 0;
        while(true) {

            // show top of discard pile
            System.out.println("\nDiscard Pile");
            System.out.println("************");
            System.out.println(discardPile.showTopCard().getCardName());
            System.out.println();

            players.get(p).displayCards(); // display player cards

            // prompt player to either discard matching card
            if(players.get(p).pickOrNo(discardPile.showTopCard()) == true) {
                Card pickedCard = players.get(p).pickCard(discardPile.showTopCard());
            
                // variables
                String playerCardName = pickedCard.getCardName();
                String playerCardColor = pickedCard.getCardColor();
                String playerCardSymbol = pickedCard.getCardSymbol();
                String requiredColor = discardPile.showTopCard().getCardColor();
                String requiredSymbol = discardPile.showTopCard().getCardSymbol();
                
                // check if player's card is a wild card
                if(playerCardName.equals(wildCard) || playerCardName.equals(wildPlus4)) {
                    pickedCard = setWildCard(pickedCard, p); // allow player that discarded the card to set card
                    discardPile.addCard(players.get(p).removeFromPlayerHand(pickedCard));

                    // extra rules for wild +4 card
                    if(playerCardName.equals(wildPlus4)) {
                        int d = nextSkipOrDraw(flip, p);
                        draw(d, 4); // force the next player to draw four cards
                        p = nextSkipOrDraw(flip, p);   // after drawing four cards, the next player loses his turn
                    }
                }
                // check if either the color or symbol of the player's card matches those of the top discard card
                else if(playerCardColor.equals(requiredColor) || playerCardSymbol.equals(requiredSymbol)) {
                    discardPile.addCard(players.get(p).removeFromPlayerHand(pickedCard));

                    // if player places a skip card
                    if(playerCardSymbol.equals(skip)) {
                        p = nextSkipOrDraw(flip, p); // skip the turn of the next player
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
                        int d = nextSkipOrDraw(flip, p); // next player draws two cards
                        draw(d, 2); // force player to draw two cards
                        p = nextSkipOrDraw(flip, p); 
                    }
                }
                else {
                    System.out.print("Invalid placement. ");
                    draw(p, 2); 
                }
            }
            // force player to draw two cards
            else {
                draw(p, 2);
            }

            // end the game if a player has no cards left 
            if(players.get(p).returnCards().size() == 0) {
                break;
            }

            p = nextSkipOrDraw(flip, p); // increment or decrement
        }

        displayPoints();
        rankPlayers(); 
    }

    // set up the deck of non-wild cards
    private static void setUpDeck(String letter, String color) {
        deck.addCard(new Card(letter + "0", color, "0")); // card 0
        
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

    // skip the turn of the next player or determine the next player to draw the cards
    private static int nextSkipOrDraw(boolean flip, int p) {
        int highestIndex = players.size() - 1;
        if(flip == false) {
            // default direction
            if(p < highestIndex) {
                p++;
            }
            else if(p == highestIndex) {
                p = 0;
            }
        }
        else {
            if(p > 0) {
                p--;
            }
            else if(p == 0) {
                p = highestIndex;
            }
        }
        return p;
    }
   
    // display the amount of points for each player 
    private static void displayPoints() { 
        int i = 1;
        System.out.println("\nThe player with the least amount of points wins the game");
        for(PlayerHand player : players) {
            System.out.println("Player " + i + " (" + player.getPlayerName() + ") had " + player.tallyPoints() + " points remaining");
            i++;
        }
    }

    // rank players
    private static void rankPlayers() {
        Collections.sort(players); // use collections

        // print out ranks
        System.out.println("\nRANKS");
        System.out.println("*****");
        int i = 1;
        for(PlayerHand player : players) {
            System.out.println(i + ". " + player.getPlayerName());
            i++;
        }
    }

    // when the amount of cards in the deck is low, alter the amount drawn
    private static void draw(int next, int defaultDrawAmount) {
        int deckSize = deck.numberOfCards();
        int max = 0;
        if(deckSize >= defaultDrawAmount) {
            max = defaultDrawAmount;
        }
        else {
            max = deckSize;
        }

        // draw cards only if there are cards left to draw
        if(max >= 1) {
            String messageNumber = messageNumber(max); // retrieve message number
            players.get(next).drawCards(messageNumber);
            for(int i = 0; i < max; i++) {
                players.get(next).addCard(deck.removeCard());
            }
        }
    }

    // print out number for prompt
    private static String messageNumber(int max) {
        String number = "";
        if(max == 4) {
            number = "four";
        }
        else if(max == 3) {
            number = "three";
        }
        else if(max == 2) {
            number = "two";
        }
        else if(max == 1) {
            number = "one";
        }
        return number;
    }

    // set the wild card
    private static Card setWildCard(Card card, int player) {
        String wildType = "";
        if(card.getCardName().equals(wildCard)) {
            wildType = wildCard;
        }
        else {
            wildType = wildPlus4;
        }
        // set color of card
        String color = players.get(player).enterColor();
        card.setCardColor(color);
        card.setCardName((Character.toString(color.charAt(0))).toUpperCase() + "_" + wildType);

        return card;
    }
}