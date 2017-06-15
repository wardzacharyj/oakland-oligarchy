package Game;

import Game.Board.Property;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Zach on 5/31/17.
 */
public class Player {

    public static final int SHAPE_CIRCLE = 0;
    public static final int SHAPE_SQUARE = 1;


    private String name;
    private int currentPosition;
    private int cash;
    private ArrayList<Property> properties;
    private int shape;

    private Color color;



    public Player(String name, Color color) {
        this.name = name;
        this.properties = new ArrayList<>();
        this.shape = (int)(Math.random() * 1);
        this.color = color;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public Color getColor(){
        return color;
    }

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
}
