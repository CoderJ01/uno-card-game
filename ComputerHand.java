import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/*
    NOTE: certain parameters are used only in this class, but are nevertheless
    passed to the methods in PlayerHand (superclass) to prevent compiler error
*/

public class ComputerHand extends PlayerHand {

    private Random rand = new Random(); // field

    // constructor
    public ComputerHand(String name) {
        super(name);
    }

    // display CPU's cards
    @Override
    public void displayCards() {
        System.out.println("\n" + this.playerName + " has " + returnCards().size() + " left");
    }

    // set CPU to pick a card 
    @Override
    public Card pickCard(Card topOfDiscardPile) {
        List<Card> cardMatches = new ArrayList<>();
        cardMatches = checkCardMatdches(cardMatches, topOfDiscardPile);
        Card cardToReturn = null;
        for(Card card : cardMatches) {
            // the CPU will attempt to get rid of the wild type cards first to lose more points
            if(card.getCardName().equals("W+4")) {
                cardToReturn = card;
            }
            else if (card.getCardName().equals("W")) {
                cardToReturn = card;
            }
            // if the CPU has no wild cards of neither type, it will select a random card to discard
            else {
                for(int i = 0; i < cardMatches.size(); i++) {
                    cardToReturn = cardMatches.get(rand.nextInt(cardMatches.size()));
                }
            }
        }
        delay();
        System.out.println(cardToReturn.getCardName());
        return cardToReturn;
    }

    // set CPU to discard card if it finds a match
    @Override
    public boolean pickOrNo(Card topOfDiscardPile) {
        List<Card> cardMatches = new ArrayList<>();
        cardMatches = checkCardMatdches(cardMatches, topOfDiscardPile);
        delay();
        if(cardMatches.size() == 0) {
            System.out.println("\n" + this.playerName + " will skip its turn");
            return false;
        }
        else {
            System.out.println("\n" + this.playerName + " will discard a card onto the discard pile");
            return true;
        }
    }

    // set CPU to draw cards
    @Override
    public void drawCards(String messageNumber) {
        String draw = "";
        while(!draw.equals("d")) {
            System.out.println("\n" + this.playerName + ", will draw " + messageNumber + " card(s)");
            draw = "d";
        }
        delay();
        System.out.println("\n" + this.playerName + " has drawn " + messageNumber + " card(s)");
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

        System.out.println("\n" + this.playerName + " will the color of the wild card");
       
        // the CPU will keep track of the number of occurences of each card color in its deck
        for(Card card : returnCards()) {
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
        System.out.println("\n" + this.playerName + " has selected " + color);
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

    // the CPU checks to see each card in its hand matches the card on the discard pile in any way
    private List<Card> checkCardMatdches(List<Card> cardMatch, Card topOfDiscardPile) {
        for(Card card : returnCards()) {
            // wildcards have no symbol and initially have no color, so the CPU will add such cards to its arsenal
            if(card.getCardName().equals("W") || card.getCardName().equals("W+4")) {
                cardMatch.add(card);
            }
            // check for match
            else if(card.getCardColor().equals(topOfDiscardPile.getCardColor()) || card.getCardSymbol().equals(topOfDiscardPile.getCardSymbol())) {
                cardMatch.add(card);
            }
        }
        return cardMatch;
    }
}