package Game.Board;



/**
 * Created by Zach on 6/1/17.
 */
public class Board {

    private int size;
    private Tile[] tiles;

    public Board(int size){
        this.size = size;
        try{
            if(this.size % 4 == 0 ){
                tiles = new Tile[size];
            }
            else {
                throw new IllegalArgumentException(this.size+" : Board Size Must Be Divisible By 4");

            }
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            System.exit(1);
        }

    }
}
