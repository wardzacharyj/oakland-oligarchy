package Game.Board;

import Game.Player;

import java.util.ArrayList;
import java.util.Arrays;


public class RailRoad extends Tile {

    public static final String JSON_NAME = "name";
    public static final String JSON_OWNER = "owner";
    public static final String JSON_PURCHASE_COST = "cost";
    public static final String JSON_MORTGAGE = "mortgage";
    public static final String JSON_RENT = "rent";
    public static final String JSON_IS_MONOPOLY = "isMonopoly";
    public static final String JSON_TILE_POSITION = "tilePosition";



    private Player owner;
    private int purchaseCost;
    private int mortgage;
    private int[] rent;
    private int tilePosition;
    private boolean isMonopoly;


    public RailRoad(String name, Player owner, int purchaseCost, int mortgage, int[] rent, boolean isMonopoly, int tilePosition) {
        super(name,tilePosition);
        this.owner = owner;
        this.purchaseCost = purchaseCost;
        this.mortgage = mortgage;
        this.rent = rent;
        this.isMonopoly = isMonopoly;
        this.tilePosition = tilePosition;
    }


    @Override
    public void notifyPlayerLanded(Player p) {
        // Called when tiles player count is increased
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(int purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    public int getMortgage() {
        return mortgage;
    }

    public void setMortgage(int mortgage) {
        this.mortgage = mortgage;
    }

    public int getRent() {
        return rent[0];
    }

    public void setRent(int[] rent) {
        this.rent = rent;
    }

    @Override
    public int getBoardPosition(){
        return tilePosition;
    }

    @Override
    public String toString() {
        return "RailRoad{" +
                "owner=" + owner +
                ", purchaseCost=" + purchaseCost +
                ", mortgage=" + mortgage +
                ", rent=" + Arrays.toString(rent) +
                ", tilePosition=" + tilePosition +
                '}';
    }
}
