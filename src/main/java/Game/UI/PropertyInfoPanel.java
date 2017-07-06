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
        this.removeLabel();
        this.removeButton();
        this.setInfoLabel();
    }

    /**
     * Sets up the JLabel and JButton to display the property information
     * TODO: Add white text outline to JLabel text.
     */
    private void setInfoLabel() {
        this.infoLabel.setText("<html><div style='text-align: center;  text-shadow: 2px 2px 0px #FFFFFF;'>"
                + property.propertyInfoToString() + "</div></html>");
        this.setColor();
        currentPlayer = getCurrentPlayer();

        if (propertyOwner.equals(currentPlayer)) {
            tradeButton = new JButton("TRADE PROPERTY");
        } else {
            tradeButton = new JButton("TRADE FOR PROPERTY");
        }
        tradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TradePanel tradePanel = new TradePanel(currentPlayer, players, property);
                int result = 0;
                if (propertyOwner.equals(currentPlayer)) {
                    result = tradePanel.selectPlayer();
                }
                if (result != JOptionPane.CANCEL_OPTION) {
                    result = tradePanel.selectOffer();
                }

                if (result != JOptionPane.CANCEL_OPTION) {
                    tradePanel.showRequest();
                }
            }
        });

        this.addLabel();
        this.addButton();
    }

    private void setColor() {
        this.setOpaque(true);
        this.setBackground(this.property.getTileColor());
    }

    public void removeLabel() {
        this.remove(this.infoLabel);
    }

    public void removeButton() {
        this.remove(this.tradeButton);
    }

    /**
     * Adds label to the panel.
     * TODO: Fix placement
     */
    public void addLabel() {
        this.add(this.infoLabel, BorderLayout.NORTH);
    }

    /**
     * Adds button to the panel.
     * TODO: Fix placement
     */
    public void addButton() {
        this.add(this.tradeButton, BorderLayout.SOUTH);
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
