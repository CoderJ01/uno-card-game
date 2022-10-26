import java.util.Collections;

public class Main {

    private static Deck deck = new Deck();
    private static DiscardPile discardPile = new DiscardPile();

    public static void main(String[] args) {
        setUpDeck("B", "blue");
        setUpDeck("G", "green");
        setUpDeck("R", "red");
        setUpDeck("Y", "yellow");
        setUpDeck();

        deck.displayCards();
        deck.shuffleCards();
        System.out.println("*********************************");
        deck.displayCards();
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
            deck.addCard(new Card("Skip (" + color + ")", color, "S"));
            deck.addCard(new Card("Reverse (" + color + ")", color, "R"));
            deck.addCard(new Card("Draw 2 (" + color + ")", color, "D"));
            // increment
            n++;
        }
    }

    // set of deck of wild cards
    private static void setUpDeck() {
        for(int i = 0; i < 4; i++) {
            deck.addCard(new Card("Wild"));
            deck.addCard(new Card("Wild (+4)"));
        }
    }
}