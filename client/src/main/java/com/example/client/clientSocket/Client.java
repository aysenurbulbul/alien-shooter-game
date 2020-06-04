package com.example.client.clientSocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Double[] coords;
    private Double[] alienCoords;
    private int isController;

    public void connectToServer() throws IOException {
        coords = new Double[2];
        alienCoords = new Double[2];
        System.out.println("Connecting....");
        socket = new Socket("localhost", 7777);
        System.out.println("Connection success");
    }

    public void sendShipCoords(double x, double y) throws IOException {
        out = new DataOutputStream(socket.getOutputStream());
        out.writeDouble(x);
        out.writeDouble(y);
    }

    public Double[] getShipCoords() throws IOException {
        in = new DataInputStream(socket.getInputStream());
        for(int i=0; i<2; i++){
            coords[i] = in.readDouble();
        }
        return coords;

    }

    public void sendUsername(String username) throws IOException {
        out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF(username);
    }

    public String getUsername() throws IOException {
        in = new DataInputStream(socket.getInputStream());
        String username = in.readUTF();
        return username;
    }

    public void sendHealth(int health) throws IOException {
        out = new DataOutputStream(socket.getOutputStream());
        out.writeInt(health);
    }

    public int getHealth() throws IOException {
        in = new DataInputStream(socket.getInputStream());
        int health = in.readInt();
        return health;
    }

    public int setController() throws IOException {
        in = new DataInputStream(socket.getInputStream());
        int isController = in.readInt();
        this.isController = isController;
        return isController;
    }

    public int isController(){
        return this.isController;
    }

    public void sendAlienShootRandom(double random)throws IOException{
        out = new DataOutputStream(socket.getOutputStream());
        out.writeDouble(random);
    }

    public void sendAlienMove(boolean status)throws IOException{
        out = new DataOutputStream(socket.getOutputStream());
        out.writeBoolean(status);
    }

    public double getAlienShootRandom() throws IOException{
        in = new DataInputStream(socket.getInputStream());
        double alienShootRandom = in.readDouble();
        return alienShootRandom;
    }

    public Double[] getAlienCoords() throws IOException {
        in = new DataInputStream(socket.getInputStream());
        for(int i=0; i<2; i++){
            alienCoords[i] = in.readDouble();
        }
        return alienCoords;
    }

    public boolean getCanAlienShoot() throws IOException {
        in = new DataInputStream(socket.getInputStream());
        boolean status = in.readBoolean();
        return status;
    }


}
