package Game.Board;

/**
 * Created by rad85000 on 7/5/2017.
 */
public class CommunityChestDeck extends Deck {
    CommunityChestDeck() {
        super(4);
        //define what cards are in the deck here.
        cards[0] = new Card("Advance To Pitt Start");
        cards[1] = new Card("Doctor Fee");
        cards[2] = new Card("Tax Refund");
        cards[3] = new Card("Hospital Fee");
        shuffle();
    }
}
