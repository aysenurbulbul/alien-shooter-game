package com.ceng453.socketserver.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Match implements Runnable {

    private DataInputStream in;
    private DataOutputStream out;
    private Gamer gamer1;
    private Gamer gamer2;
    private boolean gameOn = true;
    private Double[] gamer1Coords;
    private Double[] gamer2Coords;

    public Match(Gamer gamer1, Gamer gamer2){
        this.gamer1 = gamer1;
        this.gamer2 = gamer2;
    }

    @Override
    public void run() {
        gamer1Coords = new Double[2];
        gamer2Coords = new Double[2];
        try {
            sendUsername(gamer1, gamer2);
            sendUsername(gamer2, gamer1);
            sendHealth(gamer1, gamer2);
            sendHealth(gamer2, gamer1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (gameOn){
            try {
                sendData(gamer1, gamer2, gamer1Coords);
                sendData(gamer2, gamer1, gamer2Coords);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendData(Gamer gamer1, Gamer gamer2, Double[] gamer1Coords) throws IOException {
        in = new DataInputStream(gamer1.getSocket().getInputStream());
        for(int i=0; i<2; i++){
            gamer1Coords[i] = in.readDouble();
        }
        out = new DataOutputStream(gamer2.getSocket().getOutputStream());
        for(int i=0; i<2; i++){
            out.writeDouble(gamer1Coords[i]);
        }
    }

    public void sendUsername(Gamer gamer1, Gamer gamer2) throws IOException {
        in = new DataInputStream(gamer1.getSocket().getInputStream());
        String gamer1Username = in.readUTF();
        out = new DataOutputStream(gamer2.getSocket().getOutputStream());
        out.writeUTF(gamer1Username);
    }

    public void sendHealth(Gamer gamer1, Gamer gamer2) throws IOException {
        in = new DataInputStream(gamer1.getSocket().getInputStream());
        int gamer1Health = in.readInt();
        out = new DataOutputStream(gamer2.getSocket().getOutputStream());
        out.writeInt(gamer1Health);
    }
}
