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

    // constructor for non-wild cards
    public Card(String name, String color, String symbol) {
        this(name);
        this.color = color;
        this.symbol = symbol;
        if(symbol.equals("1") || symbol.equals("2") || symbol.equals("3") || symbol.equals("4") || 
           symbol.equals("5") || symbol.equals("6") || symbol.equals("7") || symbol.equals("8") ||
           symbol.equals("9") || symbol.equals("0")) {
            this.points = Integer.parseInt(symbol);
        }
        else {
            this.points = 20;
        }
    }
}
