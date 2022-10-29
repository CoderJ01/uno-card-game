import java.util.Scanner;

public class PlayerHand extends Deck implements Comparable<PlayerHand>{
    // variables
    protected String playerName;
    private Scanner input = new Scanner(System.in);
    private int points;

    // constructor
    public PlayerHand (String playerName) {
        this.playerName = playerName;
        this.points = 0;
    }

    // getter
    public String getPlayerName() {
        return this.playerName;
    }

    // display cards in deck
    @Override
    public void displayCards() {
        System.out.println("Your Hand");
        System.out.println("*********");
        for(Card card : returnCards()) {
            System.out.print(card.getCardName() + "   ");
        }
    }

    // prompt player to pick a card
    public Card pickCard() {
        String choosenCard = ""; 
        Card realCard = null;
        boolean valid = false;
        
        // exit loop only if player chooses card in hand
        while(valid == false) {
            System.out.print("\n" + this.playerName + ", place a card onto the discard pile: ");
            choosenCard = input.next();
            for(Card card : returnCards()) {
                // check if player input matches String representation (display) of card
                if(choosenCard.equals(card.getCardName())) {
                    valid = true;
                    realCard = card;
                    break;
                }
                else {
                    valid = false;
                }
            }
        }
        return realCard; // return actual card (instance)
    }

    // ask if player would like to place a card onto the discard pile
    public boolean pickOrNo(Card card) {
        String placeCard = "";
        // allow player to select only 'y' for "yes" or 'n' for "no"
        while(true) {
            System.out.print("\n\n" + this.playerName + ", would you like to place a card onto the discard pile? If so, press 'y'. Otherwise, press 'n': ");
            placeCard = input.next();
            if(placeCard.equals("y") || placeCard.equals("n")) {
                break;
            }
        }
        if(placeCard.equals("y")) {
            return true;
        }
        else {
            return false;
        }
    }

    // force player to draw two cards
    public void drawCards(String messageNumber) {
        String draw = "";
        while(!draw.equals("d")) {
            System.out.print("\n" + this.playerName + ", you must draw " + messageNumber + " card(s). Press 'd' to draw: ");
            draw = input.next();
        }
    }

    // have the player enter the color of a wild card
    public String enterColor() {
        String color = "";
        while(true) {
            System.out.print(this.playerName + ", set the color of the wild card to 'blue', 'green', 'red', or 'yellow': ");
            color = input.next();
            if(color.equals("blue") || color.equals("green") || 
               color.equals("red") || color.equals("yellow"))
            {
                break;
            }
        }
        return color;
    }

    // tally the points for the player
    public int tallyPoints() {
        this.points = 0;
        for (Card card : returnCards()) {
            this.points += card.getCardPoints();
        }
        return this.points;
    }

    /* sort players by the number of points they have remaining
       the less points a players has, the higher player's rank
       the player with no cards left in his hand wins the game (0 points)
    */
    @Override
    public int compareTo(PlayerHand rankPlayer) {
        this.points = tallyPoints();
        return this.points - rankPlayer.points;
    }

    // remove card from player's hand
    public Card removeFromPlayerHand(Card card) {
        returnCards().remove(card);
        return card;
    }
}