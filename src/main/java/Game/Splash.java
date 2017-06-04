package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Splash extends JPanel implements ItemListener {

    private JPanel cards;

    private Splash() {
        super(new GridLayout(2, 1));
        String[] numPlayersList = {"2", "3", "4"};
        JComboBox numPlayersBox = new JComboBox<>(numPlayersList);

        JLabel numPlayersLabel = new JLabel("Number of Players", SwingConstants.CENTER);

        numPlayersBox.setEditable(false);
        numPlayersBox.addItemListener(this);

        SpringLayout layout = new SpringLayout();

        JPanel numPlayersPane = new JPanel();
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, numPlayersLabel, 0,
                SpringLayout.HORIZONTAL_CENTER, numPlayersPane);
        layout.putConstraint(SpringLayout.NORTH, numPlayersLabel, 80,
                SpringLayout.NORTH, numPlayersPane);

        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, numPlayersBox, 0,
                SpringLayout.HORIZONTAL_CENTER, numPlayersPane);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, numPlayersBox, 0,
                SpringLayout.VERTICAL_CENTER, numPlayersPane);

        numPlayersPane.setLayout(layout);

        numPlayersPane.add(numPlayersLabel);
        numPlayersPane.add(numPlayersBox);


        add(numPlayersPane);

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            int numPlayers = Integer.parseInt((String) e.getItem());

            switch (numPlayers) {
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;

            }
        }
    }

    static void splashInit() {

        JFrame splashFrame = new JFrame("Oakland Oligarchy");
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
}
