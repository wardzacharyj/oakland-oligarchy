package Game.UI.Chat;

import Game.Player;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Zach on 6/5/17.
 */
public class ChatClient implements Runnable {



    // The client socket
    private Socket clientSocket = null;
    // The output stream
    private PrintStream os = null;
    // The input stream
    private BufferedReader is = null;

    private boolean closed = false;

    // The default port.
    private int portNumber = 2222;
    // The default host.
    private String host = "localhost";

    private MessageReceivedListener listener;

    private Player localUser;
    private String gameID;

    public ChatClient(Player localUser, String gameID) {
        try {
            this.localUser = localUser;
            this.gameID = gameID;

            clientSocket = new Socket(host, portNumber);
            os = new PrintStream(clientSocket.getOutputStream());
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            new Thread(this).start();

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + host);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to the host " + host);
        }

    }

    public void setMessageReceivedListener (MessageReceivedListener listener) {
        this.listener = listener;
    }

    public void broadcast(final String text){

        /* Create a thread to read from the server. */
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!closed) {

                    Message message = new Message();
                    message.setGameID(gameID);
                    message.setSender(localUser.getName());
                    message.setDestination(Message.ALL_DESTINATIONS);
                    message.setText(text);
                    String json = new Gson().toJson(message);

                    os.println(json);
                    if (listener != null)
                        listener.onMessageReceived(message);
                }
            }
        }).start();

    }

    public void send(final String recipient, final String text){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!closed) {
                    Message message = new Message();
                    message.setGameID(gameID);
                    message.setSender(localUser.getName());
                    message.setDestination(recipient);
                    message.setText(text);
                    String json = new Gson().toJson(message);
                    os.println(json);
                    if (listener != null)
                        listener.onMessageReceived(message);
                }
            }
        }).start();
    }

    public void closeConnection(){
        try {
            os.close();
            is.close();
            clientSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /*
     * Create a thread to read from the server. (non-Javadoc)
     *
     * @see java.lang.Runnable#run()
     */
    public void run() {
    /*
     * Keep on reading from the socket till we receive "Bye" from the
     * server. Once we received that then we want to break.
     */


        String responseMessage;
        try {
            while ((responseMessage = is.readLine()) != null) {
                listener.onMessageReceived(new Gson().fromJson(responseMessage, Message.class));
                Thread.sleep(1000);
            }
            closed = true;
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        } catch (InterruptedException e){
            System.err.println("InterruptedException:  " + e);
        }
    }
}

