import java.util.List;

public class DiscardPile extends Deck {

    public DiscardPile() {} // contructor

    // display and return top card
    public Card showTopCard() {
        List<Card> cards = returnCards();
        Card topCard = cards.get(cards.size() - 1);
        return topCard;
    }
}