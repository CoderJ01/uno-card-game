import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public final class Main {

    // fields
    private static Deck deck = new Deck();
    private static DiscardPile discardPile = new DiscardPile();
    private static List<PlayerHand> players = new ArrayList<>();
    private static final String WILD_CARD = "W";
    private static final String WILD_PLUS_4 = "W+4";
    private static final char SKIP = 'S';
    private static final char REVERSE = 'R';
    private static final char DRAW_2 = 'D';
    private static boolean flip;
    private static Scanner input = new Scanner(System.in);
    private static Random rand = new Random();
    private static Object[] color = {"blue", "green", "red", "yellow"};

    public static void main(String[] args) {
        // set up deck of cards
        for(int i = 0; i < color.length; i++) {
            setUpDeck((((String)color[i]).toUpperCase()).charAt(0), (String)color[i]);
        }
        setUpDeck();
       
        deck.shuffleCards(); // shuffle cards

        String name = name(); // retrieve player's name
        
        // inquire if player wants to view rules
        if(viewOrNo(name)) {
            gamePlay(); // describe rules of game
        }

        int numberOfOpponents = opponents(); // retrieve number of opponents

        // add players
        players.add(new PlayerHand(name));
        
        String computerName = "CPU_";
        
        for(int i = 1; i <= numberOfOpponents; i++) {
            players.add(new ComputerHand(computerName + i));
        }

        discardPile.addCard(deck.removeBottomCard()); // place first card onto discard pile

        // deal 7 cards to each player
        int q = 0;
        while(q < players.size()) {
            for(int i = 0; i < 7; i++) {
                players.get(q).addCard(deck.removeCard());
            }
            q++;
        }

        players.get(0).addCard(new Card(WILD_CARD, ""));

        // start game
        int p = rand.nextInt(players.size()); // randomize which player gets to play first
        if(discardPile.showTopCard().getCardName().equals(WILD_CARD) || discardPile.showTopCard().getCardName().equals(WILD_PLUS_4)) {
            Card firstCard = discardPile.showTopCard();
            System.out.println("\nDiscard Pile");
            System.out.println("************");
            System.out.println(firstCard.getCardName());
            System.out.println();
            players.get(p).displayCards();
            
            // get rid of large 'white space' for CPU players
            if(!(players.get(p) instanceof ComputerHand)) {
                System.out.println("\n");
            }

            setWildCard(firstCard, p); // allow the first player to set the color of the wild card
        }

        while(true) {

            // show top of discard pile
            System.out.println("\nDiscard Pile");
            System.out.println("************");
            System.out.println(discardPile.showTopCard().getCardName());
            System.out.println();

            players.get(p).displayCards(); // display player cards

            // prompt player to either discard matching card
            if(players.get(p).pickOrNo(discardPile.showTopCard())) {
                Card pickedCard = players.get(p).pickCard(discardPile.showTopCard());
            
                // variables
                String playerCardName = pickedCard.getCardName();
                String playerCardColor = pickedCard.getCardColor();
                char playerCardSymbol = pickedCard.getCardSymbol();
                String requiredColor = discardPile.showTopCard().getCardColor();
                char requiredSymbol = discardPile.showTopCard().getCardSymbol();
                
                // check if player's card is a wild card
                if(playerCardName.equals(WILD_CARD) || playerCardName.equals(WILD_PLUS_4)) {
                    
                    // disable player from setting wild card if said card is player's last card
                    if(players.get(p).getDeck().size() >= 2) {
                        pickedCard = setWildCard(pickedCard, p); // allow player that discarded the card to set card
                    }
                    
                    discardPile.addCard(players.get(p).removeFromPlayerHand(pickedCard));
                    
                    // end the game if a player has no cards left 
                    if(players.get(p).getDeck().size() == 0) {
                        break;
                    }

                    // extra rules for wild +4 card
                    if(playerCardName.equals(WILD_PLUS_4)) {
                        int nextPlayer = nextSkipOrDraw(flip, p);
                        draw(nextPlayer, 4); // force the next player to draw four cards
                        p = nextSkipOrDraw(flip, p); // after drawing four cards, the next player loses his turn
                    }
                }
                // check if either the color or symbol of the player's card matches those of the top discard card
                else if(playerCardColor.equals(requiredColor) || playerCardSymbol == requiredSymbol) {
                    discardPile.addCard(players.get(p).removeFromPlayerHand(pickedCard));
                    
                    if(players.get(p).getDeck().size() == 0) {
                        break;
                    }

                    // if player places a skip card
                    if(playerCardSymbol == SKIP) {
                        p = nextSkipOrDraw(flip, p); // skip the turn of the next player
                    }
                    // if player places a reverse card
                    if(playerCardSymbol == REVERSE) {
                        // change the direction of the game
                        if(flip == false) {
                            flip = true;
                        }
                        else {
                            flip = false;
                        }
                    }
                    // if player places a draw 2 card
                    if(playerCardSymbol == DRAW_2) {
                        int nextPlayer = nextSkipOrDraw(flip, p); // next player draws two cards
                        draw(nextPlayer, 2); // force player to draw two cards
                        p = nextSkipOrDraw(flip, p); 
                    }
                    // if player places a 0 card
                    if(playerCardSymbol == '0') {
                        // all the players pass their hand to the next player
                        for(int i = 0; i < players.size(); i++) {
                            players.get(i).passDeck(true);
                        }

                        /* all the players receive a new hand from the last player
                           for loops separate to prevent error (missing cards)
                        */ 
                        for(int i = 0; i < players.size(); i++) {
                            int j = nextSkipOrDraw(!flip, i);
                            players.get(i).receiveDeck(players.get(j).getPassedCards());
                        }
                    }
                    // if player places a 7 card
                    if(playerCardSymbol == '7') {
                        int pickedPlayer = players.get(p).pickPlayer(players, p); // player picks another player to switch cards with
                        
                        // players pass cards to each other
                        players.get(pickedPlayer).passDeck(false);
                        players.get(p).passDeck(false);

                        // players receive cards from each other
                        players.get(pickedPlayer).receiveDeck(players.get(p).getPassedCards());
                        players.get(p).receiveDeck(players.get(pickedPlayer).getPassedCards());
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

            p = nextSkipOrDraw(flip, p); // increment or decrement
        }

        // state winning player
        System.out.println();
        for(PlayerHand player : players) {
            if(player.tallyPoints() == 0) {
                System.out.println(player.getPlayerName() + " has won the game!");
            }
        }

        displayPoints();
        rankPlayers(); 
    }

    // set up the deck of non-wild cards
    private static void setUpDeck(char letter, String color) {
        deck.addCard(new Card(letter + "0", color, '0')); // card 0
        
        int n = 0;
        while(n < 2) {
            // numbered cards
            for(int i = 1; i < 10; i++) {
                deck.addCard(new Card(letter + "" + i, color, (String.valueOf(i)).charAt(0)));
            }
            // special cards
            deck.addCard(new Card(letter + "_Skip", color, SKIP));
            deck.addCard(new Card(letter + "_Reverse", color, REVERSE));
            deck.addCard(new Card(letter + "_Draw-2", color, DRAW_2));
            // increment
            n++;
        }
    }

    // set of deck of wild cards
    private static void setUpDeck() {
        for(int i = 0; i < 4; i++) {
            deck.addCard(new Card(WILD_CARD, ""));
            deck.addCard(new Card(WILD_PLUS_4, ""));
        }
    }

    // switch to the next player, skip the turn of the next player, or determine the next player to draw the cards
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
        System.out.println("\nPOINTS");
        System.out.println("******");
        for(PlayerHand player : players) {
            System.out.println(player.getPlayerName() + " had " + player.tallyPoints() + " points remaining");
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
    private static void draw(int player, int defaultDrawAmount) {
        int deckSize = deck.numberOfCards();
        int max = 0;
        if(deckSize >= defaultDrawAmount) {
            max = defaultDrawAmount;
        }
        else if(deckSize < defaultDrawAmount && deckSize > 0) {
            max = deckSize;
        }
        else if(deckSize == 0) {
            deck.createNewDeck(discardPile.removeFromDiscardPile()); // the discard pile will be the new deck
            deck.shuffleCards();
            max = defaultDrawAmount;
        }

        // draw cards 
        String messageNumber = messageNumber(max); // retrieve message number
        players.get(player).drawCards(messageNumber);
        for(int i = 0; i < max; i++) {
            players.get(player).addCard(deck.removeCard());
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
        if(card.getCardName().equals(WILD_CARD)) {
            wildType = WILD_CARD;
        }
        else {
            wildType = WILD_PLUS_4;
        }
        // set color of card
        String color = players.get(player).enterColor();
        card.setCardColor(color);
        card.setCardName((Character.toString(color.charAt(0))).toUpperCase() + "_" + wildType);

        return card;
    }

    // have player enter name
    private static String name() {
        System.out.print("Enter your name: ");
        String name = input.next();
        return name;
    }

    // getters (for class Deck and PlayerHand)
    public static String getWildCard() {
        return WILD_CARD;
    }

    public static String getWildPlus4() {
        return WILD_PLUS_4;
    }

    public static Object[] getColor() {
        return color;
    }

    // allow the player to enter the number of opponents to play against
    private static int opponents() {
        int numberOfOpponents = 0;
        boolean error = false;
        int min = 2, max = 9;

        while(true) {
            // check for appropiate data type
            do {
                error = false;
                try {
                    System.out.print("\nHow many opponents would you like to play against? Enter a number from " + min + " to " + max + ": ");
                    numberOfOpponents = input.nextInt();
                }
                catch(InputMismatchException e) {
                    error = true;
                    input.nextLine();
                }
            } while(error);

            // check if integer is within the specified range
            if(numberOfOpponents >= min && numberOfOpponents <= max) {
                break;
            }
        }
        return numberOfOpponents;
    }

    // describe the rules if the game
    private static void gamePlay() {
        System.out.println("\nRULES");
        System.out.println("*****");
        System.out.println("The first player to discard all the cards in said player's hand wins the game. When the game is finished,");
        System.out.println("players are ranked by the number of points they have.");
        System.out.println("\nWild and wild-plus-4 cards are worth 50 points. Reverse, skip, and draw-2 cards are worth 20 points;");
        System.out.println("cards numbered '0', 10 points; the remaining numbered cards; their face value.");
        System.out.println("\nThe less points a player has, the higher the player will be ranked. The winner will have 0 points due");
        System.out.println("to having no cards.");
        System.out.println("\nB = blue, G = green, R = red, and Y = yellow; " + WILD_CARD + " = wild, " + WILD_PLUS_4 + " = wild +4");
        System.out.println("\nThe discard pile shows only its top card. Unless any wild type card is placed, then players must select a card");
        System.out.println("that matches either the color or right-side symbol of the top card. Examples of such symbols are '3' and '_Skip'.");
        System.out.println("\nThe player that gets to play first will be chosen randomly.");
        System.out.println("\nWhen a player discards a skip card, the next player loses his/her turn. When a player discards a reverse card,");
        System.out.println("the direction of the game changes. When a player discards a draw-2 card, the next player must draw two cards");
        System.out.println("and loses his/her turn. When a player discards a '0' card, then all players must pass their hands to the next player.");
        System.out.println("When a player discards a '7' card, then the player must select another player to switch hands with.");

        // confirm that player understands the rules
        String confirmation = "";
        while(true) {
            System.out.print("\nEnter 'y' to confirm that you understand the rules: ");
            confirmation = input.next();
            if(confirmation.equals("y")) {
                break;
            }
        }
    }

    // ask if player would like to see the rules
    private static boolean viewOrNo(String name) {
        String view = "";
        System.out.print("\nHello " + name + ", would you like to view the rules of the game? If you would, enter 'y'. If not, enter any other key: ");
        view = input.next();
        if(view.equals("y")) {
            return true;
        }
        else {
            return false;
        }
    }
}