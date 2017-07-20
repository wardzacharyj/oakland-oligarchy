package Game.UI;

import Game.Player;

/**
 * Created by Zach on 6/7/17.
 */
public interface PlayerListener {
    void onPlayerMove(Player p);
    void onRentPayed(Player owner, Player rente);

    void onTrade(Player p);
    void onPurchase(Player p);
    void onLose();
}
