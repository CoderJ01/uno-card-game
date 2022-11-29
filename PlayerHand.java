import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
* NOTE: certain parameters are used only in ComputerHand (subclass), but are nevertheless
* passed to the methods here to prevent compiler error
*/

public class PlayerHand extends Deck implements Comparable<PlayerHand>{
    // fields
    private final String PLAYER_NAME;
    private Scanner input = new Scanner(System.in);
    private int points;
    private List<Card> playerPass = new ArrayList<>(); // transitory deck for passing hand (0 card rule) 

    // constructor
    public PlayerHand (String playerName) {
        PLAYER_NAME = playerName;
        this.points = 0;
    }

    // getter
    public final String getPlayerName() {
        return PLAYER_NAME;
    }

    // display cards in deck
    @Override
    public void displayCards() {
        System.out.println("Your Hand");
        System.out.println("*********");
        for(Card card : getDeck()) {
            System.out.print(card.getCardName() + "   ");
        }
    }

    // prompt player to pick a card
    public Card pickCard(Card placeholder) {
        String choosenCard = ""; 
        Card realCard = null;
        boolean valid = false;
        
        // exit loop only if player chooses card in hand
        while(valid == false) {
            System.out.print("\n" + PLAYER_NAME + ", place a card onto the discard pile: ");
            choosenCard = input.next();
            for(Card card : getDeck()) {
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
            System.out.print("\n\n" + PLAYER_NAME + ", would you like to place a card onto the discard pile? If so, enter 'y'. Otherwise, enter 'n': ");
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

    // force player to draw cards
    public void drawCards(String messageNumber) {
        String draw = "";
        while(!draw.equals("d")) {
            if(messageNumber.equals("one")) {
                System.out.print("\n" + PLAYER_NAME + ", you must draw " + messageNumber + " card. Enter 'd' to draw: ");
            }
            else {
                System.out.print("\n" + PLAYER_NAME + ", you must draw " + messageNumber + " cards. Enter 'd' to draw: ");
            }
            draw = input.next();
        }
    }

    // have the player enter the color of a wild card
    public String enterColor() {
        String color = "";
        while(true) {
            System.out.print("\n" + PLAYER_NAME + ", set the color of the wild card to blue, green, red, or yellow: ");
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
    public final int tallyPoints() {
        this.points = 0;
        for (Card card : getDeck()) {
            this.points += card.getCardPoints();
        }
        return this.points;
    }

    // rank players
    @Override
    public final int compareTo(PlayerHand rankPlayer) {
        this.points = tallyPoints();
        return this.points - rankPlayer.points;
    }

    // remove card from player's hand
    public final Card removeFromPlayerHand(Card card) {
        getDeck().remove(card);
        return card;
    }

    // notify player to pass hand
    private void promptToPass() {
        String pass = "";
        while(true) {
            System.out.print("\nAll players must pass their respective hands to the next player. Enter 'p' to pass your hand: ");
            pass = input.next();
            if(pass.equals("p")) {
                break;
            }
        }
    }

    // pass deck to the next player 
    public void passDeck(boolean ruleZero) {
        if(ruleZero == true) {
            promptToPass();
        }
        Iterator<Card> itr = getDeck().iterator();
        resetPlayerPass();
        while(itr.hasNext()) {
            Card card = itr.next();
            itr.remove(); // remove card from player
            this.playerPass.add(card); // add card to temporary deck
        }
    }

    // store passed cards
    public final List<Card> getPassedCards() {
        return this.playerPass;
    }

    // receive deck from player
    public final void receiveDeck(List<Card> deck) {
        Iterator<Card> itr = deck.iterator();
        while(itr.hasNext()) {
            Card card = itr.next();
            itr.remove(); // remove instance (card) to prevent creation of duplicate
            getDeck().add(card); // receive cards from last player
        }
    }

    // reset field to prevent creation of duplicate instances (cards)
    protected final void resetPlayerPass() {
        this.playerPass = new ArrayList<>();
    }

    // prompt player to choose another player to switch cards with
    public int pickPlayer(List<PlayerHand> players, int playerIndex) {
        int playerNumber = playerIndex + 1;
        int pick = 0;
        int i = 1;
        boolean error = false;

        while(true) {
            do {
                error = false;
                try {
                    System.out.println();
                    // list players
                    for(; i < players.size(); i++) {
                        // remove selector from list
                        if(i != playerIndex) {
                            System.out.println((i + 1) + ". " + players.get(i).getPlayerName());
                        }
                    }
                    System.out.print("\nYou are player " + playerNumber + ". Pick another player to switch cards with: ");
                    pick = input.nextInt();
                }
                catch(InputMismatchException e) {
                    error = true;
                    input.next();
                }
            } while(error);

            if(pick >= 1 && pick <= players.size() && pick != playerNumber) {
                break;
            }
        }

        return pick - 1;
    }
}