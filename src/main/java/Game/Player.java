package Game;


import Game.UI.PlayerListener;
import Game.Board.Property;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Zach on 5/31/17.
 */
public class Player implements PlayerListener{

    public static final int SHAPE_CIRCLE = 0;
    public static final int SHAPE_SQUARE = 1;

    private ArrayList<PlayerListener> playerListeners;
    private String name;
    private int currentPosition;
    private int previousPosition;
    private int cash;
    private ArrayList<Property> properties;
    private int shape;
    private Color color;

    /**
     * Creates Player Object
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

    @Override
    public String toString() {
        return name+" | Cash: $"+cash;
    }

    @Override
    public void onPlayerMove(Player p) {

    }

    @Override
    public void onTrade() {

    }

    @Override
    public void onPurchase() {

    }

    @Override
    public void onLose() {

    }
}
