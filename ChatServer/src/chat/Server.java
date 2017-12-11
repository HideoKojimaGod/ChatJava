package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.*;

public class Server {
    private static Logger logger = Logger.getLogger(Server.class.getName());
    static final int PORT = 3443;
    private ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();

    public Server() {
        try
        {
            FileHandler file = new FileHandler("MultiThreadServerSync.txt");
            logger.addHandler(file);
        }
        catch (IOException e)
        {
            logger.log(Level.SEVERE, "Не удалось создать лог.", e);
        }
        Socket clientSocket = null;
        ServerSocket serverSocket = null;
        try 
        {
            serverSocket = new ServerSocket(PORT);
            logger.log(Level.INFO, "Сервер запущен. Используется порт = " + PORT);
            System.out.println("Сервер запущен.");
            while (true) {
                clientSocket = serverSocket.accept();
                ClientHandler client = new ClientHandler(clientSocket, this);
                clients.add(client);
                new Thread(client).start();
            }
        }
        catch (IOException ex) {
            
            ex.printStackTrace();
        }
        finally {
            try {
                clientSocket.close();
                logger.log(Level.INFO, "Сервер успешно остановлен");
                System.out.println("Сервер успешно остановлен");
                serverSocket.close();
            }
            catch (IOException ex) {
                logger.log(Level.WARNING, "Не удалось успешно остановить сервер", e);
                ex.printStackTrace();
            }
        }
    }

    public void sendMessageToAllClients(String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }

    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }

}
