public class Main {
    public static void main(String[] args) {
        // test wild card
        Card wild = new Card("wild");
        System.out.println(wild.getCardName());
        System.out.println(wild.getCardPoints());

        // test numbered card
        Card blueEight = new Card("B8", "blue", "8");
        System.out.println(blueEight.getCardName());
        System.out.println(blueEight.getCardColor());
        System.out.println(blueEight.getCardSymbol());
        System.out.println(blueEight.getCardPoints());

        // test special card
        Card skip = new Card("skip (green)", "green", "skip");
        System.out.println(skip.getCardName());
        System.out.println(skip.getCardColor());
        System.out.println(skip.getCardSymbol());
        System.out.println(skip.getCardPoints());
    }
}