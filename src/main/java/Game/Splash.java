package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Splash extends JPanel implements ItemListener {

    private JPanel cards;

    public Splash() {
        super(new BorderLayout());

        JPanel numPlayersBoxPane = new JPanel(new FlowLayout());
        String[] numPlayersList = {"2", "3", "4"};

        JLabel numPlayersLabel = new JLabel("Number of Players");
        JComboBox numPlayersBox = new JComboBox(numPlayersList);

        add(numPlayersLabel, BorderLayout.PAGE_START);
        add(numPlayersBox, BorderLayout.PAGE_END);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    }

    static void splashInit() {

        JFrame splashFrame = new JFrame("Oakland Oligarchy");
        splashFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JComponent contentPane = new Splash();
        contentPane.setOpaque(true);
        splashFrame.setContentPane(contentPane);

        splashFrame.pack();
        splashFrame.setVisible(true);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}
