package Game.UI;

import Game.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Zach on 6/6/17.
 */
public class TurnPanel extends JPanel implements ActionListener {
    private Dimension TURN_PANEL_DIMENSION = new Dimension(300,75);

    public final int ROLL_MODE = 0;
    public final int END_TURN = 1;

    private PlayerListener boardListeners;
    private PlayerListener panelListeners;
    private JButton mainButton;
    private DiceButton leftDie;
    private DiceButton rightDie;
    private int mode;
    private Player[] players;
    private int currentPlayer;

    public TurnPanel(PlayerListener panel, Player[] players, PlayerListener board){
        Dimension dimension = new Dimension(TURN_PANEL_DIMENSION);
        setLayout(new BorderLayout());
        this.mode = ROLL_MODE;
        this.mainButton = new JButton("Roll");
        this.mainButton.setPreferredSize(dimension);
        this.mainButton.addActionListener(this);
        this.leftDie = new DiceButton();
        this.rightDie = new DiceButton();
        this.panelListeners = panel;
        this.boardListeners = board;
        this.players = players;

        JPanel diceHolder = new JPanel(new FlowLayout());
        diceHolder.add(leftDie);
        diceHolder.add(rightDie);

        add(diceHolder,BorderLayout.NORTH);
        add(mainButton,BorderLayout.CENTER);

        setBackground(Color.GREEN);

        notifyPlayer();
    }

    public void setTurnButtonMode(int mode) {
        if(mode > 1) {
            throw new IllegalArgumentException(mode + " mode is invalid");
        }
        else {
            this.mode = mode;
        }
    }

    public void resetDice(){
        leftDie.setState(0);
        rightDie.setState(0);
    }

    public void rollDice(){
        leftDie.roll();
        rightDie.roll();

        // Update To Consider Turn Switches
        panelListeners.onPlayerMove(players[0]);
        boardListeners.onPlayerMove(players[0]);
    }

    public int getDiceSum(){
        return leftDie.getState()+rightDie.getState();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(mode == ROLL_MODE){
            rollDice();
            mainButton.setText("End Turn");
            mode = END_TURN;
        }
        else {
            resetDice();
            mainButton.setText("Roll");
            mode = ROLL_MODE;
            if(winCondition()) {

            }
            currentPlayer = (currentPlayer + 1) % players.length;
            notifyPlayer();
        }
    }

    private boolean winCondition() {
        int outCount = 0;
        for (int i = 0; i < players.length; i++) {
            Player p = players[i];
            if(p.hasLost()) {
                outCount += 1;
            }
        }
        if(players.length - outCount == 1) {
            //there is only one player left, that player has won.
            return true;
        }
        else {
            return false;
        }
    }

    private void notifyPlayer() {
        while(players[currentPlayer].hasLost()) {
            currentPlayer = (currentPlayer + 1) % players.length;
        }
        JOptionPane.showMessageDialog(null, players[currentPlayer].getName() + ", it is your turn to move.");
    }


    private class DiceButton extends JButton{

        private Random r = new Random();
        private int state = 0;

        DiceButton(){
            setEnabled(false);
            setPreferredSize(new Dimension(76,76));
        }

        public void setState(int newState){
            this.state = newState;
        }

        public int getState(){
            return state;
        }

        private void roll(){
            state = r.nextInt(6) + 1;
            revalidate();
            repaint();

        }


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2D = (Graphics2D)g;
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            switch (state){
                case 0:
                    break;
                case 1:
                    g2D.fillOval((getWidth()/2)-5,(getHeight()/2)-5,10,10);
                    break;
                case 2:
                    g.fillOval((getWidth()/4)-5,(getHeight()/4)-5,10,10);
                    g.fillOval(3*(getWidth()/4)-5,3*(getHeight()/4)-5,10,10);
                    break;
                case 3:
                    g.fillOval((getWidth()/4)-5,(getHeight()/4)-5,10,10);
                    g.fillOval((getWidth()/2)-5,(getHeight()/2)-5,10,10);
                    g.fillOval(3*(getWidth()/4)-5,3*(getHeight()/4)-5,10,10);
                    break;
                case 4:
                    g.fillOval((getWidth()/4)-5,(getHeight()/4)-5,10,10);
                    g.fillOval(3*(getWidth()/4)-5,(getHeight()/4)-5,10,10);
                    g.fillOval((getWidth()/4)-5,3*(getHeight()/4)-5,10,10);
                    g.fillOval(3*(getWidth()/4)-5,3*(getHeight()/4)-5,10,10);
                    break;
                case 5:
                    g.fillOval((getWidth()/4)-5,(getHeight()/4)-5,10,10);
                    g.fillOval(3*(getWidth()/4)-5,(getHeight()/4)-5,10,10);
                    g.fillOval((getWidth()/2)-5,(getHeight()/2)-5,10,10);
                    g.fillOval((getWidth()/4)-5,3*(getHeight()/4)-5,10,10);
                    g.fillOval(3*(getWidth()/4)-5,3*(getHeight()/4)-5,10,10);
                    break;
                case 6:
                    g.fillOval((getWidth()/4)-5,(getHeight()/4)-5,10,10);
                    g.fillOval(3*(getWidth()/4)-5,(getHeight()/4)-5,10,10);
                    g.fillOval((getWidth()/4)-5,(getHeight()/2)-5,10,10);
                    g.fillOval(3*(getWidth()/4)-5,(getHeight()/2)-5,10,10);
                    g.fillOval((getWidth()/4)-5,3*(getHeight()/4)-5,10,10);
                    g.fillOval(3*(getWidth()/4)-5,3*(getHeight()/4)-5,10,10);
                    break;
                default:
                    break;
            }


        }

    }
}
