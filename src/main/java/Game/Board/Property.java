package Game.Board;

import Game.Player;
import Game.UI.PlayerListener;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Property extends Tile {

    public final static String JSON_NAME = "name";
    public final static String JSON_OWNER = "owner";
    public final static String JSON_HOUSE_COUNT = "houseCount";
    public final static String JSON_IMPROVEMENT_COST = "improvementCost";
    public final static String JSON_RENT = "rent";
    public final static String JSON_MORTGAGE = "mortgage";
    public final static String JSON_IS_IMPROVED = "isImproved";
    public final static String JSON_IS_MONOPOLY = "isMonopoly";
    public final static String JSON_PURCHASE_COST = "purchaseCost";
    public final static String JSON_TILE_GROUP = "tileGroup";
    public final static String JSON_TILE_POSITION = "tilePosition";


    private String name;
    private Player owner;
    private int houseCount; // 5th house is hotel
    private int improvementCost;
    private int[] rent;
    private int mortgage;
    private boolean isImproved;
    private boolean isMonopoly;
    private int purchaseCost;
    private String tileGroup;
    private int tilePosition;
    private boolean isForSale;


    private Color tileColor;

    /**
     * constructor for property
     * @param houseCount
     * @param improvementCost
     * @param isImproved
     * @param isMonopoly
     * @param mortgage
     * @param name
     * @param owner
     * @param purchaseCost
     * @param rent
     * @param tileGroup
     * @param tilePosition
     */
    public Property(String name, Player owner, int houseCount, int improvementCost,
                    int[] rent, int mortgage, boolean isImproved, boolean isMonopoly,
                    int purchaseCost, String tileGroup, int tilePosition) {
        super(name,tilePosition);
        this.name = name;
        this.owner = owner;
        this.houseCount = houseCount;
        this.improvementCost = improvementCost;
        this.rent = rent;
        this.mortgage = mortgage;
        this.isImproved = isImproved;
        this.isMonopoly = isMonopoly;
        this.purchaseCost = purchaseCost;
        this.tileGroup = tileGroup;
        this.tileColor = Color.decode(tileGroup);
        this.tilePosition = tilePosition;
        this.isForSale = true;
    }

    /**
     * gets owner
     */
    public Player getOwner() {
        return owner;
    }

    /**
     *get house count
     */
    public int getHouseCount() {
        return houseCount;
    }

    /**
     * checks if improved by player
     */
    public boolean isImproved() {
        return isImproved;
    }

    /**
     *checks monopoly
     */
    public boolean isMonopoly() {
        return isMonopoly;
    }

    /**
     *gets purchase cost
     */
    public int getPurchaseCost() {
        return purchaseCost;
    }

    /**
     *gets tile group
     */
    public String getTileGroup() {
        return tileGroup;
    }

    /**
     *gets tile position on board
     */
    public int getTilePosition() {
        return tilePosition;
    }

    /**
     *gets tile color
     */
    public Color getTileColor() {
        return tileColor;
    }

    public int getRent(){
        return rent[houseCount];
    }


    /**
     *sets the property to being bought
     * @param newOwner
     */
    public void setBought(Player newOwner) {
        this.isForSale = false;
        this.owner = newOwner;
    }

    /**
     *notifies player to either buy property or pay rent
     * @param p
     */
    @Override
    public void notifyPlayerLanded(Player p) {

        if (this.isForSale) {
            int dialogResult = JOptionPane.showConfirmDialog(null,
                    this.getName() + " is for sale. Would you like to purchase it?");

            if (dialogResult == JOptionPane.YES_OPTION) {
                if (p.hasEnoughCash(this.getPurchaseCost())) {
                    p.buyProperty(this);
                } else {
                    JOptionPane.showMessageDialog(null, "You don't have enough money!");
                }
            }
        }
        else {

            JOptionPane.showMessageDialog(null, "Thank you "
                    +p.getName() +" we hope you enjoy your stay here, that'll be $"+getRent());

            if (p.hasEnoughCash(this.getPurchaseCost())) {
                p.payRent(p, getRent());
            } else {
                JOptionPane.showMessageDialog(null, "You don't have enough money!");
            }



        }
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", owner=" + owner +
                ", houseCount=" + houseCount +
                ", improvementCost=" + improvementCost +
                ", rent=" + Arrays.toString(rent) +
                ", mortgage=" + mortgage +
                ", isImproved=" + isImproved +
                ", isMonopoly=" + isMonopoly +
                ", purchaseCost=" + purchaseCost +
                ", tileGroup='" + tileGroup + '\'' +
                ", tilePosition=" + tilePosition +
                ", tileColor=" + tileColor +
                '}';
    }

}