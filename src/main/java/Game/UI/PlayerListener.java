package Game.UI;

import Game.Board.Tile;
import Game.Player;

/**
 * Created by Zach on 6/7/17.
 */
public interface PlayerListener {
    void onPlayerMove(Player p);
    void onRentPayed(Player owner, Player rente);

    void onTileClick(Tile tile);
    void onTrade(Player p);
    void onPurchase(Player p);
    void onLose();
}
