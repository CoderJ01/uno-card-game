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
            System.out.print("\nPlace a card onto the discard pile: ");
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
}