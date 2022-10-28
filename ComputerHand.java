import java.util.ArrayList;
import java.util.List;

public class ComputerHand extends PlayerHand {

    // constructor
    public ComputerHand(String name) {
        super(name);
    }

    // set CPU to discard card if it finds a match
    /*
       NOTE: parameter 'card' is used only in this class, but nevertheless
       must be passed to pickOrNo in PlayerHand (superclass)
    */
    @Override
    public boolean pickOrNo(Card card) {
        List<Card> cardMatches = new ArrayList<>();

        return true;
    }
}