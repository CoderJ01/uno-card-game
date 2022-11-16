import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ComputerHand extends PlayerHand {

    // fields
    private Random rand = new Random(); 
    private String computerName = getPlayerName();
    private String wildCard = Main.getWildCard();
    private String wildPlus4 = Main.getWildPlus4();
    private List<Card> computerDeck = returnCards();

    // constructor
    public ComputerHand(String name) {
        super(name);
    }

    // display the amount of cards the CPU has in its hand
    @Override
    public void displayCards() {
        int cardsInHand = this.computerDeck.size();
        if(cardsInHand == 1) {
            System.out.println(this.computerName + " has " + cardsInHand + " card left");
        }
        else if(cardsInHand > 1) {
            System.out.println(this.computerName + " has " + cardsInHand + " cards left");
        }
    }

    // set CPU to pick a card 
    @Override
    public Card pickCard(Card topOfDiscardPile) {
        List <Card> cardMatches = checkCardMatches(topOfDiscardPile); // check for matches
        Card cardToReturn = null;
        for(Card card : cardMatches) {
            // the CPU will attempt to get rid of the wild type cards first to lose more points
            if(card.getCardName().equals(this.wildPlus4)) {
                cardToReturn = card;
            }
            else if (card.getCardName().equals(this.wildCard)) {
                cardToReturn = card;
            }
            // if the CPU has no wild cards, it will select a random card to discard
            else {
                cardToReturn = cardMatches.get(rand.nextInt(cardMatches.size()));
            }
        }
        delay();
        System.out.println("\n" + this.computerName + " put '" + cardToReturn.getCardName() + "' onto the discard pile");
        return cardToReturn;
    }

    // set CPU to discard card if it finds a match
    @Override
    public boolean pickOrNo(Card topOfDiscardPile) {
        List<Card> cardMatches = checkCardMatches(topOfDiscardPile);
        delay();
        if(cardMatches.size() == 0) {
            System.out.println("\n" + this.computerName + " will skip its turn");
            return false;
        }
        else {
            System.out.println("\n" + this.computerName + " will discard a card onto the discard pile");
            return true;
        }
    }

    // set CPU to draw cards
    @Override
    public void drawCards(String messageNumber) {
        if(messageNumber.equals("one")) {
            System.out.println("\n" + this.computerName + " has drawn " + messageNumber + " card");
        }
        else {
            System.out.println("\n" + this.computerName + " has drawn " + messageNumber + " cards");
        }
        delay();
    }

    // have the CPU select a color
    @Override
    public String enterColor() {
        String color = "";
        // variables
        List<Card> blue = new ArrayList<>();
        List<Card> green = new ArrayList<>();
        List<Card> red = new ArrayList<>();
        List<Card> yellow = new ArrayList<>();

        System.out.println("\n" + this.computerName + " will set the color of the wild card");
       
        // the CPU will keep track of the number of occurences of each card color in its deck
        for(Card card : this.computerDeck) {
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

        // the CPU will finally select said color
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
        System.out.println("\n" + this.computerName + " has selected " + color);
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
        for(Card card : this.computerDeck) {
            // wildcards have no symbol and initially have no color, so the CPU will add such cards to its arsenal
            if(card.getCardName().equals(this.wildCard) || card.getCardName().equals(this.wildPlus4)) {
                cardMatch.add(card);
            }
            // check for match
            else if(card.getCardColor().equals(topOfDiscardPile.getCardColor()) || card.getCardSymbol() == topOfDiscardPile.getCardSymbol()) {
                cardMatch.add(card);
            }
        }
        return cardMatch;
    }
}