import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
 
    private List<Card> deck = new ArrayList<>();
    private final String WILD = Main.getWildCard();
    private final String WILD_PLUS_4 = Main.getWildPlus4();
    private Object[] colorName = Main.getColor();

    // display cards in deck
    public void displayCards() {
        for(Card card : this.deck) {
            System.out.print(card.getCardName() + " ");
        }
    }

    // add a card to the deck
    public final void addCard(Card card) {
        this.deck.add(card);
    }

    // remove card from deck
    public final Card removeCard() {
        Card topCard = deck.get(this.deck.size() - 1);
        this.deck.remove(topCard);
        return topCard;
    }

    /* remove the bottom card before starting the game
       this will be the first card in the discard pile
    */
    public final Card removeBottomCard() {
        Card bottomCard = deck.get(0);
        this.deck.remove(bottomCard);
        return bottomCard;
    }

    // get number of cards
    public final int numberOfCards() {
        return this.deck.size();
    }

    // return the cards as a list
    public final List<Card> getDeck() {
        return this.deck;
    }

    // shuffle cards
    public final void shuffleCards() {
        Collections.shuffle(this.deck);
    }

    // create a new deck of cards
    public final void createNewDeck(List<Card> stackOfCards) {
        for(Card card : stackOfCards) {
            // reset wild cards
            if(card.getCardName().equals("B_" + WILD) || 
               card.getCardName().equals("G_" + WILD) || 
               card.getCardName().equals("R_" + WILD) ||
               card.getCardName().equals("Y_" + WILD)) 
            {
                card.setCardName(WILD);
                card.setCardColor("");
            }
            // reset wild +4 cards
            if(card.getCardName().equals("B_" + WILD_PLUS_4) || 
               card.getCardName().equals("G_" + WILD_PLUS_4) || 
               card.getCardName().equals("R_" + WILD_PLUS_4) ||
               card.getCardName().equals("Y_" + WILD_PLUS_4)) 
            {
                card.setCardName(WILD_PLUS_4);
                card.setCardColor("");
            }
            // add cards to new deck
            this.deck.add(card);
        }
    }

    // get wild card
    protected final String getWildCard() {
        return WILD;
    }

    // get wild +4 card
    protected final String getWildPlus4() {
        return WILD_PLUS_4;
    }

    protected final Object[] getColor() {
        return this.colorName;
    }
}