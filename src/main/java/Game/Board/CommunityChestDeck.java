package Game.Board;

/**
 * Created by rad85000 on 7/5/2017.
 */
public class CommunityChestDeck extends Deck {
    CommunityChestDeck() {
        super(10);
        //define what cards are in the deck here.
        cards[0] = new Card("Advance To Pitt Start");
        cards[1] = new Card("Doctor Fee");
        cards[2] = new Card("Tax Refund");
        cards[3] = new Card("Hospital Fee");
        cards[4] = new Card("Go to Jail");
        cards[5] = new Card("Pay School Fees");
        cards[6] = new Card("Happy Birthday");
        cards[7] = new Card("Merry Christmas");
        cards[8] = new Card("Hospital Fee");
        cards[9] = new Card("Advance To Pitt Start");
        shuffle();
    }
}
