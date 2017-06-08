package Game.UI;

import Game.Player;
import Game.UI.Chat.ChatPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;


public class RightPanel extends JPanel {

    private Player localPlayer;
    private Player[] gamePlayers;

    /*
        For online Games
        Add Chat Panel
     */
    public RightPanel(Player localPlayer, Player[] otherPlayers){
        this.localPlayer = localPlayer;
        this.gamePlayers = gamePlayers;

        setLayout(new BorderLayout());
        //NOTE GAME PLAYERS ONLY EXPECTED TO BE OTHER PLAYERS

    }

    /*
        For local Games
        No Chat Panel
     */
    public RightPanel(Player[] gamePlayers){
        this.gamePlayers = gamePlayers;
        setLayout(new BorderLayout());
        add(new Leaderboard(gamePlayers),BorderLayout.CENTER);
        add(new TurnPanel(), BorderLayout.SOUTH);


    }


}
