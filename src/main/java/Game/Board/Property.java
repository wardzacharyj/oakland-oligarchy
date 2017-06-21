package Game.Board;

import Game.Player;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Created by Zach on 6/1/17.
 */
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


    private Color tileColor;

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
    }

    public Player getOwner() {
        return owner;
    }

    public int getHouseCount() {
        return houseCount;
    }

    public boolean isImproved() {
        return isImproved;
    }

    public boolean isMonopoly() {
        return isMonopoly;
    }

    public int getPurchaseCost() {
        return purchaseCost;
    }

    public String getTileGroup() {
        return tileGroup;
    }

    public int getTilePosition() {
        return tilePosition;
    }

    public Color getTileColor() {
        return tileColor;
    }

    @Override
    public void notifyPlayerLanded(Player p) {
        System.out.print(p.getName());
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