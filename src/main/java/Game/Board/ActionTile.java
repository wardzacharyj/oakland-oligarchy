package Game.Board;


import Game.Player;


public class ActionTile extends Tile {

    public static final String JSON_NAME = "name";
    public static final String JSON_TILE_POSITION = "tilePosition";
    public static final String JSON_TILE_POSITIONS = "tilePositions";


    /**
     * Constructor
     * @param name  A string containing the name of the tile.
     * @param position  An integer containing the position of the tile on the board.
     */
    ActionTile(String name, int position) {
        super(name, position);
    }

    /**
     * Notification that a player has landed on this tile.
     * @param p The player that has landed on the tile.
     */
    @Override
    public void notifyPlayerLanded(Player p) {
        // Called when tiles player count is increased
    }


}