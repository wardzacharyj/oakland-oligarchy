package Game.UI;

import Game.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Created by Zach on 6/6/17.
 */
public class TurnPanel extends JPanel implements ActionListener, PlayerListener {
    public final int ROLL_MODE = 0;
    public final int END_TURN = 1;


    private JButton mainButton;
    private DiceButton leftDie;
    private DiceButton rightDie;
    private int mode;



    public TurnPanel(){
        Dimension dimension = new Dimension(300,75);
        setLayout(new BorderLayout());
        this.mode = ROLL_MODE;
        this.mainButton = new JButton("Roll");
        this.mainButton.setPreferredSize(dimension);
        this.mainButton.addActionListener(this);
        this.leftDie = new DiceButton();
        this.rightDie = new DiceButton();

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

    public void resetDice(){
        leftDie.setState(0);
        rightDie.setState(0);
    }

    public void rollDice(){
        leftDie.roll();
        rightDie.roll();
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
        }
    }

    @Override
    public void onPlayerMove(Player p) {

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
