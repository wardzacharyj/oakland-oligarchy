package Game.Board;

import Game.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class Tile {


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


    /**
     * Default constructor of Tile.
     */
    protected Tile() {

    }

    /**
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

    /**
     * Add a player to the tile.
     *
     * @param p The player that now resides on the tile.
     */
    // Check i
    public void addPlayer(Player p) {
        this.activePlayers.add(p);
    }

    /**
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