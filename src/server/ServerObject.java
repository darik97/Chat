package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerObject implements Runnable{
    private int port;
    private List<Socket> clients;
    private static Logger log = Logger.getLogger(ServerObject.class.getName());

    private ServerObject(Integer port) {
        this.port = port;
        clients = new ArrayList<>();
    }

    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(port)){
            System.out.println("Server is started. Waiting for connections");
            log.info("Server started");

            while (true) {
                Socket client = server.accept();
                log.info("New connection");
                ClientObject clientObject = new ClientObject(client, this);
                new Thread(clientObject).start();
            }

        }
        catch (IOException e) {
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
    }

    void addClient(Socket newClient) {
        clients.add(newClient);
    }

    void removeClient(Socket client) {
        if (clients.contains(client))
            clients.remove(client);
    }

    void BroadcastMessage(String message) {
        for (Socket client : clients) {
            DataOutputStream outputStream = null;
            try {
                outputStream = new DataOutputStream(client.getOutputStream());
                outputStream.writeUTF(message);
                outputStream.flush();
            } catch (IOException e) {
                log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            }
            log.fine("Message is sent");
        }
    }

    public static void main (String[] args) {
        try {
            ServerObject serverObject = new ServerObject(3344);
            new Thread(serverObject).start();
        } catch (Exception e) {
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
    }
}
