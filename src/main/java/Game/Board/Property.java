package Game.Board;

import Game.Player;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

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
    private boolean isForSale;
    private Player[] player;


    private Color tileColor;

    /**
     * Constructor.
     * @param houseCount        Number of houses the property has.
     * @param improvementCost   The cost to build an improvement.
     * @param isImproved        If the property is improved or not.
     * @param isMonopoly        If the property is part of a monopoly.
     * @param mortgage          Mortgage value of the property.
     * @param name              Property Name
     * @param owner             Property Owner.
     * @param purchaseCost      Cost to purchase the property.
     * @param rent              Rent cost.
     * @param tileGroup         Which tilegroup the property is in.
     * @param tilePosition      Where on the board the property is.
     */
    public Property(String name, Player owner, int houseCount, int improvementCost,
                    int[] rent, int mortgage, boolean isImproved, boolean isMonopoly,
                    int purchaseCost, String tileGroup, int tilePosition, Player[] players) {
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
        this.isForSale = true;
        this.player = players;
    }

    /**
     * Getter for owner.
     * @return Player object who currently owns property.
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * Getter for number of improvements.
     * @return 0 to Max number of houses (improvements)
     */
    public int getHouseCount() {
        return this.houseCount;
    }

    /**
     * Check to see if property is improved.
     * @return True if property is improved. False otherwise.
     */
    public boolean isImproved() {
        return this.isImproved;
    }

    /**
     * Check to see if current owner also owns all other properties of the same color.
     * @return True if owner owns all properties associated with color. False otherwise.
     */
    public boolean isMonopoly() {
        return this.isMonopoly;
    }

    /**
     *  Gets the cost associated with the property.
     *  @return Non-negative integer amount.
     */
    public int getPurchaseCost() {
        return this.purchaseCost;
    }

    /**
     *  Gets the tile group this property belongs in.
     *  @return The tilegroup String.
     */
    public String getTileGroup() {
        return this.tileGroup;
    }

    /**
     * Gets where on the board this property resides.
     * @return An integer value detailing the properties position
     */
    public int getTilePosition() {
        return this.tilePosition;
    }

    /**
     * Gets what color this tile is.
     * @return The tile's Color.
     */
    public Color getTileColor() {
        return this.tileColor;
    }

    /**
     * Gets the rent cost. Rent cost depends on the number of improvements and if it is a monopoly or not.
     *
     * @return An integer value
     */
    public int getRent() {
        return this.rent[houseCount];
    }


    /**
     *sets the property to being bought
     * @param newOwner The new owner of the property
     */
    public void setBought(Player newOwner) {
        this.isForSale = false;
        this.owner = newOwner;
    }

    /**
     *gets which player put in the highest auction
     * @param map Map of players and their auction inputs
     */
    public int getMax(Map map){
        int maxKey = -1;
        if(map.size() > 0){
            Map.Entry<Integer,Integer> entry = (Map.Entry<Integer, Integer>) map.entrySet().iterator().next();
            Integer key = entry.getKey();
            maxKey = key;
            for(int i = 0; i < player.length; i++) {
                if(map.containsKey(i)) {
                    int input = (int) map.get(i);
                    if(input > (int) map.get(maxKey)){
                        maxKey = i;
                    }
                }
            }
        }
        return maxKey;
    }

    /**
     *notifies player to either buy property, pay rent, or auction for property
     * @param p The player who has landed on the property
     */
    @Override
    public void notifyPlayerLanded(Player p) {

        if (this.isForSale) {
            int dialogResult = JOptionPane.showConfirmDialog(null,
                    this.getName() + " is for sale. Would you like to purchase it?",
                    null, JOptionPane.YES_NO_OPTION);

            if (dialogResult == JOptionPane.YES_OPTION) {
                if (p.hasEnoughCash(this.getPurchaseCost())) {
                    p.buyProperty(this);
                } else {
                    JOptionPane.showMessageDialog(null, "You don't have enough money!");
                }
            } else {
                //auction
                Map<Integer, Integer> map = new HashMap<>();
                for(int i = 0; i < player.length; i++)
                {
                    if(!player[i].hasLost() && player[i] != p)
                    {
                        JPasswordField amount = new JPasswordField();
                        final JComponent[] inputs = new JComponent[] {
                                new JLabel(player[i].getName() + " enter Auction Price:"),
                                amount,
                                new JLabel("Just enter integer value of cash"),
                                new JLabel("No Symbols ($%&*.,)")
                        };
                        int result = JOptionPane.showConfirmDialog(null, inputs, "Auction", JOptionPane.PLAIN_MESSAGE);
                        if (result == JOptionPane.OK_OPTION) {
                            try{
                                String amountStr = new String(amount.getPassword());
                                if(amountStr.length() > 0){
                                    map.put(i, Integer.parseInt(amountStr));
                                }
                            }
                            catch (Exception ex){
                                JOptionPane.showMessageDialog(null, player[i].getName() + ", you did not enter a valid input. Your auction will be voided.");
                            }
                        }
                    }
                }
                //Find Max from Map
                while(true){
                    int auctionWinner = getMax(map);
                    if(auctionWinner != -1){
                        if (player[auctionWinner].hasEnoughCash(map.get(auctionWinner))) {
                            player[auctionWinner].buyProperty(this, map.get(auctionWinner));
                            JOptionPane.showMessageDialog(null, player[auctionWinner].getName() + " won the auction!");
                            break;
                        } else {
                            JOptionPane.showMessageDialog(null, player[auctionWinner].getName() + " You don't have that much money! Your auction will be voided.");
                            map.remove(auctionWinner);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "No one wants " + name + "? OK, it'll go back in the pile..." );
                        break;
                    }
                }
            }
        } else {
            if(this.owner != p) {
                JOptionPane.showMessageDialog(null, "Thank you "
                        +p.getName() +" we hope you enjoy your stay here, that'll be $"+getRent());

                if (p.hasEnoughCash(this.getPurchaseCost())) {
                    p.payRent(owner, getRent());
                } else {
                    JOptionPane.showMessageDialog(null, "You don't have enough money!");
                }
            }

        }
    }

    /**
     * TEMP IMPLEMENTATION OF TOSTRING. This exists because JTree uses toString representation of object
     * to display it in the JTree. Will replace with commented version at future date when a treecellrender is written
     * @return Property name
     */
    @Override
    public String toString() {
        return this.getName();
    }

    public String propertyInfoToString() {

        return this.getName() + "<br />" + this.getTileColor().toString() + "<br />" + this.getPurchaseCost() + "<br />" +
                this.getRent();

    }

//    /**
//     * Returns a string representation of the property.
//     * @return A string that contains all properties of the Property.
//     */
//    @Override
//    public String toString() {
//        return "Property{" +
//                "name='" + name + '\'' +
//                ", owner=" + owner +
//                ", houseCount=" + houseCount +
//                ", improvementCost=" + improvementCost +
//                ", rent=" + Arrays.toString(rent) +
//                ", mortgage=" + mortgage +
//                ", isImproved=" + isImproved +
//                ", isMonopoly=" + isMonopoly +
//                ", purchaseCost=" + purchaseCost +
//                ", tileGroup='" + tileGroup + '\'' +
//                ", tilePosition=" + tilePosition +
//                ", tileColor=" + tileColor +
//                '}';
//    }

}