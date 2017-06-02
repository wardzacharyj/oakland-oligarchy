package Game.Board;

import java.awt.*;


public class TileGroup extends Tile {
    private Color color;
    private boolean isImprovable;

    private Tile[] tiles;


    public TileGroup(int size, Color color, boolean isImprovable){
        this.color = color;
        this.isImprovable = isImprovable;
    }


}
