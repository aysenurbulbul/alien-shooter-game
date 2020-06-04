package com.ceng453.socketserver.server;

import com.ceng453.socketserver.game.Gamer;
import com.ceng453.socketserver.game.Match;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private Queue<Gamer> players;

    /**
     * start server
     * @param port port number
     * @throws IOException IOException for socket
     * @throws InterruptedException InterruptedException for thread
     */
    public void startServer(int port) throws IOException, InterruptedException {
        System.out.println("Starting server....");
        serverSocket = new ServerSocket(port);
        System.out.println("Server started.");
        players = new LinkedBlockingQueue<>();

        while(true){
            socket = serverSocket.accept();
            Gamer gamer = new Gamer(socket);
            players.add(gamer);
            System.out.println("Connection established from " + socket.getInetAddress().getHostName());
            matchMaker();
            System.out.println("Data sent");
        }
    }

    /**
     * creates match based on players on queue
     * @throws InterruptedException InterruptedException for thread
     */
    private void matchMaker() throws InterruptedException {
        while(players.size() > 1){
            Gamer gamer1 = players.poll();
            Gamer gamer2 = players.poll();
            Match match = new Match(gamer1, gamer2);
            Thread matchThread = new Thread(match);
            matchThread.start();
            matchThread.join();
        }
    }
}
