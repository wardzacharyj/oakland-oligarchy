package Game.Board;

import java.security.SecureRandom;
/**
 * Created by rad85000 on 7/5/2017.
 */
public class Deck {
    Card[] cards;
    private SecureRandom r = new SecureRandom();
    int current = 0;

    /**
     * Initializes the deck and shuffles it.
     * @param num The number of cards in the deck.
     */
    Deck(int num) {
        cards = new Card[num];
    }

    /**
     * Reset the deck, and shuffle it.
     */
    public void shuffle() {
        int shuffles = (Math.abs(r.nextInt()) % 20) + 5;

        for (int i = 0; i < shuffles; i++) {
            for (int j = 0; j < cards.length; j++) {
                int temp = Math.abs(r.nextInt()) % cards.length;
                Card temp1 = cards[j];
                cards[j] = cards[temp];
                cards[temp] = temp1;
            }
        }

        current = 0;
    }

    /**
     * @return The card on top of the deck.
     */
    public Card drawCard() {
        return cards[current++];
    }

    /**
     * @return true if there are no more cards in the deck.  false if there are cards left in the deck.
     */
    public boolean isEmpty() {
        if(current >= cards.length) {
            return true;
        }
        else
            return false;
    }
}
