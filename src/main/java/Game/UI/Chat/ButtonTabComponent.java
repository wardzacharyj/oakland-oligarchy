package Game.UI.Chat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;


class ButtonTabComponent extends JPanel  {
    private final JTabbedPane pane;
    private final ChatPanel chatPanel;

    protected ButtonTabComponent(final int pos, final ChatPanel chatPanel) {
        //unset default FlowLayout' gaps
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.chatPanel = chatPanel;

        this.pane = chatPanel.getTabbedPane();

        if (pane == null) throw new NullPointerException("Chat Panel is null");

        JLabel userName = new JLabel() {
            public String getText() {
                int index = pane.indexOfTabComponent(ButtonTabComponent.this);
                if (index != -1) return pane.getTitleAt(index);
                return null;
            }
        };
        userName.setBorder(new EmptyBorder(0, 0, 0, 5));
        add(userName);
        if(pos != 0){
            CloseButton button = new CloseButton();
            add(button);
        }


        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        setOpaque(false);

    }

    private class CloseButton extends JButton implements ActionListener, MouseListener {
        private CloseButton() {
            Dimension buttonDim = new Dimension(17,17);
            setPreferredSize(buttonDim);
            setUI(new BasicButtonUI());
            setFocusable(false);
            setToolTipText("Close Private Chat Window");
            setContentAreaFilled(false);
            setBorderPainted(false);
            setRolloverEnabled(true);

            //Listeners
            addMouseListener(this);
            addActionListener(this);
        }

        public void actionPerformed(ActionEvent e) {
            int index = pane.indexOfTabComponent(ButtonTabComponent.this);
            if (index > 0) chatPanel.closeTabAt(index);
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            BasicStroke xThickness = new BasicStroke(2);
            g2.setColor(Color.BLACK);
            g2.setStroke(xThickness);
            if (getModel().isRollover()) g2.setColor(Color.RED);
            int offset = 5;
            g2.drawLine(getWidth() - offset - 1, offset, offset, getHeight() - offset - 1);
            g2.drawLine(offset, offset, getWidth() - offset - 1, getHeight() - offset - 1);
            g2.dispose();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}
    }



}

