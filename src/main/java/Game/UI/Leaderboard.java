package Game.UI;

import Game.Board.Property;
import Game.Player;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

/**
 * Created by Zach on 6/6/17.
 */
public class Leaderboard extends JPanel implements PlayerListener {

    private Player[] players;
    private DefaultMutableTreeNode root;
    private JTree tree;


    /**
     * Initializes the Leaderboard panel to display information on all of the players.
     * @param players A list of current players.
     */
    Leaderboard(Player[] players){
        this.players = players;

        setLayout(new BorderLayout());
        root = new DefaultMutableTreeNode("LeaderBoards");

        for(Player p : players){
            DefaultMutableTreeNode playerFolder = new DefaultMutableTreeNode(p);
            root.add(playerFolder);
            p.setNode(playerFolder);

            DefaultMutableTreeNode propertyFolder = new DefaultMutableTreeNode("Properties");
            DefaultMutableTreeNode sellingFolder = new DefaultMutableTreeNode("Selling");

            playerFolder.add(propertyFolder);
            playerFolder.add(sellingFolder);

        }



        //create the tree by passing in the root node
        tree = new JTree(root);

        for(int i = 0; i < tree.getRowCount(); i++){
            tree.expandRow(i);
        }

        tree.setCellRenderer(new LeaderBoardRenderer());
        tree.setRootVisible(false);
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.add(tree,BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setViewportView(content);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane,BorderLayout.CENTER);
    }

    class LeaderBoardRenderer extends DefaultTreeCellRenderer {
        // ../../../resources/ic_person_black_24dp_1x.png
        private final ImageIcon player = createImageIcon("","");
        private final ImageIcon propertyFolder = createImageIcon(".","");
        private final ImageIcon sellFolder = createImageIcon("","");
        private final ImageIcon property = createImageIcon("","");

        LeaderBoardRenderer(){

        }

        public Component getTreeCellRendererComponent(
                JTree tree,
                Object obj,
                boolean sel,
                boolean expanded,
                boolean leaf,
                int row,
                boolean hasFocus) {

            super.getTreeCellRendererComponent(tree, obj, sel,
                    expanded, leaf, row, hasFocus);

            DefaultMutableTreeNode node = (DefaultMutableTreeNode)obj;
            Object nodeInfo = node.getUserObject();

            if(nodeInfo instanceof Player){
                setIcon(player);
            } else if (nodeInfo instanceof String){
                String title = (String) nodeInfo;
                if (title.equals("Properties"))
                    setIcon(propertyFolder);
                else
                    setIcon(sellFolder);
            } else if (nodeInfo instanceof Property){
                setIcon(property);
            }


            setToolTipText(null);


            return this;
        }

        private ImageIcon createImageIcon(String path, String description) {
            java.net.URL imgURL = getClass().getClassLoader().getResource(path);
            if (imgURL != null) {
                return new ImageIcon(imgURL, description);
            } else {
                System.err.println("Couldn't find file: " + path);
                return null;
            }
        }


    }


    public void onPlayerMove(Player p) {

    }

    @Override
    public void onTrade() {

    }

    /**
     *update tree with updated player object
     */
    @Override
    public void onPurchase(Player p) {
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode propertyNode = (DefaultMutableTreeNode) p.getNode().getFirstChild();

        model.nodesWereInserted(propertyNode, new int[]{propertyNode.getChildCount() - 1});
    }

    @Override
    public void onLose() {

    }
}
