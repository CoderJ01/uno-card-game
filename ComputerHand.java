import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public final class ComputerHand extends PlayerHand {

    private Random rand = new Random(); // field

    // constructor
    public ComputerHand(String name) {
        super(name);
    }

    // display the amount of cards the CPU has in its hand
    @Override
    public void displayCards() {
        int cardsInHand = getDeck().size();
        if(cardsInHand == 1) {
            System.out.println(getPlayerName() + " has " + cardsInHand + " card left");
        }
        else if(cardsInHand > 1) {
            System.out.println(getPlayerName() + " has " + cardsInHand + " cards left");
        }
    }

    // set CPU to pick a card 
    @Override
    public Card pickCard(Card topOfDiscardPile) {
        List <Card> cardMatches = checkCardMatches(topOfDiscardPile); // check for matches
        Card cardToReturn = null;
        for(Card card : cardMatches) {
            // the CPU will attempt to get rid of the wild type cards first to lose more points
            if(card.getCardName().equals(getWildPlus4())) {
                cardToReturn = card;
            }
            else if (card.getCardName().equals(getWildCard())) {
                cardToReturn = card;
            }
            // if the CPU has no wild cards, it will select a random card to discard
            else {
                cardToReturn = cardMatches.get(rand.nextInt(cardMatches.size()));
            }
        }
        delay();
        System.out.println("\n" + getPlayerName() + " put '" + cardToReturn.getCardName() + "' onto the discard pile");
        return cardToReturn;
    }

    // set CPU to discard card if it finds a match
    @Override
    public boolean pickOrNo(Card topOfDiscardPile) {
        List<Card> cardMatches = checkCardMatches(topOfDiscardPile);
        delay();
        if(cardMatches.size() == 0) {
            System.out.println("\n" + getPlayerName() + " will skip its turn");
            return false;
        }
        else {
            System.out.println("\n" + getPlayerName() + " will discard a card onto the discard pile");
            return true;
        }
    }

    // set CPU to draw cards
    @Override
    public void drawCards(String messageNumber) {
        if(messageNumber.equals("one")) {
            System.out.println("\n" + getPlayerName() + " has drawn " + messageNumber + " card");
        }
        else {
            System.out.println("\n" + getPlayerName() + " has drawn " + messageNumber + " cards");
        }
        delay();
    }

    // have the CPU select a color
    @Override
    public String enterColor() {
        // variables
        String color = "";
        List<List<Card>> colorChoice = new ArrayList<>(); // two dimensional array

        // arrays within array need to be initialized to avoid an IndexOutOfBoundsException
        for(int i = 0; i < getNumberOfColors(); i++) {
            colorChoice.add(new ArrayList<>());
        }

        System.out.println("\n" + getPlayerName() + " will set the color of the wild card");
       
        // the CPU will keep track of the number of occurences of each card color in its deck
        for(Card card : getDeck()) {
            for(int i = 0; i < getNumberOfColors(); i++) {
                if(card.getCardColor().equals(getColor(i))) {
                    colorChoice.get(i).add(card);
                }
            }
        }

        // store sizes (amount of occurences) in a new array
        int[] compareSizes = new int[getNumberOfColors()];
        for(int i = 0; i < compareSizes.length; i++) {
            compareSizes[i] = colorChoice.get(i).size();
        }

        // the CPU will pick the color that it has the most of 
        int greatest = 0;
        for(int i = 0; i < compareSizes.length; i++) {
            if(compareSizes[i] > greatest) {
                greatest = compareSizes[i];
            }
        }

        // the CPU will select said color
        for(int i = 0; i < compareSizes.length; i++) {
            if(greatest == compareSizes[i]) {
                color = getColor(i);
            }
        }

        delay();
        System.out.println("\n" + getPlayerName() + " has selected " + color);
        delay();
        return color;
    }

    // delay each output
    private void delay() {
        try {
            TimeUnit.SECONDS.sleep(1);
        }
        catch(InterruptedException e) {
            System.out.println("Error");
        }
    }

    // the CPU checks to see if each card in its hand matches the card on the discard pile in any way
    private List<Card> checkCardMatches(Card topOfDiscardPile) {
        List<Card> cardMatches = new ArrayList<>();
        for(Card card : getDeck()) {
            // wildcards have no symbol and initially have no color, so the CPU will add such cards to its arsenal
            if(card.getCardName().equals(getWildCard()) || card.getCardName().equals(getWildPlus4())) {
                cardMatches.add(card);
            }
            // check for match
            else if(card.getCardColor().equals(topOfDiscardPile.getCardColor()) || card.getCardSymbol() == topOfDiscardPile.getCardSymbol()) {
                cardMatches.add(card);
            }
        }
        return cardMatches;
    }

    // have CPU pass deck to the next player 
    @Override
    public void passDeck(boolean placeholder) {
        Iterator<Card> itr = getDeck().iterator();
        resetPlayerPass();
        while(itr.hasNext()) {
            Card card = itr.next();
            itr.remove(); 
            getPassedCards().add(card); 
        }
    }

    // have CPU switch cards with another player
    @Override
    public int pickPlayer(List<PlayerHand> players, int playerIndex) {
        delay();
        System.out.println("\n" + players.get(playerIndex).getPlayerName() + " will switch cards with another player");
        int pick = 0;
        int least = 109;
        for(int i = 0; i < players.size(); i++) {
            // the CPU will not switch cards with itself
            if(i != playerIndex) {
                // the CPU will switch cards with the player with the least amount of cards
                if(players.get(i).numberOfCards() < least) {
                    least = players.get(i).numberOfCards();
                    pick = i;
                }
            }
        }
        delay();
        System.out.println("\n" + players.get(playerIndex).getPlayerName() + " has chosen to switch cards with " + players.get(pick).getPlayerName());
        return pick;
    }
}