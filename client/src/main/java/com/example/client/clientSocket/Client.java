package com.example.client.clientSocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static com.example.client.constant.GameViewConstants.SOCKET_PORT;

/**
 * Client socket for sendind data to the server socket
 */
public class Client {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Double[] coords;
    private Double[] alienCoords;
    private int isController;

    /**
     * closes sockets and datainput output streams
     * @throws IOException IOException
     */
    public void closeSocket() throws IOException{
        System.out.println("Closing socket");
        in.close();
        out.close();
        socket.close();
    }

    /**
     * connects to server, initialize coordinate arrays
     * @throws IOException IOException
     */
    public void connectToServer() throws IOException {
        coords = new Double[2];
        alienCoords = new Double[2];
        System.out.println("Connecting....");
        socket = new Socket("localhost", SOCKET_PORT);
        System.out.println("Connection success");
    }

    /**
     * sends coordiates of the ship
     * @param x x coordinate of ship
     * @param y y coordinate of ship
     * @throws IOException IOException
     */
    public void sendShipCoords(double x, double y) throws IOException {
        out = new DataOutputStream(socket.getOutputStream());
        out.writeDouble(x);
        out.writeDouble(y);
    }

    /**
     * get coordinates of the player2's ship
     * @return array of coordinates
     * @throws IOException IOException
     */
    public Double[] getShipCoords() throws IOException {
        in = new DataInputStream(socket.getInputStream());
        for(int i=0; i<2; i++){
            coords[i] = in.readDouble();
        }
        return coords;

    }

    /**
     * send username to server
     * @param username player's username
     * @throws IOException IOException
     */
    public void sendUsername(String username) throws IOException {
        out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF(username);
    }

    /**
     * get player2's username
     * @return player2 username
     * @throws IOException IOException
     */
    public String getUsername() throws IOException {
        in = new DataInputStream(socket.getInputStream());
        String username = in.readUTF();
        return username;
    }

    /**
     * send own health
     * @param health own health
     * @throws IOException IOException
     */
    public void sendHealth(int health) throws IOException {
        out = new DataOutputStream(socket.getOutputStream());
        out.writeInt(health);
    }

    /**
     * get player2's health
     * @return player2 health
     * @throws IOException IOException
     */
    public int getHealth() throws IOException {
        in = new DataInputStream(socket.getInputStream());
        int health = in.readInt();
        return health;
    }

    /**
     * get player2's score at the end of the game
     * @return player2 score
     * @throws IOException IOException
     */
    public int getEnemyScore() throws IOException {
        in = new DataInputStream(socket.getInputStream());
        int enemyScore = in.readInt();
        return enemyScore;
    }

    /**
     * send own score to the server at the end of the game
     * @param score final score
     * @throws IOException IOException
     */
    public void sendScore(int score) throws IOException {
        out = new DataOutputStream(socket.getOutputStream());
        out.writeInt(score);
    }

    /**
     * check is socket is open
     * @return status of socket
     */
    public boolean isSocketAlive(){
        return socket.isClosed();
    }

    /**
     * send if alien should move
     * @param status f alien should move
     * @throws IOException IOException
     */
    public void sendAlienMove(boolean status)throws IOException{
        out = new DataOutputStream(socket.getOutputStream());
        out.writeBoolean(status);
    }

    /**
     * get alien's coordinates from server
     * @return get alien's coordinates from server
     * @throws IOException IOException
     */
    public Double[] getAlienCoords() throws IOException {
        in = new DataInputStream(socket.getInputStream());
        for(int i=0; i<2; i++){
            alienCoords[i] = in.readDouble();
        }
        return alienCoords;
    }

    /**
     *
     * @return get if alien should fire a bullet
     * @throws IOException IOException
     */
    public boolean getCanAlienShoot() throws IOException {
        in = new DataInputStream(socket.getInputStream());
        boolean status = in.readBoolean();
        return status;
    }

    /**
     *
     * @param status send if game is on
     * @throws IOException IOException
     */
    public void sendGameStatus(boolean status) throws IOException {
        out = new DataOutputStream(socket.getOutputStream());
        out.writeBoolean(status);
    }

    /**
     *
     * @return get if game is on for other player
     * @throws IOException IOException
     */
    public boolean getGameStatus() throws IOException {
        in = new DataInputStream(socket.getInputStream());
        boolean status = in.readBoolean();
        return status;
    }

}
