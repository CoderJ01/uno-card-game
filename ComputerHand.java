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
        int cardsInHand = this.deck.size();
        if(cardsInHand == 1) {
            System.out.println(PLAYER_NAME + " has " + cardsInHand + " card left");
        }
        else if(cardsInHand > 1) {
            System.out.println(PLAYER_NAME + " has " + cardsInHand + " cards left");
        }
    }

    // set CPU to pick a card 
    @Override
    public Card pickCard(Card topOfDiscardPile) {
        List <Card> cardMatches = checkCardMatches(topOfDiscardPile); // check for matches
        Card cardToReturn = null;
        for(Card card : cardMatches) {
            // the CPU will attempt to get rid of the wild type cards first to lose more points
            if(card.getCardName().equals(WILD_PLUS_4)) {
                cardToReturn = card;
            }
            else if (card.getCardName().equals(WILD)) {
                cardToReturn = card;
            }
            // if the CPU has no wild cards, it will select a random card to discard
            else {
                cardToReturn = cardMatches.get(rand.nextInt(cardMatches.size()));
            }
        }
        delay();
        System.out.println("\n" + PLAYER_NAME + " put '" + cardToReturn.getCardName() + "' onto the discard pile");
        return cardToReturn;
    }

    // set CPU to discard card if it finds a match
    @Override
    public boolean pickOrNo(Card topOfDiscardPile) {
        List<Card> cardMatches = checkCardMatches(topOfDiscardPile);
        delay();
        if(cardMatches.size() == 0) {
            System.out.println("\n" + PLAYER_NAME + " will skip its turn");
            return false;
        }
        else {
            System.out.println("\n" + PLAYER_NAME + " will discard a card onto the discard pile");
            return true;
        }
    }

    // set CPU to draw cards
    @Override
    public void drawCards(String messageNumber) {
        if(messageNumber.equals("one")) {
            System.out.println("\n" + PLAYER_NAME + " has drawn " + messageNumber + " card");
        }
        else {
            System.out.println("\n" + PLAYER_NAME + " has drawn " + messageNumber + " cards");
        }
        delay();
    }

    // have the CPU select a color
    @Override
    public String enterColor() {
        // variables
        String color = "";
        List<Card> blue = new ArrayList<>();
        List<Card> green = new ArrayList<>();
        List<Card> red = new ArrayList<>();
        List<Card> yellow = new ArrayList<>();

        System.out.println("\n" + PLAYER_NAME + " will set the color of the wild card");
       
        // the CPU will keep track of the number of occurences of each card color in its deck
        for(Card card : this.deck) {
            if(card.getCardColor().equals("blue")) {
                blue.add(card);
            }
            else if(card.getCardColor().equals("green")) {
                green.add(card);
            }
            else if(card.getCardColor().equals("red")) {
                red.add(card);
            }
            else if(card.getCardColor().equals("yellow")) {
                yellow.add(card);
            }
        }

        // store sizes (amount of occurences) in a new array
        int[] compareSizes = new int[4];
        compareSizes[0] = blue.size();
        compareSizes[1] = green.size();
        compareSizes[2] = red.size();
        compareSizes[3] = yellow.size();

        // the CPU will pick the color that it has the most of 
        int greatest = 0;
        for(int i = 0; i < compareSizes.length; i++) {
            if(compareSizes[i] > greatest) {
                greatest = compareSizes[i];
            }
        }

        // the CPU will select said color
        if(greatest == compareSizes[0]) {
            color = "blue";
        }
        else if(greatest == compareSizes[1]) {
            color = "green";
        }
        else if(greatest == compareSizes[2]) {
            color = "red";
        }
        else if(greatest == compareSizes[3]) {
            color = "yellow";
        }

        delay();
        System.out.println("\n" + PLAYER_NAME + " has selected " + color);
        delay();
        return color;
    }

    // delay each output
    private void delay() {
        try {
            TimeUnit.SECONDS.sleep(2);
        }
        catch(InterruptedException e) {
            System.out.println("Error");
        }
    }

    // the CPU checks to see if each card in its hand matches the card on the discard pile in any way
    private List<Card> checkCardMatches(Card topOfDiscardPile) {
        List<Card> cardMatch = new ArrayList<>();
        for(Card card : this.deck) {
            // wildcards have no symbol and initially have no color, so the CPU will add such cards to its arsenal
            if(card.getCardName().equals(WILD) || card.getCardName().equals(WILD_PLUS_4)) {
                cardMatch.add(card);
            }
            // check for match
            else if(card.getCardColor().equals(topOfDiscardPile.getCardColor()) || card.getCardSymbol() == topOfDiscardPile.getCardSymbol()) {
                cardMatch.add(card);
            }
        }
        return cardMatch;
    }

    // have CPU pass deck to the next player 
    @Override
    public void passDeck() {
        Iterator<Card> itr = this.deck.iterator();
        resetPlayerPass();
        while(itr.hasNext()) {
            Card card = itr.next();
            itr.remove(); 
            this.playerPass.add(card); 
        }
    }
}