package com.ceng453.socketserver.game;

import java.net.Socket;

public class Gamer {
    private Socket socket;
    private Double x;
    private Double y;

    public Gamer(Socket socket){
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setXY(Double x, Double y){
        this.x = x;
        this.y = y;
    }

    public Double[] getXY(){
        Double[] coords = new Double[2];
        coords[0] = x;
        coords[1] = y;
        return coords;
    }
}
