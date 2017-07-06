package Game.UI;


import Game.Player;

public interface GameCreatedListener {
    void onGameCreated(String gameTitle, Player[] players);
    void onGameLoaded(String gameTitle, long timeElapsed, int playerTurn, Player[] players);
}
