package Game.UI;

import Game.Board.Property;
import Game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PropertyInfoPanel extends JPanel {
    private JLabel infoLabel = new JLabel();
    private Property property;
    private Player[] players;
    private Player currentPlayer;
    private Player propertyOwner;
    private JButton tradeButton;

    PropertyInfoPanel(Property property, Player[] players) {
        super();
        this.property = property;
        this.players = players;
        this.currentPlayer = this.getCurrentPlayer();
        this.propertyOwner = property.getOwner();
        this.setInfoLabel();
    }

    public void setProperty(Property property) {
        this.property = property;
        this.propertyOwner = property.getOwner();
        this.setInfoLabel();
    }


    private void setInfoLabel() {
        this.infoLabel.setText("<html><div style='text-align: center;  text-shadow: 2px 2px 0px #FFFFFF;'>"
                + property.propertyInfoToString() + "</div></html>");
        this.setColor();
        this.addLabel();

        if (propertyOwner.equals(currentPlayer)) {
            tradeButton = new JButton("TRADE PROPERTY");
        } else {
            tradeButton = new JButton("TRADE FOR PROPERTY");
        }
        tradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel playerSelectPanel = new JPanel();
                JLabel selectLabel = new JLabel("Please select the player you would like to trade with:");
                DefaultComboBoxModel<Player> playerSelection = new DefaultComboBoxModel<>();
                for (Player p : players) {
                    if (!p.equals(currentPlayer)) {
                        playerSelection.addElement(p);
                    }
                }
                JComboBox<Player> playerSelectionBox = new JComboBox<>(playerSelection);
                playerSelectPanel.add(selectLabel);
                playerSelectPanel.add(playerSelectionBox);
                int iResult = JOptionPane.showConfirmDialog(null, playerSelectPanel,
                        "Trade", JOptionPane.OK_CANCEL_OPTION);
                if (propertyOwner.equals(currentPlayer)) {

                } else {

                }
            }
        });
        this.add(tradeButton, BorderLayout.SOUTH);
    }

    private void setColor() {
        this.setOpaque(true);
        this.setBackground(this.property.getTileColor());
    }

    public void removeLabel() {
        this.remove(this.infoLabel);
    }

    public void addLabel() {
        this.add(this.infoLabel);
    }

    private Player getCurrentPlayer() {
        for (Player p : players) {
            if (p.isTurn()) {
                return p;
            }
        }
        return null;
    }

}
