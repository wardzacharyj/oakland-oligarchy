package Game.Board;


import Game.Player;



public class ActionTile extends Tile {

    public static final String JSON_NAME = "name";
    public static final String JSON_TILE_POSITION = "tilePosition";
    public static final String JSON_TILE_POSITIONS = "tilePositions";


    ActionTile(String name, int position){
        super(name,position);
    }

    @Override
    public void notifyPlayerLanded(Player p) {
        // Called when tiles player count is increased
    }


}