package Game;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * Created by Zach on 6/7/17.
 */
public class GameToolbar extends JMenuBar {
    /**
     * toolbar for saving and loading game
     */
    public GameToolbar(){

        // File Menu, F - Mnemonic
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem saveAsItem = new JMenuItem("Save As");
        JMenuItem loadItem = new JMenuItem("Load");

        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(loadItem);

        add(fileMenu);
    }
}
