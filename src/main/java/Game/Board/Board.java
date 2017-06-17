package Game.Board;


import Game.Player;

import javax.swing.*;
import java.awt.*;


public class Board extends JPanel {

    private final int SIZE = 36;

    private int CORNER_TOP_RIGHT = 0;
    private int CORNER_BOTTOM_RIGHT = SIZE/4;
    private int CORNER_BOTTOM_LEFT = 2*(SIZE/4);
    private int CORNER_TOP_LEFT = 3*(SIZE/4);


    private Tile[] tiles;
    private JPanel boardPanel;

    private GridBagLayout gridBagLayout;
    private GridBagConstraints gridBagConstraints;

    private Player[] players;

    public Board(Player[] players) {

        this.players = players;




        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        boardPanel = new JPanel();
        tiles = new Tile[SIZE];
        gridBagLayout = new GridBagLayout();


        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;

        boardPanel.setLayout(gridBagLayout);

        initBoard();

        add(boardPanel, BorderLayout.CENTER);
        setPreferredSize(new Dimension(1000, 1000));

    }

    // For Future
    private void initBoardFromJson(){

    }

    // Replace later
    private void initBoard(){

        // tiles[0] TOP RIGHT CORNER

        tiles[0] = new ActionTile("Pitt Start",0);

        tiles[1] = new Property("Oishi Bento",1, Color.WHITE);
        tiles[2] = new Property("Schezwan",2, Color.WHITE);
        tiles[3] = new ActionTile("PNC Bank",3);
        tiles[4] = new Property("Antoons",4, Color.CYAN);
        tiles[5] = new Property("Sorentos",5, Color.CYAN);
        tiles[6] = new Property("Pizza Romano",6, Color.CYAN);
        tiles[7] = new Property("CVS",7, Color.MAGENTA);
        tiles[8] = new Property("Rite Aid",8, Color.MAGENTA);


        // tiles[9] Bottom RIGHT CORNER
        tiles[9] = new ActionTile("Market",9);

        tiles[10] = new Property("Hemingway's",10, Color.BLACK);
        tiles[11] = new Property("Peter's Pub",11, Color.BLACK);
        tiles[12] = new ActionTile("PNC Bank",12);
        tiles[13] = new Property("G-Door",13, Color.BLACK);
        tiles[14] = new Property("The Pete",14, Color.BLUE);
        tiles[15] = new Property("Trees",15, Color.BLUE);
        tiles[16] = new Property("Pamela's",16, Color.RED);
        tiles[17] = new Property("Brueggers",17, Color.RED);

        // tiles[18] Bottom LEFT CORNER
        tiles[18] = new ActionTile("Soldiers and Sailors",18);

        tiles[19] = new Property("Starbucks",19, Color.YELLOW);
        tiles[20] = new Property("Dunkin Donuts",20, Color.YELLOW);
        tiles[21] = new ActionTile("PNC Bank",21);
        tiles[22] = new Property("Einsteins",22, Color.YELLOW);
        tiles[23] = new Property("Shenley Plaza",23, Color.ORANGE);
        tiles[24] = new Property("Flagstaff Hill",24, Color.ORANGE);
        tiles[25] = new ActionTile("PNC Bank",25);
        tiles[26] = new Property("Frenchies Subs",26, Color.GREEN);

        // tiles[27] TOP LEFT CORNER
        tiles[27] = new ActionTile("Go To Market",27);

        tiles[28] = new Property("Campus Deli",28, Color.GREEN);
        tiles[29] = new ActionTile("PNC Bank",29);
        tiles[30] = new Property("Hillman",30, Color.LIGHT_GRAY);
        tiles[31] = new Property("Cathy",31, Color.LIGHT_GRAY);
        tiles[32] = new Property("Posvar",32, Color.LIGHT_GRAY);
        tiles[33] = new Property("Forbes",33, Color.PINK);
        tiles[34] = new ActionTile("PNC Bank",34);
        tiles[35] = new Property("Fifth",35, Color.PINK);


        for(int i = 0; i < SIZE; i++){
            setTile(i,tiles[i]);
        }

        for (Player p : players){
            tiles[0].addPlayer(p);
        }

    }

    private void setTile(int pos, Tile tile){


        // Corner
        if(isCorner(pos)){
            gridBagConstraints.weightx = 2.0;
            gridBagConstraints.weighty = 2.0;
            setCorner(pos, tile);
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.weighty = 1.0;
        }
        // East Row
        else if(pos > CORNER_TOP_RIGHT && pos < CORNER_BOTTOM_RIGHT){
            setInnerTile(pos,tile, Tile.ORIENTATION_EAST);
        }
        // South Row
        else if(pos > CORNER_BOTTOM_RIGHT && pos < CORNER_BOTTOM_LEFT){
            setInnerTile(pos,tile, Tile.ORIENTATION_SOUTH);
        }
        // West Row
        else if(pos > CORNER_BOTTOM_LEFT && pos < CORNER_TOP_LEFT){
            setInnerTile(pos,tile, Tile.ORIENTATION_WEST);
        }
        //North Row
        else if(pos > CORNER_TOP_LEFT && pos < SIZE){
            setInnerTile(pos,tile, Tile.ORIENTATION_NORTH);
        }
        else {
            System.out.println(pos);
            throw new ArrayIndexOutOfBoundsException("Invalid Board Position");
        }

    }


    private void setInnerTile(int pos, Tile tile, int orientation){


        TilePanel panel = tile.getTilePanel(orientation);


        if(orientation == Tile.ORIENTATION_EAST){

            gridBagConstraints.gridx = 11;
            gridBagConstraints.gridy = pos+1;

        }
        else if(orientation == Tile.ORIENTATION_SOUTH){

            gridBagConstraints.gridx = 9 - (pos % 10);
            gridBagConstraints.gridy = 10;


        }
        else if(orientation == Tile.ORIENTATION_WEST){

            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 9 - (pos % 19);

        }
        else if(orientation == Tile.ORIENTATION_NORTH){

            gridBagConstraints.gridx = pos-26;
            gridBagConstraints.gridy = 0;

        }

        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = 1;

        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagLayout.setConstraints(panel, gridBagConstraints);
        boardPanel.add(panel);

    }

    /**
     * This is a helper method that adds a corner tile to the game board UI.
     * IMPORTANT NOTE - This method assumes that the given tile is a corner tile.
     * @param pos the position of the tile
     * @param tile the corner tile to be added.
     */
    private void setCorner(int pos, Tile tile){

        TilePanel panel  = tile.getTilePanel(Tile.ORIENTATION_CORNER);

        gridBagConstraints.gridheight = 2;
        gridBagConstraints.gridwidth = 2;

        if(pos == CORNER_TOP_RIGHT){
            gridBagConstraints.gridx = 10;
            gridBagConstraints.gridy = 0;
        }
        else if(pos == CORNER_BOTTOM_RIGHT){
            gridBagConstraints.gridx = 10;
            gridBagConstraints.gridy = 10;
        }
        else if(pos == CORNER_BOTTOM_LEFT){
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 10;
        }
        else if(pos == CORNER_TOP_LEFT){
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
        }

        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagLayout.setConstraints(panel, gridBagConstraints);

        boardPanel.add(panel);
    }


    /**
     * This helper method determines if the given position is a corner.
     * @param pos the position of the tile
     * @return true if the tile in question is a corner tile.  false if the tile in question is an edge tile.
     */
    private boolean isCorner(int pos){
        return pos == CORNER_BOTTOM_LEFT || pos == CORNER_BOTTOM_RIGHT
                || pos == CORNER_TOP_RIGHT || pos == CORNER_TOP_LEFT;
    }

}
