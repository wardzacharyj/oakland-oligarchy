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
        super(new BorderLayout());
        this.property = property;
        this.players = players;
        this.currentPlayer = this.getCurrentPlayer();
        this.propertyOwner = property.getOwner();
        this.setInfoLabel();
    }

    public void setProperty(Property property) {
        this.property = property;
        this.propertyOwner = property.getOwner();
        this.removeAllComponents();
        this.setInfoLabel();
    }

    /**
     * Sets up the JLabel and JButton to display the property information
     * TODO: Add white text outline to JLabel text.
     */
    private void setInfoLabel() {
        this.infoLabel.setText("<html><div style='text-align: center;  text-shadow: 2px 2px 0px #FFFFFF;'>"
                + property.propertyInfoToString() + "</div></html>");
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        this.setColor();
        currentPlayer = getCurrentPlayer();
        JButton sellButton = null;

        if (propertyOwner.equals(currentPlayer)) {
            tradeButton = new JButton("TRADE PROPERTY");
            sellButton = new JButton("SELL TO BANK");
            sellButton.addActionListener(e -> {
                int result = JOptionPane.showConfirmDialog(null,
                        ("<html><div style='text-align: center;'> "
                        + "Do you want to sell " + property.getName() + " to the bank for $"
                        + (property.getPurchaseCost() / 2) + "?</div></html>"));

                if (result == JOptionPane.YES_OPTION) {
                    currentPlayer.addCash(property.getPurchaseCost() / 2);
                    currentPlayer.removeProperty(property);
                    property.setForSale(true);
                }
            });
        } else {
            tradeButton = new JButton("TRADE FOR PROPERTY");
        }
        tradeButton.addActionListener(e -> {
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
        });

        this.addLabel();
        if (sellButton != null) {
            this.addSellButton(sellButton);
        }
        this.addTradeButton(tradeButton);
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

    public void removeAllComponents() {
        this.removeAll();
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
    public void addSellButton(JButton button) {
        this.add(button, BorderLayout.CENTER);
    }

    /**
     * Adds button to the panel.
     * TODO: Fix placement
     */
    public void addTradeButton(JButton button) {
        this.add(button, BorderLayout.SOUTH);
    }

    private Player getCurrentPlayer() {
        for (Player p : players) {
            if (p.isTurn()) {
                return p;
            }
        }
        return players[0];
    }

}
