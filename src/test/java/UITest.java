

import Game.UI.TurnPanel;
import junit.framework.TestCase;
import org.junit.*;
import java.util.*;

public class UITest extends TestCase {

    @Test
    public void testVisitingExistingRepo() {

        TurnPanel p = new TurnPanel();
        assertTrue("CHECKING DICE: ",p.getDiceSum() < 13);

    }




}
