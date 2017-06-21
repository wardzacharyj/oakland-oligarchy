

import Game.Board.Board;
import Game.UI.RightPanel;
import Game.UI.TurnPanel;
import Game.Player;
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
        Board board = new Board(players);
        RightPanel rightPanel = new RightPanel(players, board);
        TurnPanel p = new TurnPanel(rightPanel, players, board);
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
}
