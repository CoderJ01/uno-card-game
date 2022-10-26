public class Main {

    // variables
    private static Deck deck = new Deck();
    private static DiscardPile discardPile = new DiscardPile();
    private static PlayerHand player = new PlayerHand("Joshua");
    private static String wildCard = "W";
    private static String wildPlus4 = "W_4";
    private static String skip = "S";
    private static String reverse = "R";
    private static String draw2 = "D";

    public static void main(String[] args) {
        // set up deck of cards
        setUpDeck("B", "blue");
        setUpDeck("G", "green");
        setUpDeck("R", "red");
        setUpDeck("Y", "yellow");
        setUpDeck();

        // shuffle cards
        deck.shuffleCards(); 

        // start game
        discardPile.addCard(deck.removeBottomCard());

        // show top of discard pile
        discardPile.showTopCard();

        // deal 7 cards to player
        for(int i = 0; i < 7; i++) {
            player.addCard(deck.removeCard());
        }

        // display player cards
        player.displayCards();

        // prompt player to either discard matching card
        if(player.pickOrNo() == true) {
            player.pickCard();
           
            // variables
            String playerCardName = player.pickCard().getCardName();
            String playerCardColor = player.pickCard().getCardColor();
            String playerCardSymbol = player.pickCard().getCardSymbol();
            String requiredColor = discardPile.showTopCard().getCardColor();
            String requiredSymbol = discardPile.showTopCard().getCardSymbol();
            
            // check if player's card is a wild card
            if(playerCardName.equals(wildCard) || playerCardName.equals(wildPlus4)) {
                discardPile.addCard(player.pickCard());
            }
            // check if either the color or symbol of the player's card matches those of the top discard card
            else if(playerCardColor.equals(requiredColor) || playerCardSymbol.equals(requiredSymbol)) {
                discardPile.addCard(player.pickCard());
                // if player places a skip card
                if(playerCardSymbol.equals(skip)) {

                }
                // if player places a reverse card
                if(playerCardSymbol.equals(reverse)) {
                    
                }
                // if player places a draw 2 card
                if(playerCardSymbol.equals(draw2)) {
                    
                }
            }
            else {
                System.out.println("Invalid placement");
            }
        }
        // force player to draw two cards
        else {
            player.drawCards();
            player.addCard(deck.removeCard());
            player.addCard(deck.removeCard());
        }

        // display 
        discardPile.showTopCard();
        player.displayCards();
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
            deck.addCard(new Card(wildCard));
            deck.addCard(new Card(wildPlus4));
        }
    }
}