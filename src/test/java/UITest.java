

import Game.UI.TurnPanel;
import Game.Player;
import junit.framework.TestCase;
import org.junit.*;

import java.awt.*;
import java.util.*;

public class UITest extends TestCase {

    @Test
    public void testVisitingExistingRepo() {

        TurnPanel p = new TurnPanel();
        p.rollDice();
        assertTrue("CHECKING DICE: ",p.getDiceSum() < 13);

    }
}
