package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.net.ServerSocket;
import java.util.Scanner;

public class ClientAccept implements Runnable {
    private ArrayList<ClientHandler> clients;
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private Server server;
    public boolean Flag;

    public ClientAccept(ArrayList<ClientHandler> clients,  Socket clientSocket, ServerSocket serverSocket, Server server)
    {
        this.clients = clients;
        this.clientSocket = clientSocket;
        this.serverSocket = serverSocket;
        this.server = server;
        Flag = true;
    }

    @Override
    public void run()
    {
        while(true) {
            if (Flag) {
                try {
                    clientSocket = serverSocket.accept();
                    ClientHandler client = new ClientHandler(clientSocket, server);
                    clients.add(client);
                    new Thread(client).start();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }



        }
    }


}
