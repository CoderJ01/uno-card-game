import java.util.Scanner;

public class PlayerHand extends Deck {
    private String playerName;
    private Scanner input = new Scanner(System.in);
    private boolean valid = false;

    // constructor
    public PlayerHand (String playerName) {
        this.playerName = playerName;
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
        
        // exit loop only if player chooses card in hand
        while(this.valid == false) {
            System.out.print("\n" + this.playerName + ", place a card onto the discard pile: ");
            choosenCard = input.next();
            for(Card card : returnCards()) {
                // check if player input matches String representation (display) of card
                if(choosenCard.equals(card.getCardName())) {
                    this.valid = true;
                    realCard = card;
                    break;
                }
                else {
                    this.valid = false;
                }
            }
        }
        // return actual card (object)
        return realCard;
    }

    // ask if player would like to place a card onto the discard pile
    public boolean pickOrNo() {
        String placeCard = "";
        System.out.print("\n" + this.playerName + ", would you like to place a card onto the discard pile? If so, press 'y': ");
        placeCard = input.next();
        if(!placeCard.equals("y")) {
            return false;
        }
        return true;
    }

    // force player to draw two cards
    public void drawCards() {
        String draw = "";
        while(!draw.equals("d")) {
            System.out.print(this.playerName + ", you must draw two cards. Press 'd' to draw: ");
            draw = input.next();
        }
    }

    // have the player enter the color of a wild card
    public String enterColor() {
        System.out.print("Set the color of the wild card: ");
        String color = input.next();
        while(true) {
            if(color.equals("blue") || color.equals("green") || 
               color.equals("red") || color.equals("yellow"))
            {
                break;
            }
        }
        return color;
    }
}