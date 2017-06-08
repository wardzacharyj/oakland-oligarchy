package Game.Board;

import Game.Player;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

/**
 * Created by Zach on 6/1/17.
 */
public class Property extends Tile {

    private Player owner;

    private boolean isForSale;
    private int rent;
    private int price;
    private int improvementFee;
    private boolean isImproved;
    private String improvementType;
    private Color color;


    Property(String name, int position, Color color){
        super(name,position);
        this.color = color;
    }

    public int getPrice(){
        return price;
    }

    public String getPriceBanner(){
        return "$"+price;
    }

    public Color getColor() {
        return color;
    }
}
