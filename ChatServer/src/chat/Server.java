package chat;

import com.sun.deploy.util.SessionState;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    static final int PORT = 3443;
    private Thread ClientAcceptThread;
    private ClientAccept ClientAccept;
    private ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();

    public Server() {
        Socket clientSocket = null;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Сервер запущен!");
            ClientAccept = new ClientAccept(clients, clientSocket, serverSocket, this);
            ClientAcceptThread = new Thread(ClientAccept);
            ClientAcceptThread.start();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                clientSocket.close();
                System.out.println("Сервер остановлен");
                serverSocket.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public synchronized void sendMessageToAllClients(String msg) {
        ClientAccept.Flag = false;
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
        ClientAccept.Flag = true;

    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }

}
