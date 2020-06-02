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

    public void connectToServer() throws IOException {
        coords = new Double[2];
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
}
