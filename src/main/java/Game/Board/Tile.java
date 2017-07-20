package Game.Board;

import Game.Player;
import Game.UI.PlayerListener;
import com.google.gson.JsonObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public abstract class Tile implements PlayerListener {


    public static final Color DEFAULT_TILE_COLOR = new Color(Color.TRANSLUCENT);
    public static final int ORIENTATION_NORTH = 0;
    public static final int ORIENTATION_EAST = 1;
    public static final int ORIENTATION_SOUTH = 2;
    public static final int ORIENTATION_WEST = 3;

    public static final int ORIENTATION_CORNER = 4;

    public static final int ORIENTATION_DEFAULT = 5;


    private String name;
    private int position;
    private TilePanel tilePanel;
    private ArrayList<Player> activePlayers;
    private PlayerListener listener;

    /**
     * Default constructor of Tile.
     * @param name tile name
     */
    protected Tile(String name) {
        this.name = name;
    }

    /**
     *
     * Constructor for Tile.
     *
     * @param name     The tile name.
     * @param position The position of the tile on the board
     */
    protected Tile(String name, int position) {
        this.name = name;
        this.position = position;
        this.tilePanel = new TilePanel(this, ORIENTATION_DEFAULT);
        this.activePlayers = new ArrayList<>();
    }

    /**
     * notify if player lands on
     *
     * @param p The player that has landed on the tile.
     */
    public abstract void notifyPlayerLanded(Player p);


    public abstract JsonObject toJSONObject();

    /**
     * Display the tile information
     * @param tile the tile to display
     */
    public abstract void showInfo(Tile tile);

    /**
     * Returns whether or not the Tile is for sale.
     * @return  True if it is for sale, else false.
     */
    public abstract boolean isForSale();
    /**
     * Sets if the Tile is for sale, if applicable.
     * @param value The boolean value indicating if the tile is for sale.
     */
    public abstract void setForSale(boolean value);

    /**
     * Sets of the status of the Tile to bought and sets the owner
     * @param newOwner  The Player who has purchased the Tile
     */
    public abstract void setBought(Player newOwner);

    /**
     * Returns the owner of the tile if applicable.
     * @return  The player that owns the Tile
     */
    public abstract Player getOwner();

    /**
     * Returns the cost to buy the Tile if purchasable.
     * @return  The dollar amount in an integer.
     */
    public abstract int getPurchaseCost();

    /**
     * Gets the color associated with the tile.
     * @return  The Color associated with the tile.
     */
    public abstract Color getTileColor();

    /**
     * Returns the info associated with the tile.
     * @return  A String containing a formatted version of the tile information.
     */
    public abstract String tileInfoToString();
    /**
     *
     * Add a player to the tile.
     *
     * @param p The player that now resides on the tile.
     */
    public void addPlayer(Player p) {
        activePlayers.add(p);
    }

    /**
     *
     * removes player from tile
     *
     * @param p The player that no longer resides on the tile.
     */
    public void removePlayer(Player p) {
        this.activePlayers.remove(p);
    }


    /**
     * Get the active players on the tile.
     *
     * @return An ArrayList containing all active players currently residing on the tile.
     */
    protected ArrayList<Player> getActivePlayers() {
        return this.activePlayers;
    }


    /**
     * Get the name of the tile.
     *
     * @return A string containing the name of the tile.
     */
    public String getName() {
        return this.name;
    }


    /**
     * Get the position of the tile on the board.
     *
     * @return An integer defining where the tile resides on the board.
     */
    public int getBoardPosition() {
        return this.position;
    }


    /**
     * Get the panel that the tile resides on.
     *
     * @return The JPanel the tile has been added to.
     */
    public JPanel getTilePanel() {
        return this.tilePanel;
    }


    /**
     * Get the Tile Panel this tile resides on.
     *
     * @param orientation The desired orientation of the tile panel.
     * @return A tile panel that contains this tile.
     */
    public TilePanel getTilePanel(int orientation) {
        this.tilePanel.setOrientation(orientation);
        return this.tilePanel;
    }

    /**
     * Adds player listener to the tile.
     *
     * @param listener The PlayerListener object to attach to the tile.
     */
    public void addListener(PlayerListener listener) {
        this.listener = listener;
    }

    /**
     * A string representation of the tile.
     *
     * @return A string containing associated properties of the tile.
     */
    @Override
    public String toString() {
        return "Tile{" +
                "name='" + name + '\'' +
                ", position=" + position +
                ", tilePanel=" + tilePanel +
                ", activePlayers=" + activePlayers +
                '}';
    }
}