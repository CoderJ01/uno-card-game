import java.util.ArrayList;
import java.util.List;

public class ComputerHand extends PlayerHand {

    // constructor
    public ComputerHand(String name) {
        super(name);
    }

    // set CPU to discard card if it finds a match
    /*
       NOTE: parameter 'card' is used only in this class, but is nevertheless
       passed to pickOrNo in PlayerHand (superclass) to prevent compiler error
    */
    @Override
    public boolean pickOrNo(Card topOfDiscardPile) {
        List<Card> cardMatches = new ArrayList<>();
        for(Card card : returnCards()) {
            if(card.getCardName().equals("W") || card.getCardName().equals("W+4")) {
                cardMatches.add(card);
            }
            else if(card.getCardColor().equals(topOfDiscardPile.getCardColor()) || card.getCardSymbol().equals(topOfDiscardPile.getCardSymbol())) {
                cardMatches.add(card);
            }
        }
        if(cardMatches.size() == 0) {
            System.out.println("\n" + this.playerName + " will skip its turn");
            return false;
        }
        else {
            System.out.println("\n" + this.playerName + " will discard a card onto the discard pile");
            return true;
        }
    }
}