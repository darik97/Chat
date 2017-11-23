package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

class ClientObject implements Runnable {
    private String host = "127.0.0.1";
    private int port = 3344;
    private Socket client;
    private String name;
    private ClientGUI clientGUI;
    private static Logger log = Logger.getLogger(ClientObject.class.getName());

    ClientObject(ClientGUI clientGUI) {
        this.name = clientGUI.UserName;
        this.clientGUI = clientGUI;
    }

    void sendMessage(String message) {
        DataOutputStream output = null;
        try {
            output = new DataOutputStream(client.getOutputStream());
            output.writeUTF(message);
            output.flush();
        } catch (IOException e) {
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
        log.fine("Message is sent");
    }

    public void run() /*throws IOException, InterruptedException*/ {
        try {
            client = new Socket(host, port);
            log.info("Client socket created");

            DataInputStream inputStream = new DataInputStream(client.getInputStream());
            log.info("Input stream got");

            sendMessage(name);
            String message;

            while (true) {
                message = inputStream.readUTF();
                log.info("New message received");
                clientGUI.printMessage(message);
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
    }

    void disconnect() {
        try {
            client.close();
        } catch (IOException e) {
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
    }
}
