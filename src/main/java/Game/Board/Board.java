package Game.Board;


import Game.Player;
import Game.UI.PlayerListener;
import com.google.gson.*;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.Scanner;


public class Board extends JPanel implements PlayerListener {


    public static final int SIZE = 36;
    private final String JSON_PROPERTIES = "properties";
    private final String JSON_RAIL_ROADS = "rail_roads";
    private final String JSON_ACTION_TILES = "action_tiles";
    private final String JSON_CORNERS = "corners";
    private final Dimension BOARD_DIMENSIONS = new Dimension(1000, 1000);
    private final double CORNER_TILE_WEIGHT = 2.0;
    private final double TILE_WEIGHT = 1.0;
    private int CORNER_TOP_RIGHT = 0;
    private int CORNER_BOTTOM_RIGHT = SIZE / 4;
    private int CORNER_BOTTOM_LEFT = 2 * (SIZE / 4);
    private int CORNER_TOP_LEFT = 3 * (SIZE / 4);


    private Tile[] tiles;
    private JPanel boardPanel;

    private GridBagLayout gridBagLayout;
    private GridBagConstraints gridBagConstraints;

    private Player[] players;

    /**
     * Sets up the basics of the game board, and calls initBoard to display the game board to the UI.
     *
     * @param players list of players that are in the current game.
     */
    public Board(Player[] players) {

        this.players = players;

        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        boardPanel = new JPanel();
        tiles = new Tile[SIZE];
        gridBagLayout = new GridBagLayout();


        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = TILE_WEIGHT;
        gridBagConstraints.weighty = TILE_WEIGHT;

        boardPanel.setLayout(gridBagLayout);

        this.initBoardFromJson();

        this.add(boardPanel, BorderLayout.CENTER);
        this.setPreferredSize(BOARD_DIMENSIONS);

    }

    /**
     * Loads All tiles and information based on configuration file
     */
    private void initBoardFromJson() {
        ClassLoader classLoader = Board.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("config.json");
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        Gson g = new Gson();

        JsonObject parent = new JsonParser().parse(result).getAsJsonObject();
        JsonArray properties = parent.getAsJsonArray(JSON_PROPERTIES);
        for (JsonElement j : properties) {
            JsonObject obj = j.getAsJsonObject();

            Property property = new Property(
                    obj.get(Property.JSON_NAME).getAsString(),
                    g.fromJson(obj.get(Property.JSON_OWNER), Player.class),
                    obj.get(Property.JSON_HOUSE_COUNT).getAsInt(),
                    obj.get(Property.JSON_IMPROVEMENT_COST).getAsInt(),
                    g.fromJson(obj.get(Property.JSON_RENT), int[].class),
                    obj.get(Property.JSON_MORTGAGE).getAsInt(),
                    obj.get(Property.JSON_IS_IMPROVED).getAsBoolean(),
                    obj.get(Property.JSON_IS_MONOPOLY).getAsBoolean(),
                    obj.get(Property.JSON_PURCHASE_COST).getAsInt(),
                    obj.get(Property.JSON_TILE_GROUP).getAsString(),
                    obj.get(Property.JSON_TILE_POSITION).getAsInt(),
                    players
            );
            tiles[obj.get(Property.JSON_TILE_POSITION).getAsInt()] = property;
        }

        JsonArray railroads = parent.getAsJsonArray(JSON_RAIL_ROADS);
        for (JsonElement r : railroads) {
            JsonObject obj = r.getAsJsonObject();

            RailRoad railRoad = new RailRoad(
                    obj.get(RailRoad.JSON_NAME).getAsString(),
                    g.fromJson(obj.get(RailRoad.JSON_OWNER), Player.class),
                    obj.get(RailRoad.JSON_PURCHASE_COST).getAsInt(),
                    obj.get(RailRoad.JSON_MORTGAGE).getAsInt(),
                    g.fromJson(obj.get(RailRoad.JSON_RENT), int[].class),
                    obj.get(RailRoad.JSON_IS_MONOPOLY).getAsBoolean(),
                    obj.get(RailRoad.JSON_TILE_POSITION).getAsInt()
            );

            tiles[obj.get(RailRoad.JSON_TILE_POSITION).getAsInt()] = railRoad;
        }

        JsonArray actionTiles = parent.getAsJsonArray(JSON_ACTION_TILES);
        for (JsonElement a : actionTiles) {
            JsonObject obj = a.getAsJsonObject();
            int[] pos = g.fromJson(obj.get(ActionTile.JSON_TILE_POSITIONS), int[].class);
            for (Integer i : pos) {
                tiles[i] = new ActionTile(obj.get(ActionTile.JSON_NAME).getAsString(), i);
            }

        }

        JsonArray corners = parent.getAsJsonArray(JSON_CORNERS);
        for (JsonElement c : corners) {
            JsonObject obj = c.getAsJsonObject();
            tiles[obj.get(ActionTile.JSON_TILE_POSITION).getAsInt()] =
                    new ActionTile(obj.get(ActionTile.JSON_NAME).getAsString(),
                            obj.get(ActionTile.JSON_TILE_POSITION).getAsInt());
        }

        for (int i = 0; i < SIZE; i++) {
            this.setTile(i, tiles[i]);
        }


        for (Player p : players) {
            tiles[0].addPlayer(p);
        }
    }


    /**
     * Sets or Updates any Tile on the board
     *
     * @param pos  the position of the tile
     * @param tile the tile object to populate tile
     */
    private void setTile(int pos, Tile tile) {


        // East Row
        if (isCorner(pos)) {
            gridBagConstraints.weightx = CORNER_TILE_WEIGHT;
            gridBagConstraints.weighty = CORNER_TILE_WEIGHT;
            this.setCorner(pos, tile);
            gridBagConstraints.weightx = TILE_WEIGHT;
            gridBagConstraints.weighty = TILE_WEIGHT;
        } else if (pos > CORNER_TOP_RIGHT && pos < CORNER_BOTTOM_RIGHT) {
            this.setInnerTile(pos, tile, Tile.ORIENTATION_EAST);
        }
        // South Row
        else if (pos > CORNER_BOTTOM_RIGHT && pos < CORNER_BOTTOM_LEFT) {
            this.setInnerTile(pos, tile, Tile.ORIENTATION_SOUTH);
        }
        // West Row
        else if (pos > CORNER_BOTTOM_LEFT && pos < CORNER_TOP_LEFT) {
            this.setInnerTile(pos, tile, Tile.ORIENTATION_WEST);
        } else if (pos > CORNER_TOP_LEFT && pos < SIZE) {
            this.setInnerTile(pos, tile, Tile.ORIENTATION_NORTH);
        } else {
            System.out.println(pos);
            throw new ArrayIndexOutOfBoundsException("Invalid Board Position");
        }

    }

    /**
     * Sets a non-corner tile on the board
     *
     * @param pos         position the tile will appear on the board
     * @param tile        tile object to populate non-corner tile
     * @param orientation orientation of inner-tile
     */
    private void setInnerTile(int pos, Tile tile, int orientation) {


        TilePanel panel = tile.getTilePanel(orientation);


        if (orientation == Tile.ORIENTATION_EAST) {

            gridBagConstraints.gridx = 11;
            gridBagConstraints.gridy = pos + 1;

        } else if (orientation == Tile.ORIENTATION_SOUTH) {

            gridBagConstraints.gridx = 9 - (pos % 10);
            gridBagConstraints.gridy = 10;


        } else if (orientation == Tile.ORIENTATION_WEST) {

            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 9 - (pos % 19);

        } else if (orientation == Tile.ORIENTATION_NORTH) {

            gridBagConstraints.gridx = pos - 26;
            gridBagConstraints.gridy = 0;

        }

        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 1;

        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagLayout.setConstraints(panel, gridBagConstraints);
        boardPanel.add(panel);

    }

    /**
     * Sets a corner tile on the board
     *
     * @param pos  position for corner to be updated
     * @param tile tile object to populate corner
     */
    private void setCorner(int pos, Tile tile) {

        TilePanel panel = tile.getTilePanel(Tile.ORIENTATION_CORNER);

        gridBagConstraints.gridheight = 2;
        gridBagConstraints.gridwidth = 2;

        if (pos == CORNER_TOP_RIGHT) {
            gridBagConstraints.gridx = 10;
            gridBagConstraints.gridy = 0;
        } else if (pos == CORNER_BOTTOM_RIGHT) {
            gridBagConstraints.gridx = 10;
            gridBagConstraints.gridy = 10;
        } else if (pos == CORNER_BOTTOM_LEFT) {
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 10;
        } else if (pos == CORNER_TOP_LEFT) {
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
        }

        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagLayout.setConstraints(panel, gridBagConstraints);

        boardPanel.add(panel);
    }


    /**
     * Checks if Tile position is a corner
     *
     * @param pos tile position
     * @return True if position is on the corner of the board. False otherwise.
     */
    private boolean isCorner(int pos) {
        return pos == CORNER_BOTTOM_LEFT || pos == CORNER_BOTTOM_RIGHT
                || pos == CORNER_TOP_RIGHT || pos == CORNER_TOP_LEFT;
    }

    /**
     * Updates player objects position on board
     *
     * @param p Player that has moved.
     */
    @Override
    public void onPlayerMove(Player p) {
        int oldPosition = p.getPreviousPosition();
        int newPosition = p.getPosition();

        tiles[oldPosition].removePlayer(p);
        tiles[newPosition].addPlayer(p);
        tiles[newPosition].getTilePanel().repaint();
        tiles[oldPosition].getTilePanel().repaint();
        tiles[newPosition].notifyPlayerLanded(p);

    }

    @Override
    public void onRentPayed(Player owner, Player rente) {

    }

    @Override
    public void onTrade() {

    }

    @Override
    public void onPurchase(Player p) {

    }

    @Override
    public void onLose() {

    }
}