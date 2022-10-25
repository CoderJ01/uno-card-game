public class Card {
    // variables
    private String name;
    private String color;
    private String symbol;
    private int points;

    // constructor for wild cards
    public Card(String name) {
        this.name = name;
        this.points = 50;
    }

    // constructor for numbered cards (symbol = number)
    public Card(String name, String color, String symbol) {
        this(name);
        this.color = color;
        this.symbol = symbol;
        this.points = Integer.parseInt(symbol);
    }

    // constructor for non-wild special cards
    public Card(String name, String color, String symbol, int points) {
        this(name, color, symbol); // constructor chaining
        this.points = points;
    }
}
