package com.ceng453.socketserver;

import com.ceng453.socketserver.server.Server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.startServer(7777);
    }
}
