import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DiscardPile extends Deck {

    public DiscardPile() {} // contructor

    // display and return top card
    public Card showTopCard() {
        List<Card> cards = returnCards();
        Card topCard = cards.get(cards.size() - 1);
        return topCard;
    }

    // remove cards from discard pile
    public List<Card> removeFromDiscardPile() {
        List<Card> discardPile = returnCards();
        List<Card> removedFromDiscardPile = new ArrayList<>();
        
        // Iterator
        Iterator<Card> itr = discardPile.iterator();
        while(itr.hasNext()) {
            Card card = itr.next();
            Card topCard = discardPile.get(discardPile.size() - 1);
            // remove all the cards except for the top card
            if(!card.equals(topCard)) {
                itr.remove();
                // add all removed cards to new deck
                removedFromDiscardPile.add(card);
            }
        }
        
        return removedFromDiscardPile;
    }
}