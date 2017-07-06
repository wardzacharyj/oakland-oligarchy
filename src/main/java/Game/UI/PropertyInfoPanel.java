package Game.UI;

import Game.Board.Property;
import Game.Player;

import javax.swing.*;

public class PropertyInfoPanel extends JPanel {
    private JLabel infoLabel = new JLabel();
    private Property property;
    private Player[] players;
    private Player currentPlayer;
    private Player propertyOwner;

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
