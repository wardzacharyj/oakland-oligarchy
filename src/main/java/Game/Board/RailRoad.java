package Game.Board;

import Game.Player;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

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


    /**
     * Railroad Constructor.
     *
     * @param name         Name of the railroad.
     * @param owner        Owner of the railroad.
     * @param purchaseCost Cost to purchase the railroad.
     * @param mortgage     Mortgage value.
     * @param rent         Rent cost.
     * @param isMonopoly   If the property is a monopoly or not.
     * @param tilePosition Position of the railroad on the board.
     */
    public RailRoad(String name, Player owner, int purchaseCost, int mortgage, int[] rent, boolean isMonopoly, int tilePosition) {
        super(name, tilePosition);
        this.owner = owner;
        this.purchaseCost = purchaseCost;
        this.mortgage = mortgage;
        this.rent = rent;
        this.isMonopoly = isMonopoly;
        this.tilePosition = tilePosition;
    }


    /**
     * Notification that a player has landed on the railroad.
     *
     * @param p Player that has landed on the railroad.
     */
    @Override
    public void notifyPlayerLanded(Player p) {
        // Called when tiles player count is increased
    }

    /**
     * Get owner of railroad.
     *
     * @return The owner of the railroad.
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * sets owner of property
     *
     * @param owner The new owner of the railroad.
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Get the purchase cost of the railroad.
     *
     * @return The cost to purchase the railroad.
     */
    public int getPurchaseCost() {
        return this.purchaseCost;
    }

    /**
     * sets purchase price
     *
     * @param purchaseCost The new purchase cost of the railroad.
     */
    public void setPurchaseCost(int purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    /**
     * gets mortgage value
     *
     * @return The mortgage value of the property.
     */
    public int getMortgage() {
        return this.mortgage;
    }

    /**
     * sets mortgage value
     *
     * @param mortgage The new mortgage value of the property.
     */
    public void setMortgage(int mortgage) {
        this.mortgage = mortgage;
    }

    /**
     * gets rent value
     *
     * @return The rent cost of the property.
     */
    public int getRent() {
        return this.rent[0];
    }

    /**
     * sets rent value
     *
     * @param rent The new rent cost of the property.
     */
    public void setRent(int[] rent) {
        this.rent = rent;
    }

    /**
     * gets tile position on tile
     */
    @Override
    public int getBoardPosition() {
        return this.tilePosition;
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
     * A string representation of the railroad.
     *
     * @return A string containing properties of the railroad.
     */
    @Override
    public String toString() {
        return "RailRoad{" +
                "owner=" + this.owner +
                ", purchaseCost=" + this.purchaseCost +
                ", mortgage=" + this.mortgage +
                ", rent=" + Arrays.toString(this.rent) +
                ", tilePosition=" + this.tilePosition +
                '}';
    }
}
