public class Card {
    // fields
    private String name;
    private String color;
    private String symbol;
    private int points;

    // constructor for wild cards
    public Card(String name, String color) {
        this.name = name;
        this.color = color;
        this.points = 50;
    }

    // constructor for non-wild cards
    public Card(String name, String color, String symbol) {
        this(name, color);
        this.color = color;
        this.symbol = symbol;
        if(symbol.equals("1") || symbol.equals("2") || symbol.equals("3") || symbol.equals("4") || 
           symbol.equals("5") || symbol.equals("6") || symbol.equals("7") || symbol.equals("8") ||
           symbol.equals("9")) 
        {
            this.points = Integer.parseInt(symbol);
        }
        else if(symbol.equals("0")) {
            this.points = 10;
        }
        else {
            this.points = 20;
        }
    }

    // getters
    public String getCardName() {
        return this.name;
    }

    public String getCardColor() {
        return this.color;
    }

    public String getCardSymbol() {
        return this.symbol;
    }

    public int getCardPoints() {
        return this.points;
    }

    // setters
    public void setCardColor(String color) {
        this.color = color;
    }

    public void setCardName(String name) {
        this.name = name;
    }
}
