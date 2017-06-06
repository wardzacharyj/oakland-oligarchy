package Game.Board;


import javax.swing.*;
import java.awt.*;

/**
 * Created by Zach on 6/1/17.
 */
public class Board {

    private final int SIZE = 36;
    private Tile[] tiles;
    private JFrame frame = new JFrame("Oakland Oligarchy");
    JPanel boardPanel;

    public Board() {
        boardPanel = new JPanel();
        tiles = new Tile[SIZE];
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        boardPanel.setLayout(gbl);

        //Initializing the tiles array.
        for (int i = 0; i < 36; i++) {
            tiles[i] = new Property("", i, new JPanel());
        }

        //initializing the game board panels.
        //bottom right corner.
        gbc.weightx = 2.0;
        gbc.weighty = 2.0;
        addTile(10, 10, 2, 2, gbl, gbc, boardPanel, tiles[0].getTilePanel());
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        //south row.
        addTile(9, 10, 1, 1, gbl, gbc, boardPanel, tiles[1].getTilePanel());
        addTile(8, 10, 1, 1, gbl, gbc, boardPanel, tiles[2].getTilePanel());
        addTile(7, 10, 1, 1, gbl, gbc, boardPanel, tiles[3].getTilePanel());
        addTile(6, 10, 1, 1, gbl, gbc, boardPanel, tiles[4].getTilePanel());
        addTile(5, 10, 1, 1, gbl, gbc, boardPanel, tiles[5].getTilePanel());
        addTile(4, 10, 1, 1, gbl, gbc, boardPanel, tiles[6].getTilePanel());
        addTile(3, 10, 1, 1, gbl, gbc, boardPanel, tiles[7].getTilePanel());
        addTile(2, 10, 1, 1, gbl, gbc, boardPanel, tiles[8].getTilePanel());
        //bottom left corner.
        gbc.weightx = 2.0;
        gbc.weighty = 2.0;
        addTile(0, 10, 2, 2, gbl, gbc, boardPanel, tiles[9].getTilePanel());
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        //west row.
        addTile(0, 9, 1, 1, gbl, gbc, boardPanel, tiles[10].getTilePanel());
        addTile(0, 8, 1, 1, gbl, gbc, boardPanel, tiles[11].getTilePanel());
        addTile(0, 7, 1, 1, gbl, gbc, boardPanel, tiles[12].getTilePanel());
        addTile(0, 6, 1, 1, gbl, gbc, boardPanel, tiles[13].getTilePanel());
        addTile(0, 5, 1, 1, gbl, gbc, boardPanel, tiles[14].getTilePanel());
        addTile(0, 4, 1, 1, gbl, gbc, boardPanel, tiles[15].getTilePanel());
        addTile(0, 3, 1, 1, gbl, gbc, boardPanel, tiles[16].getTilePanel());
        addTile(0, 2, 1, 1, gbl, gbc, boardPanel, tiles[17].getTilePanel());
        //upper left corner.
        gbc.weightx = 2.0;
        gbc.weighty = 2.0;
        addTile(0, 0, 2, 2, gbl, gbc, boardPanel, tiles[18].getTilePanel());
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        //north row.
        addTile(2, 0, 1, 1, gbl, gbc, boardPanel, tiles[19].getTilePanel());
        addTile(3, 0, 1, 1, gbl, gbc, boardPanel, tiles[20].getTilePanel());
        addTile(4, 0, 1, 1, gbl, gbc, boardPanel, tiles[21].getTilePanel());
        addTile(5, 0, 1, 1, gbl, gbc, boardPanel, tiles[22].getTilePanel());
        addTile(6, 0, 1, 1, gbl, gbc, boardPanel, tiles[23].getTilePanel());
        addTile(7, 0, 1, 1, gbl, gbc, boardPanel, tiles[24].getTilePanel());
        addTile(8, 0, 1, 1, gbl, gbc, boardPanel, tiles[25].getTilePanel());
        addTile(9, 0, 1, 1, gbl, gbc, boardPanel, tiles[26].getTilePanel());
        //upper right corner.
        gbc.weightx = 2.0;
        gbc.weighty = 2.0;
        addTile(10, 0, 2, 2, gbl, gbc, boardPanel, tiles[27].getTilePanel());
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        //east row.
        addTile(11, 2, 1, 1, gbl, gbc, boardPanel, tiles[28].getTilePanel());
        addTile(11, 3, 1, 1, gbl, gbc, boardPanel, tiles[29].getTilePanel());
        addTile(11, 4, 1, 1, gbl, gbc, boardPanel, tiles[30].getTilePanel());
        addTile(11, 5, 1, 1, gbl, gbc, boardPanel, tiles[31].getTilePanel());
        addTile(11, 6, 1, 1, gbl, gbc, boardPanel, tiles[32].getTilePanel());
        addTile(11, 7, 1, 1, gbl, gbc, boardPanel, tiles[33].getTilePanel());
        addTile(11, 8, 1, 1, gbl, gbc, boardPanel, tiles[34].getTilePanel());
        addTile(11, 9, 1, 1, gbl, gbc, boardPanel, tiles[35].getTilePanel());

        //adding text and buttons to all game tiles other than corners.
        //south row
        addTileInformation(tiles[1].getTilePanel(), 0, Color.black, "Hemingway's");
        addTileInformation(tiles[2].getTilePanel(), 0, Color.black, "");
        addTileInformation(tiles[3].getTilePanel(), 0, Color.black, "");
        addTileInformation(tiles[4].getTilePanel(), 0, Color.blue, "");
        addTileInformation(tiles[5].getTilePanel(), 0, Color.blue, "");
        addTileInformation(tiles[6].getTilePanel(), 0, Color.blue, "");
        addTileInformation(tiles[7].getTilePanel(), 0, Color.red, "");
        addTileInformation(tiles[8].getTilePanel(), 0, Color.red, "");

        //west row
        addTileInformation(tiles[10].getTilePanel(), 1, Color.red, "");
        addTileInformation(tiles[11].getTilePanel(), 1, Color.yellow, "");
        addTileInformation(tiles[12].getTilePanel(), 1, Color.yellow, "");
        addTileInformation(tiles[13].getTilePanel(), 1, Color.yellow, "");
        addTileInformation(tiles[14].getTilePanel(), 1, Color.orange, "");
        addTileInformation(tiles[15].getTilePanel(), 1, Color.orange, "");
        addTileInformation(tiles[16].getTilePanel(), 1, Color.orange, "");
        addTileInformation(tiles[17].getTilePanel(), 1, Color.green, "");

        //north row
        addTileInformation(tiles[19].getTilePanel(), 2, Color.green, "");
        addTileInformation(tiles[20].getTilePanel(), 2, Color.green, "");
        addTileInformation(tiles[21].getTilePanel(), 2, Color.GRAY, "");
        addTileInformation(tiles[22].getTilePanel(), 2, Color.GRAY, "");
        addTileInformation(tiles[23].getTilePanel(), 2, Color.GRAY, "");
        addTileInformation(tiles[24].getTilePanel(), 2, Color.PINK, "");
        addTileInformation(tiles[25].getTilePanel(), 2, Color.PINK, "");
        addTileInformation(tiles[26].getTilePanel(), 2, Color.PINK, "");

        //east row
        addTileInformation(tiles[28].getTilePanel(), 3, Color.WHITE, "");
        addTileInformation(tiles[29].getTilePanel(), 3, Color.WHITE, "");
        addTileInformation(tiles[30].getTilePanel(), 3, Color.WHITE, "");
        addTileInformation(tiles[31].getTilePanel(), 3, Color.CYAN, "");
        addTileInformation(tiles[32].getTilePanel(), 3, Color.CYAN, "");
        addTileInformation(tiles[33].getTilePanel(), 3, Color.CYAN, "");
        addTileInformation(tiles[34].getTilePanel(), 3, Color.magenta, "");
        addTileInformation(tiles[35].getTilePanel(), 3, Color.magenta, "");

        frame.add(boardPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setVisible(true);
    }

    /**
     * This method places panels on the GridBagLayout based on the parameters given.
     * @param x the x coordinate to place the panel.
     * @param y the y coordinate to place the panel.
     * @param height height of the panel in the GridBagLayout.
     * @param width width of the panel in the GridBagLayout.
     * @param l GridBagLayout object reference.
     * @param c GridBagConstraints object reference.
     * @param p JPanel that the new panel needs to be added to.
     * @param panelToAdd The JPanel that needs to be added.
     */
    protected void addTile(int x, int y, int height, int width, GridBagLayout l, GridBagConstraints c, JPanel p, JPanel panelToAdd) {
        c.gridx = x;
        c.gridy = y;
        c.gridheight = height;
        c.gridwidth = width;

        panelToAdd.setBorder(BorderFactory.createLineBorder(Color.black));
        c.fill = GridBagConstraints.BOTH;
        l.setConstraints(panelToAdd, c);
        p.add(panelToAdd);
    }

    /**
     * This method adds information to each tile, including a text field displaying the name of the property, and a button with a given color.
     * @param panel The panel that the text field, and button will be added to.
     * @param orientation An integer representing the orientation of the tile. 0 for north, 1 for east, 2 for south, and 3 for west.
     * @param c The color that the button will be displayed as.
     * @param tileName The name that will be added to the text field.
     */
    protected void addTileInformation(JPanel panel, int orientation, Color c, String tileName) {

        JButton b = new JButton("");
        b.setBackground(c);
        //JLabel label = new JLabel(tileName);
        JTextArea label = new JTextArea(tileName, 5, 5);
        label.setLineWrap(true);

        if(orientation == 0) {
            panel.setLayout(new BorderLayout());
            b.setPreferredSize(new Dimension(20, 30));
            panel.add(b, BorderLayout.NORTH);
            label.setPreferredSize(new Dimension(20, 60));
            label.setMaximumSize(new Dimension(20, 60));
            label.setMinimumSize(new Dimension(20, 60));
        }
        else if(orientation == 1) {
            panel.setLayout(new BorderLayout());
            b.setPreferredSize(new Dimension(30, 20));
            panel.add(b, BorderLayout.EAST);
            label.setPreferredSize(new Dimension(60, 20));
            label.setMaximumSize(new Dimension(60, 20));
            label.setMinimumSize(new Dimension(60, 20));
        }
        else if(orientation == 2) {
            panel.setLayout(new BorderLayout());
            b.setPreferredSize(new Dimension(20, 30));
            panel.add(b, BorderLayout.SOUTH);
            label.setPreferredSize(new Dimension(20, 60));
            label.setMaximumSize(new Dimension(20, 60));
            label.setMinimumSize(new Dimension(20, 60));
        }
        else if(orientation == 3) {
            panel.setLayout(new BorderLayout());
            b.setPreferredSize(new Dimension(30, 20));
            panel.add(b, BorderLayout.WEST);
            label.setPreferredSize(new Dimension(60, 20));
            label.setMaximumSize(new Dimension(60, 20));
            label.setMinimumSize(new Dimension(60, 20));
        }
        panel.add(label);
    }
}
