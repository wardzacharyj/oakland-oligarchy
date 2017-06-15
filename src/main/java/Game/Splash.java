package Game;

import Game.Board.Board;
import Game.UI.GameCreatedListener;
import Utilities.SpringUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Splash extends JFrame implements ItemListener, ActionListener {

    //private static JFrame splashFrame;

    private GameCreatedListener gameCreatedListener;
    private JPanel topPane;
    private JPanel middlePane;
    private List<JTextField> playerNameFields;
    private JPanel bottomPane;

    Splash(GameCreatedListener listener) {
        super("Welcome to Oakland Oligarchy");
        this.gameCreatedListener = listener;
        setLayout(new GridLayout(3,1));
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(new Dimension(640, 480));

        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);

        add(setupTopPane());
        add(setupMiddlePane());
        add(setupBottomPane());


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

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
                nameInput.setText("Player " + (i + 1));
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
            Player[] players = new Player[playerNameFields.size()];

            Color[] colors = {Color.RED,Color.BLUE,Color.CYAN,Color.GREEN};

            for (int i = 0; i < players.length; i++) {
                players[i] = new Player(playerNameFields.get(i).getText(),colors[i], 1200, 0);
            }

            gameCreatedListener.onGameCreated(players);
            this.dispose();
        }

    }

}
