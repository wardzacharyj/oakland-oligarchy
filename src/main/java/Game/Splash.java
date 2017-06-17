package Game;

import Game.UI.GameCreatedListener;
import Utilities.SpringUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class Splash extends JFrame implements ItemListener, ActionListener {

    private GameCreatedListener gameCreatedListener;
    private JPanel topPane;
    private JPanel middlePane;
    private List<JTextField> playerNameFields;
    private JPanel bottomPane;


    /**
     * Initializes and sets up the initial window for the game.
     * The window is split into three seperate panels.
     *
     * @param listener A listener that will trigger the start of the game.
     * @see #setupTopPane()
     * @see #setupMiddlePane()
     * @see #setupBottomPane()
     */
    Splash(GameCreatedListener listener) {
        super("Welcome to Oakland Oligarchy");
        this.gameCreatedListener = listener;
        setLayout(new GridLayout(3,1));
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(new Dimension(640, 480));

        //int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        //int y = (int) ((dimension.getHeight() - getHeight()) / 2);

        //Sets up all three panels and adds them to the window.
        add(setupTopPane());
        add(setupMiddlePane());
        add(setupBottomPane());


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    /**
     * Sets up a panel to display the combo box to select the number of players for the game.
     *
     * @return Number of players selection panel.
     */
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

    /**
     * Sets up a blank panel as a canvas for name input boxes.
     * @return Blank panel.
     */
    private JPanel setupMiddlePane() {
        JPanel middlePanel = new JPanel();
        middlePane = middlePanel;

        return middlePanel;
    }

    /**
     * Sets up a panel for a start button.
     * @return Panel with start button and action listener attached.
     */
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

    /**
     * An item event listener for the number of players combo box. Alters the middle panel to display a number
     * of textboxes corresponding to the number of players selected.
     * @param e The fired item event.
     * @see #setupTopPane()
     * @see #setupMiddlePane()
     */
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


    /**
     * Action event listener for the start button. Checks to see that the number of players selected is valid
     * before starting the game.
     * @param e Fired action event.
     * @see #setupBottomPane()
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (playerNameFields == null) {
            //ERROR HERE
        } else {
            Player[] players = new Player[playerNameFields.size()];

            Color[] colors = {Color.RED,Color.BLUE,Color.CYAN,Color.GREEN};

            for (int i = 0; i < players.length; i++) {
                players[i] = new Player(playerNameFields.get(i).getText(),colors[i]);
            }

            gameCreatedListener.onGameCreated(players);
            this.dispose();
        }

    }

}
