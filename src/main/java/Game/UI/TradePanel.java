package Game.UI;

import Game.Board.Property;
import Game.Player;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class TradePanel extends JPanel {

    private Player currentPlayer;
    private Player selectedPlayer;
    private Player[] players;
    private Property property;
    private JLabel label;
    private int moneyOffer;
    private Property propertyOffer;
    private boolean isOwner;

    TradePanel(Player currentPlayer, Player[] players, Property property) {
        super();
        this.currentPlayer = currentPlayer;
        this.players = players;
        this.property = property;
        this.label = new JLabel();
    }

    /**
     * Shows the panel to select the player to trade with.
     * TODO: currentPlayer seems somewhat finnicky. Need to look into it.
     * @return  The result of the JOptionPane. OK_OPTION or CANCEL_OPTION
     */
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
        this.removeAll();
        return result;
    }

    /**
     * TODO: TRADING MULTIPLE PROPERTIES DOES NOT WORK. NEED TO SWITCH TO A JPANEL INSTEAD OF USING BUILTIN JOPTIONPANE
     * @return
     */
    public int selectOffer() {
        JComboBox<Object> properties;
        JComboBox<Object> money;
        isOwner = property.getOwner().equals(currentPlayer);
        if (isOwner) {
            this.label.setText("Please select what you would like for your property:");
            if (!currentPlayer.getProperties().isEmpty()) {
                properties = new JComboBox<>(selectedPlayer.getProperties().toArray());
            } else {
                properties = new JComboBox<>();
            }
            ArrayList<Integer> moneyList = new ArrayList<>();
            for (int cash = 0; cash <= selectedPlayer.getCash(); cash += 50) {
                moneyList.add(cash);
            }
            money = new JComboBox<>(moneyList.toArray());
        } else {
            this.label.setText("Please select your offer for the property:");
            if (!currentPlayer.getProperties().isEmpty()) {
                properties = new JComboBox<>(currentPlayer.getProperties().toArray());
            } else {
                properties = new JComboBox<>();
            }

            ArrayList<Integer> moneyList = new ArrayList<>();
            for (int cash = 0; cash <= currentPlayer.getCash(); cash += 50) {
                moneyList.add(cash);
            }
            money = new JComboBox<>(moneyList.toArray());
        }
        this.add(this.label);
        this.add(money);
        properties.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (e.getItem() instanceof Property) {
                        Property p = (Property) e.getItem();
                        ArrayList<Property> newPropertyList;
                        JComboBox<Object> newComboBox;
                        if (isOwner) {
                            newPropertyList = new ArrayList<>(selectedPlayer.getProperties());
                        } else {
                            newPropertyList = new ArrayList<>(currentPlayer.getProperties());
                        }
                        newPropertyList.remove(p);
                        newComboBox = new JComboBox<>(newPropertyList.toArray());
                        newComboBox.addItemListener(this);
                        newComboBox.insertItemAt("", 0);
                        add(newComboBox);
                        revalidate();
                        repaint();
                    }

                }
            }
        });
        properties.insertItemAt("", 0);
        this.add(properties);


        int result = JOptionPane.showConfirmDialog(null, this,
                "Trade", JOptionPane.OK_CANCEL_OPTION);
        this.moneyOffer = (Integer) money.getSelectedItem();
        if (properties.getSelectedItem() instanceof Property) {
            this.propertyOffer = (Property) properties.getSelectedItem();
        }

        this.removeAll();
        return result;
    }

    public void showRequest() {
        if (propertyOffer != null) {
            this.label.setText("<html><div style='text-align: center;'> " + currentPlayer.getName() + " is offering you $"
                    + moneyOffer + " and " + propertyOffer.toString()
                    + "<br /> FOR <br />" + property.getName() + "</div></html>");
        } else {
            this.label.setText("<html><div style='text-align: center;'> " + currentPlayer.getName() + " is offering you $"
                    + moneyOffer + "<br /> FOR <br />" + property.getName() + "</div></html>");
        }

        this.add(label);
        int result = JOptionPane.showConfirmDialog(null, this,
                currentPlayer.getName() + "'s Offer", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            if (isOwner) {
                selectedPlayer.buyProperty(property, moneyOffer);
                currentPlayer.removeProperty(property);
                currentPlayer.addCash(moneyOffer);
                if (propertyOffer != null) {
                    currentPlayer.buyProperty(propertyOffer, 0);
                    selectedPlayer.removeProperty(propertyOffer);
                }
            } else {
                currentPlayer.buyProperty(property, moneyOffer);
                selectedPlayer.removeProperty(property);
                selectedPlayer.addCash(moneyOffer);
                if (propertyOffer != null) {
                    selectedPlayer.buyProperty(propertyOffer, 0);
                    currentPlayer.removeProperty(propertyOffer);
                }
            }

        }
    }

}
