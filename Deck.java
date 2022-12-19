import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
 
    private List<Card> deck = new ArrayList<>();
    private final String WILD = Main.getWildCard();
    private final String WILD_PLUS_4 = Main.getWildPlus4();
    private String[] colorsAssortment = Main.getColor();

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
            for(int i = 0; i < this.colorsAssortment.length; i++) {
                // reset wild cards
                if(card.getCardName().equals(resetColor(i) + WILD)) {
                    card.setCardName(WILD);
                    card.setCardColor("");
                }
                // reset wild +4 cards
                if(card.getCardName().equals(resetColor(i) + WILD_PLUS_4)) {
                    card.setCardName(WILD_PLUS_4);
                    card.setCardColor("");
                }
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

    // get assortment of colors
    protected final String[] getAssortment() {
        return this.colorsAssortment;
    }

    // get a color in the assortment
    protected final String getColor(int index) {
        return this.colorsAssortment[index];
    }

    // get the number of colors in the assortment
    protected final int getNumberOfColors() {
        return this.colorsAssortment.length;
    }

    // returns color symbol to be checked
    private String resetColor(int index) {
        return (this.colorsAssortment[index].toUpperCase()).charAt(0) + "_";
    }
}