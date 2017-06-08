package Game.UI.Chat;

import Game.Player;
import javafx.util.Pair;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class ChatPanel extends JPanel implements ActionListener, ChangeListener, MessageReceivedListener{


    private ChatClient chatClient;
    private JTabbedPane tabbedPane;
    private JComboBox<Player> userSelector;
    private JTextField messageField;

    private JTextPane channelArea;


    private HashMap<String, Pair<Player,ArrayList<String>>> messageHistory;
    //private HashMap<Player,ArrayList<String>> messageHistory;

    private int openTabs = 1;


    private Player localUser;

    private HTMLEditorKit kit;
    private HTMLDocument doc;

    public ChatPanel(Player localUser, Player[] otherPlayers){

        Dimension chatPanelSize = new Dimension(300,250);
        Dimension tabbedPaneSize = new Dimension(100,100);


        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10,10,10,10));

        this.localUser = localUser;
        this.kit = new HTMLEditorKit();
        this.doc = new HTMLDocument();
        this.chatClient = new ChatClient(localUser, "gameID");
        this.chatClient.setMessageReceivedListener(this);
        this.tabbedPane = new JTabbedPane();
        this.tabbedPane.setPreferredSize(tabbedPaneSize);
        this.tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        this.userSelector = new JComboBox<Player>();
        this.messageHistory = new HashMap<>();

        messageField = new JTextField();


        this.userSelector.setRenderer(new PlayerComboBoxRenderer());

        ArrayList<String> mainHistory = new ArrayList<String>();

        this.messageHistory.put("Main",new Pair<>(localUser,mainHistory));
        this.userSelector.addItem(localUser);

        for(Player player : otherPlayers) {
            messageHistory.put(player.getName(), new Pair<>(player,new ArrayList<String>()));
            userSelector.addItem(player);
        }
        this.userSelector.addActionListener(this);

        tabbedPane.addChangeListener(this);

        JPanel holder = new JPanel(new BorderLayout());
        holder.add(userSelector,BorderLayout.EAST);

        // Default Main Chat
        addNewChat(0,"Main");
        add(holder,BorderLayout.NORTH);
        add(tabbedPane);
        setPreferredSize(chatPanelSize);
        setMinimumSize(chatPanelSize);
        setMaximumSize(chatPanelSize);


        setBackground(Color.PINK);

    }

    private void addNewChat(int pos, String title){
        this.tabbedPane.add(title, buildMessageWindow());
        this.tabbedPane.setTabComponentAt(pos, new ButtonTabComponent(pos,this));
    }


    private JPanel buildMessageWindow(){

        Dimension messageWindowSize = new Dimension(100,80);

        JPanel messageWindow = new JPanel();
        messageWindow.setLayout(new BorderLayout());
        messageWindow.setPreferredSize(messageWindowSize);


        channelArea = new JTextPane();
        channelArea.setContentType("text/html");
        channelArea.setFont(channelArea.getFont().deriveFont(10f));
        channelArea.setPreferredSize(messageWindowSize);
        channelArea.setBorder(new EmptyBorder(10,10,10,10));
        channelArea.setEditorKit(kit);
        channelArea.setDocument(doc);


        JScrollPane scrollPane = new JScrollPane(channelArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(messageWindowSize);


        JPanel holder = new JPanel(new BorderLayout());
        messageField.addActionListener(this);
        holder.add(messageField, BorderLayout.CENTER);

        messageWindow.add(scrollPane, BorderLayout.CENTER);
        messageWindow.add(holder, BorderLayout.SOUTH);




        return messageWindow;
    }


    protected JTabbedPane getTabbedPane(){
        return tabbedPane;
    }

    protected void closeTabAt(int pos){
        tabbedPane.remove(pos);
        openTabs = openTabs - 1;
    }

    @Override
    public void onMessageReceived(Message message) {
        System.out.println(message.toString());
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        String titleKey = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
        setUpChat(titleKey);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Message Field:" +messageField.getText());
        if(e.getActionCommand().equals("comboBoxChanged")){
            int selectedIndex = userSelector.getSelectedIndex();

            Player chosenPlayer = userSelector.getItemAt(selectedIndex);
            boolean found = false;


            if(chosenPlayer.equals(localUser)){
                tabbedPane.setSelectedIndex(0);
                found = true;
            }
            else {
                for (int i = 1; i < openTabs; i++) {
                    String title = tabbedPane.getTitleAt(i);
                    if (chosenPlayer.getName().equals(title)) {
                        tabbedPane.setSelectedIndex(i);
                        found = true;
                        break;
                    }
                }
            }

            if(!found){
                addNewChat(openTabs,chosenPlayer.getName());
                tabbedPane.setSelectedIndex(openTabs);
                openTabs++;
            }

            setUpChat(chosenPlayer.getName());


        }
        // Entered Text in Message Field
        else {
            try {
                String htmlMessageText = "<b>"+localUser.getName()+"</b> "+messageField.getText();

                kit.insertHTML(doc, doc.getLength(), htmlMessageText, 0, 0, null);


                String title = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
                System.out.println("Message TO: " +title+" "+htmlMessageText);
                messageHistory.get(title).getValue().add(htmlMessageText);


                //chatClient.broadcast(messageField.getText());

                messageField.setText("");
            }catch (IOException e3){
                e3.printStackTrace();
            }catch (BadLocationException e4){
                e4.printStackTrace();
            }
        }
    }


    private void setUpChat(String playerName){


        channelArea.setText("");
        System.out.println(playerName);
        System.out.println(Arrays.toString(messageHistory.get(playerName).getValue().toArray()));

        ArrayList<String> mHistory = messageHistory.get(playerName).getValue();

        if(mHistory != null) {
            for (int i = 0; i < mHistory.size(); i++) {
                try {
                    kit.insertHTML(doc, doc.getLength(), mHistory.get(i), 0, 0, null);
                } catch (BadLocationException e2) {
                    e2.printStackTrace();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    class PlayerComboBoxRenderer extends JLabel implements ListCellRenderer<Player> {

        private PlayerComboBoxRenderer() {
            setOpaque(true);
            setHorizontalAlignment(CENTER);
            setVerticalAlignment(CENTER);
        }

        public Component getListCellRendererComponent(
                JList<? extends Player> list, Player player, int index, boolean isSelected, boolean cellHasFocus) {
            if(!player.getName().equals(localUser.getName()))
                setText(player.getName());
            else
                setText("Main");

            return this;
        }

    }


}

