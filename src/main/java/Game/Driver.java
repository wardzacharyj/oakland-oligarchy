package Game;

import Game.Board.Board;
import Game.UI.GameCreatedListener;
import Game.UI.RightPanel;
import com.apple.eawt.FullScreenUtilities;

import javax.swing.*;
import java.awt.*;


public class Driver extends JFrame implements GameCreatedListener {

    Driver(){
        setLayout(new BorderLayout());
        Splash splash = new Splash(this);
    }


    @Override
    public void onGameCreated(Player[] players) {

        Board board = new Board(players);
        RightPanel rightPanel = new RightPanel(players);

        add(new GameToolbar(),BorderLayout.NORTH);
        add(board,BorderLayout.CENTER);
        add(rightPanel,BorderLayout.EAST);
        setPreferredSize(new Dimension(1920,1080));
        // For OSX
        FullScreenUtilities.setWindowCanFullScreen(this,true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                try{
                    UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
                    new Driver();
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });
    }
}
