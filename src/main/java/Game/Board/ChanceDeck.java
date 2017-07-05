package Game.Board;

/**
 * Created by rad85000 on 7/5/2017.
 */
public class ChanceDeck extends Deck {
    ChanceDeck() {
        super(4);
        //define what cards are in the deck here.
        cards[0] = new Card("Go Back 3 Spaces");
        cards[1] = new Card("Advance To Pitt Start");
        cards[2] = new Card("Make General Repairs");
        cards[3] = new Card("Bank Pays");
        shuffle();
    }
}
