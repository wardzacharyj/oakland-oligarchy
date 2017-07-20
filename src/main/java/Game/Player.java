package Game;

import Game.Board.Board;
import Game.Board.Property;
import Game.Board.Tile;
import Game.UI.PlayerListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Player {


    private String name;
    private int currentPosition;
    private int previousPosition;
    private int cash;
    private ArrayList<Tile> properties;
    private Color color;
    private PlayerListener listener;
    private DefaultMutableTreeNode playerNode;
    private boolean isTurn;
    private boolean inJail;

    public Player(){
        this.properties = new ArrayList<>();
    }

    /**
     * Player constructor
     * @param name
     * @param color
     */
    public Player(String name, Color color) {
        this.name = name;
        this.properties = new ArrayList<>();
        this.color = color;
        this.inJail = false;
    }

    /**
     * Player constructor
     *
     * @param name            A string containing the name of the player.
     * @param color           A Color object defining the color of the player.
     * @param cash            The amount of money the player has.
     * @param currentPosition The current position of the player on the board.
     */
    public Player(String name, Color color, int cash, int currentPosition) {
        this.name = name;
        this.properties = new ArrayList<>();
        this.color = color;
        this.cash = cash;
        this.currentPosition = currentPosition;
        this.previousPosition = currentPosition;
        this.isTurn = false;
        this.inJail = false;
    }

    /**
     * Adds player listener to the player.
     *
     * @param listener The PlayerListener object to attach to the player.
     */
    public void addListener(PlayerListener listener) {
        this.listener = listener;
    }



    /**
     * Get the player's color.
     *
     * @return A color object containing the color of the player
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Set the color of the player on the board.
     *
     * @param color A Color object containing the color of the player.
     */
    public void setColor(Color color) {
        this.color = color;
    }



    /**
     * Updates player objects positions
     *
     * @param moveBy The amount of spaces the player must move by.
     */
    public void move(int moveBy) {
        int newPosition = this.currentPosition + moveBy;

        if (newPosition < Board.SIZE) {
            this.setPosition(newPosition);
        } else {
            int loopedPosition = newPosition - Board.SIZE;
            this.setPosition(loopedPosition);
        }
    }


    /**
     * Get the player's current position on the board.
     *
     * @return The current position of the player on the board.
     */
    public int getPosition() {
        return this.currentPosition;
    }

    /**
     * Set the position of the player.
     *
     * @param newPosition The new position of the player on the board.
     */
    public void setPosition(int newPosition) {
        this.previousPosition = this.currentPosition;
        this.currentPosition = newPosition;
    }

    /**
     * Get the last position of the player.
     *
     * @return The position the player previously resided in on the board.
     */
    public int getPreviousPosition() {
        return this.previousPosition;
    }


    /**
     * Get the player's name.
     *
     * @return A string containing the player's name
     */
    public String getName() {
        return this.name;
    }


    /**
     * Sets name of player object
     *
     * @param name A string containing the player's new name.
     */
    public void setName(String name) {
        this.name = name;
    }




    public void setProperties(Tile[] props){
        this.properties = new ArrayList<>(Arrays.asList(props));
    }

    /**
     * returns stringified version of player name and cash
     * Get the string representation of the player's name and cash.
     *
     * @return A string containing the player's name and how much money he currently has.
     */
    @Override
    public String toString() {
        return this.name + " | Cash: $" + this.cash;
    }


    /**
     * Check to see if the player has met the conditions for losing the game (No, not that one).
     *
     * @return Returns true if the player has neither any properties or money left. False otherwise.
     */
    public boolean hasLost() {
        return this.properties.isEmpty() && this.cash <= 0;
    }


    /**
     * Checks to see if the player has enough cash.
     *
     * @param amount An integer containing the amount to check whether the player has or not.
     * @return True if the player has >= the amount of money passed in.
     */
    public boolean hasEnoughCash(int amount) {
        return this.cash >= amount;
    }


    /**
     * player purchases property
     * adds property to player object
     * sets property to be owned
     *
     * @param property  The property that the player is attempting to buy.
     * @return true if can buy property. False otherwise.
     */
    public boolean buyProperty(Tile property) {
        int cost = property.getPurchaseCost();
        if (hasEnoughCash(cost)) {
            this.subtractCash(cost);
            this.addProperty(property);
            property.setBought(this);
            this.updateNode(property);
            this.listener.onPurchase(this);
            return true;
        } else {
            return false;
        }
    }

    /**
     * player purchases property from auction
     * adds property to player object
     * sets property to be owned
     *
     * @param property  The property that the player is attempting to auction.
     * @return true if can buy property. False otherwise.
     */
    public boolean buyProperty(Tile property, int auction) {
        if (hasEnoughCash(auction)) {
            this.subtractCash(auction);
            this.addProperty(property);
            property.setBought(this);
            this.updateNode(property);
            this.listener.onPurchase(this);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes a property from the list of properties the Player owns.
     * @param property  Property to remove.
     */
    public void removeProperty(Tile property) {
        properties.remove(property);
        DefaultMutableTreeNode propertyFolder = (DefaultMutableTreeNode) this.playerNode.getFirstChild();
        DefaultMutableTreeNode propertyNode = (DefaultMutableTreeNode) propertyFolder.getFirstChild();

        while (propertyNode != null) {
            if (propertyNode.getUserObject().equals(property)) {
                propertyFolder.remove(propertyNode);
                break;
            }
            propertyNode =  propertyNode.getNextSibling();
        }
        this.listener.onTrade(this);

        if (hasLost()) loseMessage();

    }

    /**
     *      Displays initial lost message to user
     */
    private void loseMessage(){
        JOptionPane.showMessageDialog(null, name +", sorry you lost");
    }

    /**
     * Pays rent to specific player
     *
     * @param receivingPlayer the player
     * @param rent            The rent cost of the property the player has landed on.
     */
    // CURRENTLY NO LOSING CHECK
    public void payRent(Player receivingPlayer, int rent) {
        subtractCash(rent);
        receivingPlayer.addCash(rent);
        listener.onRentPayed(receivingPlayer, this);
    }

    public void setCash(int amount){
        cash = amount;
    }

    /**
     * Get the amount of $$ the player has.
     *
     * @return the players amount of cash
     */
    public int getCash() {
        return this.cash;
    }

    /**
     * Adds cash to player's bank
     *
     * @param amount number of dollars to add
     */
    public void addCash(int amount) {
        this.cash = this.cash + amount;
    }

    /**
     * Removes cash to player's bank
     *
     * @param amount number of dollars to subtract
     */
    public void subtractCash(int amount) {
        this.cash = this.cash - amount;
        if(hasLost()) loseMessage();

    }

    /**
     * adds property to player object
     *
     * @param property The property to add to the list of properties the player owns.
     */
    private void addProperty(Tile property) {
        this.properties.add(property);
    }

    /**
     * Add a new property node to the Properties sub-node of the player's node.
     *
     * @param property The property to be added to the player's properties list.
     */
    private void updateNode(Tile property) {

        DefaultMutableTreeNode propertyNode = (DefaultMutableTreeNode) this.playerNode.getFirstChild();
        propertyNode.add(new DefaultMutableTreeNode(property));

    }

    /**
     * Get the player's leaderboard node.
     *
     * @return A DefaultMutableTreeNode representing the player on the leaderboard.
     */
    public DefaultMutableTreeNode getNode() {
        return this.playerNode;
    }

    /**
     * Sets player node in leaderboard tree.
     *
     * @param node A DefaultMutableTreeNode that contains the player's information on the LeaderBoard
     */
    public void setNode(DefaultMutableTreeNode node) {
        this.playerNode = node;
    }

    /**
     * Get the list of properties owned by the player.
     * **NOT FULLY IMPLEMENTED**
     *
     * @return A string array containing all the properties names.
     */
    public String[] getOwnedProperties() {
        String[] retArray = new String[this.properties.size()];
        for (int i = 0; i < this.properties.size(); i++) {
            retArray[i] = this.properties.get(i).getName();
        }
        return retArray;
    }

    /**
     * Get a list of the properties the player owns.
     *
     * @return An ArrayList containing all properties the player owns.
     */
    public ArrayList<Tile> getProperties() {
        return this.properties;
    }


    public JsonObject toJSONObject(){
        JsonObject player = new JsonObject();
        player.addProperty("name", name);
        player.addProperty("currentPosition", currentPosition);
        player.addProperty("cash", cash);
        player.addProperty("color", String.format("#%06x", color.getRGB() & 0x00FFFFFF));

        JsonArray prop = new JsonArray();

        for (Tile property : properties) {
            prop.add(property.toJSONObject());
        }

        player.add("properties", prop);
        return player;

    }

    /**
     * Returns whether or not it is currently the player's turn
     *
     * @return True if it is the player's turn, false otherwise.
     */
    public boolean isTurn() {
        return isTurn;
    }

    /**
     * Sets isTurn to the passed in boolean value.
     *
     * @param turnVal True if it is the player's turn. False if it is not the player's turn.
     */
    public void setTurn(boolean turnVal) {
        isTurn = turnVal;
    }

    public void setInJail(boolean inJail) {
        this.inJail = inJail;
    }

    public boolean getInJail() { return inJail; }

}
