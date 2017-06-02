package Game.Board;

/**
 * Created by Zach on 6/1/17.
 */
public abstract class Tile {

    private String name;
    private int position;

    protected Tile(){

    }

    protected Tile(String name, int position) {
        this.name = name;
        this.position = position;
    }

    public String getName(){
        return name;
    }

    public int getBoardPosition(){
        return position;
    }






}
