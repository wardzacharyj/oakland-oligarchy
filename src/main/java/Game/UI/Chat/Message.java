package Game.UI.Chat;


import java.util.prefs.Preferences;

public class Message {
    protected static final String GAME_ID = "gid";
    protected static final String MESSAGE_SENDER = "sender";
    protected static final String MESSAGE_DESTINATION = "destination";
    protected static final String ALL_DESTINATIONS = "all";
    protected static final String MESSAGE_TEXT = "text";

    private String gameID;
    private String sender;
    private String text;
    private String destination = ALL_DESTINATIONS;

    public Message() {
        Preferences preferences = Preferences.userRoot();

    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean isPrivate(){
        return !destination.equals(ALL_DESTINATIONS);
    }

    @Override
    public String toString() {
        return "Message{" +
                "gameID='" + gameID + '\'' +
                ", sender='" + sender + '\'' +
                ", text='" + text + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }
}

