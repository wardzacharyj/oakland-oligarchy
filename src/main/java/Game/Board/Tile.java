package Game.Board;

import Game.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Zach on 6/1/17.
 */
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


    protected Tile(){

    }

    /**
     *notify if player lands on tile
     */
    public abstract void notifyPlayerLanded(Player p);

    /**
     *adds player to tile location
     */
    // Check i
    public void addPlayer(Player p){
        activePlayers.add(p);
        //notifyPlayerLanded(p);
    }

    /**
     *removes player from tile
     */
    public void removePlayer(Player p){
        activePlayers.remove(p);
    }

    /**
     *tile constructor
     */
    protected Tile(String name, int position) {
        this.name = name;
        this.position = position;
        tilePanel = new TilePanel(this,ORIENTATION_DEFAULT);
        activePlayers = new ArrayList<Player>();
    }

    /**
     *gets players currently on tile
     */
    protected ArrayList<Player> getActivePlayers(){
        return activePlayers;
    }

    /**
     *gets name of tile
     */
    public String getName(){
        return name;
    }

    /**
     *gets position on tile
     */
    public int getBoardPosition(){
        return position;
    }

    /**
     *gets tile panel
     */
    public JPanel getTilePanel(){
        return tilePanel;
    }

    /**
     *gets tile panel according to orientation
     * @param orientation
     */
    public TilePanel getTilePanel(int orientation){
        tilePanel.setOrientation(orientation);
        return tilePanel;
    }

    /**
     *stringify tile info
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