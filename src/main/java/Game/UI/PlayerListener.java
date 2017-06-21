package Game.UI;

import Game.Player;

/**
 * Created by Zach on 6/7/17.
 */
public interface PlayerListener {
    void onPlayerMove(Player p);
    void onTrade();

    void onPurchase(Player p);
    void onLose();
}
