package Game.Board;

import Game.Player;
import javax.swing.*;
import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Created by rad85000 on 7/5/2017.
 */
public class Card {
    private String name = "";


    Card(String name) {
        this.name = name;
    }

    /**
     * Anytime someone draws a card, this method is called to have the card take effect in game. The player is notified of the card they drew, and what it does.
     * @param p the player that drew the card.
     * @param tiles the game tiles that may need to be updated, depending on the card that was drawn.
     */
    public void cardEffectAndNotification(Player p, Tile[] tiles) {
        if(name.equals("Go Back 3 Spaces")) {
            int oldPosition = p.getPosition();
            int newPosition = p.getPosition() - 3;

            tiles[oldPosition].removePlayer(p);
            tiles[newPosition].addPlayer(p);
            tiles[newPosition].getTilePanel().repaint();
            tiles[oldPosition].getTilePanel().repaint();

            p.setPosition(newPosition);

            JOptionPane.showMessageDialog(new JPanel(), p.getName() + " drew 'Go Back 3 Spaces'");

            tiles[newPosition].notifyPlayerLanded(p);
        }
        else if(name.equals("Make General Repairs")) {
            JOptionPane.showMessageDialog(new JPanel(), p.getName() + " drew 'Make General Repairs'.  Pay $25 per property owned.");
            if(p.getOwnedProperties() == null || p.getOwnedProperties().length == 0) {
                // There are no properties to make repairs to.  This is intentionally left blank.
            }
            else {
                if(p.hasEnoughCash(p.getOwnedProperties().length * 25)) {
                    p.subtractCash(p.getOwnedProperties().length * 25);
                }
                else {
                    p.sellPropertiesFor(p.getOwnedProperties().length * 25);
                }
            }

        }
        else if(name.equals("Advance To Pitt Start")) {
            int pos = p.getPosition();
            tiles[pos].removePlayer(p);
            tiles[0].addPlayer(p);
            tiles[0].getTilePanel().repaint();
            tiles[pos].getTilePanel().repaint();
            p.setPosition(0);
            JOptionPane.showMessageDialog(new JPanel(), p.getName() + " drew 'Advance To Pitt Start'.");
            tiles[0].notifyPlayerLanded(p);
        }
        else if(name.equals("Bank Pays")) {
            SecureRandom sr = new SecureRandom();
            int temp = Math.abs(sr.nextInt()) % 3;
            int amount = 0;
            switch(temp) {
                case 0:
                    amount = 50;
                    break;
                case 1:
                    amount = 100;
                    break;
                case 2:
                    amount = 200;
                    break;
                default:
                    amount = 0;
                    break;
            }

            p.addCash(amount);

            JOptionPane.showMessageDialog(new JPanel(), p.getName() + " drew 'Bank Pays'.  The bank has paid you $" + amount);
        }
        else if(name.equals("Doctor Fee")) {
            JOptionPane.showMessageDialog(new JPanel(), p.getName() + " drew 'Doctor's Fee'.  You need to go to the Doctor's office for a checkup.  The copay is $50");
            if(p.hasEnoughCash(50)) {
                p.subtractCash(50);
            }
            else {
                p.sellPropertiesFor(50);
            }
        }
        else if(name.equals("Tax Refund")) {
            JOptionPane.showMessageDialog(new JPanel(), p.getName() + " drew 'Tax Refund'.  The federal government has issued you an income tax refund of $50");
            p.addCash(50);
        }
        else if(name.equals("Hospital Fee")) {
            JOptionPane.showMessageDialog(new JPanel(), p.getName() + " drew 'Hospital Fee'.  You broke your leg and need to go to the hospital.  The hospital fee is $100");
            if(p.hasEnoughCash(100)) {
                p.subtractCash(100);
            }
            else {
                p.sellPropertiesFor(100);
            }
        }
        else if(name.equals("Go to Jail")) {
            tiles[9].addPlayer(p);
            tiles[p.getPosition()].removePlayer(p);
            tiles[9].getTilePanel().repaint();
            tiles[p.getPosition()].getTilePanel().repaint();
            p.setPosition(9);
            JOptionPane.showMessageDialog(new JPanel(), p.getName() + " drew 'Go to Jail'.");
            p.setInJail(true);
        }
        else if(name.equals("Go to 61 D")) {
            int old = p.getPosition();
            int newP = 32;

            tiles[old].removePlayer(p);
            tiles[newP].addPlayer(p);
            tiles[newP].getTilePanel().repaint();
            tiles[old].getTilePanel().repaint();

            p.setPosition(newP);

            JOptionPane.showMessageDialog(new JPanel(), p.getName() + " drew 'Go to 61 D'");

            tiles[newP].notifyPlayerLanded(p);
        }
        else if(name.equals("Property Rent Doubles")) {
            SecureRandom sr = new SecureRandom();
            ArrayList<Property> props = p.getProperties();
            if(props != null && props.size() > 0) {
                int propNum = Math.abs(sr.nextInt()) % props.size();
                int[] rentArray = props.get(propNum).getRentArray();
                for(int i = 0; i < rentArray.length; i++) {
                    rentArray[i] = rentArray[i] * 2;
                }
                props.get(propNum).setRent(rentArray);
                JOptionPane.showMessageDialog(new JPanel(), p.getName() + " drew 'Property Rent Doubles'.  Rent for " + p.getProperties().get(propNum).getName() + " has doubled.");
            }
            else {
                JOptionPane.showMessageDialog(new JPanel(), p.getName() + " drew 'Property Rent Doubles'.  You don't own any properties, card does not apply.");
            }
        }
        else if(name.equals("Pay the Homeless")) {
            JOptionPane.showMessageDialog(new JPanel(), p.getName() + " drew 'Pay the Homeless'.  You lose $100.");
            if(p.hasEnoughCash(100)) {
                p.subtractCash(100);
            }
            else {
                p.sellPropertiesFor(100);
            }
        }
        else if(name.equals("Pay School Fees")) {
            JOptionPane.showMessageDialog(new JPanel(), p.getName() + " drew 'Pay School Fees'.  You lose $100.");
            if(p.hasEnoughCash(100)) {
                p.subtractCash(100);
            }
            else {
                p.sellPropertiesFor(100);
            }
        }
        else if(name.equals("Happy Birthday")) {
            JOptionPane.showMessageDialog(new JPanel(), p.getName() + " drew 'Happy Birthday'.  You gain $100.");
            p.addCash(100);
        }
        else if(name.equals("Merry Christmas")) {
            JOptionPane.showMessageDialog(new JPanel(), p.getName() + " drew 'Merry Christmas'.  You gain $150.");
            p.addCash(150);
        }
    }
}
