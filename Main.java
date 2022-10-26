public class Main {

    private static Deck deck = new Deck();
    private static DiscardPile discardPile = new DiscardPile();
    private static PlayerHand player = new PlayerHand("Joshua");

    public static void main(String[] args) {
        setUpDeck("B", "blue");
        setUpDeck("G", "green");
        setUpDeck("R", "red");
        setUpDeck("Y", "yellow");
        setUpDeck();

        deck.shuffleCards();
        discardPile.addCard(deck.removeBottomCard());
        discardPile.showTopCard();
        for(int i = 0; i < 7; i++) {
            player.addCard(deck.removeCard());
        }

        player.displayCards();
        if(player.pickOrNo() == true) {
            player.pickCard();
        }
        else {
            player.drawCards();
            player.addCard(deck.removeCard());
            player.addCard(deck.removeCard());
        }

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
            deck.addCard(new Card(letter + "_Skip", color, "S"));
            deck.addCard(new Card(letter + "_Reverse", color, "R"));
            deck.addCard(new Card(letter + "_Draw-2", color, "D"));
            // increment
            n++;
        }
    }

    // set of deck of wild cards
    private static void setUpDeck() {
        for(int i = 0; i < 4; i++) {
            deck.addCard(new Card("W"));
            deck.addCard(new Card("W_4"));
        }
    }
}