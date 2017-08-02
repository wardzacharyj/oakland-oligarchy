package Game;

import Game.Board.Board;
import Game.UI.GameCreatedListener;
import Game.UI.Leaderboard;
import Game.UI.RightPanel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;


public class Driver extends JFrame implements GameCreatedListener {


    private Preferences prefs;


    /**
     * Constructor for the driver.
     * 
     */
    Driver(){
        setLayout(new BorderLayout());


        prefs = Preferences.userRoot();


        JsonParser parser = new JsonParser();
        String rawJson = prefs.get("filenames", null);



        if(rawJson != null){
            JsonObject g = parser.parse(rawJson).getAsJsonObject();
            JsonArray games = g.getAsJsonArray("games");
            new GameSetup(this, prefs, games);
        }
        else {
            JsonObject emptyGameObject = new JsonObject();
            JsonArray games = new JsonArray();
            emptyGameObject.add("games", games);
            prefs.put("filenames", emptyGameObject.toString());
            new GameSetup(this, prefs, null);
        }




    }


    @Override
    public void onGameLoaded(String gameTitle, long timeElapsed, int playerTurn, Player[] players) {
        Leaderboard leaderboard = new Leaderboard(players);

        Board board = new Board(players, false, leaderboard);
        board.setLastStartTime(timeElapsed);

        RightPanel rightPanel = new RightPanel(players, board, playerTurn, leaderboard);

        GameToolbar gameToolbar = new GameToolbar(players,
                rightPanel.getTurnPanel(),
                rightPanel.getClockPanel(),
                gameTitle, prefs);

        add(board,BorderLayout.CENTER);
        add(rightPanel,BorderLayout.EAST);
        add(gameToolbar,BorderLayout.NORTH);

        setPreferredSize(new Dimension(1920,1080));

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        JOptionPane.showMessageDialog(null, players[playerTurn].getName() + ", it is your turn to move.");

    }

    /**
     * Initializes UI based off players entered on Start Screen
     *
     * @param players array of players that are in the current game.
     */
    @Override
    public void onGameCreated(String gameTitle,Player[] players) {
        Leaderboard leaderboard = new Leaderboard(players);
        Board board = new Board(players, true, leaderboard);

        RightPanel rightPanel = new RightPanel(players, board, leaderboard);

        GameToolbar gameToolbar = new GameToolbar(players,
                rightPanel.getTurnPanel(),
                rightPanel.getClockPanel(),
                gameTitle, prefs);

        add(board,BorderLayout.CENTER);
        add(rightPanel,BorderLayout.EAST);
        add(gameToolbar,BorderLayout.NORTH);

        setPreferredSize(new Dimension(1920,1080));

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
