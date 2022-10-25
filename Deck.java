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
    public Card removeFromDeck() {
        Card topCard = deck.get(this.deck.size() - 1);
        this.deck.remove(topCard);
        return topCard;
    }

    // remove the bottom card to start the game
    // this will be the first card in the discard pile
    public Card firstDiscard() {
        Card bottomCard = deck.get(0);
        this.deck.remove(bottomCard);
        return bottomCard;
    }
}