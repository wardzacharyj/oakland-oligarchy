package Game.Board;

import javax.swing.*;

/**
 * Created by Zach on 6/1/17.
 */
public abstract class Tile {

    private String name;
    private int position;
    JPanel tilePanel;

    protected Tile(){

    }

    protected Tile(String name, int position, JPanel tilePanel) {
        this.name = name;
        this.position = position;
        this.tilePanel = tilePanel;
    }

    public String getName(){
        return name;
    }

    public int getBoardPosition(){
        return position;
    }

    public JPanel getTilePanel() { return tilePanel; }






}
