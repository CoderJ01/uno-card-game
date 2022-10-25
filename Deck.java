import java.util.ArrayList;
import java.util.List;

public class Deck {
    // variable
    private List<Card> deck = new ArrayList<>();

    // constructor 
    public Deck () {}

    // add a card to the deck
    public void addToDeck(Card card) {
        this.deck.add(card);
    }

    // remove card from deck
    public Card removeFromDeck(Card card) {
        this.deck.remove(card);
        return card;
    }
}