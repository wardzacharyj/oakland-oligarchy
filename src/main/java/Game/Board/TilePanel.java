package Game.Board;

import Game.Player;

import javax.swing.*;
import java.awt.*;


public class TilePanel extends JPanel {

    private final Dimension BTN_NORTH_SOUTH = new Dimension(60, 20);
    private final Dimension LABEL_NORTH_SOUTH = new Dimension(120, 20);

    private final Dimension BTN_EAST_WEST = new Dimension(20, 60);
    private final Dimension LABEL_EAST_WEST = new Dimension(20, 120);

    private final Dimension CORNER = new Dimension(40, 40);


    private int orientation;

    private Tile tile;
    private JButton tileButton;

    private JLabel nameLabel;
    private JLabel costLabel;


    /**
     * Constructor for TilePanel.
     *
     * @param tile        The tile this TilePanel will contain.
     * @param orientation The orientation of the tile panel.
     */
    public TilePanel(Tile tile, int orientation) {
        this.tile = tile;
        this.orientation = orientation;

        this.initLayout();

    }

    /**
     * Sets the orientation of the panel.
     *
     * @param orientation An integer representation the orientation of the panel.
     */
    protected void setOrientation(int orientation) {
        this.removeAll();
        this.orientation = orientation;
        this.initLayout();
    }


    /**
     * EDIT THIS METHOD TO ADD DISPLAY INFORMATION TO TILES.
     * Initializes the layout of this tile.  This includes adding Layouts, JButtons, and JLabels, depending on the type of Tile, to this TilePanel.
     */
    private void initLayout() {
        this.setLayout(new BorderLayout());

        if (tile instanceof Property) {
            Property p = (Property) tile;
            this.tileButton = new JButton();
            this.tileButton.setBackground(p.getTileColor());
            this.tileButton.setOpaque(true);
            this.tileButton.setBorderPainted(false);

            JPanel holder = new JPanel(new GridLayout(2, 1));
            this.nameLabel = new JLabel(p.getName());
            this.nameLabel.setHorizontalAlignment(JLabel.CENTER);
            this.costLabel = new JLabel("$" + p.getPurchaseCost());
            this.costLabel.setHorizontalAlignment(JLabel.CENTER);

            holder.add(this.nameLabel);
            holder.add(this.costLabel);

            switch (this.orientation) {
                case Tile.ORIENTATION_CORNER:
                    // Property in Corner?
                    break;
                case Tile.ORIENTATION_NORTH:
                    holder.setPreferredSize(LABEL_NORTH_SOUTH);
                    this.tileButton.setPreferredSize(BTN_NORTH_SOUTH);
                    add(this.tileButton, BorderLayout.SOUTH);
                    break;
                case Tile.ORIENTATION_EAST:
                    holder.setPreferredSize(LABEL_EAST_WEST);
                    this.tileButton.setPreferredSize(BTN_EAST_WEST);
                    add(this.tileButton, BorderLayout.WEST);
                    break;
                case Tile.ORIENTATION_SOUTH:
                    holder.setPreferredSize(LABEL_NORTH_SOUTH);
                    this.tileButton.setPreferredSize(BTN_NORTH_SOUTH);
                    add(this.tileButton, BorderLayout.NORTH);
                    break;
                case Tile.ORIENTATION_WEST:
                    holder.setPreferredSize(LABEL_EAST_WEST);
                    this.tileButton.setPreferredSize(BTN_EAST_WEST);
                    add(this.tileButton, BorderLayout.EAST);
                    break;
                default:
                    break;
            }

            add(holder, BorderLayout.CENTER);

        }
        if (tile instanceof RailRoad) {
            RailRoad p = (RailRoad) this.tile;

            this.nameLabel = new JLabel(this.tile.getName());
            this.nameLabel.setOpaque(false);
            this.nameLabel.setHorizontalAlignment(JLabel.CENTER);

            switch (this.orientation) {
                case Tile.ORIENTATION_CORNER:
                    // Property in Corner?

                    break;
                case Tile.ORIENTATION_NORTH:
                    this.nameLabel.setPreferredSize(LABEL_NORTH_SOUTH);
                    break;
                case Tile.ORIENTATION_EAST:
                    this.nameLabel.setPreferredSize(LABEL_EAST_WEST);
                    break;
                case Tile.ORIENTATION_SOUTH:
                    this.nameLabel.setPreferredSize(LABEL_NORTH_SOUTH);
                    break;
                case Tile.ORIENTATION_WEST:
                    this.nameLabel.setPreferredSize(LABEL_EAST_WEST);
                    break;
                default:
                    break;
            }

            add(this.nameLabel, BorderLayout.CENTER);

        } else if (this.tile instanceof ActionTile) {
            this.nameLabel = new JLabel(tile.getName());
            this.nameLabel.setOpaque(false);
            this.nameLabel.setHorizontalAlignment(JLabel.CENTER);

            switch (this.orientation) {
                case Tile.ORIENTATION_CORNER:
                    // Property in Corner?

                    break;
                case Tile.ORIENTATION_NORTH:
                    this.nameLabel.setPreferredSize(LABEL_NORTH_SOUTH);
                    break;
                case Tile.ORIENTATION_EAST:
                    this.nameLabel.setPreferredSize(LABEL_EAST_WEST);
                    break;
                case Tile.ORIENTATION_SOUTH:
                    this.nameLabel.setPreferredSize(LABEL_NORTH_SOUTH);
                    break;
                case Tile.ORIENTATION_WEST:
                    this.nameLabel.setPreferredSize(LABEL_EAST_WEST);
                    break;
                default:
                    break;
            }

            add(this.nameLabel, BorderLayout.CENTER);
        }


        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    /**
     * This method is being overridden to show the player icons on the board.
     *
     * @param g The Graphics to paint.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.setBackground(Color.GREEN);
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        int markerSize = 12;
        int[] x0 = new int[]{(getWidth() / 4) - 6, 3 * (getWidth() / 4) - 6, (getWidth() / 4) - 6, 3 * (getWidth() / 4) - 6};
        int[] y0 = new int[]{(getHeight() / 4) - 6, (getHeight() / 4) - 6, 3 * (getHeight() / 4) - 6, 3 * (getHeight() / 4) - 6};


        for (int i = 0; i < tile.getActivePlayers().size(); i++) {
            Player p = tile.getActivePlayers().get(i);
            g2D.setColor(p.getColor());
            g.fillOval(x0[i], y0[i], markerSize, markerSize);
        }
    }


    public void drawPlayer() {

    }
}
