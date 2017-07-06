package Game.UI;

import Game.Board.Property;
import Game.Player;

import javax.swing.*;

public class TradePanel extends JPanel {

    private Player currentPlayer;
    private Player selectedPlayer;
    private Player[] players;
    private Property property;
    private JLabel label;

    TradePanel(Player currentPlayer, Player[] players, Property property) {
        super();
        this.currentPlayer = currentPlayer;
        this.players = players;
        this.property = property;
        this.label = new JLabel();
    }

    public int selectPlayer() {
        label.setText("Please select the player you would like to trade with:");
        DefaultComboBoxModel<Player> playerSelection = new DefaultComboBoxModel<>();
        for (Player p : this.players) {
            if (!p.equals(this.currentPlayer)) {
                playerSelection.addElement(p);
            }
        }
        JComboBox<Player> playerSelectionBox = new JComboBox<>(playerSelection);
        this.add(label);
        this.add(playerSelectionBox);
        int result = JOptionPane.showConfirmDialog(null, this,
                "Trade", JOptionPane.OK_CANCEL_OPTION);
        this.selectedPlayer = (Player) playerSelectionBox.getSelectedItem();
        return result;
    }

    public int selectOffer() {
        if (property.getOwner().equals(currentPlayer)) {
            this.label.setText("Please select what you would like for your property:");
        } else {
            this.label.setText("Please select your offer for the property:");
        }

        int result = JOptionPane.showConfirmDialog(null, this,
                "Trade", JOptionPane.OK_CANCEL_OPTION);
        return result;
    }

}
