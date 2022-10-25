public class Card {
    // variables
    private String name;
    private String color;
    private String symbol;
    private int points;

    // constructor for wild cards
    public Card(String name, int points) {
        this.name = name;
        this.points = points;
    }
}