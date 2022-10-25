import java.util.ArrayList;
import java.util.List;

public class Deck {
    // variable
    private List<Card> deck = new ArrayList<>();

    // constructor 
    public Deck () {}

    // display cards in deck
    public void displayDeck() {
        for(Card card : this.deck) {
            System.out.print(card.getCardName() + " ");
        }
    }

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