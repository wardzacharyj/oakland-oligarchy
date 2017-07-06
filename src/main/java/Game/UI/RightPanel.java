package Game.UI;

import Game.Board.Board;
import Game.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;


public class RightPanel extends JPanel implements PlayerListener {

    private Player localPlayer;
    private Player[] gamePlayers;
    private TurnPanel turnPanel;
    private Board board;
    private ClockPanel clockPanel;


    /**
     *
     * @param gamePlayers
     * @param gameBoard
     */
    public RightPanel(Player[] gamePlayers, Board gameBoard, int turn){
        this.gamePlayers = gamePlayers;
        this.board = gameBoard;
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10,10,10,10));

        clockPanel = new ClockPanel(board.getLastStartTime());
        add(clockPanel,BorderLayout.NORTH);

        Leaderboard leaderboard = new Leaderboard(gamePlayers);

        add(leaderboard, BorderLayout.CENTER);

        for (Player p : gamePlayers) {
            p.addListener(leaderboard);
        }

        turnPanel = new TurnPanel(this, gamePlayers, gameBoard);
        turnPanel.setCurrentPlayer(turn);
        add(turnPanel, BorderLayout.SOUTH);
    }

    /**
     *
     * @param gamePlayers
     * @param gameBoard
     */
    public RightPanel(Player[] gamePlayers, Board gameBoard){
        this.gamePlayers = gamePlayers;
        this.board = gameBoard;
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10,10,10,10));

        clockPanel = new ClockPanel(board.getLastStartTime());
        add(clockPanel,BorderLayout.NORTH);

        Leaderboard leaderboard = new Leaderboard(gamePlayers);
        add(leaderboard, BorderLayout.CENTER);

        for (Player p : gamePlayers) {
            p.addListener(leaderboard);
        }

        turnPanel = new TurnPanel(this, gamePlayers, gameBoard);
        add(turnPanel, BorderLayout.SOUTH);
    }

    public ClockPanel getClockPanel(){
        return clockPanel;
    }

    public TurnPanel getTurnPanel(){
        return turnPanel;
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
    public void onRentPayed(Player owner, Player rente) {

    }

    @Override
    public void onTrade(Player p) {

    }

    @Override
    public void onPurchase(Player p) {

    }

    @Override
    public void onLose() {

    }
}
