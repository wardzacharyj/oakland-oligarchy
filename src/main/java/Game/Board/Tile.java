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

    public abstract void notifyPlayerLanded(Player p);

    // Check i
    public void addPlayer(Player p){
        activePlayers.add(p);
        notifyPlayerLanded(p);
    }

    public void removePlayer(Player p){
        activePlayers.remove(p);
    }

    protected Tile(String name, int position) {
        this.name = name;
        this.position = position;
        tilePanel = new TilePanel(this,ORIENTATION_DEFAULT);
        activePlayers = new ArrayList<Player>();
    }

    protected ArrayList<Player> getActivePlayers(){
        return activePlayers;
    }

    public String getName(){
        return name;
    }

    public int getBoardPosition(){
        return position;
    }

    public JPanel getTilePanel(){
        return tilePanel;
    }

    public TilePanel getTilePanel(int orientation){
        tilePanel.setOrientation(orientation);
        return tilePanel;
    }

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