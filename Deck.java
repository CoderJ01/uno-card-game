import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Deck {
    // variable
    private List<Card> deck = new ArrayList<>();

    // constructor 
    public Deck () {}

    // display cards in deck
    public void displayCards() {
        for(Card card : this.deck) {
            System.out.println(card.getCardName() + " ");
        }
    }

    // add a card to the deck
    public void addCard(Card card) {
        this.deck.add(card);
    }

    // remove card from deck
    public Card removeCard() {
        Card topCard = deck.get(this.deck.size() - 1);
        this.deck.remove(topCard);
        return topCard;
    }

    // remove the bottom card to start the game
    // this will be the first card in the discard pile
    public Card removeBottomCard() {
        Card bottomCard = deck.get(0);
        this.deck.remove(bottomCard);
        return bottomCard;
    }

    // get number of cards
    public int numberOfCards() {
        return this.deck.size();
    }

    // return the cards as a list for Collections
    public List<Card> returnCards() {
        return this.deck;
    }

    // shuffle cards
    public void shuffleCards() {
        Collections.shuffle(this.deck);
    }
}