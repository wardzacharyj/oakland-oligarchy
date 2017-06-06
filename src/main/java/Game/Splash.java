package Game;

import Game.Board.Board;
import Utilities.SpringUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Splash extends JPanel implements ItemListener, ActionListener, WindowListener {

    private static JFrame splashFrame;
    private JPanel topPane;
    private JPanel middlePane;
    private List<JTextField> playerNameFields;
    private JPanel bottomPane;

    private Splash() {
        super(new GridLayout(3, 1));

        add(setupTopPane());
        add(setupMiddlePane());
        add(setupBottomPane());

    }

    private JPanel setupTopPane() {
        String[] numPlayersList = {" ", "2", "3", "4"};
        JComboBox numPlayersBox = new JComboBox<>(numPlayersList);

        JLabel numPlayersLabel = new JLabel("Number of Players", SwingConstants.CENTER);

        numPlayersBox.setEditable(false);
        numPlayersBox.addItemListener(this);

        SpringLayout layout = new SpringLayout();

        JPanel numPlayersPane = new JPanel();
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, numPlayersLabel, 0,
                SpringLayout.HORIZONTAL_CENTER, numPlayersPane);
        layout.putConstraint(SpringLayout.NORTH, numPlayersLabel, 40,
                SpringLayout.NORTH, numPlayersPane);

        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, numPlayersBox, 0,
                SpringLayout.HORIZONTAL_CENTER, numPlayersPane);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, numPlayersBox, 0,
                SpringLayout.VERTICAL_CENTER, numPlayersPane);

        numPlayersPane.setLayout(layout);

        numPlayersPane.add(numPlayersLabel);
        numPlayersPane.add(numPlayersBox);

        topPane = numPlayersPane;
        return numPlayersPane;

    }

    private JPanel setupMiddlePane() {
        JPanel middlePanel = new JPanel();
        middlePane = middlePanel;

        return middlePanel;
    }

    private JPanel setupBottomPane() {
        JButton startButton = new JButton("Start");
        startButton.addActionListener(this);

        SpringLayout layout = new SpringLayout();
        JPanel bottomPanel = new JPanel();

        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, startButton, 0,
                SpringLayout.HORIZONTAL_CENTER, bottomPanel);
        layout.putConstraint(SpringLayout.NORTH, startButton, 0,
                SpringLayout.NORTH, bottomPanel);

        bottomPanel.setLayout(layout);

        bottomPanel.add(startButton);

        bottomPane = bottomPanel;
        return bottomPanel;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            int numPlayers;
            if (e.getItem().equals(" ")) {
                numPlayers = 0;
            } else {
                numPlayers = Integer.parseInt((String) e.getItem());
            }

            playerNameFields = new ArrayList<>();

            middlePane.removeAll();

            for (int i = 0; i < numPlayers; i++) {
                JTextField nameInput = new JTextField();

                nameInput.setPreferredSize(new Dimension(150, 20));
                nameInput.setText("Player " + (numPlayers + 1));
                nameInput.setHorizontalAlignment(SwingConstants.CENTER);
                playerNameFields.add(nameInput);
                middlePane.add(nameInput);
            }

            SpringUtilities.makeGrid(middlePane, 1, numPlayers, SwingConstants.CENTER,
                    SwingConstants.CENTER, 3, 3);

            middlePane.updateUI();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (playerNameFields == null) {
            //ERROR HERE
        } else {
            List<String> playerNames = new ArrayList<>();
            for (JTextField j : playerNameFields) {
                playerNames.add(j.getText());
            }
            new Board();

            splashFrame.dispose();
        }


    }

    static void splashInit() {

        splashFrame = new JFrame("Oakland Oligarchy");
        splashFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        splashFrame.setResizable(false);
        splashFrame.setPreferredSize(new Dimension(640, 480));
        splashFrame.setLocationRelativeTo(null);

        JComponent contentPane = new Splash();
        contentPane.setOpaque(true);
        splashFrame.setContentPane(contentPane);

        splashFrame.pack();
        splashFrame.setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
