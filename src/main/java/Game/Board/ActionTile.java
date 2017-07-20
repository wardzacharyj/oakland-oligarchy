package Game.Board;


import Game.Player;
import Game.UI.Leaderboard;
import com.google.gson.JsonObject;
import javax.swing.*;

public class ActionTile extends Tile {

    public static final String JSON_NAME = "name";
    public static final String JSON_TILE_POSITION = "tilePosition";
    public static final String JSON_TILE_POSITIONS = "tilePositions";
    private Deck deck = null;
    private Tile[] tiles = null;
    private int JAIL_TILE = 9;
    private Leaderboard leaderboard = null;


    /**
     * Constructor
     * @param name  A string containing the name of the tile.
     * @param position  An integer containing the position of the tile on the board.
     */
    ActionTile(String name, int position, Tile[] tiles) {
        super(name, position);
        this.tiles = tiles;
    }

    ActionTile(String name, int position, Deck d, Tile[] tiles, Leaderboard leaderboard) {
        super(name, position);
        this.deck = d;
        this.tiles = tiles;
        this.leaderboard = leaderboard;
    }

    @Override
    public JsonObject toJSONObject() {
        return null;
    }

    /**
     * Notification that a player has landed on this tile.
     * @param p The player that has landed on the tile.
     */
    @Override
    public void notifyPlayerLanded(Player p) {
        // Called when tiles player count is increased

        if(getName().equals("Go to Jail")) {
            JOptionPane.showMessageDialog(new JPanel(), "Oh no, you have to go to jail!");
            p.setPosition(JAIL_TILE);
            removePlayer(p);
            tiles[JAIL_TILE].addPlayer(p);
            getTilePanel().repaint();
            tiles[JAIL_TILE].getTilePanel().repaint();
            p.setInJail(true);
        }
        else if(getName().equals("Community Chest")) {
            if(!deck.isEmpty()) {
                Card c = deck.drawCard();
                c.cardEffectAndNotification(p, tiles);
                leaderboard.updatePlayer(p);
            }
            else {
                JOptionPane.showMessageDialog(new JPanel(), "There are no more cards left in the Community Chest deck.");
            }
        }
        else if(getName().equals("Chance")) {
            if(!deck.isEmpty()) {
                Card c = deck.drawCard();
                c.cardEffectAndNotification(p, tiles);
                leaderboard.updatePlayer(p);
            }
            else {
                JOptionPane.showMessageDialog(new JPanel(), "There are no more cards left in the Chance deck.");
            }
        }
        else if(getName().equals("Pitt Start")) {
            JOptionPane.showMessageDialog(new JPanel(), "You made $200 for landing on Pitt Start.");
            p.addCash(200);
            leaderboard.updatePlayer(p);
            //not sure how to update this player's cash on screen.
        }
    }


}