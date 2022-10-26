public class PlayerHand extends Deck {
    private String playerName;

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
}