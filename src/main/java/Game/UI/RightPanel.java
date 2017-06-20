package Game.UI;

import Game.Player;
import Game.Board.Board;
import Game.UI.Chat.ChatPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;


public class RightPanel extends JPanel implements PlayerListener {

    private Player localPlayer;
    private Player[] gamePlayers;
    private TurnPanel turnPanel;
    private Board board;

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
    public RightPanel(Player[] gamePlayers, Board gameBoard){
        this.gamePlayers = gamePlayers;
        this.board = gameBoard;
        setLayout(new BorderLayout());
        add(new Leaderboard(gamePlayers),BorderLayout.CENTER);
        turnPanel = new TurnPanel(this, gamePlayers, gameBoard);
        add(turnPanel, BorderLayout.SOUTH);
    }


    @Override
    public void onPlayerMove(Player p) {
        int moveBy = turnPanel.getDiceSum();
        p.move(moveBy);
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
