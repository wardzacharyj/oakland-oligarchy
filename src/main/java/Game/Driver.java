package Game;

import Game.Board.Board;
import Game.UI.GameCreatedListener;
import Game.UI.RightPanel;

import javax.swing.*;
import java.awt.*;

//import com.apple.eawt.FullScreenUtilities;


public class Driver extends JFrame implements GameCreatedListener {

    /**
     * Constructor for the driver.
     */
    Driver() {
        this.setLayout(new BorderLayout());
        Splash splash = new Splash(this);
    }

    /**
     * runs driver, initiates start of game
     *
     * @param args The arguments passed into the driver.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                new Driver();
            } catch (Exception e) {
                e.printStackTrace();
            }


        });
    }

    /**
     * Initializes UI based off players entered on Start Screen
     *
     * @param players array of players that are in the current game.
     */
    @Override
    public void onGameCreated(Player[] players) {

        Board board = new Board(players);
        RightPanel rightPanel = new RightPanel(players, board);

        this.add(new GameToolbar(), BorderLayout.NORTH);
        this.add(board, BorderLayout.CENTER);
        this.add(rightPanel, BorderLayout.EAST);
        this.setPreferredSize(new Dimension(1920, 1080));
        // For OSX
        //FullScreenUtilities.setWindowCanFullScreen(this,true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        JOptionPane.showMessageDialog(null, players[0].getName() + ", it is your turn to move.");
    }
}
