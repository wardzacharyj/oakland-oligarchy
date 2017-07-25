package Game;

import Game.UI.ClockPanel;
import Game.UI.TurnPanel;
import Utilities.GameCrypto;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        save = new JMenuItem("Save");

        fileMenu.add(save);

        save.addActionListener(this);

        add(fileMenu);
    }


    /**
     *
     */
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


    /**
     * This encrypts and creates an oll file with respective game state
     *
     * @param playerList list of all current players and their respective states
     */
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
                fw.write(GameCrypto.encrypt(playerList.toString()));
                fw.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


    }


    /**
     * This updates local storage with the filepath where the oll was saved
     *
     * @param savedFileFilePath
     */
    private void logSavedFileLocation(String savedFileFilePath){

        String f = prefs.get("filenames", null);
        if(f == null) throw new NullPointerException();

        JsonParser jsonParser = new JsonParser();
        JsonObject filenames = jsonParser.parse(f).getAsJsonObject();
        JsonArray jsonArray = filenames.getAsJsonArray("games");

        // Avoids double entries to same fileLocation on recent list
        for (JsonElement j: jsonArray){
            JsonObject check = j.getAsJsonObject();
            if(check.get("fileLocation").getAsString().equals(savedFileFilePath))
                return;
        }

        JsonObject game = new JsonObject();
        game.addProperty("gameName",gameName);
        game.addProperty("fileLocation", savedFileFilePath);

        jsonArray.add(game);

        JsonObject modifiedSave = new JsonObject();
        modifiedSave.add("games", jsonArray);

        prefs.put("filenames", modifiedSave.toString());


    }


    /**
     *  Calls save function on button click
     *
     * @param e action event generated when save button is pressed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem item = (JMenuItem) e.getSource();
        if(item == save){
            save();
        }
    }
}
