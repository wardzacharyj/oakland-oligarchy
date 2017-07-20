package Game.Board;


import Game.Player;
import Game.UI.Leaderboard;
import com.google.gson.JsonObject;
import javax.swing.*;
import java.awt.*;

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

    @Override
    public void showInfo(Tile tile) {

    }

    /**
     * Returns whether or not the Tile is for sale.
     *
     * @return True if it is for sale, else false.
     */
    @Override
    public boolean isForSale() {
        return false;
    }

    /**
     * Sets if the Tile is for sale, if applicable.
     *
     * @param value The boolean value indicating if the tile is for sale.
     */
    @Override
    public void setForSale(boolean value) {

    }

    /**
     * Sets of the status of the Tile to bought and sets the owner
     *
     * @param newOwner The Player who has purchased the Tile
     */
    @Override
    public void setBought(Player newOwner) {

    }

    /**
     * Returns the owner of the tile if applicable.
     *
     * @return The player that owns the Tile
     */
    @Override
    public Player getOwner() {
        return null;
    }

    /**
     * Returns the cost to buy the Tile if purchasable.
     *
     * @return The dollar amount in an integer.
     */
    @Override
    public int getPurchaseCost() {
        return 0;
    }

    /**
     * Gets the color associated with the tile.
     *
     * @return The Color associated with the tile.
     */
    @Override
    public Color getTileColor() {
        return null;
    }

    /**
     * Returns the info associated with the tile.
     *
     * @return A String containing a formatted version of the tile information.
     */
    @Override
    public String tileInfoToString() {
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
            //not sure how to update this player's cash on screen.
        }
    }


    @Override
    public void onPlayerMove(Player p) {

    }

    @Override
    public void onRentPayed(Player owner, Player rente) {

    }

    @Override
    public void onTileClick(Tile tile) {

    }


    @Override
    public void onTrade(Player p) {

    }

    @Override
    public void onPurchase(Player p) {

    }

    @Override
    public void onLose() {

    }
}