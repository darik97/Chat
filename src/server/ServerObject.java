package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.*;

public class ServerObject implements Runnable{
    int port;
    List<Socket> clients;

    public ServerObject(Integer port) {
        this.port = port;
        clients = new ArrayList<>();
    }

    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(port)){
            System.out.println("server.ServerObject is started. Waiting for connections");

            while (true) {
                Socket client = server.accept();
                ClientObject clientObject = new ClientObject(client, this);
                new Thread(clientObject).start();
            }

        }
        catch (IOException e) {
            e.printStackTrace();
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
                e.printStackTrace();
            }
        }
    }

    public static void main (String[] args) {
        try {
            ServerObject serverObject = new ServerObject(3344);
            new Thread(serverObject).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
