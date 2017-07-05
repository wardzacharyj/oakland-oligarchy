package Game.Board;

import Game.Player;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

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
    private String tileGroup;


    public RailRoad(String name, Player owner, int purchaseCost, int mortgage, int[] rent, boolean isMonopoly, int tilePosition) {
        super(name,tilePosition);
        this.owner = owner;
        this.purchaseCost = purchaseCost;
        this.mortgage = mortgage;
        this.rent = rent;
        this.isMonopoly = isMonopoly;
        this.tilePosition = tilePosition;
    }


    /**
     *Notify player they have landed on property
     */
    @Override
    public void notifyPlayerLanded(Player p) {
        // Called when tiles player count is increased
    }

    /**
     *gets owner of property
     */
    public Player getOwner() {
        return owner;
    }

    /**
     *sets owner of property
     * @param owner
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     *gets property cost
     */
    public int getPurchaseCost() {
        return purchaseCost;
    }

    /**
     *sets purchase price
     * @param purchaseCost
     */
    public void setPurchaseCost(int purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    /**
     *gets mortgage value
     */
    public int getMortgage() {
        return mortgage;
    }

    /**
     *sets mortgage value
     * @param mortgage
     */
    public void setMortgage(int mortgage) {
        this.mortgage = mortgage;
    }

    /**
     *gets rent value
     *
     */
    public int getRent() {
        return rent[0];
    }

    /**
     *sets rent value
     * @param rent
     */
    public void setRent(int[] rent) {
        this.rent = rent;
    }

    /**
     *gets tile position on tile
     */
    @Override
    public int getBoardPosition(){
        return tilePosition;
    }


    @Override
    public JsonObject toJSONObject() {

        JsonArray rents = new JsonArray();
        for (Integer element : rent) {
            rents.add(new JsonPrimitive(element));
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("purchaseCost", purchaseCost);
        jsonObject.addProperty("mortgage", mortgage);
        jsonObject.add("rent", rents);
        jsonObject.addProperty("tileGroup","railroad");
        jsonObject.addProperty("mortgage", mortgage);
        jsonObject.addProperty("isMonopoly", isMonopoly);
        jsonObject.addProperty("purchaseCost", purchaseCost);
        jsonObject.addProperty("tilePosition", tilePosition);


        return jsonObject;
    }

    /**
     *stringify property info
     */
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
