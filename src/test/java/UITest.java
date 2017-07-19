

import Game.Board.Board;
import Game.Board.Property;
import Game.UI.RightPanel;
import Game.UI.TurnPanel;
import Game.Player;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import junit.framework.TestCase;
import org.junit.*;

import java.awt.*;
import java.util.*;

public class UITest extends TestCase {

    /**
     * Test that dice roll is accurate
     */
    @Test
    public void testVisitingExistingRepo() {

        Player[] players = new Player[1];
        players[0] = new Player("Test", Color.BLACK, 1200, 0);
        Board board = new Board(players, true);
        RightPanel rightPanel = new RightPanel(players, board);
        TurnPanel p = new TurnPanel(rightPanel, players, board, rightPanel);
        p.rollDice();
        assertTrue("CHECKING DICE: ",p.getDiceSum() < 13);

    }

    /**
     * Test that player moves current amount
     */
    @Test
    public void testPlayerMovement()
    {
        Player p = new Player("Test", Color.BLACK, 1200, 0);
        p.move(5);
        assertEquals(5, p.getPosition());
    }

    /**
     * Test that players position loops when it passes the end of board
     */
    @Test
    public void testPlayerPosition()
    {
        Player p = new Player("Test", Color.BLACK, 1200, 35);
        p.move(2);
        assertTrue("Check looped movement", p.getPosition() < 36);
    }


    @Test
    public void testPropertyRent(){
        Player[] players = new Player[2];

        Player p1 = new Player("Owner", Color.BLACK, 0, 0);
        Player p2 = new Player("Rente", Color.BLACK, 0, 0);
        players[0] = p1;
        players[1] = p2;

        Board board = new Board(players, true);
        RightPanel rightPanel = new RightPanel(players, board);
        TurnPanel p = new TurnPanel(rightPanel, players, board, rightPanel);

        Property property = new Property("Property", null,0,
                0, new int[]{300}, 150, false,
                false, 350,"#123456",1, players);

        players[0].buyProperty(property);
        players[1].payRent(players[0],property.getRent());

        assertTrue("Check Rent Transaction: ", p2.getCash() + 300 == 1200);
        assertTrue("Check Cash Recieved: ",p1.getCash()+50 == 1200);
    }

    /**
     *     Checks that property purchase deducts the correct amount of money from the player and player becomes owner
     */
    @Test
    public void testPropertyPurchase(){
        Player[] players = new Player[1];

        int orginalCash = 1200;
        Player p1 = new Player("Owner", Color.BLACK, 0, 0);
        players[0] = p1;

        Board board = new Board(players, true);
        RightPanel rightPanel = new RightPanel(players, board);
        TurnPanel p = new TurnPanel(rightPanel, players, board, rightPanel);

        Property property = new Property("Property", null,0,
                0, new int[]{300}, 150, false,
                false, 350,"#123456",1, players);

        players[0].buyProperty(property);

        assertTrue("Check Purchase Operation: ", (orginalCash - property.getPurchaseCost() == p1.getCash()));
        assertTrue("Check Player is owner", p1.getProperties().get(0).equals(property));

        // Write test that checks if leaderboard reflects purchase

    }


}
