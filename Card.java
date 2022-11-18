public final class Card {
    // fields
    private String name;
    private String color;
    private char symbol;
    private int points;

    // constructor for wild cards
    public Card(String name, String color) {
        this.name = name;
        this.color = color;
        this.points = 50;
    }

    // constructor for non-wild cards
    public Card(String name, String color, char symbol) {
        this(name, color);
        this.symbol = symbol;
        // numbered cards
        if(symbol == '1' || symbol == '2' || symbol == '3' || symbol == '4' || 
           symbol == '5' || symbol == '6' || symbol == '7' || symbol == '8' ||
           symbol == '9') 
        {
            this.points = Character.getNumericValue(symbol);
        }
        else if(symbol == '0') {
            this.points = 10;
        }
        // Skip, Draw 2, Reverse cards
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

    public char getCardSymbol() {
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
