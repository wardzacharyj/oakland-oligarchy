package Game.Board;

import Game.Player;
import javax.swing.*;
import java.awt.*;


public class TilePanel extends JPanel {

    private final Dimension BTN_NORTH_SOUTH = new Dimension(60,20);
    private final Dimension LABEL_NORTH_SOUTH = new Dimension(120, 20);

    private final Dimension BTN_EAST_WEST = new Dimension(20,60);
    private final Dimension LABEL_EAST_WEST = new Dimension(20, 120);

    private final Dimension CORNER = new Dimension(40,40);


    private int orientation;

    private Tile tile;
    private JButton tileButton;

    private JLabel nameLabel;
    private JLabel costLabel;


    public TilePanel(Tile tile,int orientation){
        this.tile = tile;
        this.orientation = orientation;

        initLayout();

    }

    protected void setOrientation(int orientation){
        removeAll();
        this.orientation = orientation;
        initLayout();
    }


    private void initLayout(){
        setLayout(new BorderLayout());

        if(tile instanceof Property){
            Property p = (Property) tile;
            tileButton = new JButton();
            tileButton.setBackground(p.getColor());
            tileButton.setOpaque(true);
            tileButton.setBorderPainted(false);

            JPanel holder = new JPanel(new GridLayout(2,1));
            nameLabel = new JLabel(p.getName());
            nameLabel.setHorizontalAlignment(JLabel.CENTER);
            costLabel = new JLabel(p.getPriceBanner());
            costLabel.setHorizontalAlignment(JLabel.CENTER);

            holder.add(nameLabel);
            holder.add(costLabel);

            switch (orientation){
                case Tile.ORIENTATION_CORNER:
                    // Property in Corner?
                    break;
                case Tile.ORIENTATION_NORTH:
                    holder.setPreferredSize(LABEL_NORTH_SOUTH);
                    tileButton.setPreferredSize(BTN_NORTH_SOUTH);
                    add(tileButton,BorderLayout.SOUTH);
                    break;
                case Tile.ORIENTATION_EAST:
                    holder.setPreferredSize(LABEL_EAST_WEST);
                    tileButton.setPreferredSize(BTN_EAST_WEST);
                    add(tileButton,BorderLayout.WEST);
                    break;
                case Tile.ORIENTATION_SOUTH:
                    holder.setPreferredSize(LABEL_NORTH_SOUTH);
                    tileButton.setPreferredSize(BTN_NORTH_SOUTH);
                    add(tileButton,BorderLayout.NORTH);
                    break;
                case Tile.ORIENTATION_WEST:
                    holder.setPreferredSize(LABEL_EAST_WEST);
                    tileButton.setPreferredSize(BTN_EAST_WEST);
                    add(tileButton,BorderLayout.EAST);
                    break;
                default:
                    break;
            }

            add(holder,BorderLayout.CENTER);

        }
        else if(tile instanceof ActionTile){
            nameLabel = new JLabel(tile.getName());
            nameLabel.setOpaque(false);
            nameLabel.setHorizontalAlignment(JLabel.CENTER);

            switch (orientation){
                case Tile.ORIENTATION_CORNER:
                    // Property in Corner?

                    break;
                case Tile.ORIENTATION_NORTH:
                    nameLabel.setPreferredSize(LABEL_NORTH_SOUTH);
                    break;
                case Tile.ORIENTATION_EAST:
                    nameLabel.setPreferredSize(LABEL_EAST_WEST);
                    break;
                case Tile.ORIENTATION_SOUTH:
                    nameLabel.setPreferredSize(LABEL_NORTH_SOUTH);
                    break;
                case Tile.ORIENTATION_WEST:
                    nameLabel.setPreferredSize(LABEL_EAST_WEST);
                    break;
                default:
                    break;
            }

            add(nameLabel,BorderLayout.CENTER);
        }







        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.setBackground(Color.GREEN);
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        int markerSize = 12;
        int[] x0 = new int[]{(getWidth()/4)-6, 3*(getWidth()/4)-6, (getWidth()/4)-6, 3*(getWidth()/4)-6};
        int[] y0 = new int[]{(getHeight()/4)-6, (getHeight()/4)-6, 3*(getHeight()/4)-6, 3*(getHeight()/4)-6};


        for (int i = 0; i < tile.getActivePlayers().size(); i++){
            Player p = tile.getActivePlayers().get(i);
            g2D.setColor(p.getColor());
            g.fillOval(x0[i],y0[i],markerSize,markerSize);
        }
    }


    public void drawPlayer(){

    }
}
