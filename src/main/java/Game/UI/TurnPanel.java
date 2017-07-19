package Game.UI;

import Game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


public class TurnPanel extends JPanel implements ActionListener {
    private Dimension TURN_PANEL_DIMENSION = new Dimension(300,75);

    public final int ROLL_MODE = 0;
    public final int END_TURN = 1;

    private PlayerListener boardListeners;
    private PlayerListener panelListeners;
    private RightPanel parent;
    private JButton mainButton;
    private DiceButton leftDie;
    private DiceButton rightDie;
    private int mode;
    private Player[] players;
    private int currentPlayer;

    /**
     * TurnPanel constructor
     * @param panel
     * @param board
     * @param players
     */
    public TurnPanel(PlayerListener panel, Player[] players, PlayerListener board, RightPanel parent){
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
        this.parent = parent;

        JPanel diceHolder = new JPanel(new FlowLayout());
        diceHolder.add(leftDie);
        diceHolder.add(rightDie);

        add(diceHolder,BorderLayout.NORTH);
        add(mainButton,BorderLayout.CENTER);

        setBackground(Color.GREEN);
    }



    public void setTurnButtonMode(int mode) {
        if(mode > 1) {
            throw new IllegalArgumentException(mode + " mode is invalid");
        }
        else {
            this.mode = mode;
        }
    }

    /**
     * reset game dice
     */
    public void resetDice(){
        parent.resetOpenPanels();
        leftDie.setState(0);
        rightDie.setState(0);
    }

    /**
     * roll game dice
     */
    public void rollDice(){
        leftDie.roll();
        rightDie.roll();
        panelListeners.onPlayerMove(players[currentPlayer]);
        boardListeners.onPlayerMove(players[currentPlayer]);
    }

    public void setCurrentPlayer(int currentPlayer){
        this.currentPlayer = currentPlayer;
    }

    public int getCurrentPlayer(){
        return currentPlayer;
    }


    /**
     * get sum of dice values
     */
    public int getDiceSum(){
        return leftDie.getState()+rightDie.getState();
    }


    /**
     * Sets dice text and calls dice functionality on button click
     * @param e
     */
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
                for (int i = 0; i < players.length; i++) {
                    if(!players[i].hasLost()) {
                        JOptionPane.showMessageDialog(new JPanel(), players[i].getName() + " has won the game!");
                        System.exit(0);
                    }
                }
            }
            currentPlayer = (currentPlayer + 1) % players.length;
            notifyPlayer();
        }
    }

    /**
     * returns true if there is only 1 player left in the game
     */
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


    /**
     * notifies player it is their turn to move
     */
    private void notifyPlayer() {
        while(players[currentPlayer].hasLost()) {
            currentPlayer = (currentPlayer + 1) % players.length;
        }

        for (Player p : players) {
            if (players[currentPlayer].equals(p)) {
                p.setTurn(true);
            } else {
                p.setTurn(false);
            }
        }
        JOptionPane.showMessageDialog(null, players[currentPlayer].getName()
                + ", it is your turn to move.");
    }


    /**
     * The button that controls dice functionality
     */
    private class DiceButton extends JButton{

        private Random r = new Random();
        private int state = 0;

        /**
         * Dice button constructor
         */
        DiceButton(){
            setEnabled(false);
            setPreferredSize(new Dimension(76,76));
        }

        /**
         * sets the value of the dice
         * @param newState
         */
        public void setState(int newState){
            this.state = newState;
        }

        /**
         *gets the value of the dice
         */
        public int getState(){
            return state;
        }

        /**
         *updates dice values randomly
         */
        private void roll(){
            state = r.nextInt(6) + 1;
            revalidate();
            repaint();

        }


        /**
         *paints values onto dice UI
         */
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
