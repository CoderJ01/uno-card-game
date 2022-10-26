import java.util.List;

public class DiscardPile extends Deck {
    // contructor
    public DiscardPile() {}

    // display and return top card
    public Card showTopCard() {
        List<Card> cards = returnCards();
        Card topCard = cards.get(cards.size() - 1);
        System.out.println("Discard Pile");
        System.out.println("************");
        System.out.println(topCard.getCardName());
        System.out.println();
        return topCard;
    }
}