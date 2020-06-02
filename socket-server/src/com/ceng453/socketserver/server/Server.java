package com.ceng453.socketserver.server;

import com.ceng453.socketserver.game.Gamer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private Queue<Gamer> players;

    public void startServer(int port) throws IOException {
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

    private void matchMaker() throws IOException {
        while(players.size() > 1){
            Gamer gamer1 = players.poll();
            Gamer gamer2 = players.poll();
            DataOutputStream out1 = new DataOutputStream(gamer1.getSocket().getOutputStream());
            out1.writeUTF("you will be play with 1: " + gamer2.getSocket().getInetAddress());
            DataOutputStream out2 = new DataOutputStream(gamer2.getSocket().getOutputStream());
            out2.writeUTF("you will be play with 2: " + gamer1.getSocket().getInetAddress());
        }
    }
}
