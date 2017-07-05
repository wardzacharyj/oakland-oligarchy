package Game.Board;


import Game.Player;
import com.google.gson.JsonObject;


public class ActionTile extends Tile {

    public static final String JSON_NAME = "name";
    public static final String JSON_TILE_POSITION = "tilePosition";
    public static final String JSON_TILE_POSITIONS = "tilePositions";


    /**
     * Action time constructor
     */
    ActionTile(String name, int position){
        super(name,position);
    }

    @Override
    public JsonObject toJSONObject() {
        return null;
    }

    /**
     * notfies player they have landed on the tile
     */
    @Override
    public void notifyPlayerLanded(Player p) {
        // Called when tiles player count is increased
    }


}