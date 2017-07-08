package Game.UI;

import Game.Board.Property;
import Game.Player;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PropertyInfoPanel extends JPanel {

    private PropertyPanel propertyPanel;
    private Player[] players;
    private Player currentPlayer;
    private Player propertyOwner;
    private JButton tradeButton;

    PropertyInfoPanel(Property property, Player[] players) {
        super(new GridBagLayout());
        this.propertyPanel = new PropertyPanel(property);
        this.players = players;
        this.currentPlayer = this.getCurrentPlayer();
        this.propertyOwner = property.getOwner();
        this.setInfoLabel();
    }

    public void setProperty(Property property) {
        this.propertyPanel = new PropertyPanel(property);
        this.propertyOwner = property.getOwner();
        this.removeAll();
    }

    /**
     * Sets up the JLabel and JButton to display the property information
     * TODO: Add white text outline to JLabel text.
     */
    private void setInfoLabel() {
        currentPlayer = getCurrentPlayer();
        JButton sellButton = null;

        if (propertyOwner.equals(currentPlayer)) {
            tradeButton = new JButton("TRADE PROPERTY");
            sellButton = new JButton("SELL TO BANK");
            sellButton.addActionListener(e -> {
                int result = JOptionPane.showConfirmDialog(null,
                        ("<html><div style='text-align: center;'> "
                        + "Do you want to sell " + this.propertyPanel.property.getName() + " to the bank for $"
                        + (this.propertyPanel.property.getPurchaseCost() / 2) + "?</div></html>"));

                if (result == JOptionPane.YES_OPTION) {
                    currentPlayer.addCash(this.propertyPanel.property.getPurchaseCost() / 2);
                    currentPlayer.removeProperty(this.propertyPanel.property);
                    this.propertyPanel.property.setForSale(true);
                }
            });
        } else {
            tradeButton = new JButton("TRADE FOR PROPERTY");
        }
        tradeButton.addActionListener(e -> {
            TradePanel tradePanel = new TradePanel(currentPlayer, players, this.propertyPanel.property);
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

        this.addPropertyPanel();
        if (sellButton != null) {
            this.addSellButton(sellButton);
        }
        this.addTradeButton(tradeButton);
        this.setVisible(true);
    }


    /**
     * Adds button to the panel.
     * TODO: Fix placement
     */
    public void addPropertyPanel() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        constraints.weightx = 0.5;

        this.add(propertyPanel, constraints);
    }

    /**
     * Adds button to the panel.
     * TODO: Fix placement
     */
    public void addSellButton(JButton button) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        this.add(button, constraints);
    }

    /**
     * Adds button to the panel.
     * TODO: Fix placement
     */
    public void addTradeButton(JButton button) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        this.add(button, constraints);
    }

    private Player getCurrentPlayer() {
        for (Player p : players) {
            if (p.isTurn()) {
                return p;
            }
        }
        return players[0];
    }

    private class PropertyPanel extends JPanel {
        protected JLabel infoLabel;
        protected Property property;
        protected JPanel colorBar;

        PropertyPanel(Property property) {
            super(new GridBagLayout());
            this.infoLabel = new JLabel();
            this.property = property;
            this.colorBar = new JPanel();
            this.setup();
        }

        private void setup() {
            setupColorBar();
            setupLabel();

            this.setPreferredSize(new Dimension(140, 160));
            this.setMaximumSize(this.getPreferredSize());

            this.setBorder(LineBorder.createBlackLineBorder());
        }

        private void setupLabel() {
            this.infoLabel.setText("<html><div style='text-align: center;'>" +
                    this.property.propertyInfoToString() + "</div></html>");

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = 1;
            constraints.weightx = 1.0;
            constraints.weighty = 1.0;

            this.add(this.infoLabel, constraints);
        }

        private void setupColorBar() {
            this.colorBar.setBackground(this.property.getTileColor());
            this.colorBar.setBorder(LineBorder.createBlackLineBorder());
            this.colorBar.setPreferredSize(new Dimension(140, 20));

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.weightx = 1.0;
            constraints.weighty = 1.0;
            constraints.fill = GridBagConstraints.BOTH;

            this.add(this.colorBar, constraints);
        }


    }

}
