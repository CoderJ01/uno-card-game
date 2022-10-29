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

    // have the CPU select a color
    @Override
    public String enterColor() {
        String color = "";
        // variables
        List<Card> blue = new ArrayList<>();
        List<Card> green = new ArrayList<>();
        List<Card> red = new ArrayList<>();
        List<Card> yellow = new ArrayList<>();

        System.out.print(this.playerName + ", set the color of the wild card to 'blue', 'green', 'red', or 'yellow': ");

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
        return color;
    }
}