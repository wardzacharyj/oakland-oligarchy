package Game;

import Game.Board.Board;
import Game.Board.Property;
import Game.UI.PlayerListener;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.ArrayList;

public class Player {

    public static final int SHAPE_CIRCLE = 0;
    public static final int SHAPE_SQUARE = 1;

    private String name;
    private int currentPosition;
    private int previousPosition;
    private int cash;
    private ArrayList<Property> properties;
    private int shape;
    private Color color;
    private PlayerListener listener;
    private DefaultMutableTreeNode playerNode;

    /**
     * Player constructor
     * @param name
     * @param color
     */
    public Player(String name, Color color) {
        this.name = name;
        this.properties = new ArrayList<>();
        this.color = color;
    }

    /**
     * Player constructor
     * @param name
     * @param color
     * @param cash
     * @param currentPosition
     */
    public Player(String name, Color color, int cash, int currentPosition) {
        this.name = name;
        this.properties = new ArrayList<>();
        this.shape = (int)(Math.random() * 1);
        this.color = color;
        this.cash = cash;
        this.currentPosition = currentPosition;
        this.previousPosition = currentPosition;
    }

    /**
     * Adds player listener to object
     * @param listener
     */
    public void addListener(PlayerListener listener) {
        this.listener = listener;
    }
    /**
     * Sets Color of player
     */
    public void setColor(Color color){
        this.color = color;
    }
    /**
     * Gets Color of player
     */
    public Color getColor(){
        return color;
    }
    /**
     * Sets position of player
     */
    public void setPosition(int newPosition){
        this.previousPosition = this.currentPosition;
        this.currentPosition = newPosition;
    }

    /**
     * Updates player objects positions
     * @param moveBy
     */
    public void move(int moveBy){
        int newPosition = currentPosition + moveBy;

        if(newPosition < Board.SIZE) {
            setPosition(newPosition);
        }
        else {
            int loopedPosition = newPosition - Board.SIZE;
            setPosition(loopedPosition);
        }
    }

    /**
     * Gets position of player
     */
    public int getPosition(){
        return currentPosition;
    }
    /**
     * gets previous position of player
     */
    public int getPreviousPosition(){ return previousPosition; }

    /**
     * Gets shape of player object
     */
    public int getShape() {
        return shape;
    }

    /**
     * Gets name of player object
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of player object
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets player node in tree
     * @param node
     */
    public void setNode(DefaultMutableTreeNode node) {
        this.playerNode = node;
    }

    /**
     * returns stringified version of player name and cash
     */
    @Override
    public String toString() {
        return name+" | Cash: $"+cash;
    }

    /**
     * returns True if the player is no longer in the game
     */
    public boolean hasLost() {
        return properties.isEmpty() && cash <= 0;
    }

    /**
     * checks to make sure the player can buy a property and pay rent
     */
    public boolean hasEnoughCash(int amount) {
        return this.cash >= amount;
    }

    /**
     * player purchases property
     * adds property to player object
     * sets property to be owned
     * @param property
     * @return true if can buy property
     */
    public boolean buyProperty(Property property) {
        int cost = property.getPurchaseCost();
        if (hasEnoughCash(cost)) {
            this.cash -= cost;
            this.addProperty(property);
            property.setBought(this);
            this.updateNode(property);
            listener.onPurchase(this);
            return true;
        } else {
            return false;
        }
    }


    /**
     * Pays rent to specific player
     * @param receivingPlayer the player
     * @param rent
     * @return
     */

    // CURRENTLY NO LOSING CHECK
    public void payRent(Player receivingPlayer, int rent) {
        subtractCash(rent);
        receivingPlayer.addCash(rent);
        listener.onRentPayed(receivingPlayer,this);
    }

    /**
     *
     * @return the players amount of cash
     */
    public int getCash(){
        return cash;
    }


        /**
         * Adds cash to player's bank
         * @param amount number of dollars to add
         */
    public void addCash(int amount){
        cash = cash + amount;
    }


    /**
     * Removes cash to player's bank
     * @param amount number of dollars to subtract
     */
    public void subtractCash(int amount){
        cash = cash - amount;
    }


    /**
     * adds property to player object
     * @param property
     */
    private void addProperty(Property property) {
        this.properties.add(property);
    }

    /**
     * updates player node in tree
     * @param property
     */
    private void updateNode(Property property) {
        DefaultMutableTreeNode propertyNode = (DefaultMutableTreeNode) playerNode.getFirstChild();
        propertyNode.add(new DefaultMutableTreeNode(property.getName()));

    }

    /**
     * Gets player node from tree
     */
    public DefaultMutableTreeNode getNode() {
        return this.playerNode;
    }

    /**
     * returns a string array of players properties
     */
    public String[] getOwnedProperties() {
        String[] retArray = new String[properties.size()];
        for (int i = 0; i < properties.size(); i++) {
            retArray[i] = properties.get(i).getName();
        }
        return retArray;
    }

    /**
     * returns a list of owned property objects
     */
    public ArrayList<Property> getProperties() {
        return properties;
    }
}
