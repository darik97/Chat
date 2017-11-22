package server;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class ClientObject implements Runnable {
    Socket client;
    ServerObject server;
    String userName;

    public ClientObject(Socket client, ServerObject server) {
        this.client = client;
        this.server = server;
        server.addClient(client);
    }

    @Override
    public void run() {

        try {
            DataInputStream inputStream = new DataInputStream(client.getInputStream());

            String message = inputStream.readUTF();
            userName = message;

            message += " joined to chat";
            System.out.println(message);
            server.BroadcastMessage(message);

            while (true) {
                try {
                    message = inputStream.readUTF();
                    message = new Date().toString() + " " + userName + " :" + message;
                    System.out.println(message);

                    server.BroadcastMessage(message);
                } catch (IOException e) {
                    message = userName + "left chat";
                    System.out.println(message);
                    server.removeClient(client);

                    server.BroadcastMessage(message);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            server.removeClient(client);
        }

    }
}
