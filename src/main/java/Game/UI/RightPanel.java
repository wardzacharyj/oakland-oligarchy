package Game.UI;

import Game.Player;
import Game.UI.Chat.ChatPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;


public class RightPanel extends JPanel implements PlayerListener {

    private Player localPlayer;
    private Player[] gamePlayers;
    private TurnPanel turnPanel;

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
        turnPanel = new TurnPanel(this);
        add(turnPanel, BorderLayout.SOUTH);


    }


    @Override
    public void onPlayerMove(Player p) {
        int playerPosition = p.getPosition();
        int move = turnPanel.getDiceSum();
        int newPosition = move + playerPosition;
        if(newPosition < 36)
        {
            p.setPosition(newPosition);
        }
        else
        {
            int loopedPosition = newPosition - 36;
            p.setPosition(loopedPosition);
        }
    }

    @Override
    public void onTrade() {

    }

    @Override
    public void onPurchase() {

    }

    @Override
    public void onLose() {

    }
}
