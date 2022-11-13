import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
 
    private List<Card> deck = new ArrayList<>(); // field
    private String wild = Main.getWildCard();
    private String wildPlus4 = Main.getWildPlus4();

    public Deck () {} // constructor 

    // display cards in deck
    public void displayCards() {
        for(Card card : this.deck) {
            System.out.print(card.getCardName() + " ");
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

    /* remove the bottom card before starting the game
       this will be the first card in the discard pile
    */
    public Card removeBottomCard() {
        Card bottomCard = deck.get(0);
        this.deck.remove(bottomCard);
        return bottomCard;
    }

    // get number of cards
    public int numberOfCards() {
        return this.deck.size();
    }

    // return the cards as a list
    public List<Card> returnCards() {
        return this.deck;
    }

    // shuffle cards
    public void shuffleCards() {
        Collections.shuffle(this.deck);
    }

    // create a new deck of cards
    public void createNewDeck(List<Card> stackOfCards) {
        for(Card card : stackOfCards) {
            // reset wild cards
            if(card.getCardName().equals("B_" + wild) || 
               card.getCardName().equals("G_" + wild) || 
               card.getCardName().equals("R_" + wild) ||
               card.getCardName().equals("Y_" + wild)) 
            {
                card.setCardName(wild);
                card.setCardColor("");
            }
            // reset wild +4 cards
            if(card.getCardName().equals("B_" + wildPlus4) || 
               card.getCardName().equals("G_" + wildPlus4) || 
               card.getCardName().equals("R_" + wildPlus4) ||
               card.getCardName().equals("Y_" + wildPlus4)) 
            {
                card.setCardName(wildPlus4);
                card.setCardColor("");
            }
            // add cards to new deck
            this.deck.add(card);
        }
    }
}