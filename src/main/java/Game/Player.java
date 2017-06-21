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
    PlayerListener listener;
    private DefaultMutableTreeNode playerNode;

    public Player(String name, Color color, int cash, int currentPosition) {
        this.name = name;
        this.properties = new ArrayList<>();
        this.shape = (int)(Math.random() * 1);
        this.color = color;
        this.cash = cash;
        this.currentPosition = currentPosition;
        this.previousPosition = currentPosition;
    }

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

    public int getShape() {
        return shape;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNode(DefaultMutableTreeNode node) {
        this.playerNode = node;
    }

    @Override
    public String toString() {
        return name+" | Cash: $"+cash;
    }

    public boolean hasLost() {
        return properties.isEmpty() && cash <= 0;
    }

    public boolean hasEnoughCash(int amount) {
        return this.cash >= amount;
    }

    public boolean buyProperty(Property property) {
        int cost = property.getCost();
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

    private void addProperty(Property property) {
        this.properties.add(property);
    }

    private void updateNode(Property property) {
        DefaultMutableTreeNode propertyNode = (DefaultMutableTreeNode) playerNode.getFirstChild();
        propertyNode.add(new DefaultMutableTreeNode(property.getName()));

    }

    public DefaultMutableTreeNode getNode() {
        return this.playerNode;
    }

    public String[] getProperties() {
        String[] retArray = new String[properties.size()];
        for (int i = 0; i < properties.size(); i++) {
            retArray[i] = properties.get(i).getName();
        }
        return retArray;
    }
}
