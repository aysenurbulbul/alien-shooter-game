package com.example.client.clientSocket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private Socket socket;
    private DataInputStream in;

    public String connectToServer() throws IOException {
        System.out.println("Connecting....");
        socket = new Socket("localhost", 7777);
        System.out.println("Connection success");

        in = new DataInputStream(socket.getInputStream());
        System.out.println("Receiving message");
        String info = in.readUTF();
        System.out.println("msg: " + info);
        return info;
    }
}
