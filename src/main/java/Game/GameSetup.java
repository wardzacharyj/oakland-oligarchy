package Game;


import Game.UI.GameCreatedListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;


public class GameSetup extends JFrame implements  ListCellRenderer<GameSetup.GameCell>, MouseListener{

    private int hoverIndex = -1;

    private AnimateButton btnNewGame;
    private AnimateButton btnOpenGame;
    private AnimateButton btnDevStart;
    private AnimateButton backBtn;

    private JLabel errorBanner;
    private JLabel gameNameLabel;

    private JButton startGame;


    private JList<GameCell> gameList;

    private ArrayList<PlayerRowPanel> playerPanels;

    private GameCreatedListener gameCreatedListener;

    private int DEV_PLAYER_COUNT = 4;
    private Color[] DEV_PLAYER_COLORS = {Color.BLUE, Color.DARK_GRAY, Color.GREEN, Color.CYAN};

    public GameSetup(GameCreatedListener listener){
        super("Welcome To Oakland Oligarchy");

        this.gameCreatedListener = listener;

        setPreferredSize(new Dimension(665, 460));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        playerPanels = new ArrayList<>();
        initWelcome();
    }

    /**
     *  Loads the Main Menu splash screen
     */
    private void initWelcome(){
        setLayout(new BorderLayout());
        buildLocatedGamesPanel();
        buildSplashPanel();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }


    /**
     * Loads Games from saved directory file in resources
     */
    private void loadRecentGames(){

    }


    /**
     *  Generated Splash Panel which contains create new game, load game, and dev start
     */
    private void buildSplashPanel(){
        JPanel splashPanel = new JPanel();
        splashPanel.setLayout(new BorderLayout());
        splashPanel.setBackground(Color.decode("#f9f9f9"));
        splashPanel.setBorder(new MatteBorder(0,1,0,0,Color.decode("#f0f0f0")));

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top,BoxLayout.Y_AXIS));
        top.setOpaque(false);

        JPanel topHolder = new JPanel();
        topHolder.setLayout(new BoxLayout(topHolder,BoxLayout.Y_AXIS));
        topHolder.setOpaque(false);
        topHolder.setBorder(new EmptyBorder(50,10,10,20));

        try{

            Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResource("kabel.ttf").openStream());
            GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            genv.registerFont(font);
            font = font.deriveFont(35f);

            URL url = getClass().getClassLoader().getResource("logo.png");
            BufferedImage initialImg = ImageIO.read(url);
            Image image = initialImg.getScaledInstance(initialImg.getWidth(), initialImg.getHeight(), Image.SCALE_DEFAULT);
            ImageIcon imageIcon = new ImageIcon(image);
            JLabel icon = new JLabel(imageIcon);
            icon.setAlignmentX(Component.CENTER_ALIGNMENT);


            JLabel label = new JLabel("Oakland Oligarchy", JLabel.CENTER);
            label.setBorder(new EmptyBorder(15,0,0,0));
            label.setFont(font);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);

            btnNewGame = new AnimateButton("Start A New Game", "ic_new_game_white.png");
            btnOpenGame = new AnimateButton("Open an Old Game", "ic_folder_open_white.png");
            btnOpenGame.setBorder(new EmptyBorder(15,0,0,0));
            btnNewGame.addMouseListener(this);
            btnOpenGame.addMouseListener(this);



            JPanel actionContainer = new JPanel();
            actionContainer.setOpaque(false);
            actionContainer.setLayout(new BoxLayout(actionContainer,BoxLayout.Y_AXIS));
            actionContainer.add(btnNewGame);
            actionContainer.add(btnOpenGame);
            actionContainer.setBorder(new EmptyBorder(30,0,0,0));
            actionContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
            topHolder.add(icon);
            topHolder.add(label);
            topHolder.add(actionContainer);

        }catch (Exception e){
            e.printStackTrace();
        }

        top.add(topHolder);



        JPanel bottom = new JPanel();
        bottom.setLayout(new FlowLayout());


        splashPanel.add(top,BorderLayout.CENTER);

        JPanel devMenu = new JPanel();
        devMenu.setOpaque(false);
        devMenu.setLayout(new BorderLayout());
        btnDevStart = new AnimateButton("Launch Dev Game");
        btnDevStart.setBorder(new EmptyBorder(10,0,10,15));
        btnDevStart.addMouseListener(this);
        devMenu.add(btnDevStart,BorderLayout.EAST);


        splashPanel.add(devMenu,BorderLayout.SOUTH);

        add(splashPanel,BorderLayout.CENTER);

    }


    /**
     *      Builds the scroll panel displayed on the right which lists saved
     *      games for reopening
     */
    private void buildLocatedGamesPanel(){


        DefaultListModel<GameCell> listModel = new DefaultListModel<GameCell>();


        for(int i = 0; i < 5; i++){
            String gameName = "<html><b>Game "+i+" </b></html>";
            String fileLocation ="Location Here";
            GameCell gameCell = new GameCell(gameName,fileLocation);
            listModel.addElement(gameCell);

        }

        gameList = new JList<GameCell>(listModel);
        gameList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        gameList.setCellRenderer(this);

        gameList.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = new Point(e.getX(),e.getY());
                hoverIndex = gameList.locationToIndex(p);
                gameList.repaint();
            }
        });

        gameList.addMouseListener(this);


        JScrollPane jScrollPane = new JScrollPane(gameList);

        jScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        jScrollPane.setBorder(BorderFactory.createEmptyBorder());
        jScrollPane.setBackground(Color.WHITE);


        jScrollPane.setPreferredSize(new Dimension(245,460));
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(jScrollPane,BorderLayout.WEST);

    }


    /**
     *      Builds Screen for where players can enter names and game configuration
     */
    private void loadGameConfigurationScreen(){
        setCursor(Cursor.getDefaultCursor());

        //setLayout(new BorderLayout());
        JPanel parent = new JPanel();
        parent.setBackground(Color.decode("#f9f9f9"));
        parent.setLayout(new BorderLayout());

        JPanel playerEntryBanner = new JPanel();
        playerEntryBanner.setLayout(new BorderLayout());
        playerEntryBanner.setBackground(Color.decode("#F44336"));

        JLabel bannerText = new JLabel("Game Setup");
        bannerText.setOpaque(false);
        bannerText.setForeground(Color.WHITE);
        bannerText.setBorder(new EmptyBorder(15,40,15,0));

        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResource("kabel.ttf").openStream());
            GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            genv.registerFont(font);
            font = font.deriveFont(35f);
            bannerText.setFont(font);
        }catch (Exception e){
            e.printStackTrace();
        }

        playerEntryBanner.add(bannerText,BorderLayout.WEST);


        JComboBox<String> playerCombo = new JComboBox<>();
        playerCombo.setBorder(new EmptyBorder(0,0,0,20));
        playerCombo.addItem("2 Players");
        playerCombo.addItem("3 Players");
        playerCombo.addItem("4 Players");
        int indexedOffset = 2;


        playerEntryBanner.add(playerCombo,BorderLayout.EAST);


        JPanel gameInfo = new JPanel();
        gameInfo.setLayout(new GridLayout(5,1));
        gameInfo.setBackground(Color.decode("#f9f9f9"));


        JPanel gameEntryPanel = new JPanel();
        gameEntryPanel.setBackground(Color.WHITE);

        gameEntryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        gameNameLabel = new JLabel("Game Name:");
        MatteBorder matteBorder = new MatteBorder(0,0,2,0, Color.decode("#f0f0f0"));
        EmptyBorder emptyBorder = new EmptyBorder(10,40,0,0);
        CompoundBorder compoundBorder = new CompoundBorder(matteBorder,emptyBorder);
        gameEntryPanel.setBorder(compoundBorder);

        JTextField gameNameField = new JTextField(15);
        gameEntryPanel.add(gameNameLabel);
        gameEntryPanel.add(gameNameField);

        gameInfo.add(gameEntryPanel);

        playerCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                updatePlayerEntryLayout(gameInfo,cb.getSelectedIndex()+indexedOffset);
            }
        });

        JPanel footer = new JPanel();
        footer.setOpaque(false);
        footer.setLayout(new BorderLayout());

        backBtn = new AnimateButton("Main Menu","ic_arrow_back_red.png");
        backBtn.setBorder(new EmptyBorder(10,10,10,10));
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.setHorizontalAlignment(JLabel.CENTER);
        backBtn.setVerticalAlignment(JLabel.CENTER);
        backBtn.addMouseListener(this);

        errorBanner = new JLabel("");
        errorBanner.setHorizontalAlignment(JLabel.CENTER);
        errorBanner.setVerticalAlignment(JLabel.CENTER);

        JPanel btnHolder = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnHolder.setOpaque(false);
        startGame = new JButton("Start Game");
        startGame.addMouseListener(this);
        //startGame.setPreferredSize(new Dimension(80,20));
        btnHolder.add(startGame);

        footer.add(backBtn,BorderLayout.WEST);
        footer.add(errorBanner,BorderLayout.CENTER);
        footer.add(btnHolder,BorderLayout.EAST);


        parent.add(playerEntryBanner,BorderLayout.NORTH);
        parent.add(gameInfo,BorderLayout.CENTER);
        parent.add(footer,BorderLayout.SOUTH);


        setContentPane(parent);
        getContentPane().invalidate();
        getContentPane().validate();
        setVisible(true);
        repaint();

        // Default Player start
        updatePlayerEntryLayout(gameInfo,2);

    }


    /**
     *  Switches out rows for more or less player entry panels
     * @param gameInfo
     * @param numberPlayers
     */
    private void updatePlayerEntryLayout(JPanel gameInfo, int numberPlayers){
        playerPanels.clear();
        int activeComponents = gameInfo.getComponentCount();
        // Remove previous player rows
        for (int i = activeComponents-1; i > 0; i--){
            gameInfo.remove(i);
        }

        for (int z = 0; z < numberPlayers; z++){
            PlayerRowPanel playerRowPanel = new PlayerRowPanel("Player "+(z+1)+":");
            gameInfo.add(playerRowPanel);
            playerPanels.add(playerRowPanel);
            revalidate();
            repaint();
        }

    }


    /**
     * Catches and directs all navigation click events on buttons
     *
     * @param e Click event
     * @see #loadGameConfigurationScreen()
     * @see #buildDevPlayers()
     * @see #buildPlayerObjects()
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        Object src = e.getSource();
        if(src == btnNewGame){
            loadGameConfigurationScreen();
        }
        else if(src == btnOpenGame){
            JFileChooser fileChooser = new JFileChooser();

            FileNameExtensionFilter filter = new FileNameExtensionFilter("Oligarchy Files", "oli");
            fileChooser.setFileFilter(filter);
            fileChooser.setAcceptAllFileFilterUsed(false);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println(selectedFile.getName());
            }
        }
        else if(src == btnDevStart){
            buildDevPlayers();
        }
        else if(src == gameList){
            // Load game files here
        }
        else if(src == backBtn){
            getContentPane().removeAll();
            repaint();
            initWelcome();
        }
        else if(src == startGame){
            if(isValidForm()){
                buildPlayerObjects();
            }
        }
    }


    /**
     * Checks validity of form
     *
     * @return whether all fields are filed
     */
    private boolean isValidForm(){
        errorBanner.setText("");
        revalidate();
        repaint();

        if(gameNameLabel.getText().length() == 0){
            errorBanner.setText("Remember to name this game");
            revalidate();
            repaint();
            return false;
        }

        for(PlayerRowPanel p : playerPanels){
            if(!p.hasColor()){
                errorBanner.setText("Make sure all players have selected a token color");
                revalidate();
                repaint();
                return false;
            }
            if(!p.hasName()){
                errorBanner.setText("Make sure everyone remembers to enter their name");
                revalidate();
                repaint();
                return false;
            }

        }

        return true;
    }


    /**
     * Debug method for quick testing
     */
    private void buildDevPlayers(){
        Player[] players = new Player[DEV_PLAYER_COUNT];

        for(int i = 0; i < DEV_PLAYER_COUNT; i++){
            players[i] = new Player("Player "+i, DEV_PLAYER_COLORS[i]);
        }

        gameCreatedListener.onGameCreated(players);
        this.dispose();
    }


    /**
     * Builds player objects from form to load into the game
     */
    private void buildPlayerObjects(){
        Player[] players = new Player[playerPanels.size()];

        for(int i = 0; i < players.length; i++){
            String name = playerPanels.get(i).getName();
            Color playerColor = playerPanels.get(i).getColor();
            players[i] = new Player(name, playerColor);
        }

        gameCreatedListener.onGameCreated(players);
        this.dispose();
    }


    /**
     * Switches mouse cursor to selector
     *
     * @param e the mouse event
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        Object src = e.getSource();
        if(src == btnNewGame || src == btnOpenGame || src == btnDevStart){
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }


    /**
     * Switches mouse cursor back to default
     *
     * @param e
     */
    public void mouseExited(MouseEvent e) {
        Object src = e.getSource();
        if(src == btnNewGame || src == btnOpenGame || src == btnDevStart){
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

    }


    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}


    /**
     *      Custom JPanel that acts as a form for collecting player name and color
     */
    private class PlayerRowPanel extends JPanel implements MouseMotionListener, MouseListener{

        private JTextField playerName;

        private boolean isHovered;
        private Ellipse2D circleOutline;
        private Ellipse2D circle;

        private Random random;

        private int colorIndex = -1;

        private Color[] colors = {
                Color.decode("#F44336"), // Red
                Color.decode("#E91E63"), // Pink
                Color.decode("#9C27B0"), // Purple
                Color.decode("#673AB7"), // Deep Purple
                Color.decode("#3F51B5"), // Indigo
                Color.decode("#2196F3"), // Blue
                Color.decode("#03A9F4"), // Light Blue
                Color.decode("#00BCD4"), // Cyan
                Color.decode("#009688"), // Teal
                Color.decode("#4CAF50"), // Green
                Color.decode("#8BC34A"), // Light Green
                Color.decode("#CDDC39"), // Lime
                Color.decode("#FFEB3B"), // Yellow
                Color.decode("#FFC107"), // Amber
                Color.decode("#FF9800"), // Orange
                Color.decode("#FF5722"), // Deep Orange
                Color.decode("#795548"), // Brown
                Color.decode("#607D8B") // Blue Grey
        };

        /**
         * Constructor that creates Player form panel
         *
         * @param playerLabel the label for the player name input
         */
        private PlayerRowPanel(String playerLabel){
            setLayout(new FlowLayout(FlowLayout.LEFT));
            setOpaque(false);

            setPreferredSize(new Dimension(Integer.MAX_VALUE,50));
            setMaximumSize(new Dimension(Integer.MAX_VALUE,50));

            random = new Random();

            EmptyBorder listOffsets = new EmptyBorder(22,40,0,0);
            setBorder(listOffsets);

            circleOutline = new Ellipse2D.Double(40,18,40,40);
            circle = new Ellipse2D.Double(42,20,36,36);

            JLabel playerNameLabel = new JLabel(playerLabel);

            playerNameLabel.setBorder(new EmptyBorder(0,54,0,0));
            playerName = new JTextField(15);

            add(playerNameLabel);
            add(playerName);
            addMouseMotionListener(this);
            addMouseListener(this);

        }


        /**
         * Getter for the players name
         *
         * @return player's name
         */
        public String getName(){
            return playerName.getText();
        }


        /**
         * Checks whether a name has been entered
         *
         * @return true when a name has been entered
         */
        private boolean hasName(){
            return getName().length() > 0;
        }

        /**
         * Checks whether a color has been selected
         *
         * @return true when a valid color has been chosen
         */
        private boolean hasColor(){
            return colorIndex != -1;
        }


        /**
         * Fetches the color selected
         *
         * @return the color selected or null if not selected
         */
        public Color getColor(){
            if(colorIndex != -1)
                return colors[colorIndex];
            else
                return null;
        }


        /**
         * Draws the color selector and handles random color changes
         *
         * @param g graphics object for the drawing on the row
         */
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D)g;
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setRenderingHints(rh);

            if(isHovered){
                g2d.setColor(Color.decode("#b71c1c"));
                g2d.fill(circleOutline);
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.fill(circle);

            }
            else {
                g2d.setColor(Color.decode("#dfdfdf"));
                g2d.fill(circleOutline);

            }

            if(colorIndex == -1){
                g2d.setColor(Color.WHITE);
            }
            else {
                g2d.setColor(colors[colorIndex]);
            }

            g2d.fill(circle);

        }


        /**
         * Checks to see if mouse is contained within the color selector
         * Will highlight outline if mouse is contained within the bounds of the circle
         *
         * @param e
         */
        @Override
        public void mouseMoved(MouseEvent e) {
            if(circle.contains(e.getPoint()) || circleOutline.contains(e.getPoint())){
                isHovered = true;
                repaint();
            }
            else {
                if(isHovered){
                    isHovered = false;
                    repaint();
                }
            }
        }


        /**
         * Randomly switches the color selected on every click when
         * mouse is within the bounds of the color selector
         *
         * @param e mouse event
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            if(circle.contains(e.getPoint()) || circleOutline.contains(e.getPoint())){
                int n = random.nextInt(colors.length);
                if(n == colorIndex && n == colors.length-1){
                    colorIndex = 0;
                }
                else if(n == colorIndex){
                    colorIndex = n + 1;
                }
                else {
                    colorIndex = n;
                }

                repaint();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}

        @Override
        public void mouseDragged(MouseEvent e) {}

        @Override
        public String toString() {
            if(colorIndex == -1) {
                return "PlayerRowPanel{" +
                        "playerName=" + playerName +
                        ", color=NULL" +
                        '}';
            }
            else {
                return "PlayerRowPanel{" +
                        "playerName=" + playerName +
                        ", color=" + colors[colorIndex] +
                        '}';
            }
        }
    }


    /**
     *      A Button which highlights on hover
     */
    class AnimateButton extends JLabel implements MouseListener{

        AnimateButton(String name){
            super(name);
            addMouseListener(this);

        }

        AnimateButton(String name, String resourceURL){
            super(name);
            try {
                URL url = getClass().getClassLoader().getResource(resourceURL);
                BufferedImage initialImg = ImageIO.read(url);
                Image image = initialImg.getScaledInstance(24, 24, Image.SCALE_DEFAULT);
                ImageIcon imageIcon = new ImageIcon(image);
                setIcon(imageIcon);
                addMouseListener(this);

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {
            setForeground(Color.decode("#b71c1c"));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            setForeground(Color.BLACK);
        }
    }


    /**
     *      This builds the listItem
     */
    class GameCell extends JPanel{
        private final EmptyBorder TITLE_MARGIN = new EmptyBorder(10,10,10,10);
        GameCell(String gameName, String fileLocation){
            setOpaque(true);
            setBackground(Color.WHITE);

            setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

            String text = "<html><b>"+gameName+"</b></html>";

            JLabel label1 = new JLabel(text);
            JLabel fLocation = new JLabel(fileLocation);
            fLocation.setBackground(Color.DARK_GRAY);


            label1.setBorder(TITLE_MARGIN);
            fLocation.setBorder(TITLE_MARGIN);

            add(label1);
            add(fLocation);

        }


    }


    /**
     * Custom Jlist renderer for displaying game name and file location
     *
     * @param list
     * @param value
     * @param index
     * @param isSelected
     * @param cellHasFocus
     * @return
     */
    public Component getListCellRendererComponent(JList<? extends GameCell> list,
                                                  GameCell value, int index, boolean isSelected,
                                                  boolean cellHasFocus) {

        if(hoverIndex != -1 && hoverIndex == index){
            value.setBackground(Color.decode("#EF5350"));
            JLabel gameName = (JLabel) value.getComponent(0);
            JLabel gameLocation = (JLabel) value.getComponent(1);
            gameName.setForeground(Color.WHITE);
            gameLocation.setForeground(Color.WHITE);

        }
        else {
            value.setBackground(Color.WHITE);
            JLabel gameName = (JLabel) value.getComponent(0);
            JLabel gameLocation = (JLabel) value.getComponent(1);
            gameName.setForeground(Color.BLACK);
            gameLocation.setForeground(Color.DARK_GRAY);

        }

        return value;

    }

}
