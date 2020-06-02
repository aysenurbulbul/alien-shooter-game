package com.ceng453.socketserver.game;

import java.net.Socket;

public class Gamer {
    private Socket socket;

    public Gamer(Socket socket){
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }
}
