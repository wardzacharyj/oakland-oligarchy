package Game;

import Game.Board.Board;
import Game.UI.GameCreatedListener;
import Game.UI.RightPanel;
//import com.apple.eawt.FullScreenUtilities;
import javax.swing.*;
import java.awt.*;


public class Driver extends JFrame implements GameCreatedListener {

    /**
     * Driver constructor
     * Creates splash screen
     */
    Driver(){
        setLayout(new BorderLayout());
        //Splash splash = new Splash(this);
        GameSetup gameSetup = new GameSetup(this);
    }

    /**
     * Initializes UI based off players entered on Start Screen
     * @param players array of players that are in the current game.
     */
    @Override
    public void onGameCreated(Player[] players) {

        Board board = new Board(players);
        RightPanel rightPanel = new RightPanel(players, board);

        add(new GameToolbar(),BorderLayout.NORTH);
        add(board,BorderLayout.CENTER);
        add(rightPanel,BorderLayout.EAST);
        setPreferredSize(new Dimension(1920,1080));
        // For OSX
        //FullScreenUtilities.setWindowCanFullScreen(this,true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
        JOptionPane.showMessageDialog(null, players[0].getName() + ", it is your turn to move.");
    }

    /**
     * runs driver, initiates start of game
     * @param args
     */
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                try{
                    //UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
                    new Driver();
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });
    }
}
