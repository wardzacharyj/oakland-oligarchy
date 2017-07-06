package Game;

import Game.Board.Board;
import Game.UI.ClockPanel;
import Game.UI.Leaderboard;
import Game.UI.TurnPanel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.prefs.Preferences;


public class GameToolbar extends JMenuBar implements ActionListener {

    private Player[] players;
    private JMenuItem save;
    private JMenuItem saveAs;
    private JMenuItem load;

    private TurnPanel turnPanel;
    private String gameName;
    private ClockPanel clockPanel;

    private Preferences prefs;


    public GameToolbar(Player[] players, TurnPanel turnPanel, ClockPanel clockPanel, String gameName, Preferences prefs){

        this.prefs = prefs;
        this.players = players;
        this.turnPanel = turnPanel;
        this.clockPanel = clockPanel;
        this.gameName = gameName;


        // File Menu, F - Mnemonic
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        save = new JMenuItem("Save");
        saveAs = new JMenuItem("Save As");
        load = new JMenuItem("Load");

        fileMenu.add(save);
        fileMenu.add(saveAs);
        fileMenu.add(load);

        save.addActionListener(this);
        saveAs.addActionListener(this);
        load.addActionListener(this);

        add(fileMenu);
    }

    private void save(){

        JsonArray playerObjects = new JsonArray();
        for(Player p : players){
            playerObjects.add(p.toJSONObject());
        }
        JsonObject playerList = new JsonObject();
        playerList.addProperty("gameName", gameName);
        playerList.addProperty("timeElapsed", clockPanel.getGameTime());
        playerList.addProperty("playerTurn", turnPanel.getCurrentPlayer());
        playerList.add("players", playerObjects);

        writeOutOLLFile(playerList);


    }

    private void writeOutOLLFile(JsonObject playerList){

        JFileChooser chooser = new JFileChooser();
        int code = chooser.showSaveDialog(null);
        if (code == JFileChooser.APPROVE_OPTION) {
            try {
                String name = chooser.getSelectedFile().getName();

                if (name.length() == 1 && name.contains(".")){
                    JOptionPane.showMessageDialog(null,
                            "\'.\' is removed from file names so your game cannot not be saved under that name",
                            "Filename Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String formattedFileName = chooser.getSelectedFile().getAbsolutePath() + ".oll";
                logSavedFileLocation(formattedFileName);
                File f = new File(formattedFileName);
                FileWriter fw = new FileWriter(f);
                fw.write(playerList.toString());

                System.out.println(playerList.toString());
                fw.close();

                System.out.println();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


    }

    private void logSavedFileLocation(String savedFileFilePath){

        String f = prefs.get("filenames", null);
        if(f == null) throw new NullPointerException();

        JsonParser jsonParser = new JsonParser();
        JsonObject filenames = jsonParser.parse(f).getAsJsonObject();
        JsonArray jsonArray = filenames.getAsJsonArray("games");

        JsonObject game = new JsonObject();
        game.addProperty("gameName",gameName);
        game.addProperty("fileLocation", savedFileFilePath);

        jsonArray.add(game);

        JsonObject modifiedSave = new JsonObject();
        modifiedSave.add("games", jsonArray);

        prefs.put("filenames", modifiedSave.toString());


    }


    private void saveAs(){

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem item = (JMenuItem) e.getSource();
        if(item == save){
            save();
        }
        else if(item == saveAs){
            saveAs();
        }
        else if(item == load){

        }
    }
}
