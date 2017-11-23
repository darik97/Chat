package server;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.*;

public class ClientObject implements Runnable {
    private Socket client;
    private ServerObject server;
    private String userName;

    private static Logger log = Logger.getLogger(ClientObject.class.getName());

    ClientObject(Socket client, ServerObject server) {
        this.client = client;
        this.server = server;
        server.addClient(client);
        log.info("Added new client");
    }

    @Override
    public void run() {

        try {
            DataInputStream inputStream = new DataInputStream(client.getInputStream());
            log.info("Got input stream");

            String message = inputStream.readUTF();
            userName = message;

            message += " joined to chat";
            System.out.println(message);
            server.BroadcastMessage(message);

            while (true) {
                try {
                    message = inputStream.readUTF();
                    log.info("Got new message");
                    message = new Date().toString() + " " + userName + " :" + message;
                    System.out.println(message);

                    server.BroadcastMessage(message);
                } catch (IOException e) {
                    message = userName + " left chat";
                    System.out.println(message);
                    server.removeClient(client);
                    log.info("Client disconnected");

                    server.BroadcastMessage(message);
                    break;
                }
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
        finally {
            server.removeClient(client);
        }
    }
}
