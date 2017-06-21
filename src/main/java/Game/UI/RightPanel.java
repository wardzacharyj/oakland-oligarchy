package Game.UI;

import Game.Board.Board;
import Game.Player;

import javax.swing.*;
import java.awt.*;


public class RightPanel extends JPanel implements PlayerListener {

    private Player localPlayer;
    private Player[] gamePlayers;
    private TurnPanel turnPanel;
    private Board board;

    /*
        For online Games
        Add Chat Panel
     */
    /**
     * right panel contructor for online version
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
    /**
     * right panel constructor for local version
     */
    public RightPanel(Player[] gamePlayers, Board gameBoard){
        this.gamePlayers = gamePlayers;
        this.board = gameBoard;
        setLayout(new BorderLayout());
        Leaderboard leaderboard = new Leaderboard(gamePlayers);
        add(leaderboard, BorderLayout.CENTER);
        for (int i = 0; i < gamePlayers.length; i++) {
            gamePlayers[i].addListener(leaderboard);
        }
        turnPanel = new TurnPanel(this, gamePlayers, gameBoard);
        add(turnPanel, BorderLayout.SOUTH);
    }


    /**
     * Updates player objects position
     * @param p
     */
    @Override
    public void onPlayerMove(Player p) {
        int moveBy = turnPanel.getDiceSum();
        p.move(moveBy);
    }

    @Override
    public void onTrade() {

    }

    @Override
    public void onPurchase(Player p) {

    }

    @Override
    public void onLose() {

    }
}
